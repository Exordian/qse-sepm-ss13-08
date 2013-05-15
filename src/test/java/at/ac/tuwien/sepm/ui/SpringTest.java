package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.service.TissService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class SpringTest {

    @Autowired
    private TissService tissService;

    @Autowired
    private LvaDao lvaDao;

    @Test
    public void testTest() throws Exception {
        //System.out.println(iTissService.test());
        lvaDao.test2();
    }
}
