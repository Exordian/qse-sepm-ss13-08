package at.ac.tuwien.sepm.ui.startUp;

import at.ac.tuwien.sepm.service.EscapeException;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.ui.SettingsPanel;
import at.ac.tuwien.sepm.ui.SmallInfoPanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.WideComboBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Georg Plaz
 */
public class Secound extends SimpleDisplayPanel {
    private static Logger logger = LogManager.getLogger(Secound.class);

    private JTextField facebookUsername = new JTextField();
    private JPasswordField facebookPassword = new JPasswordField();
    private JButton progressFurther= new JButton("Weiter");
    private JButton goBack= new JButton("Zurück");
    private JProgressBar progressBar = new JProgressBar();
    private WideComboBox semesterDrop = new WideComboBox(new Semester[]{Semester.W,Semester.S});
    private JTextField year = new JTextField();
    private ViewStartUp startUp;

    public Secound(double width, double height, ViewStartUp parent) {
        super(width, height,parent);
        this.startUp=parent;
        subInit();
    }
    public void subInit(){
        facebookUsername.setText(startUp.propertyService.getProperty(SettingsPanel.FACEBOOK_USER,""));
        facebookPassword.setText(startUp.propertyService.getProperty(SettingsPanel.FACEBOOK_PASSWORD,""));
        year.setText(startUp.propertyService.getProperty(SettingsPanel.FIRST_YEAR,""+ DateTime.now().getYear()));
        if(startUp.propertyService.getProperty(SettingsPanel.FIRST_SEMESTER,"WS").equals("WS")){
            semesterDrop.setSelectedIndex(0);
        }else{
            semesterDrop.setSelectedIndex(1);
        }

        progressFurther.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(year.getText().length()==0){
                        PanelTube.backgroundPanel.viewInfoText("Bitte gib ein Jahr an!", SmallInfoPanel.Warning);
                        throw new EscapeException();
                    }
                    try{
                        if(Integer.parseInt(year.getText())<=1900){
                            PanelTube.backgroundPanel.viewInfoText("Das angegebene Jahr ist ungültig", SmallInfoPanel.Warning);
                            throw new EscapeException();
                        }
                    }catch(NumberFormatException e1){
                        PanelTube.backgroundPanel.viewInfoText("Das angegebene Jahr ist ungültig", SmallInfoPanel.Warning);
                        throw new EscapeException();
                    }
                    int un = facebookUsername.getText().length();
                    int pw = facebookPassword.getPassword().length;
                    if(un>0 && pw>0){
                        startUp.propertyService.setProperty(SettingsPanel.FACEBOOK_USER, facebookUsername.getText());
                        startUp.propertyService.setProperty(SettingsPanel.FACEBOOK_PASSWORD,new String(facebookPassword.getPassword()));
                    }else if((un==0)!=(pw==0)){
                        PanelTube.backgroundPanel.viewInfoText("Die Facebook-Daten sind ungültig!", SmallInfoPanel.Warning);
                        throw new EscapeException();
                    }
                    startUp.next();
                    startUp.propertyService.setProperty(SettingsPanel.FIRST_SEMESTER,((Semester)semesterDrop.getSelectedItem()).toShortString());
                    startUp.propertyService.setProperty(SettingsPanel.FIRST_YEAR,year.getText());
                }catch(EscapeException e1){

                }
            }
        });
        goBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startUp.back();
            }
        });


        addText("Gratuliere!\n\nDu hast es durch das erste Panel geschafft!", true);

        addText("Wir würden gerne von dir wissen, in welchem Semester du angefangen hast zu studieren!", true);
        addText("Diese Angabe ist leider verpflichtend!\n", true);
        addRow(new JTextArea("Jahr"), year, true);
        addRow(new JTextArea("Semester"),semesterDrop,true);

        addRow(goBack,null,true,false);

        addText("\n\nUnser App bietet auch eine Facebookintegration an!", false);
        addText("Wenn du diesen Service nutzen willst, kannst du deine Facebookdaten hier eintragen. Die Angabe ist optional!\n", false);

        addRow(new JTextArea("Facebook-Username"), facebookUsername, false);
        addRow(new JTextArea("Facebook-Passwort"),facebookPassword,false);

        addRow(null,progressFurther,false,false);



    }
    private void setWaiting(boolean b){
        progressFurther.setEnabled(!b);
        progressBar.setVisible(b);
        facebookPassword.setEnabled(!b);
        facebookUsername.setEnabled(!b);
        //studyDrop.setEnabled(!b);
    }


}
