package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.entity.Module;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Author: MUTH Markus
 * Date: 6/2/13
 * Time: 7:30 AM
 * Description of class "CreateCurriculumServiceImplTest":
 */
public class CreateCurriculumServiceImplTest {
    private Module m;
    private Curriculum c;
    CreateCurriculumServiceImpl service = new CreateCurriculumServiceImpl();

    @Before
    public void setUp() throws Exception {
        m = new Module();
        c = new Curriculum();

        m.setId(0);
        m.setName("Name");
        m.setDescription("Description");
        m.setCompleteall(true);

        c.setId(0);
        c.setStudyNumber("033 533");
        c.setName("Informatik");
        c.setDescription("Description");
        c.setAcademicTitle("BSc");
        c.setEctsChoice(5);
        c.setEctsFree(6);
        c.setEctsSoftskill(7);
    }

    @Test
    public void testValidateModule() throws Exception {
        service.validate(m);
    }

    @Test(expected = IOException.class)
    public void testValidateModuleNameIsNull() throws Exception {
        m.setName(null);
        service.validate(m);

    }

    @Test
    public void testValidateModuleDescriptionIsNull() throws Exception {
        m.setDescription(null);
        service.validate(m);

    }

    @Test(expected = IOException.class)
    public void testValidateModuleCompleteAll() throws Exception {
        m.setCompleteall(null);
        service.validate(m);

    }

    @Test
    public void testValidateCurriculum() throws Exception {
        service.validate(c);
    }

    @Test
    public void testValidateCurriculumDescriptionIsNull() throws Exception {
        c.setDescription(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumStudyNumberIsNull() throws Exception {
        c.setStudyNumber(null);
        service.validate(c);
    }
    @Test(expected = IOException.class)
    public void testValidateCurriculumNameIsNull() throws Exception {
        c.setName(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumAcademicTitleIsNull() throws Exception {
        c.setAcademicTitle(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumEctsChoiceIsNull() throws Exception {
        c.setEctsChoice(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumEctsFreeIsNull() throws Exception {
        c.setEctsFree(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumEctsSoftSkillIsNull() throws Exception {
        c.setEctsSoftskill(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumEctsChoiceIsNegative() throws Exception {
        c.setEctsChoice(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumEctsFreeIsNegative() throws Exception {
        c.setEctsFree(null);
        service.validate(c);
    }

    @Test(expected = IOException.class)
    public void testValidateCurriculumEctsSoftSkillIsNegative() throws Exception {
        c.setEctsSoftskill(null);
        service.validate(c);
    }
}
