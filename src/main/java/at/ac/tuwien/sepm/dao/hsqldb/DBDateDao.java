package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.entity.DateEntity;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        Object[] args = new Object[]{toCreate.getName(), toCreate.getDescription(), (toCreate.getIntersectable() != null ? toCreate.getIntersectable() : false), new Timestamp(toCreate.getStart().getMillis()), new Timestamp(toCreate.getStop().getMillis())};
        jdbcTemplate.update(stmt, args);
        return true;
    } /*@Override public ArrayList<DateEntity> readAll() throws DataAccessException { String stmt="SELECT * FROM date ORDER BY start"; List<DateEntity> result = jdbcTemplate.query(stmt, RowMappers.getDateRowMapper()); return new ArrayList<DateEntity>(result); }*/

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

        try {
            jdbcTemplate.execute("SET AUTOCOMMIT FALSE");
            if (toUpdate.getName() != null) {
                jdbcTemplate.update(stmtUpdateName, toUpdate.getName(), toUpdate.getId());
            }
            if (toUpdate.getDescription() != null) {
                jdbcTemplate.update(stmtUpdateDescription, toUpdate.getDescription(), toUpdate.getId());
            }
            if (toUpdate.getIntersectable() != null) {
                jdbcTemplate.update(stmtUpdateIsIntersectable, toUpdate.getIntersectable(), toUpdate.getId());
            }
            if (toUpdate.getStart() != null) {
                jdbcTemplate.update(stmtUpdateStart, new Timestamp(toUpdate.getStart().getMillis()), toUpdate.getId());
            }
            if (toUpdate.getStop() != null) {
                jdbcTemplate.update(stmtUpdateStop,new Timestamp(toUpdate.getStop().getMillis()), toUpdate.getId());
            }
            jdbcTemplate.execute("COMMIT");
            jdbcTemplate.execute("SET AUTOCOMMIT TRUE");
        } catch (DataAccessException e) {
            jdbcTemplate.execute("ROLLBACK;");
            jdbcTemplate.execute("SET AUTOCOMMIT TRUE");
            throw e;
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
