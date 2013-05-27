package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
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

        String stmt = "INSERT INTO LVA (id,metaLva,year,isWinterSemester,description,grade,inStudyProgress) " +
                "VALUES (null,?,?,?,?,?,?);";

        boolean semester=true;
        if(toCreate.getSemester().equals(Semester.S)) {
            semester=false;
        }

        jdbcTemplate.update(stmt, toCreate.getMetaLVA().getId(), toCreate.getYear(), semester, toCreate.getDescription(),
                toCreate.getGrade(), toCreate.isInStudyProgress());

        return true;
    }

    @Override
    public LVA readById(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lva WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
            return null;
        }

        String stmt = "SELECT * FROM lva WHERE ID=?";
        LVA result = jdbcTemplate.queryForObject(stmt, RowMappers.getLvaRowMapper(), id);

        result.setMetaLVA(metaLvaDao.readByIdWithoutLvaAndPrecursor(result.getMetaLVA().getId()));

        ArrayList<TimeFrame> times = new ArrayList<TimeFrame>();
        ArrayList<String> rooms = new ArrayList<String>();

        ArrayList<TimeFrame> timesUE = new ArrayList<TimeFrame>();
        ArrayList<String> roomsUE =  new ArrayList<String>();

        ArrayList<TimeFrame> timesExam = new ArrayList<TimeFrame>();
        ArrayList<String> roomsExam = new ArrayList<String>();
        ArrayList<Integer> examGrade = new ArrayList<Integer>();

        for(LvaDate l : lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.LECTURE)) {
            times.add(new TimeFrame(l.getStart(), l.getStop()));
            rooms.add(l.getRoom());
        }

        for(LvaDate l : lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.EXERCISE)) {
            timesUE.add(new TimeFrame(l.getStart(), l.getStop()));
            roomsUE.add(l.getRoom());
        }

        for(LvaDate l : lvaDateDao.readByLvaAndType(result.getId(), LvaDateType.EXAM)) {
            timesExam.add(new TimeFrame(l.getStart(), l.getStop()));
            roomsExam.add(l.getRoom());
            examGrade.add(l.getResult());
        }

        result.setTimes(times);
        result.setRooms(rooms);
        result.setTimesUE(timesUE);
        result.setRoomsUE(roomsUE);
        result.setTimesExam(timesExam);
        result.setRoomsExam(roomsExam);
        result.setExamGrade(examGrade);

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

    @Override
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
    }

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

        try {
            jdbcTemplate.execute("SET AUTOCOMMIT FALSE");
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
            jdbcTemplate.execute("COMMIT");
            jdbcTemplate.execute("SET AUTOCOMMIT TRUE");
        } catch (DataAccessException e) {
            jdbcTemplate.execute("ROLLBACK;");
            jdbcTemplate.execute("SET AUTOCOMMIT TRUE");
            throw e;
        }
        return true;
    }
}
