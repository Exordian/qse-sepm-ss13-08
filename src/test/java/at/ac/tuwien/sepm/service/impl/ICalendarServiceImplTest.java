package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.service.ICalendarService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Markus MUTH
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class ICalendarServiceImplTest {

    @Autowired
    ICalendarService service;

    public void testIcalImport() throws Exception {

    }

    public void testIcalExport() throws Exception {

    }

}
