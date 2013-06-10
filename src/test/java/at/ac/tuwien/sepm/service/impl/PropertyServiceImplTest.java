package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.service.PropertyService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
@Ignore // TODO: store production and test property file seperately
public class PropertyServiceImplTest {
    @Autowired
    PropertyService propertyService;

    @Test
    public void testSetProperty() throws Exception {
        propertyService.setProperty("blub", "bla");
    }

    @Test
    public void testGetProperty() throws Exception {
        propertyService.setProperty("wat", "bla");
        Assert.isTrue(propertyService.getProperty("wat").equals("bla"));
    }

    @Test
    public void testGetPropertyDefault() throws Exception {
        Assert.isTrue(propertyService.getProperty("wut", "bla").equals("bla"));
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        propertyService.setProperty("saved", "true");
        propertyService.save();
        // due to the problem that its not specified where to store the file, the next test will simply try to load it
        propertyService.load();
        Assert.isTrue(propertyService.getProperty("saved").equals("true"));
    }
}
