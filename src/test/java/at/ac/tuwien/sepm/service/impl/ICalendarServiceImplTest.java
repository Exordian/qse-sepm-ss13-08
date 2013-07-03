package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBDateDao;
import at.ac.tuwien.sepm.dao.hsqldb.TestHelper;
import at.ac.tuwien.sepm.service.CalService;
import at.ac.tuwien.sepm.service.ICalendarService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.io.File;

/**
 * @author Markus MUTH
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class ICalendarServiceImplTest {

    @Autowired
    ICalendarService service;

    @Autowired
    CalService calService;

    @Autowired
    DateDao dateDao;

    @Test
    public void testIcalImport() throws Exception {
        TestHelper.drop();
        TestHelper.create();
        service.icalImport(new File("src/test/resources/testIcal.ics"));
    }

    @Test (expected = ServiceException.class)
    public void testIcalImportNotExistingFile() throws Exception {
        TestHelper.drop();
        TestHelper.create();
        service.icalImport(new File("notexistingpath/filedoes.notexist"));
    }

    @Test (expected = ServiceException.class)
    public void testIcalImportCorruptCalendarFile() throws Exception {
        TestHelper.drop();
        TestHelper.create();
        service.icalImport(new File("src/test/resources/curruptIestIcal.ics"));
    }

    @Test
    public void testIcalExport() throws Exception {
        TestHelper.drop();
        TestHelper.create();
        TestHelper.insert(0);
        service.icalExport(new File("src/test/resources/toBeOverwrittenAtIcalExportTest.ics"));
    }
}