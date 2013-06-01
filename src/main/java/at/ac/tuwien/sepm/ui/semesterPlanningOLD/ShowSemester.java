package at.ac.tuwien.sepm.ui.semesterPlanningOLD;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.ui.MetaLva.MetaLVADisplayPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: Georg Plaz
 */
public class ShowSemester extends ChainedPanel{
    private MetaLVATable table;
    private JButton backButton = new JButton("zurueck");
    public ShowSemester(ArrayList<MetaLVA> lvas){
        JScrollPane p = new JScrollPane();
        table = new MetaLVATable(lvas);
        add(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backAndKill();
            }
        });
        //JScrollPane pane = new JScrollPane(table);
        //pane.setPreferredSize(new Dimension(550,200));
        add(new MetaLVADisplayPanel(lvas,550,200));
        //revalidate();
        //repaint();

    }
}
