package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.MetaLVA;
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
    private JTextField searchNr = new JTextField();
    private JTextField searchType = new JTextField();
    private JTextField searchName = new JTextField();
    private JTextField searchECTS = new JTextField();

    private JPanel searchPanel = new JPanel();

    private JScrollPane pane = new JScrollPane();

    int tWidth;
    public MetaLVADisplayPanel(List<MetaLVA> lvas,int width,int height){
        this.tWidth =width;
        this.allLVAs = lvas;
        filteredLVAs = lvas;
        table = new MetaLVATable(lvas,width);
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
        KeyListener listener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
                logger.debug("searching for: (nr: "+searchNr.getText()+", type:"+searchType.getText()+", name: " + searchName.getText()+", ECTS: "+searchECTS.getText()+")");
                filteredLVAs = new ArrayList<MetaLVA>();
                for (MetaLVA m : allLVAs) {
                    if (m.getNr().contains(searchNr.getText()) && m.getType().toString().contains(searchType.getText()) && m.getName().contains(searchName.getText()) &&(""+m.getECTS()).contains(searchECTS.getText())) {
                        filteredLVAs.add(m);
                    }
                }
                //table = new MetaLVATable(filteredLVAs, tWidth);
                table.refreshMetaLVAs(filteredLVAs);
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
    public MetaLVA getSelectedMetaLVA(){
        return table.getSelectedMetaLVA();
    }

    public void removeSelectedMetaLVA() {
        table.removeSelectedMetaLVA();
    }

}

