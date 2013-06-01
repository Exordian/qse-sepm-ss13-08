package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.hsqldb.DBDateDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBLvaDateDao;
import at.ac.tuwien.sepm.entity.Date;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.CalService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */

@Service
public class CalServiceImpl implements CalService {

    public List<Date> getAllDatesAt(DateTime date) throws ServiceException {
        DBDateDao dateDao = new DBDateDao();
        DBLvaDateDao lvaDateDao = new DBLvaDateDao();

        List<LvaDate> l1;
        List<DateEntity> l2;
        ArrayList<Date> result = new ArrayList<Date>();

        try {
            l1 = lvaDateDao.readByDay(date);
            l2 = dateDao.readByDay(date);
        } catch (DataAccessException e) {
            throw new ServiceException("Die Termine konnten nicht geladen werden.", e);
        }

        result.addAll(l1);
        result.addAll(l2);

        return result;
    }
}
