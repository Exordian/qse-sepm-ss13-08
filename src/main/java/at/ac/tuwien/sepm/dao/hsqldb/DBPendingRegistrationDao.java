package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.PendingRegistrationDao;
import at.ac.tuwien.sepm.entity.TissExam;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class DBPendingRegistrationDao extends DBBaseDao implements PendingRegistrationDao {

    @Override
    public boolean create(TissExam toCreate) throws IOException, DataAccessException {
        if(toCreate == null)
            return false;
        String stmt = "INSERT INTO pendingRegistrations (id,lva,name,mode,start,stop) VALUES (null,?,?,?,?,?);";
        jdbcTemplate.update(stmt, toCreate.getLvanr(), toCreate.getName(), toCreate.getMode(), new Timestamp(toCreate.getStartRegistration().getMillis()), new Timestamp(toCreate.getEndRegistration().getMillis()));
        return true;
    }

    @Override
    public List<TissExam> readAllTissExams() throws DataAccessException {
        String stmt= "SELECT * FROM pendingRegistrations";
        List<TissExam> result = jdbcTemplate.query(stmt, RowMappers.getPendingRegistrationMapper());
        return result;
    }

    @Override
    public boolean delete(int id) throws DataAccessException {
        if(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM pendingRegistrations WHERE id=?", RowMappers.getIntegerRowMapper(), id) == 0){
            return false;
        }

        String stmt="DELETE FROM pendingRegistrations WHERE id=?";
        jdbcTemplate.update(stmt, id);

        return true;
    }

}
