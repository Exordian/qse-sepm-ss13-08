package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.entity.Curriculum;
import at.ac.tuwien.sepm.service.*;
import at.ac.tuwien.sepm.ui.startUp.SimpleDisplayPanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import at.ac.tuwien.sepm.ui.template.SelectItem;
import at.ac.tuwien.sepm.ui.template.WideComboBox;
import com.toedter.calendar.JYearChooser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Author: Georg Plaz
 */
@UI
public class SettingsPanel extends StandardSimpleInsidePanel {


    private JButton showWizard;
    private JButton tiss;
    private JButton facebook;
    // JTextField ects;
    private JComboBox major;
    private JButton deleteALL;

    private JYearChooser year;
    private WideComboBox semester;

    private JButton save;

    private PropertyService propertyService;
    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());
    private CreateCurriculumService createCurriculumService;

    private MySimplePanel content;
    @Autowired
    private AuthService authService;

    @Autowired
    public SettingsPanel(PropertyService propertyService, CreateCurriculumService createCurriculumService) {
        this.createCurriculumService=createCurriculumService;
        this.propertyService=propertyService;
        init();
        addImage();
        addTitle("Einstellungen");
        addReturnButton();
        addContent();
        addSaveButton();
        repaint();
        revalidate();
    }

    private void addSaveButton() {
        save = new JButton("Speichern");
        save.setFont(standardButtonFont);
        save.setBounds(deleteALL.getX() + deleteALL.getWidth()+100, deleteALL.getHeight() + deleteALL.getY() + 50, 110, 30);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                propertyService.setProperty(PropertyService.FIRST_YEAR, Integer.toString(year.getYear()));
                propertyService.setProperty(PropertyService.FIRST_SEMESTER, semester.getSelectedItem().toString());
                if (!major.getSelectedItem().toString().equals("Bitte Importieren sie ein Studium!")) {
                    propertyService.setProperty(PropertyService.MAJOR, major.getSelectedItem().toString());
                } else {
                    PanelTube.backgroundPanel.viewInfoText("Der Studiumsname konnte nicht gespeichert werden.", SmallInfoPanel.Info);
                }
                //propertyService.setProperty(PropertyService."user.defaultECTS", ects.getText());
                setVisible(false);
                PanelTube.backgroundPanel.viewInfoText("Die Daten wurden gespeichert.", SmallInfoPanel.Success);
                PanelTube.backgroundPanel.showLastComponent();
            }
        });
        //this.add(save);
    }

    private void addContent() {
        //int textHeight = 25;

        showWizard = new JButton("anzeigen");
        showWizard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelTube.backgroundPanel.viewStartup();
            }
        });


        semester = new WideComboBox(new Semester[]{Semester.W,Semester.S});
        semester.setSelectedItem(Semester.parse(propertyService.getProperty(PropertyService.FIRST_SEMESTER,Semester.W.toString())));


        year = new JYearChooser();
        year.setYear(Integer.parseInt(propertyService.getProperty(PropertyService.FIRST_YEAR,""+DateTime.now().getYear())));
        year.setStartYear(1900);
        year.setEndYear(DateTime.now().getYear());

        tiss = new JButton("eingeben");

        tiss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame newframe = new JFrame("Login Daten");
                newframe.setIconImage(new ImageIcon("src/main/resources/img/icon.jpg").getImage());
                final LoginDataFrame temp = new LoginDataFrame();
                newframe.add(temp);
                newframe.setLocationRelativeTo(null);
                newframe.pack();
                newframe.setVisible(true);
                newframe.setResizable(false);

                newframe.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        try {
                            authService.authenticate(temp.getName(), temp.getPassword());
                            propertyService.setProperty(PropertyService.TISS_USER, temp.getName());
                            propertyService.setProperty(PropertyService.TISS_PASSWORD, temp.getPassword());
                        } catch (ServiceException ex) {
                            PanelTube.backgroundPanel.viewInfoText("Login Daten ungültig",SmallInfoPanel.Error);
                        }
                        SettingsPanel.this.revalidate();
                        SettingsPanel.this.setVisible(true);
                        content.refresh();
                    }
                });
                content.refresh();
            }
        });
        //this.add(tiss);

        facebook = new JButton("eingeben");
        facebook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame newframe = new JFrame("Login Daten");
                newframe.setIconImage(new ImageIcon("src/main/resources/img/icon.jpg").getImage());
                final LoginDataFrame temp = new LoginDataFrame();
                newframe.add(temp);
                newframe.setLocationRelativeTo(null);
                newframe.pack();
                newframe.setVisible(true);
                newframe.setResizable(false);

                newframe.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        //nameLabelFacebook.setText("Eingeloggt als: " + temp.getName());
                        propertyService.setProperty(PropertyService.FACEBOOK_USER, temp.getName());
                        propertyService.setProperty(PropertyService.FACEBOOK_PASSWORD, temp.getPassword());
                        SettingsPanel.this.revalidate();
                        SettingsPanel.this.setVisible(true);
                        content.refresh();
                    }
                });
                content.refresh();
            }
        });

        
        major = new WideComboBox();
        try {
            if (!createCurriculumService.readAllCurriculum().isEmpty())
                for (Curriculum c : createCurriculumService.readAllCurriculum())
                    major.addItem(new CurriculumSelectItem(c));
            else
                major.addItem("Bitte Importieren sie ein Studium!");
        } catch (ServiceException e) {
            log.error("Error: " + e.getMessage());
            PanelTube.backgroundPanel.viewInfoText("Fehler beim Laden der Studien",SmallInfoPanel.Error);
        }

        if (propertyService.getProperty(PropertyService.MAJOR) != null && !propertyService.getProperty(PropertyService.MAJOR).isEmpty()) {
            semester.setSelectedItem(propertyService.getProperty(PropertyService.MAJOR));
        } else {
            semester.setSelectedIndex(0);
        }


        deleteALL = new JButton("ALLE Daten zurücksetzen");

        deleteALL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object[] options = {"Ja", "Abbrechen"};
                if (JOptionPane.showOptionDialog(SettingsPanel.this, "Wollen Sie wirklich alle gespeicherten Daten des Programms löschen?", "Bestätigung",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]) == JOptionPane.YES_OPTION) {
                    propertyService.removeProperty(PropertyService.FIRST_YEAR);
                    propertyService.removeProperty(PropertyService.FIRST_SEMESTER);
                    propertyService.removeProperty(PropertyService.MAJOR);
                    propertyService.removeProperty(PropertyService.FACEBOOK_USER);
                    propertyService.removeProperty(PropertyService.FACEBOOK_PASSWORD);
                    propertyService.removeProperty(PropertyService.TISS_USER);
                    propertyService.removeProperty(PropertyService.TISS_PASSWORD);
                    //propertyService.removeProperty(PropertyService."user.defaultECTS");
                    propertyService.removeProperty(PropertyService.FIRST_RUN);
                    //todo alte datenbank daten löschen
                    PanelTube.backgroundPanel.viewInfoText("Alle Daten wurden gelöscht",SmallInfoPanel.Success);
                    PanelTube.backgroundPanel.setControlsEnabled(false);
                    PanelTube.backgroundPanel.viewStartup();
                    //System.exit(0);
                }
            }
        });
        //this.add(deleteALL);
        content = new MySimplePanel(simpleWhiteSpace.getWidth(),simpleWhiteSpace.getHeight(),this);
        this.add(content);
        content.setBounds(simpleWhiteSpace);
    }
    @Override
    public void refresh(){
        semester.setSelectedItem(Semester.parse(propertyService.getProperty(PropertyService.FIRST_SEMESTER,Semester.W.toString())));
        year.setYear(Integer.parseInt(propertyService.getProperty(PropertyService.FIRST_YEAR, ""+DateTime.now().getYear())));
        content.refresh();

    }

    private static class CurriculumSelectItem extends SelectItem<Curriculum> {
        CurriculumSelectItem(Curriculum item) {
            super(item);
        }

        @Override
        public String toString() {
            return item.getName();
        }
    }
    private class MySimplePanel extends SimpleDisplayPanel{
        int buttonWidthInit =buttonWidth;
        public MySimplePanel(double width, double height, StandardInsidePanel standardInsidePanel) {
            super(width, height, standardInsidePanel);
            inputWidthLeft-= 40;
            labelWidthLeft-=50;
            inputXLeft-=30;
            labelXLeft+=bigSpace;

            refresh();

        }
        public void refresh(){
            buttonWidth= buttonWidthInit;
            lastLeftBottomHeight=getHeight();
            lastRightBottomHeight=getHeight();
            lastLeftTopHeight=bigSpace;
            lastRightTopHeight=bigSpace;
            removeAll();
            init();
        }
        private void init(){
            addEmptyArea(bigSpace*2,true);
            addText("Studienbeginn",true);
            addRow(new JTextArea("Jahr"),year,true);
            addRow(new JTextArea("Semester"),semester,true);
            addEmptyArea(bigSpace*3,true);
            addRow(new JTextArea("Startup-Wizard"),showWizard,true);

            addEmptyArea(bigSpace*2,false);
            addRow(new JTextArea("TISS-login"),tiss,false);
            if (propertyService.getProperty(PropertyService.TISS_USER) != null && !propertyService.getProperty(PropertyService.TISS_USER).isEmpty() &&
                    propertyService.getProperty(PropertyService.TISS_PASSWORD) != null && !propertyService.getProperty(PropertyService.TISS_PASSWORD).isEmpty()) {
                addText("Eingeloggt als: " + propertyService.getProperty(PropertyService.TISS_USER),Font.BOLD,false);
                //nameLabelTISS.setText("Eingeloggt als: " + propertyService.getProperty(PropertyService.TISS_USER));
            }
            addEmptyArea(bigSpace*2,false);
            addRow(new JTextArea("Facebook-login"),facebook,false);
            if (propertyService.getProperty(PropertyService.FACEBOOK_USER) != null && !propertyService.getProperty(PropertyService.FACEBOOK_USER).isEmpty() &&
                    propertyService.getProperty(PropertyService.FACEBOOK_PASSWORD) != null && !propertyService.getProperty(PropertyService.FACEBOOK_PASSWORD).isEmpty()) {
                addText("Eingeloggt als: " + propertyService.getProperty(PropertyService.FACEBOOK_USER),Font.BOLD,false);
                //nameLabelTISS.setText("Eingeloggt als: " + propertyService.getProperty(PropertyService.TISS_USER));
            }
            addEmptyArea(bigSpace,false,false);

            buttonWidth=labelWidthRight+20;
            addRow(deleteALL,save,false,false);
        }
    }
}
