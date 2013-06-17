package at.ac.tuwien.sepm.ui.metaLva;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Author: Lena Lenz
 */
@UI
public class ModuleTable extends JTable{
    List<Module> modules;
    int[] colWidth = new int[]{420,50};
    int width = colWidth[0]+colWidth[1];
    DefaultTableModel model;
    public ModuleTable(int width){
        init(width);
    }
    public ModuleTable(List<Module> modules, int width){
        this.modules = modules;
        init(width);
        setModules(modules);
    }
    public ModuleTable(List<Module> modules){
        this.modules = modules;
        init(this.width);
        setModules(modules);
    }
    public int getColWidth(int index){
        return colWidth[index];
    }
    private void init(int width){
        model = new DefaultTableModel(new String[]{"Name","ECTS"},0);

        setModel(model);
        colWidth[0] = Math.max(0,colWidth[0]+(width-this.width));

        for(int i=0;i<2;i++){
            if(i!=2){
                getColumnModel().getColumn(i).setMinWidth(colWidth[i]);
                getColumnModel().getColumn(i).setMaxWidth(colWidth[i]);
            }else{
                getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
            }
        }
    }

    public void setModules(List<Module> modules){
        for(Module m : modules){
            float ectsCount =0;
            for(MetaLVA mLVA:m.getMetaLvas()){
                ectsCount+=mLVA.getECTS();
            }
            model.addRow(new String[]{m.getName(),""+ ectsCount});
        }
    }

    public void refreshModules(List<Module> list) {
        model.setRowCount(0);
        this.setModules(list);
    }

    public Module getSelectedModule(){
        return modules.get(getSelectedRow());
    }

    public void removeSelectedModule() {
        modules.remove(getSelectedRow());
        model.removeRow(getSelectedRow());
    }
}

