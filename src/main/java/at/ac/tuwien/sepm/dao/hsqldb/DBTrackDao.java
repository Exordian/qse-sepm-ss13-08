package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.TrackDao;
import at.ac.tuwien.sepm.entity.Track;
import at.ac.tuwien.sepm.service.Semester;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DBTrackDao extends DBBaseDao implements TrackDao {
    private static final int MAX_LENGTH_NAME=200;
    private static final int MAX_LENGTH_DESCRIPTION=500;

    @Autowired
    DBLvaDao lvaDao;

    @Override
    public boolean create(Track toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        } if(toCreate.getName()!=null && toCreate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        } if(toCreate.getDescription()!=null && toCreate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }

        Integer lva=null;
        if(toCreate.getLva()!=null) {
            lva=toCreate.getLva().getId();
        }

        String stmt = "INSERT INTO track (id,lva,name,description,start,stop) VALUES (null,?,?,?,?,?);";
        jdbcTemplate.update(stmt, lva, toCreate.getName(), toCreate.getDescription(),
                new Timestamp(toCreate.getStart().getMillis()), new Timestamp(toCreate.getStop().getMillis()));

        return true;
    }

    @Override
    public Track readById(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM track WHERE id=?", RowMappers.getIntegerRowMapper(), id) == 0){
            return null;
        }

        String stmt="SELECT * FROM track WHERE id=?;";
        Track result = jdbcTemplate.queryForObject(stmt, RowMappers.getTrackRowMapper(), id);

        result.setLva(lvaDao.readByIdWithoutLvaDates(result.getLva().getId()));

        return result;
    }

    @Override
    public List<Track> readByLva(int lva) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM track WHERE lva=?", RowMappers.getIntegerRowMapper(), lva) == 0){
            return new ArrayList<Track>();
        }

        String stmt="SELECT * FROM track WHERE lva=? ORDER BY start ASC;";
        List<Track> result = jdbcTemplate.query(stmt, RowMappers.getTrackRowMapper(), lva);

        for(int i=0; i<result.size(); i++) {
            result.set(i, readById(result.get(i).getId()));
        }

        return result;
    }


    // //SELECT * from track where lva in (SELECT id FROM lva where year=2013 and iswinterSemester=false)
    @Override
    public List<Track> readBySemester(int year, Semester semester) throws DataAccessException {
        if(semester==null || !(semester.equals(Semester.S) || semester.equals(Semester.W))) {
            return null;
        }

        boolean s = false;
        if(semester.equals(Semester.W)) {
            s=true;
        }

        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM track WHERE lva IN (SELECT id FROM lva WHERE year=? AND iswintersemester=?)", RowMappers.getIntegerRowMapper(), year, s) == 0){
            return new ArrayList<Track>();
        }

        String stmt="SELECT * from track where lva in (SELECT id FROM lva where year=? and iswintersemester=?) ORDER BY start ASC";
        List<Track> result = jdbcTemplate.query(stmt, RowMappers.getTrackRowMapper(), year, s);

        for(int i=0; i<result.size(); i++) {
            result.set(i, readById(result.get(i).getId()));
        }

        return result;
    }

    @Override
    public boolean update(Track toUpdate) throws IOException, DataAccessException {
        if(toUpdate == null) {
            return false;
        } if(toUpdate.getName()!=null && toUpdate.getName().length()>MAX_LENGTH_NAME) {
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        } if(toUpdate.getDescription()!=null && toUpdate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }

        String stmt = "SELECT COUNT(*) FROM track WHERE id=?";
        if(jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), toUpdate.getId()) == 0) {
            return false;
        }

        String stmtUpdateLva = "UPDATE track SET lva=? WHERE id=?";
        String stmtUpdateName = "UPDATE track SET name=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE track SET description=? WHERE id=?";
        String stmtUpdateStart = "UPDATE track SET start=? WHERE id=?";
        String stmtUpdateStop = "UPDATE track SET stop=? WHERE id=?";

        if(toUpdate.getLva() != null) {
            jdbcTemplate.update(stmtUpdateLva, toUpdate.getLva().getId(), toUpdate.getId());
        }
        if(toUpdate.getName() != null) {
            jdbcTemplate.update(stmtUpdateName, toUpdate.getName(), toUpdate.getId());
        }
        if(toUpdate.getDescription() != null) {
            jdbcTemplate.update(stmtUpdateDescription, toUpdate.getDescription(), toUpdate.getId());
        }
        if(toUpdate.getStart() != null) {
            jdbcTemplate.update(stmtUpdateStart, new Timestamp(toUpdate.getStart().getMillis()), toUpdate.getId());
        }
        if(toUpdate.getStop() != null) {
            jdbcTemplate.update(stmtUpdateStop, new Timestamp(toUpdate.getStop().getMillis()), toUpdate.getId());
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM track WHERE id=?", RowMappers.getIntegerRowMapper(), id) == 0){
            return false;
        }

        String stmt="DELETE FROM track WHERE id=?";
        jdbcTemplate.update(stmt, id);

        return true;
    }
}
