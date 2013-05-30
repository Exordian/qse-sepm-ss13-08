package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.MetaLVA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;

/**
 * Author: Lena Lenz
 */
public class MetaLVATable extends JTable{
    List<MetaLVA> lvas;
    int[] colWidth = new int[]{60,30,420,40};
    int width = colWidth[0]+colWidth[1]+colWidth[2]+colWidth[3];
    DefaultTableModel model;
    public MetaLVATable(int width){
        init(width);
    }
    public MetaLVATable(List<MetaLVA> lvas,int width){
        init(width);
        setMetaLVAs(lvas);
    }
    public MetaLVATable(List<MetaLVA> lvas){
        init(this.width);
        setMetaLVAs(lvas);
    }
    public int getColWidth(int index){
        return colWidth[index];
    }
    private void init(int width){
        model = new DefaultTableModel(new String[]{"Nr","Typ","Name","ECTS"},0);
        setModel(model);
        colWidth[2] = Math.max(0,colWidth[2]+(width-this.width));

        for(int i=0;i<4;i++){
            if(i!=2){
                getColumnModel().getColumn(i).setMinWidth(colWidth[i]);
                getColumnModel().getColumn(i).setMaxWidth(colWidth[i]);
            }else{
                getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
            }
        }
    }

    public void setMetaLVAs(List<MetaLVA> lvas){
        for(MetaLVA lva: lvas){
            model.addRow(new String[]{lva.getNr(),lva.getType().toString(),lva.getName(),""+lva.getECTS()});
        }
    }
    public MetaLVA getSelectedMetaLVA(){
        return lvas.get(getSelectedRow());
    }
}

