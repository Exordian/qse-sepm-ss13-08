package at.ac.tuwien.sepm.ui.kalender;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Author: MUTH Markus
 * Date: 5/29/13
 * Time: 3:04 PM
 * Description of class "DayPanel":
 */
public class DayPanel extends JPanel {
    private int day;
    private JLabel title;
    private ArrayList<DateLabel> dates;

    public DayPanel () {
        super(new MigLayout());
        dates = new ArrayList<DateLabel>();
    }

    public void addDateLabel(DateLabel date) {

    }

    public void draw() {

    }
}
