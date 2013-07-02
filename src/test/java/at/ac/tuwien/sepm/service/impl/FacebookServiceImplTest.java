package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.FacebookService;
import at.ac.tuwien.sepm.service.PropertyService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
@Ignore // facebook test account not working ...
public class FacebookServiceImplTest {

    @Autowired
    FacebookService facebookService;

    @Autowired
    PropertyService propertyService;

    @Before
    public void init() {
        propertyService.setProperty("facebook.key", ""); // testkey
    }

    @Test
    public void testPostToWall() throws Exception {
        //facebookService.postToWall("#ohai #facebook #api");
        facebookService.postToWall("#sepm #qse #fml #panholzer4president");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostToWallNull() throws Exception {
        facebookService.postToWall(null);
    }

    @Test
    public void testPostMetaLvas() throws  Exception {
        List<MetaLVA> metaLVAList = new ArrayList<>();
        MetaLVA m1 = new MetaLVA();
        m1.setName("Web Engineering");
        metaLVAList.add(m1);

        MetaLVA m2 = new MetaLVA();
        m2.setName("SEPM QSE");
        metaLVAList.add(m2);

        facebookService.postLvasToWall(metaLVAList);
    }
}
