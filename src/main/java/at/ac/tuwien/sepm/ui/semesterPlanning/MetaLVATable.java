package at.ac.tuwien.sepm.ui.semesterPlanning;

import at.ac.tuwien.sepm.entity.MetaLVA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author: Georg Plaz
 */
public class MetaLVATable extends JTable{
    ArrayList<MetaLVA> lvas;
    public MetaLVATable(ArrayList<MetaLVA> lvas){
        super(new String[][]{{}},new String[]{"Nr","Name","ECTS"});
        this.lvas = lvas;
        DefaultTableModel model = new DefaultTableModel(new String[]{"Nr","Typ","Name","ECTS"},0);
        setModel(model);
        for(MetaLVA lva: lvas){
            model.addRow(new String[]{lva.getNr(),lva.getType().toString(),lva.getName(),""+lva.getECTS()});
        }
        getColumnModel().getColumn(0).setMinWidth(60);
        getColumnModel().getColumn(0).setMaxWidth(60);

        getColumnModel().getColumn(1).setMinWidth(30);
        getColumnModel().getColumn(1).setMaxWidth(30);
        getColumnModel().getColumn(2).setMinWidth(420);
        getColumnModel().getColumn(2).setMaxWidth(420);
        getColumnModel().getColumn(3).setPreferredWidth(10);

        //setPreferredSize(new Dimension(50,100));

    }
}
