package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
@Repository
public class DBMetaLvaDao extends DBBaseDao implements MetaLvaDao {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private static final int MAX_LENGTH_NUMBER=30;
    private static final int MAX_LENGTH_NAME=200;

    @Autowired
    DBLvaDao lvaDao;

    @Override
    public boolean create(MetaLVA toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        } if(toCreate.getNr()!=null && toCreate.getNr().length()>MAX_LENGTH_NUMBER) {
            throw new IOException(ExceptionMessages.tooLongLvaNumber(MAX_LENGTH_NUMBER));
        } if(toCreate.getName()!=null && toCreate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        }

        Semester semester = toCreate.getSemestersOffered();
        if(semester==null) {
            semester=Semester.UNKNOWN;
        }

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        /*
        String stmt = "INSERT INTO MetaLVA (id,lvaNumber,name,semester,type,priority,ects,module) VALUES " +
                "(null,?,?,?,?,?,?,?)";
        */
        //PrivatePreparedCreateStatementCreator psc = new PrivatePreparedCreateStatementCreator(toCreate, semester.ordinal());

        jdbcTemplate.update(new PrivatePreparedCreateStatementCreator(toCreate, semester.ordinal()), keyHolder);

        // toCreate.setId(keyHolder.getKey().intValue());

        Integer id = jdbcTemplate.queryForObject("SELECT id FROM metalva WHERE lvanumber=?", RowMappers.getIntegerRowMapper(), toCreate.getNr());
        toCreate.setId(id);

        return true;
    }

    class PrivatePreparedCreateStatementCreator implements PreparedStatementCreator {
        private MetaLVA e;
        private int semester;

        public PrivatePreparedCreateStatementCreator (MetaLVA e, int semester) {
            this.e = e;
            this.semester = semester;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            String stmt = "INSERT INTO MetaLVA (id,lvaNumber,name,semester,type,priority,ects,module,minTimeEstimate,maxTimeEstimate) VALUES " +
                    "(null,?,?,?,?,?,?,?,?,?)";

            //PreparedStatement ps = jdbcTemplate.getDataSource().getConnection().prepareStatement(stmt);
            PreparedStatement ps = con.prepareStatement(stmt);
            ps.setString(1, e.getNr());
            ps.setString(2, e.getName());
            ps.setInt(3, semester);
            ps.setInt(4, e.getType().ordinal());
            ps.setFloat(5, e.getPriority());
            ps.setFloat(6, e.getECTS());
            ps.setInt(7, e.getModule());
            ps.setObject(8, e.getMinTimeEstimate());
            ps.setObject(9, e.getMaxTimeEstimate());

            /*
            String sql = "INSERT INTO MetaLVA (id,lvaNumber,name,semester,type,priority,ects,module)" +
                    " VALUES \n(null,\n" +
                    "'" + e.getNr() + "',\n" +
                    "'" + e.getName() + "',\n" +
                    "" + semester + ",\n" +
                    "" + e.getType().ordinal() + ",\n" +
                    "" + e.getPriority() + ",\n" +
                    "" + e.getECTS() + ",\n" +
                    "" + e.getModule() + ");";
            logger.info("\n\n" + sql + "\n");
            */
            return ps;
        }
    }

    @Override
    public boolean setPredecessor(int lvaId, int predecessorId) throws DataAccessException {
        String stmt = "INSERT INTO Predecessor (predecessor,successor) VALUES (?,?)";
        jdbcTemplate.update(stmt, predecessorId, lvaId);
        return true;
    }

    @Override
    public boolean unsetPredecessor(int lvaId, int predecessorId) throws DataAccessException {
        String stmt = "DELETE FROM predecessor WHERE predecessor=? AND successor=?";
        jdbcTemplate.update(stmt, predecessorId, lvaId);
        return true;
    }

    @Override
    public MetaLVA readById(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metalva WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
            return null;
        }

        String stmt="SELECT * FROM metalva WHERE id=?";
        MetaLVA result = jdbcTemplate.queryForObject(stmt, RowMappers.getMetaLvaRowMapper(), id);

        result.setLVAs(lvaDao.readByMetaLva(result.getId()));
        result.setPrecursor(readAllPredecessors(result.getId()));

        return result;
    }

    @Override
    public List<MetaLVA> readAllPredecessors(int lvaId) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM predecessor WHERE successor=?", RowMappers.getIntegerRowMapper(), lvaId) == 0){
            return new ArrayList<MetaLVA>();
        }

        String stmt = "SELECT predecessor FROM predecessor WHERE successor=?";
        List<Integer> l = jdbcTemplate.query(stmt, RowMappers.getIntegerRowMapper(), lvaId);
        List<MetaLVA> result = new ArrayList<MetaLVA>();

        for(Integer i : l) {
            result.add(readByIdWithoutLvaAndPrecursor(i));
        }

        return result;
    }

    @Override
    public MetaLVA readByIdWithoutLvaAndPrecursor(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metalva WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
            return null;
        }

        String stmt="SELECT * FROM metalva WHERE id=?";
        return jdbcTemplate.queryForObject(stmt, RowMappers.getMetaLvaRowMapper(), id);
    }

    @Override
    public MetaLVA readByLvaNumber(String lvaNumber) throws DataAccessException {
        if(lvaNumber==null) {
            return null;
        }
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metalva WHERE lvanumber=?", RowMappers.getIntegerRowMapper(), lvaNumber) != 1){
            return null;
        }

        String stmt="SELECT * FROM metalva WHERE lvanumber=?";
        MetaLVA result = jdbcTemplate.queryForObject(stmt, RowMappers.getMetaLvaRowMapper(), lvaNumber);

        result.setLVAs(lvaDao.readByMetaLva(result.getId()));
        result.setPrecursor(readAllPredecessors(result.getId()));

        return result;
    }

    @Override
    public List<MetaLVA> readUncompletedByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws DataAccessException {
        if(!semester.equals(Semester.S) && !semester.equals(Semester.W)) {
            return null;
        }

        List<LVA> lvas = lvaDao.readUncompletedByYearSemesterStudyProgress(year, semester, isInStudyProgress);
        ArrayList<MetaLVA> result = new ArrayList<MetaLVA>(lvas.size());

        for(int i=0; i<lvas.size(); i++) {
            result.add(i, readById(lvas.get(i).getMetaLVA().getId()));
            result.get(i).setLVA(lvas.get(i));
        }

        return result;
    }
    @Override
    public List<MetaLVA> readByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws DataAccessException {
        if(!semester.equals(Semester.S) && !semester.equals(Semester.W)) {
            return null;
        }

        List<LVA> lvas = lvaDao.readByYearSemesterStudyProgress(year, semester, isInStudyProgress);
        ArrayList<MetaLVA> result = new ArrayList<MetaLVA>(lvas.size());

        for(int i=0; i<lvas.size(); i++) {
            result.add(i, readById(lvas.get(i).getMetaLVA().getId()));
            result.get(i).setLVA(lvas.get(i));
        }

        return result;
    }

    /*@Override
    public List<MetaLVA> readNotCompletedByYearSemesterStudProgress(int year, Semester semester, boolean inStudyProgress) throws DataAccessException, NullPointerException {
        if(!semester.equals(Semester.W) && !semester.equals(Semester.S)) {
            return null;
        }

        List<LVA> lva = lvaDao.readNotCompletedByYearSemesterStudyProgress(year, semester, inStudyProgress);

        if (lva.size()==0) {
            return new ArrayList<MetaLVA>();
        }

        ArrayList<MetaLVA> result = new ArrayList<MetaLVA>();

        for(LVA l : lva) {
            MetaLVA m = readById(l.getMetaLVA().getId());
            ArrayList<LVA> a = new ArrayList<LVA>();
            a.add(l);
            m.setLVAs(a);
            result.add(m);
        }

        return result;
    }*/

    @Override
    public boolean update(MetaLVA toUpdate) throws IOException, DataAccessException {
        if(toUpdate == null) {
            return false;
        }
        if(toUpdate.getNr()!=null && toUpdate.getNr().length()>MAX_LENGTH_NUMBER) {
            throw new IOException(ExceptionMessages.tooLongLvaNumber(MAX_LENGTH_NUMBER));
        }
        if(toUpdate.getName()!=null && toUpdate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        }

        String stmt = "SELECT COUNT(*) FROM metalva WHERE id=?";
        if (jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), toUpdate.getId()) == 0) {
            return false;
        }




        String stmtUpdateLvaNumber = "UPDATE metalva SET lvanumber=? WHERE id=?;";
        String stmtUpdateName = "UPDATE metalva SET name=? WHERE id=?";
        String stmtUpdateSemester = "UPDATE metalva SET semester=? WHERE id=?;";
        String stmtUpdateType = "UPDATE metalva SET type=? WHERE id=?;";
        String stmtUpdatePriority = "UPDATE metalva SET priority=? WHERE id=?;";
        String stmtUpdateEcts = "UPDATE metalva SET ects=? WHERE id=?;";
        String stmtUpdateModule= "UPDATE metalva SET module=? WHERE id=?;";
        String stmtUpdateMinTimeEstimate= "UPDATE metalva SET minTimeEstimate=? WHERE id=?;";
        String stmtUpdateMaxTimeEstimate= "UPDATE metalva SET maxTimeEstimate=? WHERE id=?;";

        if(toUpdate.getNr() != null) {
            jdbcTemplate.update(stmtUpdateLvaNumber, toUpdate.getNr(), toUpdate.getId());
        }
        if(toUpdate.getName() != null) {
            jdbcTemplate.update(stmtUpdateName, toUpdate.getName(), toUpdate.getId());
        }
        if(toUpdate.getSemestersOffered() != null) {
            jdbcTemplate.update(stmtUpdateSemester, toUpdate.getSemestersOffered().ordinal(), toUpdate.getId());
        }
        if(toUpdate.getType() != null) {
            jdbcTemplate.update(stmtUpdateType, toUpdate.getType().ordinal(), toUpdate.getId());
        }
        if(toUpdate.getMinTimeEstimate() != null) {
            jdbcTemplate.update(stmtUpdateMinTimeEstimate, toUpdate.getMinTimeEstimate(), toUpdate.getId());
        }
        if(toUpdate.getMaxTimeEstimate() != null) {
            jdbcTemplate.update(stmtUpdateMaxTimeEstimate, toUpdate.getMaxTimeEstimate(), toUpdate.getId());
        }
        jdbcTemplate.update(stmtUpdatePriority, toUpdate.getPriority(), toUpdate.getId());
        jdbcTemplate.update(stmtUpdateEcts, toUpdate.getECTS(), toUpdate.getId());
        jdbcTemplate.update(stmtUpdateModule, toUpdate.getModule(), toUpdate.getId());

        return true;
    }

    @Override
    public List<MetaLVA> readByModule(int module) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM metalva WHERE module=?", RowMappers.getIntegerRowMapper(), module) == 0){
            return new ArrayList<MetaLVA>();
        }

        String stmt="SELECT * FROM metalva WHERE module=?";
        List<MetaLVA> result = jdbcTemplate.query(stmt, RowMappers.getMetaLvaRowMapper(), module);

        for(MetaLVA r : result) {
            r.setLVAs(lvaDao.readByMetaLva(r.getId()));
            r.setPrecursor(readAllPredecessors(r.getId()));
        }

        return result;
    }

    @Override
    public List<MetaLVA> readAll() throws DataAccessException {
        String stmt = "SELECT * FROM metalva";
        List<MetaLVA> result = jdbcTemplate.query(stmt, RowMappers.getMetaLvaRowMapper());

        if(result.size()==0) {
            return new ArrayList<MetaLVA>();
        }

        for(int i=0; i<result.size(); i++) {
            result.set(i, this.readById(result.get(i).getId()));
        }

        return result;
    }
}
