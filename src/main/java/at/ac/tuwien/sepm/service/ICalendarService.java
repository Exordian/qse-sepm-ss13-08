package at.ac.tuwien.sepm.service;

import java.io.File;

/**
 * @author Markus MUTH
 */
public interface ICalendarService {
    public int icalImport(File cal) throws ServiceException;

    public boolean icalExport(File cal) throws ServiceException;

    public void iCalFileValidator(File f, boolean isExisting) throws ServiceException;
}
