package at.ac.tuwien.sepm.dao.hsqldb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Component
public abstract class DBBaseDao {

    protected static JdbcTemplate jdbcTemplate;

    @Autowired(required = true)
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);


        try {
            System.out.println(NumberGenerator.get() + dataSource.getConnection().toString());
        } catch (SQLException e) {
            System.out.println(NumberGenerator.get() + "any error with connection occurred");
        }
        System.out.println(NumberGenerator.get() + this.getClass() + ".init(...) called ...\n");
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
