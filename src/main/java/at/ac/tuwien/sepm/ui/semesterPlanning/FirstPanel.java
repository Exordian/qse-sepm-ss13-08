package at.ac.tuwien.sepm.ui.semesterPlanning;

import at.ac.tuwien.sepm.dao.hsqldb.DBLvaDao;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Author: Georg Plaz
 */
public class FirstPanel extends ChainedPanel   {


    @Autowired
    private DBLvaDao lvaDAO;

    JTextField desiredECTSText = new JTextField("30");
    JCheckBox IntersectVOCheck = new JCheckBox();
    JTextField yearText = new JTextField("2013");
    JComboBox  semesterDrop = new JComboBox (new String[]{"Winter","Sommer"});
    JTextField simulatedWaiting = new JTextField("4");

    JButton next = new JButton("next");
    JPanel settings = new JPanel();
    JPanel settingsLabels = new JPanel();
    JPanel settingsInputs = new JPanel();

    JPanel advancedSettings;
    JPanel advancedsettingsLabels = new JPanel();
    JPanel advancedsettingsInputs = new JPanel();
    public FirstPanel(PlanningPanel mum){
        super(mum);

        settings.setBackground(new Color(0,0,0,0));
        add(settings);
        settings.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.EAST;
        c.gridx=0;
        c.ipadx=10;

        c.gridy=0;
        settings.add(new JLabel("gewünschte ECTS:"),c);
        c.gridy=1;
        settings.add(new JLabel("Vorlesungstermine überschneiden:"),c);
        c.gridy=2;
        settings.add(new JLabel("Jahr:"),c);
        c.gridy=3;
        settings.add(new JLabel("Semester:"),c);
        c.gridy=4;
        settings.add(new JLabel("//Simulierte Wartezeit:"),c);

        c.gridx=1;
        c.anchor = GridBagConstraints.WEST;

        c.gridy=0;
        settings.add(desiredECTSText,c);
        c.gridy=1;
        settings.add(IntersectVOCheck,c);
        IntersectVOCheck.setEnabled(false);
        c.gridy=2;
        settings.add(yearText,c);
        c.gridy=3;
        settings.add(semesterDrop,c);
        c.gridy=4;
        settings.add(simulatedWaiting,c);
        semesterDrop.setSelectedIndex(1);
/*
        //settingsLabels.setLayout(new BoxLayout(settingsLabels, BoxLayout.Y_AXIS));
        //settingsInputs.setLayout(new BoxLayout(settingsInputs, BoxLayout.Y_AXIS));
       /* settingsLabels.setLayout(new GridLayout(1,99));
        settingsInputs.setLayout(new GridLayout(1,99));

        //.add(settingsLabels);
            settingsLabels.add(new JLabel("gewünschte ECTS:"));
            settingsLabels.add(new JLabel("Vorlesungstermine überschneiden:"));
            settingsLabels.add(new JLabel("Jahr:"));
            settingsLabels.add(new JLabel("Semester:"));
        //settings.add(settingsInputs);
            settingsInputs.add(desiredECTSText);
            settingsInputs.add(IntersectVOCheck);
            settingsInputs.add(yearText);
            settingsInputs.add(semesterDrop);
            semesterDrop.setSelectedIndex(1);

             */
        desiredECTSText.setPreferredSize(new Dimension(30,20));
        add(next);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //DirtyTestHelper d= new DirtyTestHelper();
                //d.setUp();
                ArrayList<MetaLVA> forced = new ArrayList<MetaLVA>(0);//d.getForced();
                ArrayList<MetaLVA> pool = new ArrayList<MetaLVA>(0);//d.getPool();
                float ects = Float.parseFloat(desiredECTSText.getText());
                boolean voIntersect =  IntersectVOCheck.isSelected();
                int year = Integer.parseInt(yearText.getText());
                Semester sem = Semester.S;
                if(semesterDrop.getSelectedIndex()==0){
                    sem = Semester.W;
                }
                long waiting = (long)(Float.parseFloat(simulatedWaiting.getText())*1000);
                next(new WaitingPanel(forced, pool, ects, voIntersect, year, sem, waiting));
            }
        });
    }

}
