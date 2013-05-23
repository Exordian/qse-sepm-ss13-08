package at.ac.tuwien.sepm.dao.hsqldb;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Markus MUTH
 */
@Repository
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class SqlStatements_Do_Not_Use {
    private static final String PATH = "src/test/resources/";

    protected static JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("\n<debug>");
        System.out.print("\tSqlStatements.init([" + dataSource.toString() + "]) wurde aufgerufen. jdbcTemplate==null?\t");
        System.out.println("\t" + jdbcTemplate==null);
        System.out.println("\t" + jdbcTemplate.toString());
        System.out.println("</debug>");
        jdbcTemplate.execute("CREATE TABLE jetztfunktioniertesaber (name VARCHAR(20));");
    }

    static void setAutocommitFalse() {
        jdbcTemplate.execute("SET AUTOCOMMIT FALSE");
        //jdbcTemplate.getDataSource().getConnection().setAutoCommit(false);
    }

    static void setAutocommitTrue() {
        jdbcTemplate.execute("SET AUTOCOMMIT TRUE");
        //jdbcTemplate.getDataSource().getConnection().setAutoCommit(true);
    }

    static void commit() {
        jdbcTemplate.execute("COMMIT");
        //jdbcTemplate.getDataSource().getConnection().commit();
    }

    static void rollback() {
        jdbcTemplate.execute("ROLLBACK");
        //jdbcTemplate.getDataSource().getConnection().rollback();
    }

    static void drop() {
        System.out.println("<debug>");
        System.out.print("\tjdbcTemplate is null?\t");
        System.out.println("\t" + jdbcTemplate==null);
        System.out.println("\tsql script exists?\t\t" + (new FileSystemResource(PATH + "drop.sql")).getFile().exists());
        Connection c;
        try {
            c = jdbcTemplate.getDataSource().getConnection();
            System.out.println("\tdata source: [" + c.toString() + "]");
        } catch (SQLException e) {
            System.out.println("\tUnable to get data source string.");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("</debug>");
        jdbcTemplate.execute("CREATE TABLE jetztfunktioniertesaber (name VARCHAR(20));");

        //JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "drop.sql"), false);
    }

    static void create() {
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "create.sql"), false);
    }

    static void insert(int fileNumber) {
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "insert" + fileNumber + ".sql"), false);
    }

    static void dropCreate() {
        drop();
        create();
    }

    static void dropCreateInsert(int fileNumber) {
        drop();
        create();
        insert(fileNumber);
    }
}