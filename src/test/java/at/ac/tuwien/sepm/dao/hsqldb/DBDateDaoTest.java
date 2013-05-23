package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.entity.DateEntity;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Markus MUTH
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
@Component
public class DBDateDaoTest {
    private static final String DELETE = "DELETE FROM curriculum;DELETE FROM date;DELETE FROM incurriculum;DELETE FROM lva;DELETE FROM lvadate;DELETE FROM metalva;DELETE FROM module;DELETE FROM predecessor;DELETE FROM tag;DELETE FROM todo;DELETE FROM track;";

    @Autowired
    private DBDateDao dao;

    @Autowired(required = true)
    private static TestHelper helper;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("\n" + NumberGenerator.get() + "helper==null?\t" + (helper==null) + " --> wtf!?\n");
        //System.out.println("\n" + NumberGenerator.get() + "TestHelper.jdbcTemplateIsSet()\t" + TestHelper.jdbcTemplateIsSet() + "\n");
        //EntityGenerator.read();
        //TestHelper.drop();
        //TestHelper t = new TestHelper();
        //t.drop();
        //helper.drop();
        //dao.getJdbcTemplate().execute("SET AUTOCOMMIT FALSE");


    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    }

    @Before
    public void setUp() throws Exception {
       // System.out.println(s);
       // dao.getJdbcTemplate().execute(DELETE);
       // System.out.println("setUp() aufgerufen ... ");

    }

    @After
    public void tearDown() throws Exception {
        //dao.getJdbcTemplate().execute("ROLLBACK");
        // System.out.println("tearDown() aufgerufen ... ");
    }

    @Test
    public void dummyTest() throws Exception {
        System.out.println("test executed ... ");
    }
    /*
    @Test
    public void testCreate() throws Exception {
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName("Name0");
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(new DateTime(2013, 04, 16, 1, 13, 0));
        e.setStop(new DateTime(2013, 04, 16, 15, 13, 0));

        dao.create(e);
        e.setId(0);
        assert(e.equals(dao.readById(0)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateNull() throws Exception {
        dao.create(null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateStartIsNull() throws Exception {
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName("Name0");
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(null);
        e.setStop(new DateTime(2013, 04, 16, 15, 13, 0));
        dao.create(e);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateStopIsNull() throws Exception {
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName("Name0");
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(new DateTime(2013, 04, 16, 15, 13, 0));
        e.setStop(null);
        dao.create(e);
    }

    @Test
    public void testReadById() throws Exception {
        DateEntity e = new DateEntity();
        e.setId(0);
        e.setName("Name0");
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(new DateTime(2013, 04, 16, 1, 13, 0));
        e.setStop(new DateTime(2013, 04, 16, 15, 13, 0));
        dao.create(e);
        System.out.println("testReadById(): " + e);
        System.out.println("testReadById(): " + dao.readById(0));
        assert(e.equals(dao.readById(0)));
    }

    @Test
    public void testReadByNegativeId() throws Exception {
        DateEntity e = dao.readById(-1);
        System.out.println(e);
        assert(e==null);
    }

    @Test
    public void testReadInTimeframe() throws Exception {
        DateEntity e0 = new DateEntity();
        e0.setId(0);
        e0.setName("Name0");
        e0.setDescription("Description0");
        e0.setIntersectable(true);
        e0.setStart(new DateTime(2013, 04, 16, 1, 13, 0));
        e0.setStop(new DateTime(2013, 04, 16, 15, 13, 0));

        DateEntity e1 = new DateEntity();
        e1.setId(1);
        e1.setName("Name1");
        e1.setDescription("Description1");
        e1.setIntersectable(true);
        e1.setStart(new DateTime(2013, 04, 17, 1, 13, 0));
        e1.setStop(new DateTime(2013, 04, 17, 15, 13, 0));

        DateEntity e2 = new DateEntity();
        e2.setId(2);
        e2.setName("Name2");
        e2.setDescription("Description2");
        e2.setIntersectable(null);
        e2.setStart(new DateTime(2013, 04, 18, 1, 13, 0));
        e2.setStop(new DateTime(2013, 04, 30, 15, 13, 0));

        DateEntity e3 = new DateEntity();
        e3.setId(3);
        e3.setName("Name3");
        e3.setDescription("Description3");
        e3.setIntersectable(false);
        e3.setStart(new DateTime(2013, 04, 19, 1, 13, 0));
        e3.setStop(new DateTime(2013, 04, 19, 15, 13, 0));

        DateEntity e4 = new DateEntity();
        e4.setId(4);
        e4.setName("Name4");
        e4.setDescription("Description4");
        e4.setIntersectable(false);
        e4.setStart(new DateTime(2013, 04, 20, 1, 13, 0));
        e4.setStop(new DateTime(2013, 04, 20, 15, 13, 0));

        dao.create(e0);
        dao.create(e1);
        dao.create(e2);
        dao.create(e3);
        dao.create(e4);

        List<DateEntity> l = dao.readInTimeframe(new DateTime(2013, 04, 17, 1, 13, 0), new DateTime(2013, 04, 19, 1, 13, 0));

        //assert(l.size()==3);
        System.out.println(e1);
        System.out.println(l.get(0));
        System.out.println(e2);
        System.out.println(l.get(1));
        System.out.println(e3);
        System.out.println(l.get(2));
        System.out.println(l.size());
        for(DateEntity d : l) {
            System.out.println(d);
        }
        assert(l.get(0).equals(e1));
        assert(l.get(2).equals(e2));
        assert(l.get(3).equals(e3));
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }  */
}
