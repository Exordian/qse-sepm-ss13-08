package at.ac.tuwien.sepm.ui.semesterPlanning;

import at.ac.tuwien.sepm.dao.MetaLvaDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBLvaDao;
import at.ac.tuwien.sepm.dao.hsqldb.DBMetaLvaDao;
import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Author: Georg Plaz
 */
public class FirstPanel extends ChainedPanel   {


    @Autowired
    DBMetaLvaDao metaLVADAO;

    private JTextField desiredECTSText = new JTextField("30");
    private JCheckBox IntersectVOCheck = new JCheckBox();
    private JTextField yearText = new JTextField("2013");
    private JComboBox  semesterDrop = new JComboBox (new String[]{"Winter","Sommer"});
    private JTextField simulatedWaiting = new JTextField("2");

    private JButton next = new JButton("next");
    private JPanel settings = new JPanel();
    private JPanel settingsLabels = new JPanel();
    private JPanel settingsInputs = new JPanel();

    private JPanel advancedSettings;
    private JPanel advancedsettingsLabels = new JPanel();
    private JPanel advancedsettingsInputs = new JPanel();
    public FirstPanel(PlanningPanel mum){
        super(mum);

        final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                metaLVADAO = (DBMetaLvaDao) ctx.getBean("DBMetaLvaDao");
            }
        });

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
                List<MetaLVA> forced = metaLVADAO.readUncompletedByYearSemesterStudyProgress(2013, Semester.S, true);
                List<MetaLVA> pool = metaLVADAO.readUncompletedByYearSemesterStudyProgress(2013,Semester.S,false);
                //.add(pool.get(1));

                float ects = Float.parseFloat(desiredECTSText.getText());
                boolean voIntersect =  IntersectVOCheck.isSelected();
                int year = Integer.parseInt(yearText.getText());
                Semester sem = Semester.S;
                if(semesterDrop.getSelectedIndex()==0){
                    sem = Semester.W;
                }
                long waiting = (long)(Float.parseFloat(simulatedWaiting.getText())*1000);
                WaitingPanel w = new WaitingPanel(forced, pool, ects, voIntersect, year, sem, waiting);
                next(w);
                w.startThreads();
            }
        });
    }

}
