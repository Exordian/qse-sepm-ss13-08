package at.ac.tuwien.sepm.dao.hsqldb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Author: MUTH Markus
 * Date: 5/26/13
 * Time: 7:18 AM
 * Description of class "DBMetaLvaDaoTest":
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBMetaLvaDaoTest {

    @Autowired
    private DBMetaLvaDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {

    }

    @Test
    public void testCreateNull() throws Exception {

    }

    @Test
    public void testSetPredecessor() throws Exception {

    }

    @Test
    public void testReadById() throws Exception {

    }

    @Test
    public void testReadByIdWithoutLvaAndPrecursor() throws Exception {

    }

    @Test
    public void testReadByLvaNumber() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testReadByModule() throws Exception {

    }
}
