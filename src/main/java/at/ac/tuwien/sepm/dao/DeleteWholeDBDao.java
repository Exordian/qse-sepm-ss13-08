package at.ac.tuwien.sepm.dao;

import org.springframework.dao.DataAccessException;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 02.07.13
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public interface DeleteWholeDBDao {

    /**
     * Drops the whole database and creates a new one.
     * @throws DataAccessException IF any error occurred and the data could not be deleted.
     *
     */
    public void deleteAll() throws DataAccessException;
}
