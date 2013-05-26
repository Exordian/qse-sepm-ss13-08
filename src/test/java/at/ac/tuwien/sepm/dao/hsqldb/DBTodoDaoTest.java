package at.ac.tuwien.sepm.dao.hsqldb;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.Todo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * Author: MUTH Markus
 * Date: 5/26/13
 * Time: 5:05 PM
 * Description of class "DBTodoDaoTest":
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class DBTodoDaoTest {

    @Autowired
    DBTodoDao dao;

    @Before
    public void setUp() throws Exception {
        TestHelper.drop();
        TestHelper.create();
    }

    @Test
    public void testCreate() throws Exception {
        TestHelper.insert(10);

        Todo e0 = new Todo();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(0);
        e0.setLva(l);
        e0.setName("Skript Kaufen");
        e0.setDescription("Favoritenbstraße, Stiege 3, 3. Stock Zimmer H1234");
        e0.setDone(false);

        assert(dao.create(e0));
    }

    @Test
    public void testCreateNull() throws Exception {
        TestHelper.insert(10);
        assert(!dao.create(null));
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongName() throws Exception {
        TestHelper.insert(10);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Todo e0 = new Todo();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(0);
        e0.setLva(l);
        e0.setName(s);
        e0.setDescription("Favoritenbstraße, Stiege 3, 3. Stock Zimmer H1234");
        e0.setDone(false);

        assert(dao.create(e0));
    }

    @Test(expected = IOException.class)
    public void testCreateTooLongDescription() throws Exception {
        TestHelper.insert(10);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Todo e0 = new Todo();
        e0.setId(1234);
        LVA l = new LVA();
        l.setId(0);
        e0.setLva(l);
        e0.setName("Skript Kaufen");
        e0.setDescription(s);
        e0.setDone(false);

        assert(dao.create(e0));
    }

    @Test
    public void testReadById() throws Exception {
        TestHelper.insert(0);

        Todo e0 = new Todo();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(1);
        e0.setLva(l);
        e0.setName("Skript Kaufen");
        e0.setDescription("Favoritenbstraße, Stiege 3, 3. Stock Zimmer H1234");
        e0.setDone(false);

        Todo e1 = dao.readById(0);
        assert(e1.equals(e0));
        assert(e1.getLva().getId()==1);
    }

    @Test
    public void testReadByNotExistingId() throws Exception {
        TestHelper.insert(0);
        assert(dao.readById(-1)==null);
    }

    @Test
    public void testReadByDone() throws Exception {
        TestHelper.insert(0);
        List<Todo> done = dao.readByDone(true);
        List<Todo> undone = dao.readByDone(false);
        assert(undone.size()==3);
        assert(undone.get(0).getId()==0);
        assert(undone.get(1).getId()==1);
        assert(undone.get(2).getId()==2);
        assert(done.size()==1);
        assert(done.get(0).getId()==3);
    }

    @Test
    public void testUpdate() throws Exception {
        TestHelper.insert(0);

        Todo e0 = new Todo();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(5);
        e0.setLva(l);
        e0.setName("New Name");
        e0.setDescription("New Description");
        e0.setDone(true);

        assert(dao.update(e0));
        Todo e1 = dao.readById(0);

        assert(e1.equals(e0));
        assert(e1.getLva().getId()==5);
    }

    @Test
    public void testUpdateNull() throws Exception {
        TestHelper.insert(0);
        assert(!dao.update(null));
    }

    @Test
    public void testUpdateNotExistingTodo() throws Exception {
        TestHelper.insert(0);

        Todo e0 = new Todo();
        e0.setId(-1);
        LVA l = new LVA();
        l.setId(0);
        e0.setLva(l);
        e0.setName("Skript Kaufen");
        e0.setDescription("Favoritenbstraße, Stiege 3, 3. Stock Zimmer H1234");
        e0.setDone(false);

        assert(!dao.update(e0));
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongName() throws Exception {
        TestHelper.insert(0);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Todo e0 = new Todo();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(0);
        e0.setLva(l);
        e0.setName(s);
        e0.setDescription("Favoritenbstraße, Stiege 3, 3. Stock Zimmer H1234");
        e0.setDone(false);

        dao.update(e0);
    }

    @Test(expected = IOException.class)
    public void testUpdateTooLongDescription() throws Exception {
        TestHelper.insert(0);

        String s = "aa";
        for(int i=0; i<10; i++) {
            s=s.concat(s);
        }

        Todo e0 = new Todo();
        e0.setId(0);
        LVA l = new LVA();
        l.setId(0);
        e0.setLva(l);
        e0.setName("Skript Kaufen");
        e0.setDescription(s);
        e0.setDone(false);

        dao.update(e0);
    }

    @Test
    public void testDelete() throws Exception {
        TestHelper.insert(0);
        assert(dao.readById(0)!=null);
        assert(dao.delete(0));
        assert(dao.readById(0)==null);
    }

    @Test
    public void testDeleteNotExistingTodo() throws Exception {
        TestHelper.insert(0);
        assert(!dao.delete(-1));
    }
}
