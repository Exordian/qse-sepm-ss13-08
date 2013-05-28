package at.ac.tuwien.sepm.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class SpringTest {

   // @Autowired
  //  private TissService tissService;

   // @Autowired
   // private OLD_LvaDao_FROM_JAKOB lvaDao;

    @Test
    public void testTest() throws Exception {
        //System.out.println(iTissService.test());
     //   lvaDao.test();
    }
}
