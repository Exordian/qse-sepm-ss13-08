package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.entity.Date;
import at.ac.tuwien.sepm.entity.LvaDate;
import org.joda.time.DateTime;

import java.util.List;

/**
 * @author Markus MUTH
 */

public interface CalService {

    /**
     * Return all dates and lva dates in one list. The first elements are all lva dates in chronlogical order, the
     * last elements are all normal dates in chronological order.
     * @param date the Day from where the dates should be returned.
     * @return A list containing all dates from the specified day.
     * @throws ServiceException If the data could not be read.
     */
    public List<Date> getAllDatesAt(DateTime date) throws ServiceException;
    public List<LvaDate> getLVADatesByDateInStudyProgress(DateTime date) throws ServiceException;
}
