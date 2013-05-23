package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.entity.DateEntity;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Markus MUTH
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBDateDaoTest {
    //protected static TestHelper helper = new TestHelper();

    @Autowired
    private TestHelper helper;

    @Autowired
    private DBDateDao dao;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        TestHelper t = new TestHelper();
        t.drop();
        // helper.drop();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testReadById() throws Exception {
        DateEntity e = dao.readById(0);

        assert(e.getId()==0);
        assert(e.getName()==null);
        assert(e.getDescription()==null);
        assert(e.getIntersectable()==false);
        assert(e.getStart().getMillis() == (new DateTime(2013, 04, 16, 1, 13, 0)).getMillis());
        assert((e.getStop()).getMillis()==(new DateTime(2013, 04, 16, 15, 13, 0)).getMillis());
    }

    @Test
    public void testReadInTimeframe() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}
