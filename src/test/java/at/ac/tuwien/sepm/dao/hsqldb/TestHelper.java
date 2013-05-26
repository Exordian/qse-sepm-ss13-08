package at.ac.tuwien.sepm.dao.hsqldb;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/applicationContext-test.xml"})
//@Component
@Repository
public class TestHelper extends DBBaseDao {
    static final String PATH = "src/test/resources/";
    /*
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("TestHelper.init(...) called");
    }
    */
    static void drop() {
        //System.out.print(NumberGenerator.get() + "TestHelper.drop() called ... jdbcTemplate==null?\t");
        //System.out.println((jdbcTemplate==null) + "\n");
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "drop.sql"), false);
    }

    static void create() {
        //System.out.println(NumberGenerator.get() + "TestHelper.create() called ...");
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "create.sql"), false);
    }

    static void insert(int fileNumber) {
        //System.out.println(NumberGenerator.get() + "TestHelper.insert() called ...");
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "insert" + fileNumber + ".sql"), false);
    }

    static void all(int fileNumber) {
        //System.out.println(NumberGenerator.get() + "TestHelper.all() called ...");
        drop();
        create();
        insert(fileNumber);
    }
}
