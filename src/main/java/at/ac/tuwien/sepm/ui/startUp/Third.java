package at.ac.tuwien.sepm.ui.startUp;

import at.ac.tuwien.sepm.ui.template.WideComboBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Georg Plaz
 */
public class Third extends StartRowPanel {
    private static Logger logger = LogManager.getLogger(Third.class);

    private JTextField tissUsername = new JTextField();
    private JPasswordField tissPassword = new JPasswordField();
    private WideComboBox studyDrop;
    private JButton progressFurther;
    private JProgressBar progressBar = new JProgressBar();


    //academicPrograms.setMinimumSize(new Dimension((int)this.getBounds().getWidth()-145, 20));



    public Third(double width, double height, ViewStartUp parent) {
        super(width, height,parent);
        subInit();
    }
    public void subInit(){
        progressFurther = new JButton("Abschließen");
        progressFurther.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                getStartUp().next();
            }
        });
        addText("Das wars..", true);
        addText("Und jetzt?", true);

        addText("",false);
        addRow(null,progressFurther,false);



    }
    private void setWaiting(boolean b){
        progressFurther.setEnabled(!b);
        progressBar.setVisible(b);
        tissPassword.setEnabled(!b);
        tissUsername.setEnabled(!b);
        studyDrop.setEnabled(!b);
    }


}
