package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author Markus MUTH
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBLvaDateDaoTest {

    @Autowired
    private DBLvaDateDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {
        TestHelper.insert(1);
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
        e.setId(0);
        e.setResult(0);
        assert(dao.readById(0).equals(e));
    }

    @Test
    public void testCreateDifferentLvaDateTypes() throws Exception {
        TestHelper.insert(1);

        LvaDate e0 = new LvaDate();
        LvaDate e1 = new LvaDate();
        LvaDate e2 = new LvaDate();
        LvaDate e3 = new LvaDate();

        e0.setLva(0);
        e0.setName("Deadline 1");
        e0.setDescription("blablabla über Deadline 1");
        e0.setType(LvaDateType.DEADLINE);
        e0.setRoom("HS 17 Friedrich Hartmann");
        e0.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        e0.setAttendanceRequired(false);
        e0.setWasAttendant(true);

        e1.setLva(1);
        e1.setName("Prüfung 1");
        e1.setDescription("blablabla über Prüfung 1");
        e1.setType(LvaDateType.EXAM);
        e1.setRoom("HS 17 Friedrich Hartmann");
        e1.setTime(new TimeFrame(new DateTime(2013, 3, 10, 12, 15, 0),new DateTime(2013, 3, 10, 13, 45, 0)));
        e1.setAttendanceRequired(false);
        e1.setWasAttendant(true);

        e2.setLva(2);
        e2.setName("UE 1");
        e2.setDescription("blablabla über UE 1");
        e2.setType(LvaDateType.EXERCISE);
        e2.setRoom("HS 17 Friedrich Hartmann");
        e2.setTime(new TimeFrame(new DateTime(2013, 3, 11, 12, 15, 0),new DateTime(2013, 3, 11, 13, 45, 0)));
        e2.setAttendanceRequired(false);
        e2.setWasAttendant(true);

        e3.setLva(3);
        e3.setName("VO 1");
        e3.setDescription("blablabla über VO 1");
        e3.setType(LvaDateType.LECTURE);
        e3.setRoom("HS 17 Friedrich Hartmann");
        e3.setTime(new TimeFrame(new DateTime(2013, 3, 1, 12, 15, 0),new DateTime(2013, 3, 1, 13, 45, 0)));
        e3.setAttendanceRequired(false);
        e3.setWasAttendant(true);


        dao.create(e0);
        dao.create(e1);
        dao.create(e2);
        dao.create(e3);

        e0.setId(0);
        e1.setId(1);
        e2.setId(2);
        e3.setId(3);

        e0.setResult(0);
        e1.setResult(0);
        e2.setResult(0);
        e3.setResult(0);

        e0.setTime(new TimeFrame(e0.getStop(), e0.getStop()));

        assert(dao.readById(0).equals(e0));
        assert(dao.readById(1).equals(e1));
        assert(dao.readById(2).equals(e2));
        assert(dao.readById(3).equals(e3));
    }

    @Test
    public void testCreateStartAndStopDateAreDifferent() throws Exception {
        TestHelper.insert(1);
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2005, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2005, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
        e.setId(0);
        e.setResult(0);
        //e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 13, 45, 0),e.getStop()));
        //e.setStart(new DateTime(2013, 3, 4, 13, 45, 0));
        assert(dao.readById(0).equals(e));
    }

    @Test
    public void testCreateNull() throws Exception {
        TestHelper.insert(1);
        assert(!dao.create(null));
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongName() throws Exception {
        TestHelper.insert(1);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName(s);
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongDescription() throws Exception {
        TestHelper.insert(1);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName("VO - Algebra");
        e.setDescription(s);
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongRoom() throws Exception {
        TestHelper.insert(1);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom(s);
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
    }


    @Test(expected = NullPointerException.class)
    public void testCreateStartIsNull() throws Exception {
        TestHelper.insert(1);
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(null,new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(null);
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 13, 45, 0),e.getStop()));
        //e.setStart(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setId(0);
        e.setResult(0);
        assert(e.equals(dao.readById(0)));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateStopIsNull() throws Exception {
        TestHelper.insert(1);
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),null));
        //e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        //e.setStop(null);
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateLvaIsNull() throws Exception {
        TestHelper.insert(1);
        LvaDate e = new LvaDate();
        e.setLva(null);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateLvaIdIsNotExisting() throws Exception {
        TestHelper.insert(1);
        LvaDate e = new LvaDate();
        e.setLva(-1);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
    }

    @Test
    public void testReadById() throws Exception {
        TestHelper.insert(2);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName("VO - Algebra");
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0),new DateTime(2013, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2013, 3, 4, 13, 45, 0));
        //e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        e.setResult(0);
        assert(dao.readById(0).equals(e));
    }

    @Test
    public void testReadByIdIsNull() throws Exception {
        assert(dao.readById(-1)==null);
    }

    @Test
    public void testReadByLva() throws Exception {
        TestHelper.insert(0);
        List<LvaDate> l = dao.readByLva(0);
        for(int i=0; i<l.size()-1; i++) {
            assert(l.get(i).getStart().isBefore(l.get(i+1).getStart().getMillis()) || l.get(i).getStart().equals(l.get(i+1).getStart()));
        }
        assert(l.size()==14);
    }

    @Test
    public void testReadByNotExistingLva() throws Exception {
        TestHelper.insert(0);
        List<LvaDate> l = dao.readByLva(-1);
        assert(l.size()==0);
    }

    @Test
    public void testReadByLvaAndType() throws Exception {
        TestHelper.insert(0);
        List<LvaDate> l = dao.readByLvaAndType(0,LvaDateType.EXAM);
        assert(l.size()==2);
    }

    @Test
    public void testUpdate() throws Exception {
        TestHelper.insert(2);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName("New VO - Algebra");
        e.setDescription("New blablabla über Mathe - VO");
        e.setType(LvaDateType.EXERCISE);
        e.setRoom("NEw HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2014, 3, 4, 12, 15, 0),new DateTime(2014, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(true);
        e.setWasAttendant(false);
        e.setResult(0);
        assert(dao.update(e));
        //e.setTime(new TimeFrame(new DateTime(2014, 3, 4, 13, 45, 0),e.getStop()));
        //e.setStart(new DateTime(2014, 3, 4, 13, 45, 0));
        assert(dao.readById(0).equals(e));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateAllAttributesButTimeAreNull() throws Exception {
        TestHelper.insert(2);
        LvaDate d = dao.readById(0);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(null);
        e.setName(null);
        e.setDescription(null);
        e.setType(null);
        e.setRoom(null);
        e.setTime(new TimeFrame(null,null));
        //e.setStart(null);
        //e.setStop(null);
        e.setAttendanceRequired(null);
        e.setWasAttendant(null);
        e.setResult(null);
        assert(dao.update(e));
        assert(dao.readById(0).equals(d));
    }

    @Test
    public void testUpdateAllAttributesAreNull() throws Exception {
        TestHelper.insert(2);
        LvaDate d = dao.readById(0);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(null);
        e.setName(null);
        e.setDescription(null);
        e.setType(null);
        e.setRoom(null);
        e.setTime(null);
        //e.setStart(null);
        //e.setStop(null);
        e.setAttendanceRequired(null);
        e.setWasAttendant(null);
        e.setResult(null);
        assert(dao.update(e));
        assert(dao.readById(0).equals(d));
    }

    @Test
    public void testUpdateIdIsNull() throws Exception {
        TestHelper.insert(2);
        assert(!dao.update(new LvaDate()));
    }

    @Test
    public void testUpdateNull() throws Exception {
        assert(!dao.update(null));
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongName() throws Exception {
        TestHelper.insert(2);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName(s);
        e.setDescription("New blablabla über Mathe - VO");
        e.setType(LvaDateType.EXERCISE);
        e.setRoom("NEw HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2014, 3, 4, 12, 15, 0),new DateTime(2014, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(true);
        e.setWasAttendant(false);
        e.setResult(0);
        dao.update(e);
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongDescription() throws Exception {
        TestHelper.insert(2);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName("New VO - Algebra");
        e.setDescription(s);
        e.setType(LvaDateType.EXERCISE);
        e.setRoom("NEw HS 17 Friedrich Hartmann");
        e.setTime(new TimeFrame(new DateTime(2014, 3, 4, 12, 15, 0),new DateTime(2014, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(true);
        e.setWasAttendant(false);
        e.setResult(0);
        dao.update(e);
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongRoom() throws Exception {
        TestHelper.insert(2);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName("New VO - Algebra");
        e.setDescription("New blablabla über Mathe - VO");
        e.setType(LvaDateType.EXERCISE);
        e.setRoom(s);
        e.setTime(new TimeFrame(new DateTime(2014, 3, 4, 12, 15, 0),new DateTime(2014, 3, 4, 13, 45, 0)));
        //e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        //e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(true);
        e.setWasAttendant(false);
        e.setResult(0);
        dao.update(e);
    }

    @Test
    public void testDelete() throws Exception {
        TestHelper.insert(2);
        assert(dao.readById(0)!=null);
        assert(dao.delete(0));
        assert(dao.readById(0)==null);
    }
}
