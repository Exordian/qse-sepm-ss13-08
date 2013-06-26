package at.ac.tuwien.sepm.ui.startUp;

import at.ac.tuwien.sepm.service.EscapeException;
import at.ac.tuwien.sepm.service.Semester;
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
public class Secound extends StartRowPanel {
    private static Logger logger = LogManager.getLogger(Secound.class);

    private JTextField facebookUsername = new JTextField();
    private JPasswordField facebookPassword = new JPasswordField();
    private JButton progressFurther;
    private JProgressBar progressBar = new JProgressBar();
    private WideComboBox semesterDrop = new WideComboBox(new Semester[]{Semester.W,Semester.S});
    private JTextField year = new JTextField();


    //academicPrograms.setMinimumSize(new Dimension((int)this.getBounds().getWidth()-145, 20));



    public Secound(double width, double height, ViewStartUp parent) {
        super(width, height,parent);
        subInit();
    }
    public void subInit(){
        facebookUsername.setText(getStartUp().propertyService.getProperty("facebook.user",""));
        facebookPassword.setText(getStartUp().propertyService.getProperty("facebook.password",""));
        year.setText(getStartUp().propertyService.getProperty("user.firstYear",""+ DateTime.now().getYear()));
        if(getStartUp().propertyService.getProperty("user.firstSemester","WS").equals("WS")){
            semesterDrop.setSelectedIndex(0);
        }else{
            semesterDrop.setSelectedIndex(1);
        }

        progressFurther = new JButton("Weiter");
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
                        getStartUp().propertyService.setProperty("facebook.user", facebookUsername.getText());
                        getStartUp().propertyService.setProperty("facebook.password",new String(facebookPassword.getPassword()));
                    }else if((un==0)!=(pw==0)){
                        PanelTube.backgroundPanel.viewInfoText("Die Facebook-Daten sind ungültig!", SmallInfoPanel.Warning);
                        throw new EscapeException();
                    }
                    getStartUp().next();
                    getStartUp().propertyService.setProperty("user.firstSemester",((Semester)semesterDrop.getSelectedItem()).toShortString());
                    getStartUp().propertyService.setProperty("user.firstYear",year.getText());
                }catch(EscapeException e1){

                }
            }
        });



        addText("Gratuliere!\n\nDu hast es durch das erste Panel geschafft!", true);

        addText("Wir würden gerne von dir wissen, in welchem Semester du angefangen hast zu studieren!", true);
        addText("Diese Angabe ist leider verpflichtend!\n", true);
        addRow(new JTextArea("Jahr"), year, true);
        addRow(new JTextArea("Semester"),semesterDrop,true);

        addText("\n\nUnser App bietet auch eine Facebookintegration an!", false);
        addText("Wenn du diesen Service nutzen willst, kannst du deine Facebookdaten hier eintragen. Die Angabe ist optional!\n", false);

        addRow(new JTextArea("Facebook-Username"), facebookUsername, false);
        addRow(new JTextArea("Facebook-Passwort"),facebookPassword,false);

        addRow(null,progressFurther,false);



    }
    private void setWaiting(boolean b){
        progressFurther.setEnabled(!b);
        progressBar.setVisible(b);
        facebookPassword.setEnabled(!b);
        facebookUsername.setEnabled(!b);
        //studyDrop.setEnabled(!b);
    }


}
