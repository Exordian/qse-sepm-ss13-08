package at.ac.tuwien.sepm.service;


import at.ac.tuwien.sepm.entity.DateEntity;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Author: Flo
 */
public interface DateService {

    /**
     * Validates toCreate and passes it to DateDAO.
     * @param toCreate A DateEntity containing all data to be stored.
     * @throws ServiceException If toCreate is not valid
     *
     */
    public void createDate(DateEntity toCreate) throws ServiceException;

    /**
     * Validates toUpdate and passes it to DateDAO.
     * @param toUpdate A DateEntity containing all data to be stored.
     * @throws ServiceException If toUpdate is not valid
     *
     */
    public void updateDate(DateEntity toUpdate) throws ServiceException;

    /**
     * Validates Id and passes the Id of the DateEntity to be deleted to DateDAO.
     * @param id The Id of the DateEntity to be deleted.
     * @throws ServiceException If id is not valid
     *
     */
    public void deleteDate(int id) throws ServiceException;

    /**
     * Validates Id and passes it on to DateDAO.
     * @param id The Id of the DateEntity to be read.
     * @throws ServiceException If id is not valid
     * @return DateEntity or null if DateEntity with id does not exist
     *
     */
    public DateEntity readDateById(int id) throws ServiceException;

    /**
     * Validates from and till and passes them on to DateDAO.
     * @param  from The start date and time. This moment is included to the search.
     * @param  till The stop date and time. This moment is included to the search.
     * @throws ServiceException If from or till are not valid
     * @return List of DateEntity or an empty list if no DateEntity exists within from and till
     *
     */
    public List<DateEntity> readDateInTimeframe(DateTime from, DateTime till) throws ServiceException;

    /**
     * Validates a DateEntity.
     * @param  toValidate The DateEntity to validate.
     * @throws ServiceException If toValidate is not valid
     * (toValidate.id < 0, toValidate.name/description/isIntersectable/start/stop == null).
     *
     */
    public void validateDateEntity(DateEntity toValidate) throws ServiceException;

    /**
     * Validates an id.
     * @param  id The DateEntity to validate.
     * @throws ServiceException If id is not valid (id < 0).
     *
     */
    public void validateId(int id) throws ServiceException;

    /**
     * Validates a date.
     * @param  from The DateTime to validate.
     * @param  to The DateTime to validate.
     * @throws ServiceException If from or to are not valid (datetime == null) or if TO is before FROM.
     *
     */
    public void validateDates(DateTime from, DateTime to) throws ServiceException;
}
