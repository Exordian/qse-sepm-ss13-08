package at.ac.tuwien.sepm.ui.verlauf;

import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewPanel extends StandardInsidePanel {
    private JPanel panel;
    private JButton saveb;
    private JComboBox<Integer> yearcb;
    private JComboBox<Semester> semestercb;
    private DefaultComboBoxModel<Integer> yearcbm;
    private DefaultComboBoxModel<Semester> semestercbm;
    private JTable lvat;

    public ViewPanel () {
        //this.setLayout(new MigLayout());
        loadFonts();
        setSize((int) whiteSpaceCalendar.getWidth(), (int) whiteSpaceCalendar.getHeight());
        setLocation(CalStartCoordinateOfWhiteSpace);
        this.setVisible(true);
        initPanel();
        placeComponents();
        revalidate();
        repaint();
    }

    private void placeComponents() {
        this.add(panel);

    }

    private void initPanel() {
        panel = new JPanel(new MigLayout());
        panel.setBackground(Color.YELLOW);
        panel.setBounds(whiteSpaceStud);

    }

    //TODO
    //ein JTabel der alle lvas des angegebenen semesters anzeigt
    //ein defaulttablemodel
    //zwei comboboxen zum auswählen vom semester und jahr
    //ein speichern button

}
