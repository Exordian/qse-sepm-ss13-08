package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.Semester;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Lena
 * Date: 01.06.13
 * Time: 09:37
 * To change this template use File | Settings | File Templates.
 */
public class MetaLVAServiceImplTest {

    private MetaLVA validMetaLVA;
    private MetaLVA invalidMetaLva;
    private MetaLVAService metaLVAService;

    @Before
    public void setUp() {
        validMetaLVA = new MetaLVA();
        validMetaLVA.setId(20);
        validMetaLVA.setSemestersOffered(Semester.W_S);
        validMetaLVA.setName("valid name");
        validMetaLVA.setNr("30");
        validMetaLVA.setCompleted(false);
        validMetaLVA.setECTS(3);
        validMetaLVA.setModule(2);
        validMetaLVA.setPriority(10);
        validMetaLVA.setType(LvaType.PR);

        invalidMetaLva = new MetaLVA();
        invalidMetaLva.setId(-50);
        invalidMetaLva.setSemestersOffered(null);
        invalidMetaLva.setName(null);
        invalidMetaLva.setNr(null);
        invalidMetaLva.setCompleted(false);
        invalidMetaLva.setECTS(-50);
        invalidMetaLva.setModule(-1);
        invalidMetaLva.setPriority(-30);
        invalidMetaLva.setType(null);

        metaLVAService = new MetaLVAServiceImpl();
    }

    @Test
    public void validateValidMetaLVAShouldPersist() throws Exception {
        metaLVAService.validateMetaLVA(validMetaLVA);
    }

    @Test(expected = ValidationException.class)
    public void validateInvalidMetaLVAShouldThrowException() throws Exception {
        metaLVAService.validateMetaLVA(invalidMetaLva);
    }

    @Test(expected = ValidationException.class)
    public void validateInvalidIDShouldThrowException() throws Exception {
        validMetaLVA.setId(-100);
        metaLVAService.validateID(validMetaLVA.getId());
    }

    @Test
    public void updateValidMetaLVAShouldPersist() throws Exception {
        validMetaLVA.setPriority(2);
        metaLVAService.update(validMetaLVA);
    }

    @Test(expected = Exception.class)
    public void updateInvalidMetaLVAShouldThrowException() throws Exception {
        validMetaLVA.setId(-10);
        metaLVAService.update(validMetaLVA);
    }

    @Test(expected = ValidationException.class)
    public void setInvalidPredecessorShouldThrowException() throws Exception {
        metaLVAService.setPredecessor(-30,-100);
    }

    @Test(expected = ValidationException.class)
    public void unsetInvalidPredecessorShouldThrowException() throws Exception {
        metaLVAService.unsetPredecessor(-2,-2);
    }

    @Test(expected = ValidationException.class)
    public void readByInvalidIdShouldThrowException() throws Exception {
        metaLVAService.readById(-3);
    }

    @Test(expected = ValidationException.class)
    public void readByInvalidLvaNumberShouldThrowException() throws Exception {
        metaLVAService.readByLvaNumber(null);
    }

}
