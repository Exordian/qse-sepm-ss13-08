package at.ac.tuwien.sepm.dao.hsqldb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: MUTH Markus
 * Date: 5/23/13
 * Time: 6:23 PM
 * Description of class "TestHelper":
 */
@Repository
public class TestHelper {
    private static final String PATH = "src/test/resources/";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("TestHelper.init(...) called");
    }

    void drop() {
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "drop.sql"), false);
    }

    void create() {
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "create.sql"), false);
    }

    void insert(int fileNumber) {
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "insert" + fileNumber + ".sql"), false);
    }

    void all(int fileNumber) {
        drop();
        create();
        insert(fileNumber);
    }
}
