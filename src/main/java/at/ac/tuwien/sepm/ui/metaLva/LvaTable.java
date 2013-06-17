package at.ac.tuwien.sepm.ui.metaLva;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 12.06.13
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
@UI
public class LvaTable extends JTable {
    List<LVA> lvas;
    int[] colWidth = new int[]{35,62,30,410,40};
    int width = colWidth[0]+colWidth[1]+colWidth[2]+colWidth[3]+colWidth[4];
    DefaultTableModel model;

    public LvaTable(int width){
        init(width);
    }

    public LvaTable(List<LVA> lvas,int width){
        this.lvas = lvas;
        init(width);
        setLVAs(lvas);
    }

    public LvaTable(List<LVA> lvas){
        this.lvas = lvas;
        init(this.width);
        setLVAs(lvas);
    }

    public int getColWidth(int index){
        return colWidth[index];
    }

    private void init(int width){
        model = new DefaultTableModel(new String[]{"Jahr","Semester","Typ","Name","Note"},0){
            @Override
            public boolean isCellEditable(int row,int col){
                return false;
            }
        };

        setModel(model);
        colWidth[3] = Math.max(0,colWidth[3]+(width-this.width));

        for(int i=0;i<5;i++){
            if(i!=3){
                getColumnModel().getColumn(i).setMinWidth(colWidth[i]);
                getColumnModel().getColumn(i).setMaxWidth(colWidth[i]);
            }else{
                getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
            }
        }
    }

    public void setLVAs(List<LVA> lvas){
        for(LVA lva: lvas){
            String grade = ""+lva.getGrade();
            if(lva.getGrade()==0){
                grade="n.a.";
            }
            model.addRow(new String[]{Integer.toString(lva.getYear()), lva.getSemester().toString(),lva.getMetaLVA().getType().toString(),lva.getMetaLVA().getName(),grade});
        }
        this.lvas=lvas;
    }

    public void refreshLVAs(List<LVA> list) {
        model.setRowCount(0);
        this.setLVAs(list);
    }

    public LVA getSelectedLVA(){
        return lvas.get(getSelectedRow());
    }
}
