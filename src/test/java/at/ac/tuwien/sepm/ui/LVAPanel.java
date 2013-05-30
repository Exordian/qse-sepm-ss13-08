package at.ac.tuwien.sepm.ui;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import at.ac.tuwien.sepm.entity.MetaLVA;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author Lena Lenz
 */
public class LVAPanel extends JPanel {
    private static final Logger logger = Logger.getLogger(LVAPanel.class);

    private static final int MAX_ROWS = 500;
    private static final int COLUMNS = 3;

    private JTable displayLVAs;
    private JButton add_lva;
    private JButton edit_lva;
    private JButton delete_lva;
    private ArrayList<MetaLVA> metaLVAs;



    public LVAPanel() {
        initJTables();
        configureColumns();
        addButtons();
        addActionListeners();
    }

    public void initJTables() {
        String[][] data = new String[MAX_ROWS][COLUMNS];
        displayLVAs = new JTable(data, new String[] {"Nummer", "Typ", "Titel", "ECTS"});
        displayLVAs.setRowHeight(20);
        JTableHeader header = displayLVAs.getTableHeader();
        header.setBackground(new Color(73, 133, 255));
        header.setFont(new Font("Arial",Font.HANGING_BASELINE+Font.BOLD,15));
        this.add(displayLVAs);
    }

    public void configureColumns() {
        JScrollPane scrollpane = new JScrollPane(displayLVAs);
        displayLVAs.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcm = displayLVAs.getColumnModel();
        TableColumn column0 = tcm.getColumn(0);
        TableColumn column1 = tcm.getColumn(1);
        TableColumn column2 = tcm.getColumn(2);
        TableColumn column3 = tcm.getColumn(3);
        column0.setPreferredWidth(30);
        column1.setPreferredWidth(20);
        column2.setPreferredWidth(50);
        column3.setPreferredWidth(20);
    }

    public void addButtons() {
        add_lva = new JButton("LVA Hinzufügen");
        edit_lva = new JButton("LVA Bearbeiten");
        delete_lva = new JButton("LVA Löschen");

        this.add(add_lva);
        this.add(edit_lva);
        this.add(delete_lva);
    }

    public void addActionListeners() {
        add_lva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LVAPanel.this.addLvaPressed();
            }
        });

        edit_lva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LVAPanel.this.editLvaPressed();
            }
        });

        delete_lva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LVAPanel.this.deleteLvaPressed();
            }
        });
    }

    private void deleteLvaPressed() {
    }

    private void editLvaPressed() {
    }

    private void addLvaPressed() {
    }
}
