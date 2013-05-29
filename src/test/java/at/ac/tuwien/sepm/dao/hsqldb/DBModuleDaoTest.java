package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Markus MUTH
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBModuleDaoTest {

    @Autowired
    private DBModuleDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();

    }

    @Test
    public void testCreate() throws Exception {
        TestHelper.insert(9);

        Module e0 = new Module();
        e0.setId(1234);
        e0.setName("Algebra und Diskrete Mathematik");
        e0.setDescription("Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweist...");
        e0.setCompleteall(true);
        MetaLVA l1 = new MetaLVA();
        MetaLVA l2 = new MetaLVA();
        ArrayList<MetaLVA> lvaList = new ArrayList<MetaLVA>();
        lvaList.add(l1);
        lvaList.add(l2);
        e0.setMetaLvas(lvaList);

        assert(dao.create(e0));

        Module e1 = dao.readById(0);
        e0.setId(0);

        assert(e1.equals(e0));
        assert(e1.getMetaLvas().size()==0);
    }

    @Test
    public void testCreateNull() throws Exception {
        TestHelper.insert(0);
        assert(!dao.create(null));
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongName() throws Exception {
        TestHelper.insert(9);

        String s = "aa";
        for(int i=0; i<12; i++) {
            s=s.concat(s);
        }

        Module e0 = new Module();
        e0.setId(1234);
        e0.setName(s);
        e0.setDescription("Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweist...");
        e0.setCompleteall(true);
        MetaLVA l1 = new MetaLVA();
        MetaLVA l2 = new MetaLVA();
        ArrayList<MetaLVA> lvaList = new ArrayList<MetaLVA>();
        lvaList.add(l1);
        lvaList.add(l2);
        e0.setMetaLvas(lvaList);

        dao.create(e0);
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongDescription() throws Exception {
        TestHelper.insert(9);

        String s = "aa";
        for(int i=0; i<12; i++) {
            s=s.concat(s);
        }

        Module e0 = new Module();
        e0.setId(1234);
        e0.setName("Algebra und Diskrete Mathematik");
        e0.setDescription(s);
        e0.setCompleteall(true);
        MetaLVA l1 = new MetaLVA();
        MetaLVA l2 = new MetaLVA();
        ArrayList<MetaLVA> lvaList = new ArrayList<MetaLVA>();
        lvaList.add(l1);
        lvaList.add(l2);
        e0.setMetaLvas(lvaList);

        dao.create(e0);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateNameIsNull() throws Exception {
        TestHelper.insert(9);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Module e0 = new Module();
        e0.setId(1234);
        e0.setName(null);
        e0.setDescription("Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweist...");
        e0.setCompleteall(true);
        MetaLVA l1 = new MetaLVA();
        MetaLVA l2 = new MetaLVA();
        ArrayList<MetaLVA> lvaList = new ArrayList<MetaLVA>();
        lvaList.add(l1);
        lvaList.add(l2);
        e0.setMetaLvas(lvaList);

        dao.create(e0);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateCompleteAllIsNull() throws Exception {
        TestHelper.insert(9);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Module e0 = new Module();
        e0.setId(1234);
        e0.setName("Algebra und Diskrete Mathematik");
        e0.setDescription("Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweist...");
        e0.setCompleteall(null);
        MetaLVA l1 = new MetaLVA();
        MetaLVA l2 = new MetaLVA();
        ArrayList<MetaLVA> lvaList = new ArrayList<MetaLVA>();
        lvaList.add(l1);
        lvaList.add(l2);
        e0.setMetaLvas(lvaList);

        dao.create(e0);
    }

    @Test
    public void testReadById() throws Exception {
        TestHelper.insert(0);

        Module e0 = new Module();
        e0.setId(0);
        e0.setName("Algebra und Diskrete Mathematik");
        e0.setDescription("Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweistechniken und Sätze in den Teilgebieten Algebra (v.a. algebraische Strukturen und lineare Algebra) und Diskrete Mathematik (v.a Kombinatorik und Graphentheorie). Es setzt sich aus einem Vorlesungsteil und einem begleitenden Übungsteil zusammen, der der Vertiefung der Vorlesungsinhalte und der Entwicklung von Fertigkeiten zur Erstellung korrekter mathematischer Beweise und der mathematischen Modellierung und Analyse praktischer Problemstellungen dient.");
        e0.setCompleteall(true);
        MetaLVA l1 = new MetaLVA();
        l1.setId(0);
        MetaLVA l2 = new MetaLVA();
        l2.setId(1);
        ArrayList<MetaLVA> lvaList = new ArrayList<MetaLVA>();
        lvaList.add(l1);
        lvaList.add(l2);
        e0.setMetaLvas(lvaList);

        Module e1 = dao.readById(0);
        assert(e1.equals(e0));
        assert(e1.getMetaLvas().size()==2);
        assert(e1.getMetaLvas().get(0).getId()==0);
        assert(e1.getMetaLvas().get(1).getId()==1);
        assert(e1.getMetaLvas().get(0).getPrecursor().size()==1);
        assert(e1.getMetaLvas().get(1).getPrecursor().size()==1);
        assert(e1.getMetaLvas().get(0).getPrecursor().get(0).getId()==8);
        assert(e1.getMetaLvas().get(1).getPrecursor().get(0).getId()==8);
        assert(e1.getMetaLvas().get(0).getLVAs().size()==1);
        assert(e1.getMetaLvas().get(1).getLVAs().size()==1);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getId()==0);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getId()==1);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getLectures().size()==12);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getExercises().size()==0);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getExams().size()==2);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getLectures().size()==0);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getExercises().size()==12);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getExams().size()==0);
    }

    @Test
    public void testReadByNotExistingId() throws Exception {
        TestHelper.insert(0);
        assert(dao.readById(-1)==null);
    }

    @Test
    public void testReadByName() throws Exception {
        TestHelper.insert(0);

        Module e0 = new Module();
        e0.setId(0);
        e0.setName("Algebra und Diskrete Mathematik");
        e0.setDescription("Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweistechniken und Sätze in den Teilgebieten Algebra (v.a. algebraische Strukturen und lineare Algebra) und Diskrete Mathematik (v.a Kombinatorik und Graphentheorie). Es setzt sich aus einem Vorlesungsteil und einem begleitenden Übungsteil zusammen, der der Vertiefung der Vorlesungsinhalte und der Entwicklung von Fertigkeiten zur Erstellung korrekter mathematischer Beweise und der mathematischen Modellierung und Analyse praktischer Problemstellungen dient.");
        e0.setCompleteall(true);
        MetaLVA l1 = new MetaLVA();
        l1.setId(0);
        MetaLVA l2 = new MetaLVA();
        l2.setId(1);
        ArrayList<MetaLVA> lvaList = new ArrayList<MetaLVA>();
        lvaList.add(l1);
        lvaList.add(l2);
        e0.setMetaLvas(lvaList);

        Module e1 = dao.readByName("Algebra und Diskrete Mathematik");

        assert(e1.equals(e0));
        assert(e1.getMetaLvas().size()==2);
        assert(e1.getMetaLvas().get(0).getId()==0);
        assert(e1.getMetaLvas().get(1).getId()==1);
        assert(e1.getMetaLvas().get(0).getPrecursor().size()==1);
        assert(e1.getMetaLvas().get(1).getPrecursor().size()==1);
        assert(e1.getMetaLvas().get(0).getPrecursor().get(0).getId()==8);
        assert(e1.getMetaLvas().get(1).getPrecursor().get(0).getId()==8);
        assert(e1.getMetaLvas().get(0).getLVAs().size()==1);
        assert(e1.getMetaLvas().get(1).getLVAs().size()==1);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getId()==0);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getId()==1);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getLectures().size()==12);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getExercises().size()==0);
        assert(e1.getMetaLvas().get(0).getLVAs().get(0).getExams().size()==2);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getLectures().size()==0);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getExercises().size()==12);
        assert(e1.getMetaLvas().get(1).getLVAs().get(0).getExams().size()==0);

    }

    @Test
    public void testReadByNotExistingName() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByName("Not Existing Name")==null);
    }

    @Test
    public void testReadByNameIsNull() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByName(null)==null);
    }

    /**
     * There is only tested if the amount of returned modules is correct, because the method <code>readByCurriculum()</code>
     * calls internal the method <code>readById()</code>.
     * @throws Exception
     */
    @Test
    public void testReadByCurriculum() throws Exception {
        TestHelper.insert(0);
        HashMap<Module, Boolean> moduleMap = dao.readByCurriculum(0);
        assert(moduleMap.size()==2);
    }

    @Test
    public void testReadByNotExistingCurriculum() throws Exception {
        TestHelper.insert(0);
        HashMap<Module, Boolean> moduleMap = dao.readByCurriculum(-1);
        assert(moduleMap.size()==0);
    }

    @Test
    public void testAddToCurriculum() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByCurriculum(0).size()==2);
        assert(dao.addToCurriculum(2,0,false));
        assert(dao.readByCurriculum(0).size()==3);
    }

    @Test(expected = DataAccessException.class)
    public void testAddToNotExistingCurriculum() throws Exception {
        TestHelper.insert(0);
        dao.addToCurriculum(2,-1,false);
    }

    @Test(expected = DataAccessException.class)
    public void testAddNotExistingModuleToCurriculum() throws Exception {
        TestHelper.insert(0);
        dao.addToCurriculum(-1,2,true);
    }

    @Test
    public void testDeleteFromCurriculum() throws Exception {
        TestHelper.insert(0);
        assert(dao.readByCurriculum(0).size()==2);
        assert(dao.deleteFromCurriculum(1,0));
        assert(dao.deleteFromCurriculum(3,0));
        assert(dao.readByCurriculum(0).size()==0);
    }

    @Test
    public void testDeleteFromNotExistingCurriculum() throws Exception {
        TestHelper.insert(0);
        assert(dao.deleteFromCurriculum(-1,2));
    }

    @Test
    public void testDeleteNotExistingModuleFromCurriculum() throws Exception {
        TestHelper.insert(0);
        assert(dao.deleteFromCurriculum(-1,2));
    }

    @Test
    public void testUpdate() throws Exception {
        TestHelper.insert(0);

        Module e0 = new Module();
        e0.setId(0);
        e0.setName("Algebra und Diskrete Mathematik");
        e0.setDescription("Algebra und Diskrete Mathematik','Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweistechniken und Sätze in den Teilgebieten Algebra (v.a. algebraische Strukturen und lineare Algebra) und Diskrete Mathematik (v.a Kombinatorik und Graphentheorie). Es setzt sich aus einem Vorlesungsteil und einem begleitenden Übungsteil zusammen, der der Vertiefung der Vorlesungsinhalte und der Entwicklung von Fertigkeiten zur Erstellung korrekter mathematischer Beweise und der mathematischen Modellierung und Analyse praktischer Problemstellungen dient.");
        e0.setCompleteall(true);

        assert(dao.update(e0));
        Module e1 = dao.readById(0);

        assert(e1.equals(e0));

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
        for(int i=0; i<12; i++) {
            s=s.concat(s);
        }
        Module e0 = new Module();
        e0.setId(0);
        e0.setName(s);
        e0.setDescription("Algebra und Diskrete Mathematik','Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweistechniken und Sätze in den Teilgebieten Algebra (v.a. algebraische Strukturen und lineare Algebra) und Diskrete Mathematik (v.a Kombinatorik und Graphentheorie). Es setzt sich aus einem Vorlesungsteil und einem begleitenden Übungsteil zusammen, der der Vertiefung der Vorlesungsinhalte und der Entwicklung von Fertigkeiten zur Erstellung korrekter mathematischer Beweise und der mathematischen Modellierung und Analyse praktischer Problemstellungen dient.");
        e0.setCompleteall(true);

        dao.update(e0);
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongDescription() throws Exception {
        TestHelper.insert(0);

        String s = "aa";
        for(int i=0; i<12; i++) {
            s=s.concat(s);
        }
        Module e0 = new Module();
        e0.setId(0);
        e0.setName("Algebra und Diskrete Mathematik");
        e0.setDescription(s);
        e0.setCompleteall(true);

        dao.update(e0);
    }
}