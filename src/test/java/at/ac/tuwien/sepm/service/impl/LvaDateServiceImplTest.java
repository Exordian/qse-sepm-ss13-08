package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.entity.LvaDateType;
import at.ac.tuwien.sepm.service.LvaDateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Lena Lenz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml"})
public class LvaDateServiceImplTest {

    private LvaDate validLvaDate;
    private LvaDate invalidLvaDate;
    @Autowired
    private LvaDateService lvaDateService;

    @Before
    public void setUp() {
        validLvaDate = new LvaDate();
        validLvaDate.setId(10);
        validLvaDate.setLva(1);
        validLvaDate.setName("Valid LvaDate");
        validLvaDate.setDescription("blabla");
        validLvaDate.setAttendanceRequired(true);
        validLvaDate.setRoom("HS 13");
        validLvaDate.setWasAttendant(false);
        validLvaDate.setType(LvaDateType.LECTURE);
        validLvaDate.setResult(2);
        validLvaDate.setTime(new TimeFrame(new DateTime(2013, 12, 12, 12, 12, 12), new DateTime(2014, 12, 12, 12, 12, 12)));

        invalidLvaDate = new LvaDate();
        invalidLvaDate.setId(-3);
        invalidLvaDate.setLva(null);
        invalidLvaDate.setName("Invalid LvaDate");
        invalidLvaDate.setDescription(null);
        invalidLvaDate.setAttendanceRequired(true);
        invalidLvaDate.setRoom(null);
        invalidLvaDate.setWasAttendant(false);
        invalidLvaDate.setType(null);
        invalidLvaDate.setResult(-1);
        invalidLvaDate.setTime(new TimeFrame(new DateTime(1990, 11, 12, 12, 12, 12), new DateTime(1990, 12, 12, 12, 12, 12)));
    }

    @Test
    public void validateValidLvaDateShouldPersist() throws Exception {
        lvaDateService.validateLvaDate(validLvaDate);
    }

    @Test(expected = ServiceException.class)
    public void validateInvalidLvaDateShouldThrowException() throws Exception {
        lvaDateService.validateLvaDate(invalidLvaDate);
    }

    @Test(expected = ServiceException.class)
    public void validateInvalidIDShouldThrowException() throws Exception {
        validLvaDate.setId(-100);
        lvaDateService.validateID(validLvaDate.getId());
        validLvaDate.setId(10); //wieder valide ID zurücksetzen
    }
    
}
