package at.ac.tuwien.sepm.ui.lehrangebot;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.ui.UI;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Author: Lena Lenz
 */
@UI
public class CurriculumTable extends JTable{
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    List<Module> modules;
    int[] colWidth = new int[]{80,80,80,500,40,0};
    int width = calcWidth();
    DefaultTableModel model;
    private CurriculumDisplayPanel parent;

    public CurriculumTable(int width){
        init(width);
    }

    public CurriculumTable(List<Module> modules, int width, CurriculumDisplayPanel parent){
        this.modules = modules;
        this.parent = parent;
        init(width);
        setModules(modules);
    }

    public CurriculumTable(List<Module> modules){
        this.modules = modules;
        init(this.width);
        setModules(modules);
    }

    public int getColWidth(int index){
        return colWidth[index];
    }

    private int calcWidth() {
        int w = 0;
        for(int i=0; i<colWidth.length; i++){
            w += colWidth[i];
        }
        return w;
    }

    private void init(int width){
        model = new DefaultTableModel(new String[]{"Enthalten", "Optional", "Vollständig", "Name", "ECTS", "Beschreibung"}, 0){
            @Override
            public boolean isCellEditable(int row,int col){
                if (col <= 2){
                    return true;
                }
                return false;
            }
        };

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getFirstRow() < modules.size()) {
                    if (e.getColumn() == 0) {
                        for(Module m : parent.getAllModules()) {
                            if (m.getName().equals(modules.get(e.getFirstRow()).getName())) {
                                m.setTempBooleanContained(!m.getTempBooleanContained());
                                return;
                            }
                        }
                    } else if (e.getColumn() == 1) {
                        for(Module m : parent.getAllModules()) {
                            if (m.getName().equals(modules.get(e.getFirstRow()).getName())) {
                                m.setTempBooleanOptional(!m.getTempBooleanOptional());
                                return;
                            }
                        }
                    } else if (e.getColumn() == 2) {
                        for(Module m : parent.getAllModules()) {
                            if (m.getName().equals(modules.get(e.getFirstRow()).getName())) {
                                m.setCompleteall(!m.getCompleteall());
                                return;
                            }
                        }

                    }
                }
            }
        });

        setModel(model);
        colWidth[colWidth.length-1] = Math.max(0,width-this.width);

        for(int i=0;i<colWidth.length;i++){
            getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
            getColumnModel().getColumn(i).setMinWidth(colWidth[i]);
            getColumnModel().getColumn(i).setMaxWidth(colWidth[i]);
        }
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return Boolean.class;
            case 1:
                return Boolean.class;
            case 2:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    public void setModules(List<Module> modules){
        for(Module m : modules){
            float ectsCount =0;
            for(MetaLVA mLVA:m.getMetaLvas()){
                ectsCount+=mLVA.getECTS();
            }
            model.addRow(new Object[]{m.getTempBooleanContained(), m.getTempBooleanOptional(), m.getCompleteall(), m.getName(),""+ ectsCount, m.getDescription()});
        }
        this.modules = modules;
        logger.info("CurriculumTable.setModules(...) with " + modules.size() + " modules completed ... ");
    }

    public void refreshModules(List<Module> list) {
        model.setRowCount(0);
        this.setModules(list);
        logger.info("CurriculumTable.refreshModules(...) with " + list.size() + " modules completed ... ");
    }

    public boolean getContainedInCurriculum () {
        return ((Boolean) model.getValueAt(getSelectedRow(), 0)).booleanValue();
    }

    public Module getSelectedModule(){
        return modules.get(getSelectedRow());
    }

    public void removeSelectedModule() {
        modules.remove(getSelectedRow());
        model.removeRow(getSelectedRow());
    }
}