package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.dao.LvaDateDao;
import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.dao.ModuleDao;
import at.ac.tuwien.sepm.dao.hsqldb.TestHelper;
import at.ac.tuwien.sepm.entity.*;
import at.ac.tuwien.sepm.service.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class ModuleServiceImplTest {
    @Autowired
    ModuleService moduleService;

    @Autowired
    ModuleDao moduleDao;

    @Autowired
    MetaLvaDao metaLvaDao;

    @Autowired
    LvaDateDao lvaDateDao;

    @Autowired
    LvaDao lvaDao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {
        Module o0 = new Module();
        o0.setId(1234);
        o0.setName("Algebra und Diskrete Mathematik");
        o0.setDescription("Das Modul bietet eine Einführung in die zentralen mathematische Grundlagen, Beweist...");
        o0.setCompleteall(true);

        MetaLVA m0 = new MetaLVA();
        m0.setNr("104.265");
        m0.setName("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik");
        m0.setSemestersOffered(Semester.W_S);
        m0.setType(LvaType.VO);
        m0.setPriority(0f);
        m0.setECTS(4);

        LVA l0 = new LVA();
        l0.setId(-1234);
        l0.setMetaLVA(m0);
        l0.setYear(2013);
        l0.setSemester(Semester.S);
        l0.setDescription("Desrciption zu Algebra und Diskrete Mathematik");
        l0.setGrade(0);
        l0.setInStudyProgress(true);

        LvaDate d0 = new LvaDate();
        d0.setLva(756);
        d0.setName("Mathe V0");
        d0.setDescription("blablabla über Mathe V0");
        d0.setType(LvaDateType.LECTURE);
        d0.setRoom("HS 17 Friedrich Hartmann");
        d0.setTime(new TimeFrame(new DateTime(2013, 3, 4, 12, 15, 0), new DateTime(2013, 3, 4, 13, 45, 0)));
        d0.setAttendanceRequired(false);
        d0.setWasAttendant(true);

        LvaDate d1 = new LvaDate();
        d1.setLva(23);
        d1.setName("Mahte UE");
        d1.setDescription("blablabla über Mahte UE");
        d1.setType(LvaDateType.EXERCISE);
        d1.setRoom("HS 17 Friedrich Hartmann");
        d1.setTime(new TimeFrame(new DateTime(2013, 3, 5, 12, 15, 0), new DateTime(2013, 3, 5, 13, 45, 0)));
        d1.setAttendanceRequired(false);
        d1.setWasAttendant(true);

        LvaDate d2 = new LvaDate();
        d2.setLva(345678);
        d2.setName("VO - Algebra");
        d2.setDescription("blablabla über VO - Algebra");
        d2.setType(LvaDateType.EXAM);
        d2.setRoom("HS 17 Friedrich Hartmann");
        d2.setTime(new TimeFrame(new DateTime(2013, 3, 6, 12, 15, 0), new DateTime(2013, 3, 6, 13, 45, 0)));
        d2.setAttendanceRequired(false);
        d2.setWasAttendant(true);

        LvaDate d3 = new LvaDate();
        d3.setLva(3456);
        d3.setName("Mathe Deadline");
        d3.setDescription("blablabla über Mathe Deadline");
        d3.setType(LvaDateType.DEADLINE);
        d3.setRoom("HS 17 Friedrich Hartmann");
        d3.setTime(new TimeFrame(new DateTime(2013, 3, 7, 12, 15, 0), new DateTime(2013, 3, 7, 13, 45, 0)));
        d3.setAttendanceRequired(false);
        d3.setWasAttendant(true);

        List<LvaDate> ld0 = new ArrayList<LvaDate>();
        List<LvaDate> ld1 = new ArrayList<LvaDate>();
        List<LvaDate> ld2 = new ArrayList<LvaDate>();
        List<LvaDate> ld3 = new ArrayList<LvaDate>();

        ld0.add(d0);
        ld0.add(d1);
        ld0.add(d2);
        ld0.add(d3);

        l0.setLectures(ld0);
        l0.setExercises(ld1);
        l0.setExams(ld2);
        l0.setDeadlines(ld3);

        m0.setLVA(l0);

        List<MetaLVA> om0 = new ArrayList<MetaLVA>();
        om0.add(m0);

        o0.setMetaLvas(om0);

        assert(moduleDao.readById(0)==null);
        assert(metaLvaDao.readById(0)==null);
        assert(lvaDao.readById(0)==null);
        assert(lvaDateDao.readById(0)==null);
        assert(lvaDateDao.readById(1)==null);
        assert(lvaDateDao.readById(2)==null);
        assert(lvaDateDao.readById(3)==null);

        assert(moduleService.create(o0));

        assert(moduleDao.readById(0)!=null);
        assert(metaLvaDao.readById(0)!=null);
        assert(lvaDao.readById(0)!=null);
        assert(lvaDateDao.readById(0)!=null);
        assert(lvaDateDao.readById(1)!=null);
        assert(lvaDateDao.readById(2)!=null);
        assert(lvaDateDao.readById(3)!=null);

        assert(moduleDao.readById(0).getName().equals("Algebra und Diskrete Mathematik"));

        assert(metaLvaDao.readById(0).getName().equals("Algebra und Diskrete Mathematik für Informatik und Wirtschaftsinformatik"));
        assert(lvaDateDao.readByLvaAndType(0, LvaDateType.LECTURE).size()==1);
        assert(lvaDateDao.readByLvaAndType(0, LvaDateType.EXERCISE).size()==1);
        assert(lvaDateDao.readByLvaAndType(0, LvaDateType.EXAM).size()==1);
        assert(lvaDateDao.readByLvaAndType(0, LvaDateType.DEADLINE).size()==1);

        assert(lvaDao.readById(0).getLectures().size()==1);
        assert(lvaDao.readById(0).getExercises().size()==1);
        assert(lvaDao.readById(0).getExams().size()==1);
        assert(lvaDao.readById(0).getDeadlines().size()==1);
    }

    @Test
    public void testRequiredModules() throws Exception {
        List<Module> requiredModules = moduleService.getRequiredModules(ClassLoader.getSystemResource("033534-SP-2011-Bac_Software_und_Information_Engineering.pdf").getPath());
        Assert.notNull(requiredModules);
        Assert.isTrue(requiredModules.get(0).getName().contains("Algorithmen"));

    }

    @Test(expected = NullPointerException.class)
    public void testRequiredModulesNull() throws Exception {
        List<Module> requiredModules = moduleService.getRequiredModules(null);
    }


    @Test(expected = ServiceException.class)
    public void testRequiredModulesWat() throws Exception {
        List<Module> requiredModules = moduleService.getRequiredModules("wat");
    }

    @Test
    public void testOptionalModules() throws Exception {
        List<Module> optionalModules = moduleService.getOptionalModules(ClassLoader.getSystemResource("033534-SP-2011-Bac_Software_und_Information_Engineering.pdf").getPath());
        Assert.notNull(optionalModules);
        Assert.isTrue(optionalModules.get(0).getName().equals("Deklaratives Problemlösen"));
    }

    @Test(expected = NullPointerException.class)
    public void testOptionalModulesNull() throws Exception {
        List<Module> optionalModules = moduleService.getOptionalModules(null);
    }


    @Test(expected = ServiceException.class)
    public void testOptionalModulesWat() throws Exception {
        List<Module> optionalModules = moduleService.getOptionalModules("wat");
    }

}
