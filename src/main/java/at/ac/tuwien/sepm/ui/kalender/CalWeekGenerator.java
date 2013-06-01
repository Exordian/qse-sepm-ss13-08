package at.ac.tuwien.sepm.ui.kalender;

import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 */
@UI
public class CalWeekGenerator extends JPanel implements CalendarInterface {
    private CalPanelWeek calPanelWeek;

    @Autowired
    public CalWeekGenerator(CalPanelWeek calPanelWeek) {
        this.calPanelWeek=calPanelWeek;
        this.setLayout(null);
        this.setOpaque(false);
        this.add(calPanelWeek);
    }

    @Override
    public void semester() {
        calPanelWeek.semester();
    }

    @Override
    public String next() throws ServiceException {
        return calPanelWeek.next();
    }

    @Override
    public String last() throws ServiceException {
        return calPanelWeek.last();
    }
}
