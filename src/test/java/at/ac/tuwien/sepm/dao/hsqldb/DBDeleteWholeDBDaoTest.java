package at.ac.tuwien.sepm.dao.hsqldb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 02.07.13
 * Time: 20:40
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBDeleteWholeDBDaoTest {
    @Autowired
    private DBDeleteWholeDBDao dbDeleteWholeDBDao;
    @Autowired
    private DBDateDao dateDao;
    @Autowired
    DBTodoDao todoDao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
        TestHelper.insert(1);
    }

    @Test
    public void deleteAllTest() throws Exception {
        assert(dateDao.readById(0) != null);
        assert(todoDao.readById(0) != null);
        dbDeleteWholeDBDao.deleteAll();
        assert(dateDao.readById(0) == null);
        assert(todoDao.readById(0) == null);
    }
}
