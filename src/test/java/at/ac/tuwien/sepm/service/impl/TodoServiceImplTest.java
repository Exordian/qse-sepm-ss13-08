package at.ac.tuwien.sepm.service.impl;


import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Lena Lenz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class TodoServiceImplTest {
    private Todo validTodo;
    private Todo invalidTodo;
    @Autowired
    private TodoService todoService;

    @Before
    public void setUp() {
        validTodo = new Todo();
        validTodo.setId(10);
        validTodo.setLva(new LVA());
        validTodo.setName("Valid Todo");
        validTodo.setDescription("blabla");
        validTodo.setDone(false);

        invalidTodo = new Todo();
        invalidTodo.setId(-3);
        invalidTodo.setLva(null);
        invalidTodo.setName("Invalid Todo");
        invalidTodo.setDescription(null);
        invalidTodo.setDone(false);
    }

    @Test
    public void validateValidTodoShouldPersist() throws Exception {
        todoService.validateTodo(validTodo);
    }

    @Test(expected = ServiceException.class)
    public void validateInvalidTodoShouldThrowException() throws Exception {
        todoService.validateTodo(invalidTodo);
    }

    @Test(expected = ServiceException.class)
    public void validateInvalidIDShouldThrowException() throws Exception {
        validTodo.setId(-100);
        todoService.validateID(validTodo.getId());
        validTodo.setId(10); //wieder valide ID zurücksetzen
    }

}
