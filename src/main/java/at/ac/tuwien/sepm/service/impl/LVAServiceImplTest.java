package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.Semester;
import org.junit.Before;
import org.junit.Test;


/**
 * Created with IntelliJ IDEA.
 * User: Lena
 * Date: 01.06.13
 * Time: 09:28
 * To change this template use File | Settings | File Templates.
 */
public class LVAServiceImplTest {

    private LVA validLVA;
    private LVA invalidLVA;

    private LVAService lvaService;

    @Before
    public void setUp() {
        validLVA = new LVA();
        validLVA.setId(20);
        validLVA.setMetaLVA(new MetaLVA());
        validLVA.setDescription("valid Description");
        validLVA.setSemester(Semester.S);
        validLVA.setGrade(1);

        invalidLVA = new LVA();
        invalidLVA.setId(-100);
        invalidLVA.setMetaLVA(null);
        invalidLVA.setDescription(null);
        invalidLVA.setSemester(null);
        invalidLVA.setGrade(-5);

        lvaService = new LVAServiceImpl();
    }

    @Test
    public void validateValidLVAShouldPersist() throws Exception {
        lvaService.validateLVA(validLVA);
    }

    @Test(expected = ValidationException.class)
    public void validateInvalidLVAShouldThrowException() throws Exception {
        lvaService.validateLVA(invalidLVA);
    }

    @Test
    public void validateValidIDShouldPersist() throws Exception {
        lvaService.validateID(10);
    }

    @Test(expected = ValidationException.class)
    public void validateInvalidIDShouldThrowException() throws Exception {
        lvaService.validateID(-1);
    }

    @Test(expected = ValidationException.class)
    public void readByInvalidIDShouldThrowException() throws Exception {
        lvaService.readById(-2);
    }

    @Test(expected = ValidationException.class)
    public void readByInvalidMetaLvaShouldThrowException() throws Exception {
        lvaService.readByMetaLva(-44);
    }

    @Test(expected = ValidationException.class)
    public void readUncompletedByInvalidYearSemesterProgress() throws Exception {
        lvaService.readUncompletedByYearSemesterStudyProgress(-200, null, false);
    }

    @Test
    public void updateValidLVAShouldPersist() throws Exception {
        validLVA.setDescription("another valid description");
        lvaService.update(validLVA);
    }

    @Test(expected = ValidationException.class)
    public void updateInvalidLVAShouldThrowException() throws Exception {
        validLVA.setDescription(null); //now invalid lva
        lvaService.update(validLVA);
    }

}
