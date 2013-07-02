package at.ac.tuwien.sepm.service;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 02.07.13
 * Time: 19:01
 * To change this template use File | Settings | File Templates.
 */
public interface DeleteWholeDBService {

    /**
     * Drops the whole database and creates a new one.
     * @throws ServiceException IF any error occurred and the data could not be deleted.
     *
     */
    public void deleteAll() throws ServiceException;
}
