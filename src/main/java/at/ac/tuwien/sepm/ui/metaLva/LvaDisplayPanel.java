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
    private JTextField searchGrade = new HintTextField("Note");

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
                if (e.isPopupTrigger() || (e.getButton() == 3)) {
                    JTable source = LvaDisplayPanel.this.getTable();
                    int row = source.rowAtPoint( e.getPoint() );
                    int column = source.columnAtPoint( e.getPoint() );

                    if (! source.isRowSelected(row))
                        source.changeSelection(row, column, false, false);

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
        searchPanel.add(searchGrade);
        searchGrade.setPreferredSize(new Dimension(table.getColWidth(4), searchHeight));
        pane.setViewportView(table);

        pane.setPreferredSize(new Dimension(table.getPreferredSize().width, height-searchPanel.getHeight()));
        KeyListener listener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                logger.debug("searching for: (year: "+ searchYear.getText()+", semester:"+ searchSemester.getText()+", typ: " + searchType.getText()+", name: "+ searchName.getText()+", grade: "+searchGrade.getText()+")");
                filteredLVAs = new ArrayList<LVA>();
                for (LVA m : allLVAs) {
                    if (String.valueOf(m.getYear()).toLowerCase().contains(searchYear.getText().toLowerCase()) && m.getSemester().toString().toLowerCase().contains(searchSemester.getText().toLowerCase()) && m.getMetaLVA().getType().toString().toLowerCase().contains(searchType.getText().toLowerCase()) && (m.getMetaLVA().getName().toLowerCase().contains(searchName.getText().toLowerCase()))&& ((""+m.getGrade()).toLowerCase().contains(searchGrade.getText().toLowerCase()))) {
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
        searchGrade.addKeyListener(listener);
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
                    new Thread(){
                        @Override
                        public void run(){
                            try {
                                sleep(10);//waiting, so the right-clicked item really is selected
                            } catch (InterruptedException ignore) {
                            }
                            PanelTube.backgroundPanel.viewLva(getSelectedLVA());
                        }
                    }.start();

                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            add(button2);
        }
    }

    public LVA getSelectedLVA(){
        return table.getSelectedLVA();
    }

    public void refresh(List<LVA> lvas) {
        table.refreshLVAs(lvas);
        this.allLVAs=lvas;
    }

    public LvaTable getTable() {
        return table;
    }
}


