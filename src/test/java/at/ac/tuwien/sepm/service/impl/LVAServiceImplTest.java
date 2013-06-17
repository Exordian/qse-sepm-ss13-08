package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.service.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Author: Georg Plaz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class LVAServiceImplTest {
    @Autowired
    LVAServiceImpl lvaService;

    @Before
    public void setUp() throws Exception {

    }

    @Test(expected = ServiceException.class)
    public void testCreateNull() throws Exception {
        lvaService.create(null);
    }

    @Test(expected = ValidationException.class)
    public void testReadByIdNull() throws Exception {
        lvaService.readById(-1);
    }

    @Test(expected = ValidationException.class)
    public void testReadByMetaLvaNull() throws Exception {
        lvaService.readByMetaLva(-1);
    }

    @Test(expected = ServiceException.class)
    public void testReadUncompletedByYearSemesterStudyProgressNull() throws Exception {
        lvaService.readUncompletedByYearSemesterStudyProgress(0,null,false);
    }

    @Test(expected = ValidationException.class)
    public void testReadByIdWithoutLvaDatesNull() throws Exception {
        lvaService.readByIdWithoutLvaDates(-1);
    }

    @Test(expected = ValidationException.class)
    public void testReadByYearAndSemesterNull() throws Exception {
        lvaService.readByYearAndSemester(-1,false);
    }

    @Test(expected = ServiceException.class)
    public void testUpdateNull() throws Exception {
        lvaService.update(null);
    }

    @Test(expected = ServiceException.class)
    public void testValidateLVANull() throws Exception {
        lvaService.validateLVA(null);
    }
}
