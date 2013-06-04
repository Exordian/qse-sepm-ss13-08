package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Override
    public void createDate(DateEntity toCreate) throws ServiceException {
        this.validateDateEntity(toCreate);

        try {
            dateDao.create(toCreate);
        } catch (IOException e) {
            log.error("DateEntity could not be created.");
            throw new ServiceException("DateEntity could not be created.", e);
        } catch (DataAccessException d) {
            log.error("DateEntity could not be created.");
            throw new ServiceException("DateEntity could not be created.", d);
        }
    }

    @Override
    public void updateDate(DateEntity toUpdate) throws ServiceException {
        this.validateDateEntity(toUpdate);
        this.validateId(toUpdate.getId());

        try {
            dateDao.update(toUpdate);
        } catch (IOException e) {
            log.error("DateEntity with ID:" + toUpdate.getId()  + " could not be updated.");
            throw new ServiceException("DateEntity with ID:" + toUpdate.getId()  + " could not be updated.", e);
        } catch (DataAccessException d) {
            log.error("DateEntity with ID:" + toUpdate.getId()  + " could not be updated.");
            throw new ServiceException("DateEntity with ID:" + toUpdate.getId()  + " could not be updated.", d);
        }
    }

    @Override
    public void deleteDate(int id) throws ServiceException {
        this.validateId(id);

        try {
            dateDao.delete(id);
        } catch (DataAccessException d) {
            log.error("DateEntity with ID:" + id  + " could not be deleted.");
            throw new ServiceException("DateEntity with ID:" + id  + " could not be deleted.", d);
        }
    }

    @Override
    public DateEntity readDateById(int id) throws ServiceException {
        this.validateId(id);

        try {
            return dateDao.readById(id);
        } catch (DataAccessException d) {
            log.error("DateEntity with ID:" + id  + " could not be found.");
            throw new ServiceException("DateEntity with ID:" + id  + " could not be found.", d);
        }
    }

    @Override
    public List<DateEntity> readDateInTimeframe(DateTime from, DateTime to) throws ServiceException {
        this.validateDates(from, to);

        try {
            return dateDao.readInTimeframe(from, to);
        } catch (DataAccessException d) {
            log.error("DateEntities could not be found.");
            throw new ServiceException("DateEntities could not be found.", d);
        }
    }

    @Override
    public void validateDateEntity(DateEntity toValidate) throws ServiceException {
        if (toValidate == null) {
            log.error("DateEntity is invalid.");
            throw new ServiceException("DateEntity is invalid.");
        }

        if (toValidate.getName() == null) {
            log.error("Name of DateEntity is invalid.");
            throw new ServiceException("Name of DateEntity is invalid.");
        }

        if (toValidate.getDescription() == null) {
            log.error("Description of DateEntity is invalid.");
            throw new ServiceException("Description of DateEntity is invalid.");
        }

        if (toValidate.getIntersectable() == null) {
            log.error("Intersectable of DateEntity is invalid.");
            throw new ServiceException("Intersectable of DateEntity is invalid.");
        }

        if (toValidate.getTime() == null) {
            log.error("Start of DateEntity is invalid.");
            throw new ServiceException("Start of DateEntity is invalid.");
        }

        validateDates(toValidate.getStart(), toValidate.getStop());
    }

    @Override
    public void validateId(int id) throws ServiceException {
        if (id < 0) {
            log.error("Id of DateEntity is negative.");
            throw new ServiceException("Id of DateEntity is negative.");
        }
    }

    @Override
    public void validateDates(DateTime from, DateTime to) throws ServiceException {
        if (from == null) {
            log.error("Date is invalid.");
            throw new ServiceException("Date is invalid.");
        }

        if (to == null) {
            log.error("Date is invalid.");
            throw new ServiceException("Date is invalid.");
        }

        if (from.isAfter(to)) {
            log.error("To is before From.");
            throw new ServiceException("To is before From.");
        }
    }

    @Override
    public Semester getCurrentSemester() {
        DateTime currentTime = new DateTime(System.currentTimeMillis());
        if(WINTER_SEMESTER_START_MONTH<WINTER_SEMESTER_END_MONTH){
            if(currentTime.getMonthOfYear()>WINTER_SEMESTER_START_MONTH && currentTime.getMonthOfYear()<WINTER_SEMESTER_END_MONTH){
                return Semester.S;
            }
            return Semester.W;
        }else{
            if(currentTime.getMonthOfYear()>WINTER_SEMESTER_START_MONTH || currentTime.getMonthOfYear()<WINTER_SEMESTER_END_MONTH){
                return Semester.W;
            }
            return Semester.S;
        }
    }

    @Override
    public int getCurrentYear() {
        //todo not just return the year, but also check for the semester first!!
        DateTime currentTime = new DateTime(System.currentTimeMillis());
        return currentTime.getYear();
    }
}
