package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.LvaType;
import org.joda.time.DateTime;
import org.junit.After;
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
 * Author: MUTH Markus
 * Date: 5/25/13
 * Time: 11:36 AM
 * Description of class "DBLvaDateDaoTest":
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBLvaDateDaoTest {

    @Autowired
    private DBLvaDateDao dao;

    @Autowired(required = true)
    private TestHelper helper;

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
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
        e.setId(0);
        e.setResult(0);
        assert(dao.readById(0).equals(e));
    }

    @Test
    public void testCreateNull() throws Exception {
        TestHelper.insert(1);
        assert(dao.create(null)==false);
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongName() throws Exception {
        TestHelper.insert(1);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        System.out.println("############## " + s.length());
        LvaDate e = new LvaDate();
        e.setLva(0);
        e.setName(s);
        e.setDescription("blablabla über Mathe - VO");
        e.setType(LvaDateType.LECTURE);
        e.setRoom("HS 17 Friedrich Hartmann");
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
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
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
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
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
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
        e.setStart(null);
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(false);
        e.setWasAttendant(true);
        dao.create(e);
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
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(null);
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
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
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
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
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
        e.setStart(new DateTime(2013, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2013, 3, 4, 13, 45, 0));
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
        LvaDate d = dao.readById(0);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName("New VO - Algebra");
        e.setDescription("New blablabla über Mathe - VO");
        e.setType(LvaDateType.EXERCISE);
        e.setRoom("NEw HS 17 Friedrich Hartmann");
        e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
        e.setAttendanceRequired(true);
        e.setWasAttendant(false);
        e.setResult(0);
        assert(dao.update(e)==true);
        assert(dao.readById(0).equals(e));
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
        e.setStart(null);
        e.setStop(null);
        e.setAttendanceRequired(null);
        e.setWasAttendant(null);
        e.setResult(null);
        assert(dao.update(e)==true);
        assert(dao.readById(0).equals(d));
    }

    @Test
    public void testUpdateIdIsNull() throws Exception {
        TestHelper.insert(2);
        assert(dao.update(new LvaDate())==false);
    }

    @Test
    public void testUpdateNull() throws Exception {
        assert( dao.update(null)==false);
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongName() throws Exception {
        TestHelper.insert(2);
        String s="aa";
        for(int i=0; i<10; i++){
            s=s.concat(s);
        }
        LvaDate d = dao.readById(0);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName(s);
        e.setDescription("New blablabla über Mathe - VO");
        e.setType(LvaDateType.EXERCISE);
        e.setRoom("NEw HS 17 Friedrich Hartmann");
        e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
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
        LvaDate d = dao.readById(0);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName("New VO - Algebra");
        e.setDescription(s);
        e.setType(LvaDateType.EXERCISE);
        e.setRoom("NEw HS 17 Friedrich Hartmann");
        e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
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
        LvaDate d = dao.readById(0);
        LvaDate e = new LvaDate();
        e.setId(0);
        e.setLva(0);
        e.setName("New VO - Algebra");
        e.setDescription("New blablabla über Mathe - VO");
        e.setType(LvaDateType.EXERCISE);
        e.setRoom(s);
        e.setStart(new DateTime(2014, 3, 4, 12, 15, 0));
        e.setStop(new DateTime(2014, 3, 4, 13, 45, 0));
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
