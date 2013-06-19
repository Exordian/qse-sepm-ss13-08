package at.ac.tuwien.sepm.ui.metaLva;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.HintTextField;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lena Lenz
 */
@UI
public class ModuleDisplayPanel extends JPanel {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private List<Module> allModules;
    private List<Module> filteredModules;
    private ModuleTable table;
    private JTextField searchName = new HintTextField("Name");
    private JTextField searchECTS = new HintTextField("ECTS");

    private JPanel searchPanel = new JPanel();

    private JScrollPane pane = new JScrollPane();

    private MetaLVADisplayPanel metaLVAPanel;

    int tWidth;
    public ModuleDisplayPanel(List<Module> modules, int width, int height){
        this.tWidth =width;
        this.allModules = modules;
        //this.metaLVAPanel = metaPanel;
        filteredModules = modules;
        table = new ModuleTable(modules,width);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || (e.getButton() == 3)) {
                    logger.debug("TEST rechtsklick");
                    JTable source = ModuleDisplayPanel.this.getTable();
                    int row = source.rowAtPoint( e.getPoint() );
                    int column = source.columnAtPoint( e.getPoint() );

                    if (! source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    JPopupMenu popup = new PopUpMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
                refreshMetaLVAs();
            }
        });
        int searchHeight = 20;
        add(searchPanel);
        add(pane);

        searchPanel.setLayout(new FlowLayout(0,0,0));//.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        BoxLayout bl = new BoxLayout(this,BoxLayout.Y_AXIS);
        setLayout(bl);
        searchPanel.add(searchName);
        searchName.setPreferredSize(new Dimension(table.getColWidth(0), searchHeight));
        searchPanel.add(searchECTS);
        searchECTS.setPreferredSize(new Dimension(table.getColWidth(1), searchHeight));

        pane.setViewportView(table);

        pane.setPreferredSize(new Dimension(table.getPreferredSize().width, height-searchPanel.getHeight()));
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                logger.debug("name: " + searchName.getText()+", total ECTS: "+searchECTS.getText()+")");
                filteredModules = new ArrayList<Module>();
                for (Module m : allModules) {
                    float ectsCount =0;
                    for(MetaLVA mLVA : m.getMetaLvas()){
                        ectsCount+=mLVA.getECTS();
                    }
                    if (m.getName().toLowerCase().contains(searchName.getText().toLowerCase()) &&(""+ectsCount).toLowerCase().contains(searchECTS.getText().toLowerCase())) {
                        filteredModules.add(m);
                    }
                }
                table.refreshModules(filteredModules);
                refreshMetaLVAs();
                pane.setViewportView(table);

                revalidate();
                repaint();
            }
        };

        searchName.addKeyListener(listener);
        searchECTS.addKeyListener(listener);
    }
    public MetaLVADisplayPanel getMetaLVAPanel(){
        return metaLVAPanel;
    }

    public void setMetaLVAPanel(MetaLVADisplayPanel metaLVAPanel) {
        this.metaLVAPanel = metaLVAPanel;
        refreshMetaLVAs();
    }


    private class PopUpMenu extends JPopupMenu {
        private JMenuItem button2;

        public PopUpMenu(){
            button2 = new JMenuItem("Bearbeiten");
            button2.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    PanelTube.backgroundPanel.viewModule(getSelectedModule());
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            add(button2);
        }
    }

    public ModuleTable getTable() {
        return this.table;
    }

    public Module getSelectedModule(){
        return table.getSelectedModule();
    }

    public void removeSelectedMetaLVA() {
        table.removeSelectedModule();
    }

    public void refresh(List<Module> modules) {
        table.refreshModules(modules);
        this.allModules=modules;
        refreshMetaLVAs();
        //metaLVAPanel.clearSearch();
    }
    private void refreshMetaLVAs(){
        //System.out.println("panel==null: "+metaLVAPanel==null);
        if(metaLVAPanel!=null){
            metaLVAPanel.refresh(new ArrayList<MetaLVA>(0));
            if(table.getSelectedRowCount()>0){
                //System.out.println("rowcount: "+table.getSelectedRowCount());
                metaLVAPanel.refresh(getSelectedModule().getMetaLvas());
            }
        }
    }
}

