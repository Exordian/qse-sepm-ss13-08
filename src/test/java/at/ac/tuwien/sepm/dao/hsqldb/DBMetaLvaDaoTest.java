package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.Semester;
import junit.framework.Assert;
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
public class DBMetaLvaDaoTest {

    @Autowired
    private DBMetaLvaDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {
        TestHelper.insert(5);
        MetaLVA e0 = new MetaLVA();
        e0.setNr("104.265");
        e0.setName("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik");
        e0.setSemestersOffered(Semester.W_S);
        e0.setType(LvaType.VO);
        e0.setPriority(0f);
        e0.setECTS(4);
        e0.setModule(0);
        e0.setMinTimeEstimate(175);
        e0.setMaxTimeEstimate(1234);

        assert(dao.create(e0));

        MetaLVA e1 = dao.readById(0);
        assert(e1.getId()==0);
        assert(e1.getNr().equals(e0.getNr()));
        assert(e1.getName().equals(e0.getName()));
        assert(e1.getSemestersOffered().equals(e0.getSemestersOffered()));
        assert(e1.getType().equals(e0.getType()));
        assert(e1.getPriority()==e0.getPriority());
        assert(e1.getECTS()==e0.getECTS());
        assert(e1.getLVAs().size()==0);
        assert(e1.getPrecursor().size()==0);
        assert(e1.getModule()==e0.getModule());
        assert(e1.getMinTimeEstimate().intValue()==e0.getMinTimeEstimate().intValue());
        assert(e1.getMaxTimeEstimate().intValue()==e0.getMaxTimeEstimate().intValue());
        Assert.assertEquals(e1.getMinTimeEstimate().intValue(), e0.getMinTimeEstimate().intValue());
        Assert.assertEquals(e1.getMaxTimeEstimate().intValue(), e0.getMaxTimeEstimate().intValue());
    }

    @Test
    public void testCreateCorrectIdShouldBeReturned() throws Exception {
        TestHelper.drop();
        TestHelper.create();

        TestHelper.insert(5);
        MetaLVA e0 = new MetaLVA();
        MetaLVA e1 = new MetaLVA();
        MetaLVA e2 = new MetaLVA();
        MetaLVA e3 = new MetaLVA();

        e0.setNr("104.asdf");
        e0.setName("Name 0");
        e0.setSemestersOffered(Semester.S);
        e0.setType(LvaType.VO);
        e0.setPriority(0f);
        e0.setECTS(4);
        e0.setModule(0);
        e0.setMinTimeEstimate(null);
        e0.setMaxTimeEstimate(1234);

        e1.setNr("104.qwer");
        e1.setName("Name 1");
        e1.setSemestersOffered(Semester.W);
        e1.setType(LvaType.VU);
        e1.setPriority(1f);
        e1.setECTS(5);
        e1.setModule(0);
        e1.setMinTimeEstimate(1234);
        e1.setMaxTimeEstimate(null);

        e2.setNr("104.wert");
        e2.setName("Name 2");
        e2.setSemestersOffered(Semester.W_S);
        e2.setType(LvaType.UE);
        e2.setPriority(2f);
        e2.setECTS(6);
        e2.setModule(0);
        e2.setMinTimeEstimate(12341234);
        e2.setMaxTimeEstimate(1234);

        e3.setNr("104.zrt");
        e3.setName("NAme 3");
        e3.setSemestersOffered(Semester.UNKNOWN);
        e3.setType(LvaType.PR);
        e3.setPriority(3f);
        e3.setECTS(7);
        e3.setModule(0);
        e3.setMinTimeEstimate(1234);
        e3.setMaxTimeEstimate(1234123);

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
    public void testCreateTooLongLvaNumber() throws Exception {
        TestHelper.insert(5);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        MetaLVA e0 = new MetaLVA();
        e0.setNr(s);
        e0.setName("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik");
        e0.setSemestersOffered(Semester.W_S);
        e0.setType(LvaType.VO);
        e0.setPriority(0f);
        e0.setECTS(4);

        dao.create(e0);
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongName() throws Exception {
        TestHelper.insert(5);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        MetaLVA e0 = new MetaLVA();
        e0.setNr("104.265");
        e0.setName(s);
        e0.setSemestersOffered(Semester.W_S);
        e0.setType(LvaType.VO);
        e0.setPriority(0f);
        e0.setECTS(4);

        dao.create(e0);
    }

    @Test
    public void testCreateSemesterIsNull() throws Exception {
        TestHelper.insert(5);

        MetaLVA e0 = new MetaLVA();
        e0.setNr("104.265");
        e0.setName("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik");
        e0.setSemestersOffered(null);
        e0.setType(LvaType.VO);
        e0.setPriority(0f);
        e0.setECTS(4);

        assert(dao.create(e0));

        MetaLVA e1 = dao.readById(0);
        assert(e1.getId()==0);
        assert(e1.getNr().equals(e0.getNr()));
        assert(e1.getName().equals(e0.getName()));
        assert(e1.getSemestersOffered().equals(Semester.UNKNOWN));
        assert(e1.getType().equals(e0.getType()));
        assert(e1.getPriority()==e0.getPriority());
        assert(e1.getECTS()==e0.getECTS());
        assert(e1.getLVAs().size()==0);
        assert(e1.getPrecursor().size()==0);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateLvaTypeIsNull() throws Exception {
        TestHelper.insert(5);

        MetaLVA e0 = new MetaLVA();
        e0.setNr("104.265");
        e0.setName("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik");
        e0.setSemestersOffered(Semester.W_S);
        e0.setType(null);
        e0.setPriority(0f);
        e0.setECTS(4);

        dao.create(e0);
    }

    @Test
    public void testCreateNull() throws Exception {
        assert(!dao.create(null));

    }

    @Test
    public void testSetPredecessor() throws Exception {
        TestHelper.insert(6);
        MetaLVA e0 = dao.readById(0);
        assert(e0.getPrecursor().size()==0);
        assert(dao.setPredecessor(0,1));
        assert(dao.setPredecessor(0,2));
        e0 = dao.readById(0);
        assert(e0.getPrecursor().size()==2);
        assert(e0.getPrecursor().get(0).getId()==1);
        assert(e0.getPrecursor().get(1).getId()==2);
    }

    @Test(expected = DataAccessException.class)
    public void testSetPredecessorNotExistingPredecessor() throws Exception {
        TestHelper.insert(6);
        dao.setPredecessor(-1,0);
    }

    @Test
    public void testUnsetPredecessor() throws Exception {
        TestHelper.insert(8);
        assert(dao.readAllPredecessors(0).size()==4);
        assert(dao.unsetPredecessor(0,8));
        assert(dao.readAllPredecessors(0).size()==3);
        assert(dao.unsetPredecessor(0,7));
        assert(dao.readAllPredecessors(0).size()==2);
        assert(dao.unsetPredecessor(0,6));
        assert(dao.readAllPredecessors(0).size()==1);
        assert(dao.unsetPredecessor(0,5));
        assert(dao.readAllPredecessors(0).size()==0);
    }

    @Test(expected = DataAccessException.class)
    public void testUnsetPredecessorNotExistingPredecessor() throws Exception {
        TestHelper.insert(8);
        dao.setPredecessor(-1,0);
    }

    @Test(expected = DataAccessException.class)
    public void testUnsetPredecessorNotExistingLva() throws Exception {
        TestHelper.insert(8);
        dao.setPredecessor(0,-1);
    }

    @Test
    public void testReadById() throws Exception {
        TestHelper.insert(0);
        MetaLVA e0 = dao.readById(0);
        assert(e0.getId()==0);
        assert(e0.getNr().equals("104.265"));
        assert(e0.getName().equals("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik"));
        assert(e0.getSemestersOffered().equals(Semester.W_S));
        assert(e0.getType().equals(LvaType.VO));
        assert(e0.getPriority()==0f);
        assert(e0.getECTS()==4);

        assert(e0.getLVAs().size()==1);
        assert(e0.getLVAs().get(0).getLectures().size()==12);
        assert(e0.getLVAs().get(0).getExams().size()==2);
        assert(e0.getLVAs().get(0).getExercises().size()==0);

        assert(e0.getPrecursor().size()==1);
        assert(e0.getPrecursor().get(0).getId()==8);

        assert(e0.getLVAs().get(0).getMetaLVA().getId()==0);
        assert(e0.getLVAs().get(0).getMetaLVA().getNr().equals("104.265"));
        assert(e0.getLVAs().get(0).getMetaLVA().getName().equals("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik"));
        assert(e0.getLVAs().get(0).getMetaLVA().getSemestersOffered().equals(Semester.W_S));
        assert(e0.getLVAs().get(0).getMetaLVA().getType().equals(LvaType.VO));
        assert(e0.getLVAs().get(0).getMetaLVA().getPriority()==0f);
        assert(e0.getLVAs().get(0).getMetaLVA().getECTS()==e0.getECTS());
        assert(e0.getLVAs().get(0).getMetaLVA().getLVAs()==null);
        assert(e0.getLVAs().get(0).getMetaLVA().getPrecursor().size()==0);
    }

    @Test
    public void testReadByNotExistingId() throws Exception {
        assert(dao.readById(-1)==null);
    }

    @Test
    public void testReadByIdWithoutLvaAndPrecursor() throws Exception {
        TestHelper.insert(0);
        MetaLVA e0 = dao.readByIdWithoutLvaAndPrecursor(0);
        assert(e0.getId()==0);
        assert(e0.getNr().equals("104.265"));
        assert(e0.getName().equals("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik"));
        assert(e0.getSemestersOffered().equals(Semester.W_S));
        assert(e0.getType().equals(LvaType.VO));
        assert(e0.getPriority()==0f);
        assert(e0.getECTS()==4);
        assert(e0.getLVAs()==null);
        assert(e0.getPrecursor().size()==0);
    }

    @Test
    public void testReadByNotExistingIdWithoutLvaAndPrecursor() throws Exception {
        assert(dao.readByIdWithoutLvaAndPrecursor(-1)==null);
    }

    @Test
    public void testReadByLvaNumber() throws Exception {
        TestHelper.insert(0);
        MetaLVA e0 = dao.readByLvaNumber("104.265");
        assert(e0.getId()==0);
        assert(e0.getNr().equals("104.265"));
        assert(e0.getName().equals("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik"));
        assert(e0.getSemestersOffered().equals(Semester.W_S));
        assert(e0.getType().equals(LvaType.VO));
        assert(e0.getPriority()==0f);
        assert(e0.getECTS()==4);
        assert(e0.getMinTimeEstimate()==1234);
        assert(e0.getMaxTimeEstimate()==1234);

        assert(e0.getLVAs().size()==1);
        assert(e0.getLVAs().get(0).getLectures().size()==12);
        assert(e0.getLVAs().get(0).getExams().size()==2);
        assert(e0.getLVAs().get(0).getExercises().size()==0);

        assert(e0.getPrecursor().size()==1);
        assert(e0.getPrecursor().get(0).getId()==8);

        assert(e0.getLVAs().get(0).getMetaLVA().getId()==0);
        assert(e0.getLVAs().get(0).getMetaLVA().getNr().equals("104.265"));
        assert(e0.getLVAs().get(0).getMetaLVA().getName().equals("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik"));
        assert(e0.getLVAs().get(0).getMetaLVA().getSemestersOffered().equals(Semester.W_S));
        assert(e0.getLVAs().get(0).getMetaLVA().getType().equals(LvaType.VO));
        assert(e0.getLVAs().get(0).getMetaLVA().getPriority()==0f);
        assert(e0.getLVAs().get(0).getMetaLVA().getECTS()==e0.getECTS());
        assert(e0.getLVAs().get(0).getMetaLVA().getLVAs()==null);
        assert(e0.getLVAs().get(0).getMetaLVA().getPrecursor().size()==0);
    }

    @Test
    public void testReadByLvaNumberIsNull() throws Exception {
        assert(dao.readByLvaNumber(null)==null);
    }

    @Test
    public void testReadByModule() throws Exception {
        TestHelper.insert(0);
        List<MetaLVA> l = dao.readByModule(0);
        assert(l.size()==2);
        assert(l.get(0).getId()==0);
        assert(l.get(1).getId()==1);
    }

    @Test
    public void testReadUncompletedByYearSemesterStudyProgress() throws Exception {
        TestHelper.insert(14);

        List<MetaLVA> l0 = dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.S, true);
        List<MetaLVA> l1 = dao.readUncompletedByYearSemesterStudyProgress(2013, Semester.S, false);

        assert(l0.size()==2);
        assert(l1.size()==3);

        assert(l0.get(0).getId()==2);
        assert(l0.get(1).getId()==3);

        assert(l1.get(0).getId()==1);
        assert(l1.get(1).getId()==4);
        assert(l1.get(2).getId()==8);

        assert(l0.get(0).getLVAs().size()==1);
        assert(l0.get(1).getLVAs().size()==1);

        assert(l1.get(0).getLVAs().size()==1);
        assert(l1.get(1).getLVAs().size()==1);
        assert(l1.get(2).getLVAs().size()==1);

        assert(l0.get(0).getLVAs().get(0).getId()==10);
        assert(l0.get(1).getLVAs().get(0).getId()==11);

        assert(l1.get(0).getLVAs().get(0).getId()==9);
        assert(l1.get(1).getLVAs().get(0).getId()==12);
        assert(l1.get(2).getLVAs().get(0).getId()==15);
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

    /*@Test
    public void readNotCompletedByYearSemesterStudProgress() throws Exception {
        TestHelper.insert(12);
        List<MetaLVA> l0 = dao.readNotCompletedByYearSemesterStudProgress(2013, Semester.S, true);
        List<MetaLVA> l1 = dao.readNotCompletedByYearSemesterStudProgress(2013, Semester.S, false);

        assert(l0.size()==4);
        assert(l1.size()==4);
        assert(l0.get(0).getId()==1);
        assert(l0.get(1).getId()==3);
        assert(l0.get(2).getId()==4);
        assert(l0.get(3).getId()==5);

        assert(l1.get(0).getId()==0);
        assert(l1.get(1).getId()==2);
        assert(l1.get(2).getId()==7);
        assert(l1.get(3).getId()==8);

        assert(l0.get(0).getLVAs().size()==1);
        assert(l0.get(1).getLVAs().size()==1);
        assert(l0.get(2).getLVAs().size()==1);
        assert(l0.get(3).getLVAs().size()==1);

        assert(l0.get(0).getLVAs().get(0).getId()==1);
        assert(l0.get(1).getLVAs().get(0).getId()==3);
        assert(l0.get(2).getLVAs().get(0).getId()==4);
        assert(l0.get(3).getLVAs().get(0).getId()==5);

        assert(l1.get(0).getLVAs().size()==1);
        assert(l1.get(1).getLVAs().size()==1);
        assert(l1.get(2).getLVAs().size()==1);
        assert(l1.get(3).getLVAs().size()==1);

        assert(l1.get(0).getLVAs().get(0).getId()==0);
        assert(l1.get(1).getLVAs().get(0).getId()==2);
        assert(l1.get(2).getLVAs().get(0).getId()==6);
        assert(l1.get(3).getLVAs().get(0).getId()==7);
    }

    @Test
    public void readNotCompletedByNotExistingYearSemesterStudProgress() throws Exception {
        TestHelper.insert(12);
        assert(dao.readNotCompletedByYearSemesterStudProgress(-1, Semester.S, true).size()==0);

    }

    @Test
    public void readNotCompletedByYearNotExistingSemesterStudProgress() throws Exception {
        TestHelper.insert(12);
        assert(dao.readNotCompletedByYearSemesterStudProgress(2013, Semester.UNKNOWN, true)== null);
    }

    @Test(expected = NullPointerException.class)
    public void readNotCompletedByYearSemesterIsNullStudProgress() throws Exception {
        TestHelper.insert(12);
        dao.readNotCompletedByYearSemesterStudProgress(2013, null, true);
    }*/

    @Test
    public void testUpdate() throws Exception {
        TestHelper.insert(0);
        MetaLVA e0 = new MetaLVA();
        e0.setId(0);
        e0.setNr("new number");
        e0.setName("new name");
        e0.setSemestersOffered(Semester.S);
        e0.setType(LvaType.UE);
        e0.setPriority(10f);
        e0.setECTS(10);
        e0.setModule(2);
        e0.setMinTimeEstimate(234);
        e0.setMaxTimeEstimate(12341234);

        assert(dao.update(e0));

        MetaLVA e1 = dao.readById(0);
        assert(e1.getId()==0);
        assert(e1.getNr().equals(e0.getNr()));
        assert(e1.getName().equals(e0.getName()));
        assert(e1.getSemestersOffered().equals(e0.getSemestersOffered()));
        assert(e1.getType().equals(e0.getType()));
        assert(e1.getPriority()==e0.getPriority());
        assert(e1.getECTS()==e0.getECTS());
        assert(e1.getLVAs().size()==1);
        assert(e1.getPrecursor().size()==1);
        assert(e1.getModule()==e0.getModule());
        assert(e1.getMinTimeEstimate().intValue()==e0.getMinTimeEstimate().intValue());
        assert(e1.getMaxTimeEstimate().intValue()==e0.getMaxTimeEstimate().intValue());
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongLvaNumber() throws Exception {
        TestHelper.insert(0);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        MetaLVA e0 = new MetaLVA();
        e0.setId(0);
        e0.setNr(s);
        e0.setName("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik");
        e0.setSemestersOffered(Semester.W_S);
        e0.setType(LvaType.VO);
        e0.setPriority(0f);
        e0.setECTS(4);

        dao.update(e0);
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongName() throws Exception {
        TestHelper.insert(0);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        MetaLVA e0 = new MetaLVA();
        e0.setId(0);
        e0.setNr("104.265");
        e0.setName(s);
        e0.setSemestersOffered(Semester.W_S);
        e0.setType(LvaType.VO);
        e0.setPriority(0f);
        e0.setECTS(4);

        dao.update(e0);
    }

    @Test
    public void testUpdateNull() throws Exception {
        assert(!dao.create(null));
    }

    @Test
    public void testReadByNotExistingModule() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByModule(-1).size()==0);
    }
}