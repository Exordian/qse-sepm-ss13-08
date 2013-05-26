package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.DateEntity;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author Markus MUTH
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBDateDaoTest {
    // private static final String DELETE = "DELETE FROM curriculum;DELETE FROM date;DELETE FROM incurriculum;DELETE FROM lva;DELETE FROM lvadate;DELETE FROM metalva;DELETE FROM module;DELETE FROM predecessor;DELETE FROM tag;DELETE FROM to do;DELETE FROM track;";

    @Autowired
    private DBDateDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName("Name0");
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        assert(dao.create(e));
        e.setId(0);
        assert(e.equals(dao.readById(0)));
    }

    @Test(expected = IOException.class)
    public void testCreateToLongName() throws Exception {
        String s = "aa";
        for(int i=0; i<10; i++) {
                    s=s.concat(s);
        }
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName(s);
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        dao.create(e);
        e.setId(0);
        assert(e.equals(dao.readById(0)));
    }

    @Test(expected = IOException.class)
    public void testCreateToLongDescription() throws Exception {
        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName("Name0");
        e.setDescription(s);
        e.setIntersectable(true);
        e.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        dao.create(e);
        e.setId(0);
        assert(e.equals(dao.readById(0)));
    }

    @Test
    public void testCreateNull() throws Exception {
        assert(!dao.create(null));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateStartIsNull() throws Exception {
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName("Name0");
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(null);
        e.setStop(new DateTime(2013, 4, 16, 15, 13, 0));
        dao.create(e);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateStopIsNull() throws Exception {
        DateEntity e = new DateEntity();
        e.setId(null);
        e.setName("Name0");
        e.setDescription("Description0");
        e.setIntersectable(true);
        e.setStart(new DateTime(2013, 4, 16, 15, 13, 0));
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
        e.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e.setStop(new DateTime(2013, 4, 16, 15, 13, 0));
        dao.create(e);
        assert(e.equals(dao.readById(0)));
    }

    @Test
    public void testReadByNegativeId() throws Exception {
        DateEntity e = dao.readById(-1);
        assert(e==null);
    }

    @Test
    public void testReadInTimeframe() throws Exception {
        DateEntity e0 = new DateEntity();
        e0.setId(0);
        e0.setName("Name0");
        e0.setDescription("Description0");
        e0.setIntersectable(true);
        e0.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e0.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        DateEntity e1 = new DateEntity();
        e1.setId(1);
        e1.setName("Name1");
        e1.setDescription("Description1");
        e1.setIntersectable(true);
        e1.setStart(new DateTime(2013, 4, 17, 1, 13, 0));
        e1.setStop(new DateTime(2013, 4, 17, 15, 13, 0));

        DateEntity e2 = new DateEntity();
        e2.setId(2);
        e2.setName("Name2");
        e2.setDescription("Description2");
        e2.setIntersectable(null);
        e2.setStart(new DateTime(2013, 4, 18, 1, 13, 0));
        e2.setStop(new DateTime(2013, 4, 30, 15, 13, 0));

        DateEntity e3 = new DateEntity();
        e3.setId(3);
        e3.setName("Name3");
        e3.setDescription("Description3");
        e3.setIntersectable(false);
        e3.setStart(new DateTime(2013, 4, 19, 1, 13, 0));
        e3.setStop(new DateTime(2013, 4, 19, 15, 13, 0));

        DateEntity e4 = new DateEntity();
        e4.setId(4);
        e4.setName("Name4");
        e4.setDescription("Description4");
        e4.setIntersectable(false);
        e4.setStart(new DateTime(2013, 4, 20, 1, 13, 0));
        e4.setStop(new DateTime(2013, 4, 20, 15, 13, 0));

        dao.create(e0);
        dao.create(e1);
        dao.create(e2);
        dao.create(e3);
        dao.create(e4);

        List<DateEntity> l = dao.readInTimeframe(new DateTime(2013, 4, 17, 1, 13, 0), new DateTime(2013, 4, 19, 1, 13, 0));

        e2.setIntersectable(false);

        assert(l.size()==3);
        assert(l.get(0).equals(e1));
        assert(l.get(1).equals(e2));
        assert(l.get(2).equals(e3));
    }

    @Test
    public void testReadInTimeframeWhichDoesNotContainDtes() throws Exception {
        DateEntity e0 = new DateEntity();
        e0.setId(0);
        e0.setName("Name0");
        e0.setDescription("Description0");
        e0.setIntersectable(true);
        e0.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e0.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        DateEntity e1 = new DateEntity();
        e1.setId(1);
        e1.setName("Name1");
        e1.setDescription("Description1");
        e1.setIntersectable(true);
        e1.setStart(new DateTime(2013, 4, 17, 1, 13, 0));
        e1.setStop(new DateTime(2013, 4, 17, 15, 13, 0));

        DateEntity e2 = new DateEntity();
        e2.setId(2);
        e2.setName("Name2");
        e2.setDescription("Description2");
        e2.setIntersectable(null);
        e2.setStart(new DateTime(2013, 4, 18, 1, 13, 0));
        e2.setStop(new DateTime(2013, 4, 30, 15, 13, 0));

        DateEntity e3 = new DateEntity();
        e3.setId(3);
        e3.setName("Name3");
        e3.setDescription("Description3");
        e3.setIntersectable(false);
        e3.setStart(new DateTime(2013, 4, 19, 1, 13, 0));
        e3.setStop(new DateTime(2013, 4, 19, 15, 13, 0));

        DateEntity e4 = new DateEntity();
        e4.setId(4);
        e4.setName("Name4");
        e4.setDescription("Description4");
        e4.setIntersectable(false);
        e4.setStart(new DateTime(2013, 4, 20, 1, 13, 0));
        e4.setStop(new DateTime(2013, 4, 20, 15, 13, 0));

        dao.create(e0);
        dao.create(e1);
        dao.create(e2);
        dao.create(e3);
        dao.create(e4);

        List<DateEntity> l = dao.readInTimeframe(new DateTime(2000, 4, 17, 1, 13, 0), new DateTime(2000, 4, 19, 1, 13, 0));
        assert(l.size()==0);
    }

    @Test
    public void testUpdate() throws Exception {
        DateEntity e0 = new DateEntity();
        e0.setId(null);
        e0.setName("Name0");
        e0.setDescription("Description0");
        e0.setIntersectable(true);
        e0.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e0.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        DateEntity e1 = new DateEntity();
        e1.setId(0);
        e1.setName("New Name #################################");
        e1.setDescription("New Description");
        e1.setIntersectable(false);
        e1.setStart(new DateTime(2013, 4, 17, 1, 13, 0));
        e1.setStop(new DateTime(2013, 4, 17, 15, 13, 0));

        dao.create(e0);
        dao.update(e1);

        assert(dao.readById(0).equals(e1));
    }

    @Test(expected = IOException.class)
    public void testUpdateToLongName() throws Exception {
        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        DateEntity e0 = new DateEntity();
        e0.setId(null);
        e0.setName("Name0");
        e0.setDescription("Description0");
        e0.setIntersectable(true);
        e0.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e0.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        DateEntity e1 = new DateEntity();
        e1.setId(0);
        e1.setName(s);
        e1.setDescription("New Description");
        e1.setIntersectable(false);
        e1.setStart(new DateTime(2013, 4, 17, 1, 13, 0));
        e1.setStop(new DateTime(2013, 4, 17, 15, 13, 0));

        dao.create(e0);
        dao.update(e1);
    }

    @Test(expected = IOException.class)
    public void testUpdateToLongDescription() throws Exception {
        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        DateEntity e0 = new DateEntity();
        e0.setId(null);
        e0.setName("Name0");
        e0.setDescription("Description0");
        e0.setIntersectable(true);
        e0.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e0.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        DateEntity e1 = new DateEntity();
        e1.setId(0);
        e1.setName("New Name---------------");
        e1.setDescription(s);
        e1.setIntersectable(false);
        e1.setStart(new DateTime(2013, 4, 17, 1, 13, 0));
        e1.setStop(new DateTime(2013, 4, 17, 15, 13, 0));

        dao.create(e0);
        dao.update(e1);
    }

    @Test
    public void testUpdateNull() throws Exception {
        assert(!dao.update(null));
    }

    @Test
    public void testDelete() throws Exception {
        DateEntity e0 = new DateEntity();
        e0.setId(null);
        e0.setName("Name0");
        e0.setDescription("Description0");
        e0.setIntersectable(true);
        e0.setStart(new DateTime(2013, 4, 16, 1, 13, 0));
        e0.setStop(new DateTime(2013, 4, 16, 15, 13, 0));

        DateEntity e1 = new DateEntity();
        e1.setId(1);
        e1.setName("Name1");
        e1.setDescription("Description1");
        e1.setIntersectable(false);
        e1.setStart(new DateTime(2013, 4, 17, 1, 13, 0));
        e1.setStop(new DateTime(2013, 4, 17, 15, 13, 0));

        dao.create(e0);
        dao.create(e1);
        dao.delete(0);
        //System.out.println(e0);
        //System.out.println(e1);
        //System.out.println(dao.readById(0));
        //System.out.println(dao.readById(1));
        assert(dao.readById(0) == null);
        assert(dao.readById(1).equals(e1));
    }

    @Test
    public void testDeleteNotExistingId() throws Exception {
        assert(!dao.delete(-1));
    }
}
