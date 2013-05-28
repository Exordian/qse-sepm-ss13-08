package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LvaType;
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
        assert(e0.getLVAs().get(0).getTimes().size()==12);
        assert(e0.getLVAs().get(0).getTimesExam().size()==2);
        assert(e0.getLVAs().get(0).getTimesUE().size()==0);

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

        assert(e0.getLVAs().size()==1);
        assert(e0.getLVAs().get(0).getTimes().size()==12);
        assert(e0.getLVAs().get(0).getTimesExam().size()==2);
        assert(e0.getLVAs().get(0).getTimesUE().size()==0);

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