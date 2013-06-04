package at.ac.tuwien.sepm.ui.MetaLva;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.LvaType;
import at.ac.tuwien.sepm.service.MetaLVAService;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.ui.MetaLva.MetaLVADisplayPanel;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Lena
 * Date: 01.06.13
 * Time: 09:23
 * To change this template use File | Settings | File Templates.
 */
@UI
public class MetaLVAAdderFrame extends JFrame {

    private JTextField nr;
    private JTextField name;
    private JTextField ects;
    private ArrayList<MetaLVA> precursor = new ArrayList<MetaLVA>();
    private JComboBox<LvaType> type;
    private JTextField priority;
    private JComboBox<Semester> semestersOffered;
    private JTextField module;

    private MetaLVADisplayPanel metaLVADisplayPanel;


    public MetaLVAAdderFrame(MetaLVADisplayPanel metaLVATable, MetaLVAService service) {

    }
}
