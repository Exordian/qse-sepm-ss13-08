package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.hsqldb.TestHelper;
import at.ac.tuwien.sepm.entity.Date;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author Markus MUTH
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class CalServiceTest {
    private CalServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new CalServiceImpl();
        TestHelper.drop();
        TestHelper.create();

    }

    @Test
    public void testGetAllDatesAt() throws Exception {
        TestHelper.insert(15);
        List<Date> l = service.getAllDatesAt(new DateTime(2014, 5, 2, 4, 4, 4, 4));

        assert(l.size()==12);
    }
}
