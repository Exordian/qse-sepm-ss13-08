package at.ac.tuwien.sepm.ui.metaLva;

import at.ac.tuwien.sepm.entity.LVA;
import at.ac.tuwien.sepm.entity.MetaLVA;
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
public class MetaLVADisplayPanel extends JPanel {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private List<MetaLVA> allLVAs;
    private List<MetaLVA> filteredLVAs;
    private MetaLVATable table;
    private JTextField searchNr = new HintTextField("Nr");
    private JTextField searchType = new HintTextField("Typ");
    private JTextField searchName = new HintTextField("Name");
    private JTextField searchECTS = new HintTextField("ECTS");

    private JPanel searchPanel = new JPanel();

    private JScrollPane pane = new JScrollPane();

    private LvaDisplayPanel lvaDisplayPanel;

    int tWidth;
    public MetaLVADisplayPanel(List<MetaLVA> lvas,int width,int height){
        this.tWidth =width;
        this.allLVAs = lvas;
        filteredLVAs = lvas;
        table = new MetaLVATable(lvas,width);
        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()||(e.getButton() == 3)) {
                    JTable source = MetaLVADisplayPanel.this.getTable();
                    int row = source.rowAtPoint( e.getPoint() );
                    int column = source.columnAtPoint( e.getPoint() );

                    if (! source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

                    JPopupMenu popup = new PopUpMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
                refreshLVAs();
            }
        });
        int searchHeight = 20;
        add(searchPanel);
        add(pane);

        searchPanel.setLayout(new FlowLayout(0,0,0));//.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        BoxLayout bl = new BoxLayout(this,BoxLayout.Y_AXIS);
        setLayout(bl);
        searchPanel.add(searchNr);
        searchNr.setPreferredSize(new Dimension(table.getColWidth(0), searchHeight));
        searchPanel.add(searchType);
        searchType.setPreferredSize(new Dimension(table.getColWidth(1), searchHeight));
        searchPanel.add(searchName);
        searchName.setPreferredSize(new Dimension(table.getColWidth(2), searchHeight));
        searchPanel.add(searchECTS);
        searchECTS.setPreferredSize(new Dimension(table.getColWidth(3), searchHeight));
        pane.setViewportView(table);

        pane.setPreferredSize(new Dimension(table.getPreferredSize().width, height-searchPanel.getHeight()));
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                logger.debug("searching for: (nr: "+searchNr.getText()+", type:"+searchType.getText()+", name: " + searchName.getText()+", ECTS: "+searchECTS.getText()+")");
                filteredLVAs = new ArrayList<MetaLVA>();
                for (MetaLVA m : allLVAs) {
                    if (m.getNr().contains(searchNr.getText()) && m.getType().toString().contains(searchType.getText()) && m.getName().contains(searchName.getText()) &&(""+m.getECTS()).contains(searchECTS.getText())) {
                        filteredLVAs.add(m);
                    }
                }
                table.refreshMetaLVAs(filteredLVAs);
                refreshLVAs();
                pane.setViewportView(table);
                revalidate();
                repaint();
            }
        };

        searchNr.addKeyListener(listener);
        searchType.addKeyListener(listener);
        searchName.addKeyListener(listener);
        searchECTS.addKeyListener(listener);
    }
    public void clearSearch(){
        searchNr.setText("");
        searchName.setText("");
        searchType.setText("");
        searchECTS.setText("");
    }

    public void setLvaDisplayPanel(LvaDisplayPanel lvaDisplayPanel) {
        this.lvaDisplayPanel = lvaDisplayPanel;
        refreshLVAs();
    }
    public LvaDisplayPanel getLvaDisplayPanel() {
        return lvaDisplayPanel;
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
                    PanelTube.backgroundPanel.viewMetaLva(getSelectedMetaLVA());
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            add(button2);
        }
    }

    public MetaLVATable getTable() {
        return this.table;
    }

    public MetaLVA getSelectedMetaLVA(){
        return table.getSelectedMetaLVA();
    }

    public void removeSelectedMetaLVA() {
        table.removeSelectedMetaLVA();
    }

    public void refresh(List<MetaLVA> lvas) {
        table.refreshMetaLVAs(lvas);
        this.allLVAs=lvas;
        filteredLVAs = new ArrayList<MetaLVA>();
        for (MetaLVA m : allLVAs) {
            if (m.getNr().contains(searchNr.getText()) && m.getType().toString().contains(searchType.getText()) && m.getName().contains(searchName.getText()) &&(""+m.getECTS()).contains(searchECTS.getText())) {
                filteredLVAs.add(m);
            }
        }
        table.refreshMetaLVAs(filteredLVAs);
        pane.setViewportView(table);
        refreshLVAs();
        revalidate();
        repaint();
    }
    private  void refreshLVAs(){
        if(lvaDisplayPanel!=null){
            lvaDisplayPanel.refresh(new ArrayList<LVA>(0));
            if(table.getSelectedRowCount()>0){
                lvaDisplayPanel.refresh(getSelectedMetaLVA().getLVAs());
            }
        }
    }
}

