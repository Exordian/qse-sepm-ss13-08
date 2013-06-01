package at.ac.tuwien.sepm.ui.semesterPlanningOLD;

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

    private JPanel settings = new JPanel();
    // < basic settings >
    private JPanel basicSettings = new JPanel(new GridBagLayout());

    private JLabel desiredECTSTextLabel = new JLabel("Gewünschte ECTS:");
    private JTextField desiredECTSText = new JTextField("30");

    private JLabel yearTextLabel = new JLabel("Jahr:");
    private JTextField yearText = new JTextField("2013");

    private JLabel semesterDropLabel = new JLabel("Semester:");
    private JComboBox  semesterDrop = new JComboBox (new String[]{"Winter","Sommer"});

    // </ basic settings >

    // < advanced settings >

    private JButton showAdvancedOptions = new JButton("Erweiterte Optionen");

    private JPanel advancedSettings= new JPanel(new GridBagLayout());

    private JLabel intersectVOCheckLabel = new JLabel("Überprüfe Vorlesungstermine auf Überschneidungen:");
    private JCheckBox intersectVOCheck = new JCheckBox();

    private JLabel intersectUECheckLabel = new JLabel("Überprüfe Übungstermine auf Überschneidungen:");
    private JCheckBox intersectUECheck = new JCheckBox();

    private JLabel intersectExamCheckLabel = new JLabel("Überprüfe Prüfungstermine auf Überschneidungen:");
    private JCheckBox intersectExamCheck = new JCheckBox();

    private JLabel intersectCustomCheckLabel = new JLabel("Überprüfe selbst erstellte Termine auf Überschneidungen:");
    private JCheckBox intersectCustomCheck = new JCheckBox();

    private JLabel timeBetweenLabel = new JLabel("Zeit zwischen Terminen:");
    private JComboBox timeBetween= new JComboBox(new String[]{"exakte Zeiten verwenden","Termine dürfen sich überschneiden","Zwischen Terminen Zeit erzwingen"});

    private String[] timeBetweenTextLabelStrings= new String[]{"","Zeit um die sich Termine schneiden dürfen:","Zeit die zwischen zwei Terminen liegen muss:"};
    private JLabel timeBetweenTextLabel = new JLabel(timeBetweenTextLabelStrings[0]);
    private JTextField timeBetweenText = new JTextField("0");

    private JLabel simulatedWaitingLabel = new JLabel("//simulierte Wartezeit");
    private JTextField simulatedWaiting = new JTextField("2");

    // </ advanced settings >

    private JButton next = new JButton("next");

    public FirstPanel(PlanningPanel mum){
        super(mum);
        this.setBackground(new Color(0,0,0,0));

        final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                metaLVADAO = (DBMetaLvaDao) ctx.getBean("DBMetaLvaDao");
            }
        });

        add(settings);
        settings.setBackground(new Color(0,0,0,0));
        settings.setLayout(null);
        settings.add(basicSettings);
        settings.add(advancedSettings);
        advancedSettings.setVisible(false);



        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        
        c.ipadx=10;
        // < basic settings >
        c.anchor = GridBagConstraints.EAST;
        
        //labels
        c.gridx=0;
        
        c.gridy=0;
        basicSettings.add(desiredECTSTextLabel);
        c.gridy++;
        basicSettings.add(yearTextLabel, c);
        c.gridy++;
        basicSettings.add(semesterDropLabel, c);
        
        //inputs
        c.gridx++;
        c.anchor = GridBagConstraints.WEST;

        c.gridy=0;
        basicSettings.add(desiredECTSText,c);
        c.gridy++;
        basicSettings.add(yearText, c);
        c.gridy++;
        basicSettings.add(semesterDrop, c);
        c.gridy++;
        basicSettings.add(showAdvancedOptions, c);
        desiredECTSText.setPreferredSize(new Dimension(30,20));
        showAdvancedOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(advancedSettings.isVisible()){
                    advancedSettings.setVisible(false);
                }else{
                    advancedSettings.setVisible(true);
                }
                getParent().getParent().repaint();
            }
        });

        // </ basic settings >

        // < advanced settings >
        
        //labels
        c.anchor = GridBagConstraints.EAST;
        c.gridx=0;

        c.gridy=0;
        advancedSettings.add(intersectVOCheckLabel, c);
        c.gridy++;
        advancedSettings.add(intersectUECheckLabel, c);
        c.gridy++;
        advancedSettings.add(intersectExamCheckLabel, c);
        c.gridy++;
        advancedSettings.add(intersectCustomCheckLabel, c);
        c.gridy++;
        advancedSettings.add(timeBetweenLabel, c);
        c.gridy++;
        advancedSettings.add(timeBetweenTextLabel, c);

        //inputs
        c.anchor = GridBagConstraints.WEST;
        c.gridx++;

        c.gridy=0;
        advancedSettings.add(intersectVOCheck, c);
        c.gridy++;
        advancedSettings.add(intersectUECheck, c);
        c.gridy++;
        advancedSettings.add(intersectExamCheck, c);
        c.gridy++;
        advancedSettings.add(intersectCustomCheck, c);
        c.gridy++;
        advancedSettings.add(timeBetween, c);
        c.gridy++;
        advancedSettings.add(timeBetweenText, c);
        timeBetween.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = timeBetween.getSelectedIndex();
                switch(index){
                    case 0:
                        timeBetweenText.setEnabled(false);
                        break;
                    case 1:
                    case 2:
                        timeBetweenText.setEnabled(true);
                        break;
                }
                timeBetweenTextLabel.setText(timeBetweenTextLabelStrings[index]);
            }
        });
        timeBetween.setSelectedIndex(0);

        // </ advanced settings >
        

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
                boolean vointersect =  intersectVOCheck.isSelected();
                int year = Integer.parseInt(yearText.getText());
                Semester sem = Semester.S;
                if(semesterDrop.getSelectedIndex()==0){
                    sem = Semester.W;
                }
                long waiting = (long)(Float.parseFloat(simulatedWaiting.getText())*1000);
                WaitingPanel w = new WaitingPanel(forced, pool, ects, vointersect, year, sem, waiting);
                next(w);
                w.startThreads();
            }
        });
    }
}
