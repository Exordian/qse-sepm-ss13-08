package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.dao.LvaDateDao;
import at.ac.tuwien.sepm.entity.Date;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.CalService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */

@Service
public class CalServiceImpl implements CalService {
    DateDao dateDao;
    LvaDateDao lvaDateDao;
    @Autowired
    public CalServiceImpl(DateDao dateDao,LvaDateDao lvaDateDao){
        this.dateDao=dateDao;
        this.lvaDateDao=lvaDateDao;
    }
    @Override
    public List<Date> getAllDatesAt(DateTime date) throws ServiceException {

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

    @Override
    public List<DateEntity> getAllNotLVADatesAt(DateTime date) throws ServiceException {
        try {
            return dateDao.readByDay(date);
        } catch (DataAccessException e) {
            throw new ServiceException("Die Termine konnten nicht geladen werden.", e);
        }
    }

    @Override
    public List<LvaDate> getLVADatesByDateInStudyProgress(DateTime date) throws ServiceException {
        try {
            return lvaDateDao.readByDayInStudyProgress(date);
        } catch (DataAccessException e) {
            throw new ServiceException("Die Termine konnten nicht geladen werden.", e);
        }
    }
}