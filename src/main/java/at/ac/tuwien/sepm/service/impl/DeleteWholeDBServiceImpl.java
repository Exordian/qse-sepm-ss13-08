package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.DeleteWholeDBDao;
import at.ac.tuwien.sepm.service.DeleteWholeDBService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 02.07.13
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DeleteWholeDBServiceImpl implements DeleteWholeDBService{
    @Autowired
    DeleteWholeDBDao deleteWholeDBDao;
    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Override
    public void deleteAll() throws ServiceException {
        try {
            deleteWholeDBDao.deleteAll();
        } catch (DataAccessException d) {
            log.error("DB could not be deleted.");
            throw new ServiceException("DB could not be deleted.", d);
        }
    }
}
