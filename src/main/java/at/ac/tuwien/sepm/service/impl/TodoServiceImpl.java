package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.LvaDao;
import at.ac.tuwien.sepm.dao.TodoDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBLvaDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBTodoDao;
import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.Todo;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TodoService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lena Lenz
 */

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoDao todoDao;

    @Autowired
    LvaDao lvaDao;

    Logger logger = LogManager.getLogger(this.getClass().getSimpleName());


    @Override
    public boolean create(Todo toCreate) throws ServiceException, ValidationException {
        try {
            this.validateTodo(toCreate);

            boolean created = todoDao.create(toCreate);
            return created;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public Todo readById(int id) throws ServiceException, ValidationException {
        try {
            this.validateID(id);

            Todo todo = todoDao.readById(id);
            return todo;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public List<Todo> readByDone(boolean done) throws ServiceException {
        try {
            List<Todo> readByDoneTodos = todoDao.readByDone(done);
            return readByDoneTodos;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public boolean update(Todo toUpdate) throws ServiceException, ValidationException {
        try {
            this.validateID(toUpdate.getId());
            this.validateTodo(toUpdate);

            boolean updated = todoDao.update(toUpdate);
            return updated;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(IOException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public boolean delete(int id) throws ServiceException, ValidationException {
        try {
            this.validateID(id);

            boolean deleted = todoDao.delete(id);
            return deleted;
        } catch(ServiceException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ValidationException("Exception: "+ e.getMessage());
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    @Override
    public List<Todo> getAllTodos() throws ServiceException {
        List<Todo> todoList = null;
        try {
            todoList = todoDao.getAllTodos();
            return todoList;
        } catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    public LVA getLVAByID(int id) throws ServiceException, ValidationException {
        if(id < 0) {
            throw new ValidationException("invalid id of lva!");
        }
        try {
            LVA lva = lvaDao.readById(id);
            return lva;
        }  catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }

    public List<LVA> getLVAByYearAndSemester(int year, boolean isWinterSemester) throws ServiceException {
        try {
            List<LVA> lvaList = lvaDao.readByYearAndSemester(year, isWinterSemester);
            return lvaList;
        }  catch(DataAccessException e) {
            logger.error("Exception: "+ e.getMessage());
            throw new ServiceException("Exception: "+ e.getMessage());
        }
    }


    @Override
    public void validateTodo(Todo toValidate) throws ServiceException {

        String error_msg = "";
        boolean valid = true;

        /*if(toValidate.getLva() == null) {   //wenn null dann privates to-do
            valid = false;
            error_msg += "invalid LVA!\n";
        } */
        if(toValidate.getName() == null ) {
            valid = false;
            error_msg += "invalid name!\n";
        }
        if(toValidate.getDescription() == null) {
            valid = false;
            error_msg += "invalid description(null)!\n";
        }
        if(toValidate.getDone() == null) {
            valid = false;
            error_msg += "invalid done-boolean!\n";
        }
        if(valid == false) {
            logger.error("Invalid Todo: "+ error_msg);
            throw new ServiceException("Invalid Todo: "+ error_msg);
        }
    }

    @Override
    public void validateID(int id) throws ServiceException {
        if(id <= 0) {
            logger.error("invalid id!");
            throw new ServiceException("invalid id!");
        }
    }
}
