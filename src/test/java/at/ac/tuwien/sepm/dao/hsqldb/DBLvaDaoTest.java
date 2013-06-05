package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
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
public class DBLvaDaoTest {

    @Autowired
    private DBLvaDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {
        TestHelper.insert(3);

        LVA e0 = new LVA();
        e0.setId(-1234);
        MetaLVA m0 = new MetaLVA();
        m0.setId(0);
        e0.setMetaLVA(m0);
        e0.setYear(2000);
        e0.setSemester(Semester.S);
        e0.setDescription("Description0");
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        LVA e1 = new LVA();
        e1.setId(5);
        MetaLVA m1 = new MetaLVA();
        m1.setId(4);
        e1.setMetaLVA(m1);
        e1.setYear(2005);
        e1.setSemester(Semester.W);
        e1.setDescription("Description1");
        e1.setGrade(1);
        e1.setInStudyProgress(false);

        assert(dao.create(e0));
        assert(dao.create(e1));

        LVA l0 = dao.readById(0);
        LVA l1 = dao.readById(1);
        e0.setId(0);
        e1.setId(1);

        assert(l0.getId()==0);
        assert(l0.getMetaLVA().getId()==0);
        assert(l0.getYear()==2000);
        assert(l0.getSemester().equals(Semester.S));
        assert(l0.getDescription().equals("Description0"));
        assert(l0.getGrade()==0);
        assert(l0.isInStudyProgress());assert(l0.getId()==0);

        assert(l1.getId()==1);
        assert(l1.getMetaLVA().getId()==4);
        assert(l1.getYear()==2005);
        assert(l1.getSemester().equals(Semester.W));
        assert(l1.getDescription().equals("Description1"));
        assert(l1.getGrade()==1);
        assert(!l1.isInStudyProgress());
    }

    @Test
    public void testCreateCorrectIdShouldBeReturned() throws Exception {
        TestHelper.insert(3);

        LVA e0 = new LVA();
        LVA e1 = new LVA();
        LVA e2 = new LVA();
        LVA e3 = new LVA();

        e0.setId(-4);
        MetaLVA m0 = new MetaLVA();
        m0.setId(0);
        e0.setMetaLVA(m0);
        e0.setYear(2000);
        e0.setSemester(Semester.S);
        e0.setDescription("Description0");
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        e1.setId(-45);
        MetaLVA m1 = new MetaLVA();
        m1.setId(1);
        e1.setMetaLVA(m0);
        e1.setYear(2001);
        e1.setSemester(Semester.W);
        e1.setDescription("Description1");
        e1.setGrade(1);
        e1.setInStudyProgress(true);

        e2.setId(-13);
        MetaLVA m2 = new MetaLVA();
        m2.setId(2);
        e2.setMetaLVA(m0);
        e2.setYear(2002);
        e2.setSemester(Semester.W);
        e2.setDescription("Description2");
        e2.setGrade(2);
        e2.setInStudyProgress(false);

        e3.setId(-1234);
        MetaLVA m3 = new MetaLVA();
        m3.setId(3);
        e3.setMetaLVA(m0);
        e3.setYear(2003);
        e3.setSemester(Semester.S);
        e3.setDescription("Description3");
        e3.setGrade(3);
        e3.setInStudyProgress(true);



        assert(dao.create(e0));
        assert(dao.create(e1));
        assert(dao.create(e2));
        assert(dao.create(e3));

        assert(e0.getId()==0);
        assert(e1.getId()==1);
        assert(e2.getId()==2);
        assert(e3.getId()==3);
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongDescription() throws Exception {
        TestHelper.insert(3);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        LVA e0 = new LVA();
        e0.setId(12341234);
        MetaLVA m0 = new MetaLVA();
        m0.setId(0);
        e0.setMetaLVA(m0);
        e0.setYear(2000);
        e0.setSemester(Semester.S);
        e0.setDescription(s);
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        dao.create(e0);
    }

    @Test
    public void testCreateNull() throws Exception {
        assert(!dao.create(null));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateSemesterIsNull() throws Exception {
        TestHelper.insert(3);

        LVA e0 = new LVA();
        e0.setId(34256);
        MetaLVA m0 = new MetaLVA();
        m0.setId(0);
        e0.setMetaLVA(m0);
        e0.setYear(2000);
        e0.setSemester(null);
        e0.setDescription("Description0");
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        dao.create(e0);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateMetaLvaIsNull() throws Exception {
        TestHelper.insert(3);

        LVA e0 = new LVA();
        e0.setId(2345);
        e0.setMetaLVA(null);
        e0.setYear(2000);
        e0.setSemester(Semester.S);
        e0.setDescription("Description0");
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        dao.create(e0);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateNotExistingMetaLva() throws Exception {
        TestHelper.insert(3);

        LVA e0 = new LVA();
        e0.setId(456);
        MetaLVA m0 = new MetaLVA();
        m0.setId(-5);
        e0.setMetaLVA(m0);
        e0.setYear(2000);
        e0.setSemester(Semester.S);
        e0.setDescription("Description0");
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        dao.create(e0);
    }

    /**
     * The chronological order of the dates is not tested, because it is tested in <code>DBLvaDateDaoTest</code>.
     */
    @Test
    public void testReadById() throws Exception {
        TestHelper.insert(0);
        LVA e = dao.readById(2);
        assert(e.getId()==2);
        assert(e.getMetaLVA().getId()==2);
        assert(e.getYear()==2013);
        assert(e.getSemester().equals(Semester.S));
        assert(e.getDescription().equals("Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 1"));
        assert(!e.isInStudyProgress());
        assert(e.getGrade()==0);

        assert(e.getLectures().size()==18);
        //assert(e.getRooms().size()==18);
        assert(e.getExercises().size()==4);
        //assert(e.getRoomsUE().size()==4);
        assert(e.getExams().size()==3);
        //assert(e.getRoomsExam().size()==3);
        //assert(e.getExamGrade().size()==3);
    }

    @Test
    public void testReadByIdNoStoredDates() throws Exception {
        TestHelper.insert(0);
        LVA e = dao.readById(2);
        assert(e.getId()==2);
        assert(e.getMetaLVA().getId()==2);
        assert(e.getYear()==2013);
        assert(e.getSemester().equals(Semester.S));
        assert(e.getDescription().equals("Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 1"));
        assert(!e.isInStudyProgress());
        assert(e.getGrade()==0);

        assert(e.getLectures().size()==18);
        //assert(e.getRooms().size()==18);
        assert(e.getExercises().size()==4);
        //assert(e.getRoomsUE().size()==4);
        assert(e.getExams().size()==3);
        //assert(e.getRoomsExam().size()==3);
        //assert(e.getExamGrade().size()==3);
    }

    @Test
    public void testReadByNotExistingId() throws Exception {
       TestHelper.insert(0);
       assert(dao.readById(-1)==null);
    }

    @Test
    public void testReadByMetaLva() throws Exception {
        TestHelper.insert(7);
        List<LVA> l = dao.readByMetaLva(0);
        assert(l.size()==4);
        assert(l.get(0).getId()==10);
        assert(l.get(1).getId()==2);
        assert(l.get(2).getId()==1);
        assert(l.get(3).getId()==0);
    }

    @Test
    public void testReadByNotExistingMetaLva() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByMetaLva(-1).size()==0);
    }

    @Test
    public void testReadByIdWithoutLvaDates() throws Exception {
        TestHelper.insert(0);
        LVA e = dao.readByIdWithoutLvaDates(2);
        assert(e.getId()==2);
        assert(e.getMetaLVA().getId()==2);
        assert(e.getYear()==2013);
        assert(e.getSemester().equals(Semester.S));
        assert(e.getDescription().equals("Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 1"));
        assert(!e.isInStudyProgress());
        assert(e.getGrade()==0);
        assert(e.getLectures()==null);
        //assert(e.getRooms()==null);
        assert(e.getExercises()==null);
        //assert(e.getRoomsUE()==null);
        assert(e.getExams()==null);
        //assert(e.getRoomsExam()==null);
        //assert(e.getExamGrade()==null);
    }

    @Test
    public void testReadByIdWithoutLvaDatesNotExistingId() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByIdWithoutLvaDates(-1)==null);
    }

    /**
     * The method <code>reaUncompletedByYearSemesterStudyProgress()</code> calls internal the method
     * <code>readById()</code>, so there is only tested if the id and the amount of returned lvas is correct, and not if
     * the returned lvas itself are correct. The correctness of the method <code>readById()</code> is tested in its own
     * test methods.
     */
    @Test
    public void testReadUncompletedByYearSemesterStudyProgress() throws Exception {
        TestHelper.insert(14);

        List<LVA> l0 = dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.S, true);
        List<LVA> l1 = dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.S, false);

        assert(l0.size()==2);
        assert(l1.size()==3);

        assert(l0.get(0).getId()==10);
        assert(l0.get(1).getId()==11);

        assert(l1.get(0).getId()==9);
        assert(l1.get(1).getId()==12);
        assert(l1.get(2).getId()==15);
    }

    @Test
    public void testReadUncompletedByNotExistingYearSemesterStudyProgress() throws Exception {
        TestHelper.insert(14);
        assert(dao.readUncompletedByYearSemesterStudyProgress(2000, Semester.S, true).size()==0);
    }

    @Test
    public void testReadUncompletedByYearInvalidSemesterStudyProgress() throws Exception {
        TestHelper.insert(14);
        assert(dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.UNKNOWN, true)==null);
        assert(dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.W_S, true)==null);
        assert(dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.S, true)!=null);
        assert(dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.W, true)!=null);
    }

    @Test
    public void testReadUncompletedByYearNotExistingSemesterStudyProgress() throws Exception {
        TestHelper.insert(14);
        assert(dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.W, true).size()==0);
    }

    @Test(expected = NullPointerException.class)
    public void testReadUncompletedByYearSemesterIsNullStudyProgress() throws Exception {
        TestHelper.insert(14);
        dao.readUncompletedByYearSemesterStudyProgress(2013, null, true);
    }

    /*
    @Test
    public void testReadByName() throws Exception {
        TestHelper.insert(0);
        LVA e = dao.readByName(2);
        assert(e.getId()==2);
        assert(e.getMetaLVA().getId()==2);
        assert(e.getYear()==2013);
        assert(e.getSemester().equals(Semester.S));
        assert(e.getDescription().equals("Hier steht dann die Beschreibung zu Algorithmen und Datenstrukturen 1"));
        assert(!e.isInStudyProgress());
        assert(e.getGrade()==0);
        assert(e.getLectures().size()==18);
        assert(e.getRooms().size()==18);
        assert(e.getExercises().size()==4);
        assert(e.getRoomsUE().size()==4);
        assert(e.getExams().size()==3);
        assert(e.getRoomsExam().size()==3);
        assert(e.getExamGrade().size()==3);
    }*/

    /*
    @Test
    public void testReadByNameIsNull() throws Exception {

    }
    */

    /**
     * The method <code>readByYearSemester()</code> calls internal the method <code>readById()</code>, so there is only
     * tested if the amount of returned lvas is correct, and not if the returned lvas itself are correct. The correctness
     * of the method <code>readById()</code> is tested in its own test methods.
     */
    @Test
    public void testReadByYearSemester() throws Exception {
        TestHelper.insert(0);
        List<LVA> list = dao.readByYearAndSemester(2013, false);
        assert(list.size()==8);
    }

    /*
     * The method <code>readNotCompletedByYearSemesterStudyProgress</code> calls internal the method <code>readById()</code>
     * in <code>LVA</code>, so here is only tested if the ids of the returned lvas are correct.
     */
    /*@Test
    public void readNotCompletedByYearSemesterStudProgress() throws Exception {
        TestHelper.insert(12);
        List<LVA> l0 = dao.readNotCompletedByYearSemesterStudyProgress(2013, Semester.S, true);
        List<LVA> l1 = dao.readNotCompletedByYearSemesterStudyProgress(2013, Semester.S, false);
        assert(l0.size()==4);
        assert(l1.size()==4);
        assert(l0.get(0).getId()==1);
        assert(l0.get(1).getId()==3);
        assert(l0.get(2).getId()==4);
        assert(l0.get(3).getId()==5);
        assert(l1.get(0).getId()==0);
        assert(l1.get(1).getId()==2);
        assert(l1.get(2).getId()==6);
        assert(l1.get(3).getId()==7);
    }

    @Test
    public void readNotCompletedByNotExistingYearSemesterStudProgress() throws Exception {
        TestHelper.insert(12);
        assert(dao.readNotCompletedByYearSemesterStudyProgress(-1, Semester.S, true).size()==0);

    }

    @Test
    public void readNotCompletedByYearNotExistingSemesterStudProgress() throws Exception {
        TestHelper.insert(12);
        assert(dao.readNotCompletedByYearSemesterStudyProgress(2013, Semester.UNKNOWN, true)==null);
    }

    @Test(expected = NullPointerException.class)
    public void readNotCompletedByYearSemesterIsNullStudProgress() throws Exception {
        TestHelper.insert(12);
        dao.readNotCompletedByYearSemesterStudyProgress(2013, null, true);
    }*/

    @Test
    public void testUpdate() throws Exception {
        TestHelper.insert(4);

        LVA e0 = new LVA();
        e0.setId(0);
        MetaLVA m0 = new MetaLVA();
        m0.setId(2);
        e0.setMetaLVA(m0);
        e0.setYear(3000);
        e0.setSemester(Semester.W);
        e0.setDescription("asdf");
        e0.setGrade(5);
        e0.setInStudyProgress(false);

        dao.update(e0);

        LVA l0 = dao.readById(0);

        assert(l0.getId()==0);
        assert(l0.getMetaLVA().getId()==2);
        assert(l0.getYear()==3000);
        assert(l0.getSemester().equals(Semester.W));
        assert(l0.getDescription().equals("asdf"));
        assert(l0.getGrade()==5);
        assert(!l0.isInStudyProgress());
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongDescription() throws Exception {
        TestHelper.insert(4);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        LVA e0 = new LVA();
        e0.setId(12341234);
        MetaLVA m0 = new MetaLVA();
        m0.setId(0);
        e0.setMetaLVA(m0);
        e0.setYear(2000);
        e0.setSemester(Semester.S);
        e0.setDescription(s);
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        dao.update(e0);
    }

    @Test
    public void testUpdateNull() throws Exception {
        assert(!dao.create(null));
    }

    /*@Test(expected = DataAccessException.class)
    public void testUpdateNotExistingMetaLva() throws Exception {
        TestHelper.insert(4);

        LVA e0 = new LVA();
        e0.setId(0);
        MetaLVA m0 = new MetaLVA();
        m0.setId(-5);
        e0.setMetaLVA(m0);
        e0.setYear(2000);
        e0.setSemester(null);
        e0.setDescription("Description0");
        e0.setGrade(0);
        e0.setInStudyProgress(true);

        assert(dao.update(e0));
    }*///todo repair this test
}
