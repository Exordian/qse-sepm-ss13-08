package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 04.06.13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewLva extends StandardSimpleInsidePanel {
    private JComboBox metaLVA;
    private JTextField description;
    private JSpinner year;
    private JComboBox semester=null;
    private JTextField grade;

    private JCheckBox inStudyProgress;
    private JTextField goals;
    private JTextField content;
    private JTextField additionalInfo1;
    private JTextField additionalInfo2;
    private JTextField institute;
    private JTextField performanceRecord;
    private JTextField language;

    public ViewLva() {

    }

    public void setLva(LVA lva) {

    }
}
