package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.Track;
import at.ac.tuwien.sepm.service.Semester;
import org.junit.Before;
import org.junit.Test;
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
public class DBTrackDaoTest {

    @Autowired
    DBTrackDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {
        TestHelper.insert(11);

        Track e0 = new Track();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(1);
        e0.setLva(l);
        e0.setName("Übung machen");
        e0.setDescription("qertbe fg");
        e0.setStart(2013, 4, 5, 11, 1, 0);
        e0.setStop(2013, 4, 5, 20, 0, 0);

        assert(dao.create(e0));
        Track e1 = dao.readById(0);
        e0.setId(0);
        assert(e1.equals(e0));
        assert(e1.getLva().getId()==e0.getLva().getId());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateStartIsNull() throws Exception {
        TestHelper.insert(11);

        Track e0 = new Track();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(1);
        e0.setLva(l);
        e0.setName("Übung machen");
        e0.setDescription("qertbe fg");
        e0.setStart(null);
        e0.setStop(2013,4,5,20,0,0);

        dao.create(e0);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateStopIsNull() throws Exception {
        TestHelper.insert(11);

        Track e0 = new Track();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(1);
        e0.setLva(l);
        e0.setName("Übung machen");
        e0.setDescription("qertbe fg");
        e0.setStart(2013,4,5,11,1,0);
        e0.setStop(null);

        dao.create(e0);
    }

    @Test
    public void testCreateNull() throws Exception {
        TestHelper.insert(11);
        assert(!dao.create(null));
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongName() throws Exception {
        TestHelper.insert(11);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Track e0 = new Track();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(1);
        e0.setLva(l);
        e0.setName(s);
        e0.setDescription("qertbe fg");
        e0.setStart(2013, 4, 5, 11, 1, 0);
        e0.setStop(2013, 4, 5, 20, 0, 0);

        dao.create(e0);
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongDescription() throws Exception {
        TestHelper.insert(11);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Track e0 = new Track();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(1);
        e0.setLva(l);
        e0.setName("Übung machen");
        e0.setDescription(s);
        e0.setStart(2013, 4, 5, 11, 1, 0);
        e0.setStop(2013, 4, 5, 20, 0, 0);

        dao.create(e0);
    }

    @Test
    public void testReadById() throws Exception {
        TestHelper.insert(0);

        Track e0 = new Track();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(1);
        e0.setLva(l);
        e0.setName("Übung machen");
        e0.setDescription("qertbe fg");
        e0.setStart(2013, 4, 5, 11, 1, 0);
        e0.setStop(2013, 4, 5, 20, 0, 0);

        Track e1 = dao.readById(0);

        assert(e1.equals(e0));
        assert(e1.getLva().getId()==1);
    }

    @Test
    public void testReadByNotExistingId() throws Exception {
        TestHelper.insert(0);
        assert(dao.readById(-1)==null);
    }

    @Test
    public void testReadByLva() throws Exception {
        TestHelper.insert(0);
        List<Track> l = dao.readByLva(2);
        assert(l.size()==6);
        assert(l.get(0).getId()==3);
        assert(l.get(1).getId()==4);
        assert(l.get(2).getId()==5);
        assert(l.get(3).getId()==6);
        assert(l.get(4).getId()==14);
        assert(l.get(5).getId()==12);

        for(int i=0; i<l.size()-1; i++) {
            assert(l.get(i).getStart().isBefore(l.get(i+1).getStart().getMillis()) || l.get(i).getStart().equals(l.get(i + 1).getStart()));
        }
    }

    @Test
    public void testReadByNotExistingLva() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByLva(-1).size()==0);
    }

    @Test
    public void testReadBySemester() throws Exception {
        TestHelper.insert(0);
        List<Track> l = dao.readBySemester(2013, Semester.S);

        assert(l.size()==16);

        for(int i=0; i<l.size()-1; i++) {
            assert(l.get(i).getStart().isBefore(l.get(i+1).getStart().getMillis()) || l.get(i).getStart().equals(l.get(i + 1).getStart()));
        }
    }

    @Test
    public void testReadBySemesterNotExistingYear() throws Exception {
        TestHelper.insert(0);
        assert(dao.readBySemester(2000, Semester.S).size()==0);
    }

    @Test
    public void testReadBySemesterNotExistingSemester() throws Exception {
        TestHelper.insert(0);
        assert(dao.readBySemester(2013, Semester.W).size()==0);
    }

    @Test
    public void testReadBySemesterIsNotEitherWOrS() throws Exception {
        TestHelper.insert(0);
        assert(dao.readBySemester(2013, Semester.W_S)==null);
    }


    @Test
    public void testReadBySemesterIsNull() throws Exception {
        TestHelper.insert(0);
        assert(dao.readBySemester(2013,null)==null);
    }

    @Test
    public void testUpdate() throws Exception {
        TestHelper.insert(0);

        Track e0 = new Track();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(5);
        e0.setLva(l);
        e0.setName("New Name");
        e0.setDescription("New Description");
        e0.setStart(2014, 4, 5, 11, 1, 0);
        e0.setStop(2014, 4, 5, 20, 0, 0);

        assert(dao.update(e0));
        Track e1 = dao.readById(0);
        assert(e1.equals(e0));
        assert(e1.getLva().getId()==e0.getLva().getId());
    }

    @Test
    public void testUpdateNull() throws Exception {
        TestHelper.insert(0);
        assert(!dao.update(null));
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongName() throws Exception {
        TestHelper.insert(0);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Track e0 = new Track();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(5);
        e0.setLva(l);
        e0.setName(s);
        e0.setDescription("New Description");
        e0.setStart(2014, 4, 5, 11, 1, 0);
        e0.setStop(2014, 4, 5, 20, 0, 0);

        dao.update(e0);
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongDescription() throws Exception {
        TestHelper.insert(0);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Track e0 = new Track();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(5);
        e0.setLva(l);
        e0.setName("New Name");
        e0.setDescription(s);
        e0.setStart(2014, 4, 5, 11, 1, 0);
        e0.setStop(2014, 4, 5, 20, 0, 0);

        dao.update(e0);

    }

    @Test
    public void testDelete() throws Exception {
        TestHelper.insert(0);
        assert(dao.readById(0)!=null);
        assert(dao.delete(0));
        assert(dao.readById(0)==null);
    }

    @Test
    public void testDeleteNotExistingTrack() throws Exception {
        TestHelper.insert(0);
        assert(!dao.delete(-1));
    }
}
