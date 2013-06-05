package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 04.06.13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
@UI
public class ViewMetaLva extends StandardSimpleInsidePanel {
    private JTextField id;
    private JTextField nr;
    private JTextField name;
    private JSpinner ects;
    private JComboBox type;
    private JSpinner priority;
    private JComboBox semestersOffered;
    private JComboBox module;
    private JCheckBox completed;

    public ViewMetaLva() {

    }

    @Override
    public void refresh() {
        //do nothing
    }

    public void setMetaLva(MetaLVA metalva) {

    }
}
