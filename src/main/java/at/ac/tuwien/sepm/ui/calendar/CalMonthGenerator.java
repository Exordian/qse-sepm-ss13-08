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
public class CalMonthGenerator extends JPanel {
    private CalPanelMonth calPanelMonth;

    @Autowired
    public CalMonthGenerator(CalPanelMonth calPanelMonth) {

        //braucht man, damit man den kalender frei verschieben kann
        //das verschieben passiert dann in calpanelmonth
        //das muss bei der wochenansicht auch so gemacht werden
        this.calPanelMonth=calPanelMonth;
        this.setLayout(null);
        this.setOpaque(false);
        this.add(calPanelMonth);
    }
}
