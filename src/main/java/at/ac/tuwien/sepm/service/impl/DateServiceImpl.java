package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Author: Flo
 */
@Service
public class DateServiceImpl implements DateService {
    @Autowired
    DateDao dateDao;

    @Override
    public void createDate(DateEntity toCreate) throws ServiceException {
        this.validateDateEntity(toCreate);

        try {
            dateDao.create(toCreate);
        } catch (IOException e) {
            throw new ServiceException("DateEntity could not be created.", e);
        } catch (DataAccessException d) {
            throw new ServiceException("DateEntity could not be created.", d);
        }
    }

    @Override
    public void updateDate(DateEntity toUpdate) throws ServiceException {
        this.validateDateEntity(toUpdate);

        try {
            dateDao.update(toUpdate);
        } catch (IOException e) {
            throw new ServiceException("DateEntity with ID:" + toUpdate.getId()  + " could not be updated.", e);
        } catch (DataAccessException d) {
            throw new ServiceException("DateEntity with ID:" + toUpdate.getId()  + " could not be updated.", d);
        }
    }

    @Override
    public void deleteDate(int id) throws ServiceException {
        this.validateId(id);

        try {
            dateDao.delete(id);
        } catch (DataAccessException d) {
            throw new ServiceException("DateEntity with ID:" + id  + " could not be deleted.", d);
        }
    }

    @Override
    public DateEntity readDateById(int id) throws ServiceException {
        this.validateId(id);

        try {
            return dateDao.readById(id);
        } catch (DataAccessException d) {
            throw new ServiceException("DateEntity with ID:" + id  + " could not be found.", d);
        }
    }

    @Override
    public List<DateEntity> readDateInTimeframe(DateTime from, DateTime till) throws ServiceException {
        this.validateDate(from);
        this.validateDate(till);

        try {
            return dateDao.readInTimeframe(from, till);
        } catch (DataAccessException d) {
            throw new ServiceException("DateEntities could not be found.", d);
        }
    }

    @Override
    public void validateDateEntity(DateEntity toValidate) throws ServiceException {
        validateId(toValidate.getId());

        if (toValidate.getName() == null || toValidate.getName().isEmpty()) {
            throw new ServiceException("Name of DateEntity is invalid or empty.");
        }

        if (toValidate.getDescription() == null) {
            throw new ServiceException("Description of DateEntity is invalid.");
        }

        if (toValidate.getIntersectable() == null) {
            throw new ServiceException("Intersectable of DateEntity is invalid.");
        }

        if (toValidate.getStart() == null) {
            throw new ServiceException("Start of DateEntity is invalid.");
        }

        if (toValidate.getStop() == null) {
            throw new ServiceException("Stop of DateEntity is invalid.");
        }
    }

    @Override
    public void validateId(int id) throws ServiceException {
        if (id < 0) {
            throw new ServiceException("Id of DateEntity is negative.");
        }
    }

    @Override
    public void validateDate(DateTime date) throws ServiceException {
        if (date == null) {
            throw new ServiceException("Date is invalid.");
        }
    }
}
