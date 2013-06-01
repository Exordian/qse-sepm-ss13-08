package at.ac.tuwien.sepm.ui.kalender;

import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.UI;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
@UI
public interface CalendarInterface {
    public void semester();
    public String next() throws ServiceException;
    public String last() throws ServiceException;
}
