package at.ac.tuwien.sepm.ui.lehrangebot;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.entity.Module;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.metaLva.MetaLVADisplayPanel;
import at.ac.tuwien.sepm.ui.template.HintTextField;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lena Lenz
 */
@UI
public class CurriculumDisplayPanel extends StandardInsidePanel {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    private List<Module> allModules;
    private List<Module> filteredModules;
    private CurriculumTable table;
    private JTextField searchName = new HintTextField("Name");
    private JTextField searchEcts = new HintTextField("ECTS");
    private JTextField searchDescription = new HintTextField("Beschreibung");
    private JCheckBox searchContains = new JCheckBox();
    private JCheckBox searchObligatory = new JCheckBox();
    private JCheckBox searchCompleteAll = new JCheckBox();
    private KeyListener listener;
    private SearchCheckBoxMouseListener searchContainsListener;

    private JPanel searchPanel = new JPanel();

    private JScrollPane pane = new JScrollPane();

    private MetaLVADisplayPanel metaLVAPanel;

    int tWidth;
    public CurriculumDisplayPanel(List<Module> modules, int width, int height){
        this.tWidth =width;
        this.allModules = modules;
        loadFonts();
        //this.metaLVAPanel = metaPanel;
        filteredModules = modules;
        table = new CurriculumTable(modules, width - pane.getVerticalScrollBar().getWidth()-10, this);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                refreshMetaLVAs();
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || (e.getButton() == 3)) {
                    logger.debug("TEST rechtsklick");
                    JTable source = CurriculumDisplayPanel.this.getTable();
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

        searchContains.setPreferredSize(new Dimension(table.getColWidth(0), searchHeight));
        searchContains.setBackground(Color.WHITE);
        searchObligatory.setPreferredSize(new Dimension(table.getColWidth(1), searchHeight));
        searchObligatory.setBackground(Color.WHITE);
        searchCompleteAll.setPreferredSize(new Dimension(table.getColWidth(2), searchHeight));
        searchCompleteAll.setBackground(Color.WHITE);
        searchName.setPreferredSize(new Dimension(table.getColWidth(3) + 1, searchHeight));
        searchEcts.setPreferredSize(new Dimension(table.getColWidth(4), searchHeight));
        searchDescription.setPreferredSize(new Dimension(table.getColWidth(5)-23, searchHeight));

        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(searchContains);
        searchPanel.add(searchObligatory);
        searchPanel.add(searchCompleteAll);
        searchPanel.add(searchName);
        searchPanel.add(searchEcts);
        searchPanel.add(searchDescription);

        pane.setViewportView(table);

        pane.setPreferredSize(new Dimension(table.getPreferredSize().width, height-searchPanel.getHeight()));
        listener = new SearchTextFieldKeyAdapter();
        searchContainsListener = new SearchCheckBoxMouseListener(searchContains);

        searchObligatory.addMouseListener(new SearchCheckBoxMouseListener(searchObligatory));
        searchContains.addMouseListener(searchContainsListener);
        searchCompleteAll.addMouseListener(new SearchCheckBoxMouseListener(searchCompleteAll));
        searchName.addKeyListener(listener);
        searchEcts.addKeyListener(listener);
        searchDescription.addKeyListener(listener);
    }

    public List<Module> getAllModules() {
        return allModules;
    }

    private class SearchTextFieldKeyAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            logger.debug("name: " + searchName.getText() + ", total ECTS: " + searchEcts.getText() + ", description: " + searchDescription.getText() + ")");
            filteredModules = new ArrayList<Module>();
            for (Module m : allModules) {
                logger.info(m.toString());
                float ectsCount = 0;
                for(MetaLVA mLVA : m.getMetaLvas()){
                    ectsCount+=mLVA.getECTS();
                }

                boolean contains = true;
                if(searchContains.isEnabled() && ((searchContains.isSelected() && !m.getTempBooleanContained()) || (!searchContains.isSelected() && m.getTempBooleanContained()))) {
                    contains = false;
                }

                boolean optional = true;
                if(searchObligatory.isEnabled() && ((searchObligatory.isSelected() && !m.getTempBooleanOptional()) || (!searchObligatory.isSelected() && m.getTempBooleanOptional()))) {
                    optional = false;
                }

                boolean completeAll = true;
                if(searchCompleteAll.isEnabled() && ((searchCompleteAll.isSelected() && !m.getCompleteall()) || (!searchCompleteAll.isSelected() && m.getCompleteall()))) {
                    completeAll = false;
                }

                boolean description = true;
                if (searchDescription.getText() != null && !(searchDescription.getText().equals(""))) {
                    if(m.getDescription() != null && m.getDescription().toLowerCase().contains(searchDescription.getText().toLowerCase())) {
                        logger.info("description = true");
                        description = true;
                    } else if (m.getDescription() == null || (m.getDescription() != null && !(m.getDescription().toLowerCase().contains(searchDescription.getText().toLowerCase())))) {
                        logger.info("description = false");
                        description = false;
                    }
                }

                if (m.getName().toLowerCase().contains(searchName.getText().toLowerCase()) &&
                        (""+ectsCount).toLowerCase().contains(searchEcts.getText().toLowerCase()) &&
                        description && contains && optional && completeAll) {
                    filteredModules.add(m);
                }
            }
            table.refreshModules(filteredModules);
            refreshMetaLVAs();
            pane.setViewportView(table);

            revalidate();
            repaint();
        }
    }

    public void changeStateOfContainedComboBox(String state) {
        if (state.equals("disabled")) {
            searchContainsListener.changeState(0);
        } else if (state.equals("selected")) {
            searchContainsListener.changeState(1);
        } else if (state.equals("not selected")) {
            searchContainsListener.changeState(2);
        }
    }

    private class SearchCheckBoxMouseListener implements MouseListener {
        JCheckBox cb;

        public SearchCheckBoxMouseListener (JCheckBox cb) {
            this.cb = cb;
            cb.setEnabled(false);
        }

        public void changeState(int i) {
            if (i == 0) {
                cb.setEnabled(false);
                cb.setSelected(false);
            } else if (i == 1) {
                cb.setEnabled(true);
                cb.setSelected(true);
            } else if (i== 2) {
                cb.setEnabled(true);
                cb.setSelected(false);
            }
            listener.keyReleased(null);
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {
            if(!cb.isEnabled()) {
                cb.setEnabled(true);
                cb.setSelected(false);
            } else if (cb.isEnabled() && cb.isSelected()) {
                cb.setEnabled(true);
                cb.setSelected(true);
            } else if (cb.isEnabled() && !cb.isSelected()) {
                cb.setEnabled(false);
                cb.setSelected(false);
            }
            listener.keyReleased(null);
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
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
            button2.setFont(standardButtonFont);
        }
    }

    public CurriculumTable getTable() {
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