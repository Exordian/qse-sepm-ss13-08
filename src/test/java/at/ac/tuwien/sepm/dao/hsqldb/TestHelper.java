package at.ac.tuwien.sepm.dao.hsqldb;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.test.jdbc.JdbcTestUtils;

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
    static final String PATH = "src/main/resources/";
    static final String PATHTEST = "src/test/resources/";
    /*
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        System.out.println("TestHelper.init(...) called");
    }
    */
    public static void drop() {
        //System.out.print(NumberGenerator.get() + "TestHelper.drop() called ... jdbcTemplate==null?\t");
        //System.out.println((jdbcTemplate==null) + "\n");
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "drop.sql"), false);
    }

    public static void create() {
        //System.out.println(NumberGenerator.get() + "TestHelper.create() called ...");
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATH + "create.sql"), false);
    }

    public static void insert(int fileNumber) {
        //System.out.println(NumberGenerator.get() + "TestHelper.insert() called ...");
        JdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(PATHTEST + "insert" + fileNumber + ".sql"), false);
    }

    public static void all(int fileNumber) {
        //System.out.println(NumberGenerator.get() + "TestHelper.all() called ...");
        drop();
        create();
        insert(fileNumber);
    }
}
