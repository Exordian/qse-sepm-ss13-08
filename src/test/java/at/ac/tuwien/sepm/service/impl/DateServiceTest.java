package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

/**
 * Author: Flo
 */
public class DateServiceTest {
    private DateEntity validDateEntity;
    private DateService dateService;

    @Before
    public void setUp() {
        dateService = new DateServiceImpl();
        validDateEntity = new DateEntity();
        validDateEntity.setName("valid name");
        validDateEntity.setDescription("valid description");
        validDateEntity.setId(3);
        validDateEntity.setIntersectable(true);
        validDateEntity.setStart(new DateTime(2000, 1, 1, 1, 1));
        validDateEntity.setStop(new DateTime(2002, 2, 2, 2, 2));
    }

    @Test
    public void testValidateDateEntityVALID() throws Exception {
        dateService.validateDateEntity(validDateEntity);
    }

    @Test(expected = ServiceException.class)
    public void testValidateDateEntityINVALID() throws Exception {
        validDateEntity.setId(-1);
        dateService.validateDateEntity(validDateEntity);
    }

    @Test
    public void testValidateIdVALID() throws Exception {
        dateService.validateId(1);
    }

    @Test(expected = ServiceException.class)
    public void testValidateIdINVALID() throws Exception {
        dateService.validateId(-1);
    }

    @Test
    public void testValidateDateVALID() throws Exception {
        dateService.validateDate(validDateEntity.getStart());
    }

    @Test(expected = ServiceException.class)
    public void testValidateDateINVALID() throws Exception {
        validDateEntity.setStart(null);
        dateService.validateDate(validDateEntity.getStart());
    }
}
