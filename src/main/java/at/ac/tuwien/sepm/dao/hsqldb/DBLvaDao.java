package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;
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

@Repository
public class DBLvaDao extends DBBaseDao implements LvaDao {
    private static final int MAX_LENGTH_DESCRIPTION=500;

    @Autowired
    DBLvaDateDao lvaDateDao;// = new DBLvaDateDao();

    @Autowired
    DBMetaLvaDao metaLvaDao;// = new DBMetaLvaDao();

    @Override
    public boolean create(LVA toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        }
        if(toCreate.getDescription()!=null && toCreate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }

        /*
        String stmt = "INSERT INTO LVA (id,metaLva,year,isWinterSemester,description,grade,inStudyProgress) " +
                "VALUES (null,?,?,?,?,?,?);";
        */

        boolean semester=true;
        if(toCreate.getSemester().equals(Semester.S)) {
            semester=false;
        }
        /*
        jdbcTemplate.update(stmt, toCreate.getMetaLVA().getId(), toCreate.getYear(), semester, toCreate.getDescription(),
                toCreate.getGrade(), toCreate.isInStudyProgress());
        */
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PrivatePreparedCreateStatementCreator(toCreate, semester), keyHolder);

        // TODO use the key holder
        // toCreate.setId(keyHolder.getKey().intValue());

        Integer id = jdbcTemplate.queryForObject("SELECT id FROM lva WHERE metalva=? AND year=? AND iswintersemester=?", RowMappers.getIntegerRowMapper(), toCreate.getMetaLVA().getId(), toCreate.getYear(), semester);
        toCreate.setId(id);

        return true;
    }

    class PrivatePreparedCreateStatementCreator implements PreparedStatementCreator {
        private LVA e;
        private boolean semester;

        public PrivatePreparedCreateStatementCreator (LVA e, boolean semester) {
            this.e = e;
            this.semester = semester;
        }

        @Override
        public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
            String stmt = "INSERT INTO LVA (id,metaLva,year,isWinterSemester,description,grade,inStudyProgress) " +
                    "VALUES (null,?,?,?,?,?,?);";

            PreparedStatement ps = con.prepareStatement(stmt);
            ps.setInt(1, e.getMetaLVA().getId());
            ps.setInt(2, e.getYear());
            ps.setBoolean(3, semester);
            ps.setString(4, e.getDescription());
            ps.setInt(5, e.getGrade());
            ps.setBoolean(6, e.isInStudyProgress());

            return ps;
        }
    }

    @Override
    public LVA readById(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lva WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
            return null;
        }

        String stmt = "SELECT * FROM lva WHERE ID=?";
        LVA result = jdbcTemplate.queryForObject(stmt, RowMappers.getLvaRowMapper(), id);

        result.setMetaLVA(metaLvaDao.readByIdWithoutLvaAndPrecursor(result.getMetaLVA().getId()));

        /*
        ArrayList<TimeFrame> times = new ArrayList<TimeFrame>();
        ArrayList<String> rooms = new ArrayList<String>();

        ArrayList<TimeFrame> timesUE = new ArrayList<TimeFrame>();
        ArrayList<String> roomsUE =  new ArrayList<String>();

        ArrayList<TimeFrame> timesExam = new ArrayList<TimeFrame>();
        ArrayList<String> roomsExam = new ArrayList<String>();
        ArrayList<Integer> examGrade = new ArrayList<Integer>();

        for(LvaDate l : lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.LECTURE)) {
            times.add(l.getTime());
            rooms.add(l.getRoom());
        }

        for(LvaDate l : lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.EXERCISE)) {
            timesUE.add(l.getTime());
            roomsUE.add(l.getRoom());
        }

        for(LvaDate l : lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.EXAM)) {
            timesExam.add(l.getTime());
            roomsExam.add(l.getRoom());
            examGrade.add(l.getResult());
        }

        result.setLectures(times);
        result.setRooms(rooms);
        result.setExercises(timesUE);
        result.setRoomsUE(roomsUE);
        result.setExams(timesExam);
        result.setRoomsExam(roomsExam);
        result.setExamGrade(examGrade);
        */
        result.setLectures(lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.LECTURE));
        result.setExams(lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.EXAM));
        result.setExercises(lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.EXERCISE));
        result.setDeadlines(lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.DEADLINE));

        return result;
    }

    @Override
    public List<LVA> readByMetaLva(int metaLvaId) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lva WHERE metalva=?", RowMappers.getIntegerRowMapper(), metaLvaId) == 0){
            return new ArrayList<LVA>();
        }

        String stmt = "SELECT * FROM lva WHERE metalva=?";
        List<LVA> result = jdbcTemplate.query(stmt, RowMappers.getLvaRowMapper(), metaLvaId);

        for(int i=0; i<result.size(); i++) {
            result.set(i, readById(result.get(i).getId()));
        }

        return result;
    }

    @Override
    public List<LVA> readUncompletedByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws DataAccessException {
        boolean s=true;
        if(semester.equals(Semester.S)) {
            s=false;
        } else if(!semester.equals(Semester.W)) {
            return null;
        }

        String stmtCount = "SELECT COUNT(*) FROM lva WHERE " +
                "metalva NOT IN (SELECT metalva FROM lva WHERE grade BETWEEN 1 AND 4) " +
                "AND year=? " +
                "AND iswintersemester=? " +
                "AND instudyprogress=?";
        if(jdbcTemplate.queryForObject(stmtCount, RowMappers.getIntegerRowMapper(), year, s, isInStudyProgress) == 0) {
            return new ArrayList<LVA>();
        }

        String stmt = "SELECT * FROM lva WHERE " +
                "metalva NOT IN (SELECT metalva FROM lva WHERE grade BETWEEN 1 AND 4) " +
                "AND year=? " +
                "AND iswintersemester=? " +
                "AND instudyprogress=?";
        List<LVA> result = jdbcTemplate.query(stmt, RowMappers.getLvaRowMapper(), year, s, isInStudyProgress);

        for(int i=0; i<result.size(); i++) {
            result.set(i, readById(result.get(i).getId()));
        }

        return result;
    }
    @Override
    public List<LVA> readByYearSemesterStudyProgress(int year, Semester semester, boolean isInStudyProgress) throws DataAccessException {
        boolean s=true;
        if(semester.equals(Semester.S)) {
            s=false;
        } else if(!semester.equals(Semester.W)) {
            return null;
        }

        String stmtCount = "SELECT COUNT(*) FROM lva WHERE " +
                "year=? " +
                "AND iswintersemester=? " +
                "AND instudyprogress=?";
        if(jdbcTemplate.queryForObject(stmtCount, RowMappers.getIntegerRowMapper(), year, s, isInStudyProgress) == 0) {
            return new ArrayList<LVA>();
        }

        String stmt = "SELECT * FROM lva WHERE " +
                "year=? " +
                "AND iswintersemester=? " +
                "AND instudyprogress=?";
        List<LVA> result = jdbcTemplate.query(stmt, RowMappers.getLvaRowMapper(), year, s, isInStudyProgress);

        for(int i=0; i<result.size(); i++) {
            result.set(i, readById(result.get(i).getId()));
        }

        return result;
    }
    @Override
    public LVA readByIdWithoutLvaDates(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM date WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
            return null;
        }

        String stmt = "SELECT * FROM lva WHERE ID=?";
        return jdbcTemplate.queryForObject(stmt, RowMappers.getLvaRowMapper(), id);
    }

    @Override
    public List<LVA> readByYearAndSemester(int year, boolean isWinterSemester) throws DataAccessException {
        String stmt = "SELECT * FROM lva WHERE year=? AND iswintersemester=?";
        List<LVA> result = jdbcTemplate.query(stmt, RowMappers.getLvaRowMapper(), year, isWinterSemester);

        if(result.size()==0) {
            return new ArrayList<LVA>();
        }

        for(int i=0; i<result.size(); i++) {
            result.set(i, this.readById(result.get(i).getId()));
        }

        return result;
    }

    /*@Override
    public List<LVA> readNotCompletedByYearSemesterStudyProgress(int year, Semester semester, boolean inStudyProgress) throws DataAccessException, NullPointerException {
        boolean s = true;
        if(semester.equals(Semester.S)) {
            s = false;
        } else if(!semester.equals(Semester.W))  {
            return null;
        }

        String stmt = "SELECT * FROM lva WHERE year=? AND iswintersemester=? AND instudyprogress=? AND grade IN (0,5)";
        List<LVA> result = jdbcTemplate.query(stmt, RowMappers.getLvaRowMapper(), year, s, inStudyProgress);

        if(result.size()==0) {
            return new ArrayList<LVA>();
        }

        for(int i=0; i<result.size(); i++) {
            result.set(i, this.readById(result.get(i).getId()));
        }

        return result;
    }*/

    @Override
    public boolean update(LVA toUpdate) throws IOException, DataAccessException {
        if(toUpdate == null) {
            return false;
        }
        if(toUpdate.getDescription()!=null && toUpdate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }

        String stmt = "SELECT COUNT(*) FROM lva WHERE id=?";
        if (jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), toUpdate.getId()) == 0) {
            return false;
        }

        String stmtUpdateMetaLva = "UPDATE lva SET metalva=? WHERE id=?";
        String stmtUpdateYear = "UPDATE lva SET year=? WHERE id=?";
        String stmtUpdateIsWinterSemester = "UPDATE lva SET iswintersemester=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE lva SET description=? WHERE id=?";
        String stmtUpdateGrade = "UPDATE lva SET grade=? WHERE id=?";
        String stmtUpdateInStudyProgress = "UPDATE lva SET instudyprogress=? WHERE id=?";

        if (toUpdate.getMetaLVA() != null && toUpdate.getMetaLVA().getId() != null) {
            jdbcTemplate.update(stmtUpdateMetaLva, toUpdate.getMetaLVA().getId(), toUpdate.getId());
        } if (toUpdate.getSemester() != null) {
            if(toUpdate.getSemester().equals(Semester.S)) {
                jdbcTemplate.update(stmtUpdateIsWinterSemester, false, toUpdate.getId());
            } else {
                jdbcTemplate.update(stmtUpdateIsWinterSemester, true, toUpdate.getId());
            }
        } if (toUpdate.getDescription() != null) {
            jdbcTemplate.update(stmtUpdateDescription, toUpdate.getDescription(), toUpdate.getId());
        }
        jdbcTemplate.update(stmtUpdateYear, toUpdate.getYear(), toUpdate.getId());
        jdbcTemplate.update(stmtUpdateGrade, toUpdate.getGrade(), toUpdate.getId());
        jdbcTemplate.update(stmtUpdateInStudyProgress, toUpdate.isInStudyProgress(), toUpdate.getId());

        return true;
    }

    @Override
    public List<LVA> readAll() throws DataAccessException {
        String stmt = "SELECT * FROM lva";
        List<LVA> result = jdbcTemplate.query(stmt, RowMappers.getLvaRowMapper());

        if(result.size()==0) {
            return new ArrayList<LVA>();
        }

        for(int i=0; i<result.size(); i++) {
            result.set(i, this.readById(result.get(i).getId()));
        }

        return result;
    }
}