package at.ac.tuwien.sepm.ui.verlauf;

import at.ac.tuwien.sepm.entity.MetaLVA;
import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.ui.MetaLva.MetaLVADisplayPanel;
import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
@UI
public class PlanPanel extends StandardInsidePanel {
    private Rectangle outputPlane = new Rectangle(483,12,521,496);
    private MetaLVADisplayPanel pane;
    private ArrayList<MetaLVA> lvas;
    private JButton plan;
    private JButton take;

    // < basic settings >
    private JLabel desiredECTSTextLabel;
    private JTextField desiredECTSText;
    private JLabel yearTextLabel;
    private JTextField yearText;
    private JLabel semesterDropLabel;
    private JComboBox  semesterDrop;
    // </ basic settings >

    // < advanced settings >
    private JButton showAdvancedOptions;
    private JLabel intersectVOCheckLabel;
    private JCheckBox intersectVOCheck;
    private JLabel intersectUECheckLabel;
    private JCheckBox intersectUECheck;
    private JLabel intersectExamCheckLabel;
    private JCheckBox intersectExamCheck;
    private JLabel intersectCustomCheckLabel;
    private JCheckBox intersectCustomCheck;
    private JLabel timeBetweenLabel;
    private JComboBox timeBetween;
    private String[] timeBetweenTextLabelStrings;
    private JLabel timeBetweenTextLabel;
    private JTextField timeBetweenText;
    // </ advanced settings >

    private boolean advancedShown = true;

    public PlanPanel() {
        this.setLayout(null);
        this.setOpaque(false);
        loadFonts();
        setBounds((int)StudStartCoordinateOfWhiteSpace.getX(), (int)StudStartCoordinateOfWhiteSpace.getY(),(int)whiteSpaceStud.getWidth(),(int)whiteSpaceStud.getHeight());
        initTextAndLabels();
        toggleAdvanced();
        initButtons();
        initLVAPane();
    }

     /*-----------------------RIGHT SIDE  PLAN ANZEIGEN-------------------*/

    private void initLVAPane() {
        lvas = new ArrayList<MetaLVA>();
        pane = new MetaLVADisplayPanel(lvas, (int)outputPlane.getWidth(), (int)outputPlane.getHeight());
        pane.setBounds(outputPlane);
        this.add(pane);
    }


    /*-----------------------LEFT SIDE  PLANEN-------------------*/
    private void initButtons() {
        take = new JButton("Übernehmen");
        take.setFont(standardButtonFont);
        take.setBounds((int) outputPlane.getX() - 150, (int) outputPlane.getY() + (int) outputPlane.getHeight() - 40, 130, 40);
        take.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo plan übernehmen nach anzeigen
            }
        });
        this.add(take);

        plan = new JButton("Planen");
        plan.setFont(standardButtonFont);
        plan.setBounds((int) outputPlane.getX() - 130 -take.getWidth(), (int) outputPlane.getY() + (int) outputPlane.getHeight() - 40, 90, 40);
        plan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float ects = Float.parseFloat(desiredECTSText.getText());
                boolean vointersect = intersectVOCheck.isSelected();
                int year = Integer.parseInt(yearText.getText());
                Semester sem = Semester.S;
                if (semesterDrop.getSelectedIndex() == 0) {
                    sem = Semester.W;
                }
                //todo logic

            }
        });
        this.add(plan);
    }

    private void toggleAdvanced() {
        if (advancedShown == false) {
            intersectVOCheckLabel.setVisible(true);
            intersectVOCheck.setVisible(true);

            intersectUECheckLabel.setVisible(true);
            intersectUECheck.setVisible(true);

            intersectExamCheckLabel.setVisible(true);
            intersectExamCheck.setVisible(true);

            intersectCustomCheckLabel.setVisible(true);
            intersectCustomCheck.setVisible(true);

            timeBetweenLabel.setVisible(true);
            timeBetween.setVisible(true);

            timeBetweenTextLabel.setVisible(true);
            timeBetweenText.setVisible(true);
            advancedShown=true;
        } else {
            intersectVOCheckLabel.setVisible(false);
            intersectVOCheck.setVisible(false);

            intersectUECheckLabel.setVisible(false);
            intersectUECheck.setVisible(false);

            intersectExamCheckLabel.setVisible(false);
            intersectExamCheck.setVisible(false);

            intersectCustomCheckLabel.setVisible(false);
            intersectCustomCheck.setVisible(false);

            timeBetweenLabel.setVisible(false);
            timeBetween.setVisible(false);

            timeBetweenTextLabel.setVisible(false);
            timeBetweenText.setVisible(false);
            advancedShown=false;
        }
    }

    private void initTextAndLabels() {
        int verticalSpace = 10;
        int textHeight = 25;
        int textWidth = 150;

        // < basic settings >
        desiredECTSTextLabel = new JLabel("Gewünschte ECTS:");
        desiredECTSTextLabel.setFont(standardTextFont);
        desiredECTSTextLabel.setBounds(10, 10, textWidth, textHeight);
        this.add(desiredECTSTextLabel);

        desiredECTSText = new JTextField("30");
        desiredECTSText.setBounds(desiredECTSTextLabel.getX()+desiredECTSTextLabel.getWidth()+5,desiredECTSTextLabel.getY(),25,textHeight);
        desiredECTSText.setFont(standardTextFont);
        this.add(desiredECTSText);

        yearTextLabel = new JLabel("Jahr:");
        yearTextLabel.setBounds(desiredECTSTextLabel.getX(),desiredECTSTextLabel.getY()+desiredECTSTextLabel.getHeight()+verticalSpace, textWidth, textHeight);
        yearTextLabel.setFont(standardTextFont);
        this.add(yearTextLabel);

        yearText = new JTextField("2013");
        yearText.setBounds(yearTextLabel.getX() + yearTextLabel.getWidth() +5, yearTextLabel.getY(), 50, textHeight);
        yearText.setFont(standardTextFont);
        this.add(yearText);

        semesterDropLabel = new JLabel("Semester:");
        semesterDropLabel.setFont(standardTextFont);
        semesterDropLabel.setBounds(yearTextLabel.getX(),yearTextLabel.getY()+yearTextLabel.getHeight()+verticalSpace, textWidth, textHeight);
        this.add(semesterDropLabel);

        semesterDrop = new JComboBox (new String[]{"Winter","Sommer"});
        semesterDrop.setFont(standardButtonFont);
        semesterDrop.setBounds(semesterDropLabel.getX()+semesterDropLabel.getWidth()+5, semesterDropLabel.getY(), 100, textHeight);
        this.add(semesterDrop);
        // </ basic settings >

        // < advanced settings >
        showAdvancedOptions = new JButton("Erweiterte Optionen");
        showAdvancedOptions.setFont(standardButtonFont);
        showAdvancedOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleAdvanced();
                PlanPanel.this.repaint();
            }
        });
        showAdvancedOptions.setBounds(semesterDropLabel.getX()+50, semesterDropLabel.getY()+semesterDropLabel.getHeight()+verticalSpace, textWidth+20, textHeight);
        this.add(showAdvancedOptions);

        textWidth=410;

        intersectVOCheckLabel = new JLabel("Überprüfe Vorlesungstermine auf Überschneidungen:");
        intersectVOCheckLabel.setFont(standardTextFont);
        intersectVOCheckLabel.setBounds(semesterDropLabel.getX(), showAdvancedOptions.getY()+showAdvancedOptions.getHeight()+verticalSpace, textWidth,textHeight);
        this.add(intersectVOCheckLabel);

        intersectVOCheck = new JCheckBox();
        intersectVOCheck.setBackground(new Color(0,0,0,0));
        intersectVOCheck.setBounds(intersectVOCheckLabel.getX()+intersectVOCheckLabel.getWidth()+5, intersectVOCheckLabel.getY()+5, 20, 20);
        this.add(intersectVOCheck);

        intersectUECheckLabel = new JLabel("Überprüfe Übungstermine auf Überschneidungen:");
        intersectUECheckLabel.setFont(standardTextFont);
        intersectUECheckLabel.setBounds(intersectVOCheckLabel.getX(), intersectVOCheckLabel.getY()+intersectVOCheckLabel.getHeight()+verticalSpace, textWidth,textHeight);
        this.add(intersectUECheckLabel);

        intersectUECheck = new JCheckBox();
        intersectUECheck.setBackground(new Color(0,0,0,0));
        intersectUECheck.setBounds(intersectUECheckLabel.getX()+intersectUECheckLabel.getWidth()+5, intersectUECheckLabel.getY()+5, 20, 20);
        this.add(intersectUECheck);

        intersectExamCheckLabel = new JLabel("Überprüfe Prüfungstermine auf Überschneidungen:");
        intersectExamCheckLabel.setFont(standardTextFont);
        intersectExamCheckLabel.setBounds(intersectUECheckLabel.getX(), intersectUECheckLabel.getY()+intersectUECheckLabel.getHeight()+verticalSpace, textWidth,textHeight);
        this.add(intersectExamCheckLabel);

        intersectExamCheck = new JCheckBox();
        intersectExamCheck.setBackground(new Color(0,0,0,0));
        intersectExamCheck.setBounds(intersectExamCheckLabel.getX()+intersectExamCheckLabel.getWidth()+5, intersectExamCheckLabel.getY()+5, 20, 20);
        this.add(intersectExamCheck);

        intersectCustomCheckLabel = new JLabel("Überprüfe private Termine auf Überschneidungen:");
        intersectCustomCheckLabel.setFont(standardTextFont);
        intersectCustomCheckLabel.setBounds(intersectExamCheckLabel.getX(), intersectExamCheckLabel.getY()+intersectExamCheckLabel.getHeight()+verticalSpace, textWidth,textHeight);
        this.add(intersectCustomCheckLabel);

        intersectCustomCheck = new JCheckBox();
        intersectCustomCheck.setBackground(new Color(0,0,0,0));
        intersectCustomCheck.setBounds(intersectCustomCheckLabel.getX()+intersectCustomCheckLabel.getWidth()+5, intersectCustomCheckLabel.getY()+5, 20, 20);
        this.add(intersectCustomCheck);

        timeBetweenLabel = new JLabel("Zeit zwischen Terminen:");
        timeBetweenLabel.setFont(standardTextFont);
        timeBetweenLabel.setBounds(intersectCustomCheckLabel.getX(), intersectCustomCheckLabel.getY()+intersectCustomCheckLabel.getHeight()+verticalSpace, textWidth,textHeight);
        this.add(timeBetweenLabel);

        timeBetween= new JComboBox(new String[]{"exakte Zeiten verwenden","Termine dürfen sich überschneiden","Zwischen Terminen Zeit erzwingen"});
        timeBetween.setFont(standardButtonFont);
        timeBetween.setBounds(timeBetweenLabel.getX()+timeBetweenLabel.getWidth()-197, timeBetweenLabel.getY(), 220, textHeight);
        timeBetween.setSelectedIndex(0);
        this.add(timeBetween);

        timeBetweenTextLabelStrings= new String[]{"","Zeit um die sich Termine schneiden dürfen:","Zeit die zwischen zwei Terminen liegen muss:"};

        timeBetweenTextLabel = new JLabel(timeBetweenTextLabelStrings[0]);
        timeBetweenTextLabel.setFont(standardTextFont);
        timeBetweenTextLabel.setBounds(timeBetweenLabel.getX(), timeBetweenLabel.getY()+timeBetweenLabel.getHeight()+verticalSpace, textWidth,textHeight);
        timeBetweenTextLabel.setVisible(false);
        this.add(timeBetweenTextLabel);

        //todo fix anzeige bug von timeBetweenText
        timeBetweenText = new JTextField("0");
        timeBetweenText.setBounds(timeBetweenTextLabel.getX()+timeBetweenTextLabel.getWidth(), timeBetweenTextLabel.getY(), 21, textHeight);
        timeBetweenText.setVisible(false);
        this.add(timeBetweenText);

        timeBetween.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(timeBetween.getSelectedIndex()){
                    case 0:
                        timeBetweenText.setVisible(false);
                        break;
                    case 1:
                        timeBetweenText.setVisible(true);
                        break;
                    case 2:
                        timeBetweenText.setVisible(true);
                        break;
                }
                timeBetweenTextLabel.setText(timeBetweenTextLabelStrings[timeBetween.getSelectedIndex()]);
            }
        });
        // </ advanced settings >
        this.repaint();
    }
}
