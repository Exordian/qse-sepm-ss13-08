package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import sun.util.logging.PlatformLogger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DBLvaDao extends DBBaseDao implements LvaDao {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private static final int MAX_LENGTH_DESCRIPTION=5000;
    private static final int MAX_LENGTH_ADDITIONAL_DESCRIPTIONS=5000;
    private static final int MAX_LENGTH_INSTITUTE=5000;
    private static final int MAX_LENGTH_LANGUAGE=100;

    @Autowired
    DBLvaDateDao lvaDateDao;

    @Autowired
    DBMetaLvaDao metaLvaDao;

    @Override
    public boolean create(LVA toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        }
        /*
        if(toCreate.getDescription()!=null && toCreate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }
        if(toCreate.getGoals()!=null && toCreate.getGoals().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongGoals(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toCreate.getContent()!=null && toCreate.getContent().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongContent(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toCreate.getAdditionalInfo1()!=null && toCreate.getAdditionalInfo1().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongAdditionalinfo1(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toCreate.getAdditionalInfo2()!=null && toCreate.getAdditionalInfo2().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongAdditionalinfo2(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toCreate.getInstitute()!=null && toCreate.getInstitute().length()>MAX_LENGTH_INSTITUTE) {
            throw new IOException(ExceptionMessages.tooLongInsitute(MAX_LENGTH_INSTITUTE));
        }
        if(toCreate.getLanguage()!=null && toCreate.getLanguage().length()>MAX_LENGTH_LANGUAGE) {
            throw new IOException(ExceptionMessages.tooLongLanguage(MAX_LENGTH_LANGUAGE));
        }
        */
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
        logger.info(toCreate.toString());
        logger.info("bevore jdbcTemplate");
        jdbcTemplate.update(new PrivatePreparedCreateStatementCreator(toCreate, semester), keyHolder);
        logger.info("after jdbcTemplate");

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
            String stmt = "INSERT INTO LVA (id,metaLva,year,isWinterSemester,description,goals,content,additionalinfo1," +
                    "additionalinfo2,institute,language,grade,inStudyProgress) VALUES (null,?,?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement ps = con.prepareStatement(stmt);

            ps.setInt(1, e.getMetaLVA().getId());
            ps.setInt(2, e.getYear());
            ps.setBoolean(3, semester);
            ps.setString(4, e.getDescription());
            ps.setString(5, e.getGoals());
            ps.setString(6, e.getContent());
            ps.setString(7, e.getAdditionalInfo1());
            ps.setString(8, e.getAdditionalInfo2());
            ps.setString(9, e.getInstitute());
            ps.setString(10, e.getLanguage());
            ps.setInt(11, e.getGrade());
            ps.setBoolean(12, e.isInStudyProgress());

            /*
            String s1 = setNullToEmptyString(e.getDescription());
            String s2 = setNullToEmptyString(e.getGoals());
            String s3 = setNullToEmptyString(e.getContent());
            s3 = "";
            String s4 = setNullToEmptyString(e.getAdditionalInfo1());
            String s5 = setNullToEmptyString(e.getAdditionalInfo2());
            String s6 = setNullToEmptyString(e.getInstitute());
            String s7 = setNullToEmptyString(e.getLanguage());


            PreparedStatement ps = con.prepareStatement(stmt);
            //logger.info("meta lva will be set: " + e.getMetaLVA());
            ps.setInt(1, e.getMetaLVA().getId());

            //logger.info("year will be set: " + e.getYear());
            ps.setInt(2, e.getYear());

            //logger.info("semester will be set: " + semester);
            ps.setBoolean(3, semester);

            //logger.info("description will be set: " + s1.length() + "\t" + s1);
            ps.setString(4, s1);

            //logger.info("goeals will be set: " + s2.length() + "\t" + s2);
            ps.setString(5, s2);

            //logger.info("content will be set: " + s3.length() + "\t" + s3);
            ps.setString(6, s3);

            //logger.info("additional info 1 will be set: " + s4.length() + "\t" + s4);
            ps.setString(7, s4);

            //logger.info("additional info 2 will be set: " + s5.length() + "\t" + s5);
            ps.setString(8, s5);

            //logger.info("institute will be set: " + s6.length() + "\t" + s6);
            ps.setString(9, s6);

            //logger.info("language will be set: " + s7.length() + "\t" + s7);
            ps.setString(10, s7);

            //logger.info("grade will be set: " + e.getGrade());
            ps.setInt(11, e.getGrade());

            //logger.info("in study progress will be set: " + e.isInStudyProgress());
            ps.setBoolean(12, e.isInStudyProgress());

            //logger.info("all attributes set");
            */
            /*
            String sql = "INSERT INTO LVA (id,metaLva,year,isWinterSemester,description,goals,content,additionalinfo1," +
            "additionalinfo2,institute,language,grade,inStudyProgress)" +
                    " VALUES \n(null,\n" +
                    "" + e.getMetaLVA().getId() + ",\n" +
                    "" + e.getYear() + ",\n" +
                    "" + semester + ",\n" +
                    "'" + s1 + "',\n" +
                    "'" + s2 + "',\n" +
                    "'" + s3 + "',\n" +
                    "'" + s4 + "',\n" +
                    "'" + s5 + "',\n" +
                    "'" + s6 + "',\n" +
                    "'" + s7 + "',\n" +
                    "" + e.getGrade() + ",\n" +
                    "" + e.isInStudyProgress() + ");";
            logger.info("\n\n" + sql + "\n");
            */

            return ps;
        }
    }

    private String setNullToEmptyString(String s) {
        if (s == null)
                return "";
        return s;
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
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lva WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
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
        /*
        if(toUpdate.getDescription()!=null && toUpdate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }
        if(toUpdate.getGoals()!=null && toUpdate.getGoals().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongGoals(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toUpdate.getContent()!=null && toUpdate.getContent().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongContent(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toUpdate.getAdditionalInfo1()!=null && toUpdate.getAdditionalInfo1().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongAdditionalinfo1(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toUpdate.getAdditionalInfo2()!=null && toUpdate.getAdditionalInfo2().length()>MAX_LENGTH_ADDITIONAL_DESCRIPTIONS) {
            throw new IOException(ExceptionMessages.tooLongAdditionalinfo2(MAX_LENGTH_ADDITIONAL_DESCRIPTIONS));
        }
        if(toUpdate.getInstitute()!=null && toUpdate.getInstitute().length()>MAX_LENGTH_INSTITUTE) {
            throw new IOException(ExceptionMessages.tooLongInsitute(MAX_LENGTH_INSTITUTE));
        }
        if(toUpdate.getLanguage()!=null && toUpdate.getLanguage().length()>MAX_LENGTH_LANGUAGE) {
            throw new IOException(ExceptionMessages.tooLongLanguage(MAX_LENGTH_LANGUAGE));
        }

        String stmt = "SELECT COUNT(*) FROM lva WHERE id=?";
        if (jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), toUpdate.getId()) == 0) {
            return false;
        }
        */
        String stmtUpdateMetaLva = "UPDATE lva SET metalva=? WHERE id=?";
        String stmtUpdateYear = "UPDATE lva SET year=? WHERE id=?";
        String stmtUpdateIsWinterSemester = "UPDATE lva SET iswintersemester=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE lva SET description=? WHERE id=?";
        String stmtUpdateGoals = "UPDATE lva SET goals=? WHERE id=?";
        String stmtUpdateContent = "UPDATE lva SET content=? WHERE id=?";
        String stmtUpdateAdditionalinfo1 = "UPDATE lva SET additionalinfo1=? WHERE id=?";
        String stmtUpdateAdditionalinfo2 = "UPDATE lva SET additionalinfo2=? WHERE id=?";
        String stmtUpdateInsitute = "UPDATE lva SET institute=? WHERE id=?";
        String stmtUpdateLanguage = "UPDATE lva SET language=? WHERE id=?";
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
        } if (toUpdate.getGoals() != null) {
            jdbcTemplate.update(stmtUpdateGoals, toUpdate.getGoals(), toUpdate.getId());
        } if (toUpdate.getContent() != null) {
            jdbcTemplate.update(stmtUpdateContent, toUpdate.getContent(), toUpdate.getId());
        } if (toUpdate.getAdditionalInfo1() != null) {
            jdbcTemplate.update(stmtUpdateAdditionalinfo1, toUpdate.getAdditionalInfo1(), toUpdate.getId());
        } if (toUpdate.getAdditionalInfo2() != null) {
            jdbcTemplate.update(stmtUpdateAdditionalinfo2, toUpdate.getAdditionalInfo2(), toUpdate.getId());
        } if (toUpdate.getInstitute() != null) {
            jdbcTemplate.update(stmtUpdateInsitute, toUpdate.getInstitute(), toUpdate.getId());
        } if (toUpdate.getLanguage() != null) {
            jdbcTemplate.update(stmtUpdateLanguage, toUpdate.getLanguage(), toUpdate.getId());
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