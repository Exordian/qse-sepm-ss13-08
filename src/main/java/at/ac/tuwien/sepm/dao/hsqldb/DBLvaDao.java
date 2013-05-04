package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.LvaDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DBLvaDao extends DBBaseDao implements LvaDao {

    public int test() {
        jdbcTemplate.update("create table blub (id int)");

//        jdbcTemplate.queryForObject("select id from blub", Integer.class);
        return 5;
    }

    public void test2() {
        jdbcTemplate.query("SELECT * FROM blub", new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                System.out.println(rs.getInt("id"));
                return null;
            }
        });
    }

    public void test3() {
        jdbcTemplate.update("insert into blub values (1)");
        // Force Rollbackk
        //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        jdbcTemplate.update("insert into blub values ('a')");
    }
}
