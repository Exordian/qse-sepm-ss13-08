package at.ac.tuwien.sepm.ui.calender.cal;

import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.UI;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
@UI
public interface CalendarInterface {
    public void refresh();
    public void semester();
    public void next() throws ServiceException;
    public void last() throws ServiceException;
    public void goToDay(DateTime date);
    public void init();
    String getTimeIntervalInfo();
}
