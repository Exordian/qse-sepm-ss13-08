package at.ac.tuwien.sepm.ui.calendar;

import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.calendar.CalPanelMonth;
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
public class CalWeekGenerator extends JPanel {
    private CalPanelMonth calPanelMonth;

    @Autowired
    public CalWeekGenerator(CalPanelMonth calPanelMonth) {

        //todo calpanelmonth austauschen mit calpanelweek
        this.calPanelMonth=calPanelMonth;
        this.setLayout(null);
        this.setOpaque(false);
        this.add(calPanelMonth);
    }
}
