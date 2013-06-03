package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.Semester;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
@Repository
public class DBDateDao extends DBBaseDao implements DateDao {

    private static final int MAX_LENGTH_NAME=200;
    private static final int MAX_LENGTH_DESCRIPTION=200;

    @Override
    public boolean create(DateEntity toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        }
        if(toCreate.getName()!=null && toCreate.getName().length()>MAX_LENGTH_NAME){
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        }
        if(toCreate.getDescription()!=null && toCreate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }

        String stmt = "INSERT INTO Date (id,tag,name,description,isIntersectable,start,stop) VALUES (null,null,?,?,?,?,?)";
        Object[] args = new Object[]{toCreate.getName(), toCreate.getDescription(), (toCreate.getIntersectable() != null ? toCreate.getIntersectable() : false), new Timestamp(toCreate.getTime().from().getMillis()), new Timestamp(toCreate.getTime().to().getMillis())};
        jdbcTemplate.update(stmt, args);
        return true;
    }

    @Override
    public DateEntity readById(int id) throws DataAccessException {
        String stmt = "SELECT * FROM date WHERE ID=?";

        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM date WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
            return null;
        }

        return jdbcTemplate.queryForObject(stmt, RowMappers.getDateRowMapper(), id);
    }

    @Override
    public List<DateEntity> readInTimeframe(DateTime from, DateTime till) throws DataAccessException {
        String stmt = " SELECT * FROM date WHERE start>=? AND start<=? ORDER BY start";
        Object[] args = new Object[]{new Timestamp(from.getMillis()), new Timestamp(till.getMillis())};
        return jdbcTemplate.query(stmt, RowMappers.getDateRowMapper(), args);
    }

    @Override
    public List<DateEntity> readByDay(DateTime date) throws DataAccessException {
        String stmt = "SELECT * FROM date WHERE " +
                "(start>=? AND start<=?) OR " +
                "(stop>=? AND stop<=?) OR " +
                "(start<=? AND stop>=?)" +
                "ORDER BY start";

        String stmtCount = "SELECT COUNT(*) FROM date WHERE " +
                "(start>=? AND start<=?) OR " +
                "(stop>=? AND stop<=?) OR " +
                "(start<=? AND stop>=?)";
        Timestamp s1 = new Timestamp((new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0, 0, 0).getMillis()));
        Timestamp s2 = new Timestamp((new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 23, 59, 59, 999).getMillis()));

        if(jdbcTemplate.queryForObject(stmtCount, RowMappers.getIntegerRowMapper(), s1, s2, s1, s2, s1, s2) == 0){
            return new ArrayList<DateEntity>();
        }

        return jdbcTemplate.query(stmt, RowMappers.getDateRowMapper(), s1, s2, s1, s2, s1, s2);
    }

    @Override
    public LVA readNotToIntersectByYearSemester(int year, Semester semester) throws DataAccessException, NullPointerException {
        int monthStart=3;
        int monthEnd=6;
        int dayEnd=30;
        int yearEnd=year;
        if(semester.equals(Semester.W)) {
            monthStart=10;
            monthEnd=1;
            dayEnd=31;
            yearEnd++;
        } else if(!semester.equals(Semester.S)) {
            return null;
        }

        DateTime start = new DateTime(year, monthStart, 1, 0, 0, 0, 0);
        DateTime stop = new DateTime(yearEnd, monthEnd, dayEnd, 23, 59, 59, 999);

        String stmt = " SELECT * FROM date WHERE isintersectable=FALSE AND start>=? AND start<=? ORDER BY start";
        List<DateEntity> dates = jdbcTemplate.query(stmt, RowMappers.getDateRowMapper(), new Timestamp(start.getMillis()), new Timestamp(stop.getMillis()));

        LVA result = new LVA();
        ArrayList<LvaDate> lvaDates = new ArrayList<LvaDate>();
        for(DateEntity d : dates) {
            LvaDate l = new LvaDate();
            l.setTime(d.getTime());
            lvaDates.add(l);
        }
        result.setLectures(lvaDates);

        /*
        ArrayList<TimeFrame> times = new ArrayList<TimeFrame>();
        for(DateEntity d : dates) {
            times.add(d.getTime());
        }
        result.setLectures(times);
        */
        result.setYear(year);
        result.setSemester(semester);
        return result;
    }

    @Override
    public boolean update(DateEntity toUpdate) throws IOException, DataAccessException {
        if(toUpdate == null) {
            return false;
        }
        if(toUpdate.getName()!=null && toUpdate.getName().length()>MAX_LENGTH_NAME){
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        }
        if(toUpdate.getDescription()!=null && toUpdate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }

        String stmt = "SELECT COUNT(*) FROM date WHERE id=?";
        if (jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), toUpdate.getId()) == 0) {
            return false;
        }

        String stmtUpdateName = "UPDATE date SET name=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE date SET description=? WHERE id=?";
        String stmtUpdateIsIntersectable = "UPDATE date SET isintersectable=? WHERE id=?";
        String stmtUpdateStart = "UPDATE date SET start=? WHERE id=?";
        String stmtUpdateStop = "UPDATE date SET stop=? WHERE id=?";

        if (toUpdate.getName() != null) {
            jdbcTemplate.update(stmtUpdateName, toUpdate.getName(), toUpdate.getId());
        }
        if (toUpdate.getDescription() != null) {
            jdbcTemplate.update(stmtUpdateDescription, toUpdate.getDescription(), toUpdate.getId());
        }
        if (toUpdate.getIntersectable() != null) {
            jdbcTemplate.update(stmtUpdateIsIntersectable, toUpdate.getIntersectable(), toUpdate.getId());
        }
        if (toUpdate.getTime() != null && toUpdate.getTime().from() != null) {
            jdbcTemplate.update(stmtUpdateStart, new Timestamp(toUpdate.getTime().from().getMillis()), toUpdate.getId());
        }
        if (toUpdate.getTime() != null && toUpdate.getTime().to() != null) {
            jdbcTemplate.update(stmtUpdateStop,new Timestamp(toUpdate.getTime().to().getMillis()), toUpdate.getId());
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        String stmtCount = "SELECT COUNT(*) FROM date WHERE id=?";
        if (jdbcTemplate.queryForObject(stmtCount, RowMappers.getIntegerRowMapper(), id) == 0) {
            return false;
        }

        jdbcTemplate.update("DELETE FROM date WHERE id=?;", new Integer(id));

        return true;
    }
}
