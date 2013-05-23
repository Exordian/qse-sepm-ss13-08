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
    @Override
    public void create(DateEntity toCreate) throws IOException, DataAccessException {
        System.out.println("create(DateEntity toCreate): " + toCreate.toString());
        String stmt = "INSERT INTO Date (id,tag,name,description,isIntersectable,start,stop) " +
                "VALUES (null,null,?,?,?,?,?)";
        Object[] args = new Object[] {toCreate.getName(), toCreate.getDescription(), toCreate.getIntersectable(),
                new Timestamp(toCreate.getStart().getMillis()), new Timestamp(toCreate.getStop().getMillis())};

        jdbcTemplate.update(stmt, args);
    }

    /*@Override
    public ArrayList<DateEntity> readAll() throws DataAccessException {
        String stmt="SELECT * FROM date ORDER BY start";
        List<DateEntity> result = jdbcTemplate.query(stmt, RowMappers.getDateRowMapper());

        return new ArrayList<DateEntity>(result);
    }*/

    @Override
    public DateEntity readById(int id) throws DataAccessException {
        String stmt="SELECT * FROM date WHERE ID=?";
        Object[] args = new Object[] {new Integer(id)};
        try {
            return jdbcTemplate.queryForObject(stmt, RowMappers.getDateRowMapper(), args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<DateEntity> readInTimeframe(DateTime from, DateTime till) throws DataAccessException {
        String stmt=" SELECT * FROM date WHERE start>=? AND start<=? ORDER BY start";
        Object[] args = new Object[] {new Timestamp(from.getMillis()), new Timestamp(till.getMillis())};

        return jdbcTemplate.query(stmt, RowMappers.getDateRowMapper(), args);
    }

    /*@Override
    public ArrayList<DateEntity> readIntersectableInTimeframe(DateTime from, DateTime till, boolean isIntersectable) throws DataAccessException {
        String stmt=" SELECT * FROM date WHERE start>=? AND stop<=? AND isintersectable=? ORDER BY start";
        Object[] args = new Object[] {new Timestamp(from.getMillis()), new Timestamp(till.getMillis()), new Boolean(isIntersectable)};

        List<DateEntity> result = jdbcTemplate.query(stmt, RowMappers.getDateRowMapper(), args);

        return new ArrayList<DateEntity>(result);
    }*/

    @Override
    public boolean update(DateEntity toUpdate) throws IOException, DataAccessException {
        String stmt = "SELECT COUNT(*) FROM date WHERE id=?";
        if(jdbcTemplate.queryForObject(stmt, RowMappers.getIntegerRowMapper(), new Object[] {toUpdate.getId()}) == 0) {
            return false;
        }

        String stmtUpdateName = "UPDATE date SET name=? WHERE id=?";
        String stmtUpdateDescription = "UPDATE date SET description=? WHERE id=?";
        String stmtUpdateIsIntersectable = "UPDATE date SET isintersectable=? WHERE id=?";
        String stmtUpdateStart = "UPDATE date SET start=? WHERE id=?";
        String stmtUpdateStop = "UPDATE date SET stop=? WHERE id=?";

        if(toUpdate.getName() != null) {
            jdbcTemplate.update(stmtUpdateName, new Object[] {toUpdate.getName(), toUpdate.getId()});
        }
        if(toUpdate.getDescription() != null) {
            jdbcTemplate.update(stmtUpdateDescription, new Object[] {toUpdate.getDescription(), toUpdate.getId()});
        }
        if(toUpdate.getIntersectable() != null) {
            jdbcTemplate.update(stmtUpdateIsIntersectable, new Object[] {toUpdate.getIntersectable(), toUpdate.getId()});
        }
        if(toUpdate.getStart() != null) {
            jdbcTemplate.update(stmtUpdateStart, new Object[] {new Timestamp(toUpdate.getStart().getMillis()), toUpdate.getId()});
        }
        if(toUpdate.getStop() != null) {
            jdbcTemplate.update(stmtUpdateStop, new Object[] {new Timestamp(toUpdate.getStop().getMillis()), toUpdate.getId()});
        }

        return true;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        String stmtCount = "SELECT COUNT(*) FROM date WHERE id=?";
        if(jdbcTemplate.queryForObject(stmtCount, RowMappers.getIntegerRowMapper(), new Object[] {new Integer(id)}) == 0) {
            return false;
        }

        String stmt = "DELETE FROM date WHERE id=?;";
        Object[] args = new Object[] {new Integer(id)};

        jdbcTemplate.update(stmt, args);

        return true;
    }
}
