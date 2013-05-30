package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.LvaDateDao;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Author: MUTH Markus
 * Date: 5/25/13
 * Time: 11:19 AM
 * Description of class "DBLvaDateDao":
 */
@Repository
public class DBLvaDateDao extends DBBaseDao implements LvaDateDao {
    private static final int MAX_LENGTH_NAME = 200;
    private static final int MAX_LENGTH_DESCRIPTION=500;
    private static final int MAX_LENGTH_ROOM=200;

    @Override
    public boolean create(LvaDate toCreate) throws IOException, DataAccessException {
        if(toCreate == null) {
            return false;
        }
        if(toCreate.getName()!=null && toCreate.getName().length()>MAX_LENGTH_NAME){
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        }
        if(toCreate.getDescription()!=null && toCreate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }
        if(toCreate.getRoom()!=null && toCreate.getRoom().length()>MAX_LENGTH_ROOM) {
            throw new IOException(ExceptionMessages.tooLongRoom(MAX_LENGTH_ROOM));
        }

        String stmt = "INSERT INTO lvadate " +
                "(id,lva,name,description,type,room,result,start,stop,attendanceRequired,wasAttendant) VALUES" +
                "(null,?,?,?,?,?,?,?,?,?,?)";

        Object[] args = new Object[] {toCreate.getLva(), toCreate.getName(), toCreate.getDescription(),
                toCreate.getType().ordinal(), toCreate.getRoom(), toCreate.getResult(),
                new Timestamp(toCreate.getStart().getMillis()), new Timestamp(toCreate.getStop().getMillis()),
                toCreate.getAttendanceRequired(), toCreate.getWasAttendant()};

        jdbcTemplate.update(stmt, args);
        return true;
    }

    @Override
    public LvaDate readById(int id) throws DataAccessException {
        String stmt = "SELECT * FROM lvadate WHERE ID=?";

        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM lvadate WHERE id=?", RowMappers.getIntegerRowMapper(), id) != 1){
            return null;
        }

        return jdbcTemplate.queryForObject(stmt, RowMappers.getLvaDateRowMapper(), id);
    }

    @Override
    public List<LvaDate> readByLva(int lvaId) throws DataAccessException {
        String stmt = "SELECT * FROM lvadate WHERE lva=? ORDER BY start";
        return jdbcTemplate.query(stmt, RowMappers.getLvaDateRowMapper(), lvaId);
    }

    @Override
    public List<LvaDate> readByLvaAndType(int lvaId, LvaDateType type) throws DataAccessException {
        String stmt = "SELECT * FROM lvadate WHERE lva=? AND type=? ORDER BY start";
        return jdbcTemplate.query(stmt, RowMappers.getLvaDateRowMapper(), lvaId, type.ordinal());
    }

    @Override
    public boolean update(LvaDate toUpdate) throws IOException, DataAccessException {
        if(toUpdate == null) {
            return false;
        }
        if(toUpdate.getId() == null) {
            return false;
        }
        if(toUpdate.getName()!=null && toUpdate.getName().length()>MAX_LENGTH_NAME){
            throw new IOException(ExceptionMessages.tooLongName(MAX_LENGTH_NAME));
        }
        if(toUpdate.getDescription()!=null && toUpdate.getDescription().length()>MAX_LENGTH_DESCRIPTION) {
            throw new IOException(ExceptionMessages.tooLongDescription(MAX_LENGTH_DESCRIPTION));
        }
        if(toUpdate.getRoom()!=null && toUpdate.getRoom().length()>MAX_LENGTH_ROOM) {
            throw new IOException(ExceptionMessages.tooLongRoom(MAX_LENGTH_ROOM));
        }

        String stmt = "SELECT COUNT(*) FROM lvadate WHERE id=?";
        if(jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(),toUpdate.getId()) == 0) {
            return false;
        }

        String stmtUpdateLva = "UPDATE lvadate SET lva=? WHERE id=?";
        String stmtUpdateName = "UPDATE lvadate SET name=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE lvadate SET description=? WHERE id=?";
        String stmtUpdateType = "UPDATE lvadate SET type=? WHERE id=?";
        String stmtUpdateRoom = "UPDATE lvadate SET room=? WHERE id=?";
        String stmtUpdateResult = "UPDATE lvadate SET result=? WHERE id=?";
        String stmtUpdateStart = "UPDATE lvadate SET start=? WHERE id=?";
        String stmtUpdateStop = "UPDATE lvadate SET stop=? WHERE id=?";
        String stmtUpdateAttendanceRequired = "UPDATE lvadate SET attendancerequired=? WHERE id=?";
        String stmtUpdateWasAttendant = "UPDATE lvadate SET wasattendant=? WHERE id=?";

        try {
            jdbcTemplate.execute("SET AUTOCOMMIT FALSE");
            if(toUpdate.getName() != null) {
                jdbcTemplate.update(stmtUpdateLva, toUpdate.getLva(), toUpdate.getId());
            }
            if(toUpdate.getName() != null) {
                jdbcTemplate.update(stmtUpdateName,toUpdate.getName(), toUpdate.getId());
            }
            if(toUpdate.getDescription() != null) {
                jdbcTemplate.update(stmtUpdateDescription, toUpdate.getDescription(), toUpdate.getId());
            }
            if(toUpdate.getType() != null) {
                jdbcTemplate.update(stmtUpdateType, toUpdate.getType().ordinal(), toUpdate.getId());
            }
            if(toUpdate.getRoom() != null) {
                jdbcTemplate.update(stmtUpdateRoom, toUpdate.getRoom(), toUpdate.getId());
            }
            if(toUpdate.getResult() != null) {
                jdbcTemplate.update(stmtUpdateResult, toUpdate.getResult(), toUpdate.getId());
            }
            if(toUpdate.getStart() != null) {
                jdbcTemplate.update(stmtUpdateStart, new Timestamp(toUpdate.getStart().getMillis()), toUpdate.getId());
            }
            if(toUpdate.getStop() != null) {
                jdbcTemplate.update(stmtUpdateStop, new Timestamp(toUpdate.getStop().getMillis()), toUpdate.getId());
            }
            if(toUpdate.getAttendanceRequired() != null) {
                jdbcTemplate.update(stmtUpdateAttendanceRequired, toUpdate.getAttendanceRequired(), toUpdate.getId());
            }
            if(toUpdate.getWasAttendant() != null) {
                jdbcTemplate.update(stmtUpdateWasAttendant, toUpdate.getWasAttendant(), toUpdate.getId());
            }
            jdbcTemplate.execute("COMMIT");
            jdbcTemplate.execute("SET AUTOCOMMIT TRUE");
        } catch (DataAccessException e) {
            jdbcTemplate.execute("ROLLBACK");
            jdbcTemplate.execute("SET AUTOCOMMIT TRUE");
            throw e;
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        String stmtCount = "SELECT COUNT(*) FROM lvadate WHERE id=?";
        if(jdbcTemplate.queryForObject(stmtCount, RowMappers.getIntegerRowMapper(), id) == 0) {
            return false;
        }

        String stmt = "DELETE FROM lvadate WHERE id=?;";
        jdbcTemplate.update(stmt, id);

        return true;
    }

    @Override
    public List<LvaDate> getAllDeadlines() throws DataAccessException {
        String query = "SELECT * FROM lvadate WHERE type = 3";
        List<LvaDate> result = jdbcTemplate.query(query, RowMappers.getLvaDateRowMapper());
        for(int i=0; i<result.size(); i++) {
            result.set(i, readById(result.get(i).getId()));
        }
        return result;
    }
}
