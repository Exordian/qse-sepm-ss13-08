package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.hsqldb.TestHelper;
import at.ac.tuwien.sepm.entity.TissExam;
import at.ac.tuwien.sepm.service.AutomaticExamRegisterService;
import at.ac.tuwien.sepm.service.PropertyService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
@Ignore
public class AutomaticExamRegisterServiceImplTest {

    @Autowired
    private AutomaticExamRegisterService automaticExamRegisterService;

    @Autowired
    private PropertyServiceImpl propertyService;

    @Before
    public void tissData()  {
        TestHelper.drop();
        TestHelper.create();
        propertyService.setProperty(PropertyService.TISS_USER, "jenglisc");
        propertyService.setProperty(PropertyService.TISS_PASSWORD, "xx");
    }

    @Test
    @Ignore
    public void testRegisterForExam() throws Exception {
        automaticExamRegisterService.registerForExam("188.366", "Internet Security Written Exam");
    }

    @Test
    public void testListExamsForLva() throws Exception {
        List<TissExam> tissExamList = automaticExamRegisterService.listExamsForLva("188.366");
        Assert.isTrue(tissExamList.size() == 1);
    }

    @Test
    public void registerForExam() throws Exception {
        List<TissExam> tissExamList = automaticExamRegisterService.listExamsForLva("188.366");
        Assert.isTrue(tissExamList.size() == 1);
        automaticExamRegisterService.addRegistration(tissExamList.get(0));
        Thread.sleep(10000);
    }
}
