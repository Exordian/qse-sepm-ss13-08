package at.ac.tuwien.sepm.ui.metaLva;

import at.ac.tuwien.sepm.entity.LVA;
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
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 12.06.13
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
@UI
public class LvaDisplayPanel extends JPanel {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private List<LVA> allLVAs;
    private List<LVA> filteredLVAs;
    private LvaTable table;
    private JTextField searchYear = new HintTextField("Jahr");
    private JTextField searchSemester = new HintTextField("Semester");
    private JTextField searchType = new HintTextField("Typ");
    private JTextField searchName = new HintTextField("Name");

    private JPanel searchPanel = new JPanel();

    private JScrollPane pane = new JScrollPane();

    int tWidth;
    public LvaDisplayPanel(List<LVA> lvas,int width,int height){
        this.tWidth =width;
        this.allLVAs = lvas;
        filteredLVAs = lvas;
        table = new LvaTable(lvas,width);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = LvaDisplayPanel.this.getTable().rowAtPoint(e.getPoint());
                if (r >= 0 && r < LvaDisplayPanel.this.getTable().getRowCount()) {
                    LvaDisplayPanel.this.getTable().setRowSelectionInterval(r, r);
                } else {
                    LvaDisplayPanel.this.getTable().clearSelection();
                }

                int rowindex = LvaDisplayPanel.this.getTable().getSelectedRow();
                if (rowindex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
                    JPopupMenu popup = new PopUpMenu();
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        int searchHeight = 20;
        add(searchPanel);
        add(pane);

        searchPanel.setLayout(new FlowLayout(0,0,0));//.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        BoxLayout bl = new BoxLayout(this,BoxLayout.Y_AXIS);
        setLayout(bl);
        searchPanel.add(searchYear);
        searchYear.setPreferredSize(new Dimension(table.getColWidth(0), searchHeight));
        searchPanel.add(searchSemester);
        searchSemester.setPreferredSize(new Dimension(table.getColWidth(1), searchHeight));
        searchPanel.add(searchType);
        searchType.setPreferredSize(new Dimension(table.getColWidth(2), searchHeight));
        searchPanel.add(searchName);
        searchName.setPreferredSize(new Dimension(table.getColWidth(3), searchHeight));
        pane.setViewportView(table);

        pane.setPreferredSize(new Dimension(table.getPreferredSize().width, height-searchPanel.getHeight()));
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                logger.debug("searching for: (year: "+ searchYear.getText()+", semester:"+ searchSemester.getText()+", typ: " + searchType.getText()+", name: "+ searchName.getText()+")");
                filteredLVAs = new ArrayList<LVA>();
                for (LVA m : allLVAs) {
                    if (String.valueOf(m.getYear()).contains(searchYear.getText()) && m.getSemester().toString().contains(searchSemester.getText()) && m.getMetaLVA().getType().toString().contains(searchType.getText()) && (m.getMetaLVA().getName().contains(searchName.getText()))) {
                        filteredLVAs.add(m);
                    }
                }
                table.refreshLVAs(filteredLVAs);
                pane.setViewportView(table);
                revalidate();
                repaint();
            }
        };

        searchYear.addKeyListener(listener);
        searchSemester.addKeyListener(listener);
        searchType.addKeyListener(listener);
        searchName.addKeyListener(listener);
    }


    private class PopUpMenu extends JPopupMenu {
        private JMenuItem edit;
        private JMenuItem create;

        public PopUpMenu(){
            create = new JMenuItem("Erstellen");
            create.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    PanelTube.backgroundPanel.viewLva(null);
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            add(create);

            edit = new JMenuItem("Bearbeiten");
            edit.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    PanelTube.backgroundPanel.viewLva(getSelectedLVA());
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            add(edit);
        }
    }

    public LVA getSelectedLVA(){
        return table.getSelectedLVA();
    }

    public void refresh(List<LVA> lvas) {
        table.refreshLVAs(lvas);
    }

    public LvaTable getTable() {
        return table;
    }
}


