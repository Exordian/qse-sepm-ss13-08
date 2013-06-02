package at.ac.tuwien.sepm.dao;

import at.ac.tuwien.sepm.entity.Todo;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Author: MUTH Markus
 * Date: 5/18/13
 * Time: 9:06 PM
 * Description of class "TodoDao":
 */
public interface TodoDao {
    /**
     * Insert persistent a new to-do. If <code>toCreate.getDone()==null</code>, the done-flag will set to <code>false</code>.
     * @param toCreate A to-do containing all data to be stored.
     * @throws IOException If the name or the description are too long to store.
     * @throws DataAccessException If the data from <code>toCreate</code> could not be stored because any other error occurred.
     */
    public boolean create(Todo toCreate) throws IOException, DataAccessException;

    /**
     * Read all to-do which are either done or undone.
     * @param id The id of the to-do.
     * @return All to-do which are done or undone.
     * @throws DataAccessException If the data could not be read because any error occurred.
     */
    public Todo readById(int id) throws DataAccessException;

    /**
     * Read all to-do which are either done or undone.
     * @param done <code>true</code> if the to-do is marked as done, <code>false</code> otherwise.
     * @return All to-do which are done or undone.
     * @throws DataAccessException If the data could not be read because any error occurred.
     */
    public List<Todo> readByDone(boolean done) throws DataAccessException;

    /**
     * Update the values of a to-do. Every attribute of toUpdate, which is not null, is included to the
     * update-statements. The to-do is identified by its id, so this value should not be null
     * @param toUpdate The to-do containing all values which should be updated.
     * @return <code>true</code> if there was a to-do found and was successfully updated and <code>false</code> if there
     * was no to-do with the specified id found or <code>toUpdate==null</code>.
     * @throws IOException If the name or the description are too long to store.
     * @throws DataAccessException If the data could not be updated because any other error occurred.
     */
    public boolean update(Todo toUpdate) throws IOException, DataAccessException;

    /**
     * Delete the with <code>toDelete</code> identified to-do.
     * @param id The identifier of the to-do which should be deleted.
     * @return <code>true</code> if a to-do with the specified id could be found and was successfully deleted and
     * <code>false</code> if there is no to-do with the specified id.
     * @throws DataAccessException If the data could not be deleted because any error occurred.
     */
    public boolean delete(int id) throws DataAccessException;

    /**
     * returns all todos which are currently in the database
     * @return all todos in database
     * @throws DataAccessException if the data could not be read
     */
    public List<Todo> getAllTodos() throws DataAccessException;
}
