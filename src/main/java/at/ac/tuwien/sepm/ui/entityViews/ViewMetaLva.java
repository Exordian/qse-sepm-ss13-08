package at.ac.tuwien.sepm.ui.entityViews;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.ui.StandardSimpleInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

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

    private MetaLVAService metaLVAService;
    private MetaLVA metaLVA;

    @Autowired
    public ViewMetaLva(MetaLVAService metaLVAService) {
        this.metaLVAService=metaLVAService;
        init();
        addImage();
        metaLVA = new MetaLVA();
        addTitle("Neue Lva");
        addReturnButton();
        addContent();
        addButtons();
        this.repaint();
        this.revalidate();
    }

    private void addButtons() {

    }

    private void addContent() {

    }

    public void setMetaLva(MetaLVA metalva) {

    }
}
