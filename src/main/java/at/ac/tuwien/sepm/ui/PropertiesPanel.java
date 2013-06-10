package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.service.AuthService;
import at.ac.tuwien.sepm.service.PropertyService;
import at.ac.tuwien.sepm.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

@UI
public class PropertiesPanel extends StandardSimpleInsidePanel {
    private JLabel tissLogin;
    private JLabel facebookLogin;
    private JLabel defaultEcts;
    private JLabel pickMajor;

    private JLabel nameLabelFacebook;
    private JLabel nameLabelTISS;

    private JButton tiss;
    private JButton facebook;
    private JTextField ects;
    private JComboBox major;
    private JButton deleteALL;

    private JButton save;

    private String loginNameFB;
    private String loginPWDFB;

    private String loginNameTISS;
    private String loginPWDTISS;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private AuthService authService;

    public PropertiesPanel() {
        init();
        addImage();
        addTitle("Einstellungen");
        addReturnButton();
        addContent();
        addSaveButton();
        //todo studienanfang eingeben (erstes semester)
    }

    private void addSaveButton() {
        save = new JButton("Speichern");
        save.setFont(standardButtonFont);
        save.setBounds(deleteALL.getX() + deleteALL.getWidth()+100, deleteALL.getHeight() + deleteALL.getY() + 60, 110, 30);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //todo speichern
            }
        });
        this.add(save);
    }

    private void addContent() {
        int textHeight = 25;
        int textWidth = 200;
        int verticalSpace = 10;
        int verticalSpaceVast = 40;

        tissLogin = new JLabel("TISS Login Daten");
        tissLogin.setFont(standardTextFont);
        tissLogin.setBounds((int)simpleWhiteSpace.getX()+70, (int)simpleWhiteSpace.getY()+30, textWidth, textHeight);
        this.add(tissLogin);

        tiss = new JButton("eingeben");
        tiss.setFont(standardButtonFont);
        tiss.setBounds(tissLogin.getX(), tissLogin.getY() + tissLogin.getHeight() + verticalSpace, textWidth - 20, textHeight);

        nameLabelTISS = new JLabel();
        nameLabelTISS.setFont(standardTextFont);
        nameLabelTISS.setBounds(tiss.getX()+tiss.getWidth()+30, tiss.getY(), 500, textHeight);
        this.add(nameLabelTISS);

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
                            loginNameTISS = temp.getName();
                            nameLabelTISS.setText("Eingeloggt als: " + temp.getName());
                            propertyService.setProperty("tiss.user", temp.getName());
                            loginPWDTISS = temp.getPassword();
                            propertyService.setProperty("tiss.password", temp.getPassword());
                        } catch (ServiceException ex) {
                            nameLabelTISS.setText("Login Daten ungültig");
                        }
                        PropertiesPanel.this.revalidate();
                        PropertiesPanel.this.setVisible(true);
                    }
                });
            }
        });
        this.add(tiss);


        facebookLogin = new JLabel("Facebook Login Daten");
        facebookLogin.setFont(standardTextFont);
        facebookLogin.setBounds(tissLogin.getX(), tiss.getHeight() + tiss.getY() + verticalSpaceVast, textWidth, textHeight);
        this.add(facebookLogin);

        facebook = new JButton("eingeben");
        facebook.setFont(standardButtonFont);
        facebook.setBounds(facebookLogin.getX(), facebookLogin.getY() + facebookLogin.getHeight() + verticalSpace, textWidth-20, textHeight);

        nameLabelFacebook= new JLabel();
        nameLabelFacebook.setFont(standardTextFont);
        nameLabelFacebook.setBounds(facebook.getX()+facebook.getWidth()+30, facebook.getY(), 500, textHeight);
        this.add(nameLabelFacebook);

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
                        loginNameFB = temp.getName();
                        nameLabelFacebook.setText("Eingeloggt als: " + temp.getName());
                        //todo do with name whatever
                        loginPWDFB = temp.getPassword();
                        //todo do with password whatever
                        PropertiesPanel.this.revalidate();
                        PropertiesPanel.this.setVisible(true);
                    }
                });
            }
        });
        this.add(facebook);

        defaultEcts = new JLabel("Default ECTS angeben");
        defaultEcts.setFont(standardTextFont);
        defaultEcts.setBounds(tissLogin.getX(), facebook.getHeight() + facebook.getY() + verticalSpaceVast, textWidth, textHeight);
        this.add(defaultEcts);

        ects = new JTextField("30");
        ects.setFont(standardButtonFont);
        ects.setBounds(defaultEcts.getX() + defaultEcts.getWidth() - 10, defaultEcts.getY(), 25, textHeight);
        this.add(ects);

        pickMajor = new JLabel("Studium auswählen");
        pickMajor.setFont(standardTextFont);
        pickMajor.setBounds(tissLogin.getX(), ects.getHeight() + ects.getY() + verticalSpaceVast, textWidth, textHeight);
        this.add(pickMajor);

        major = new JComboBox();
        major.addItem("Teststudium1");
        major.addItem("Teststudium2");
        major.addItem("Teststudium3");
        major.setFont(standardButtonFont);
        major.setBounds(pickMajor.getX() + pickMajor.getWidth() - 10, pickMajor.getY(), 150, textHeight);
        this.add(major);


        deleteALL = new JButton("ALLE Daten zurücksetzen");
        deleteALL.setFont(standardTextFont);
        deleteALL.setBounds(pickMajor.getX(), pickMajor.getHeight() + pickMajor.getY() + verticalSpaceVast, textWidth+50, textHeight);
        deleteALL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object[] options = {"Ja", "Abbrechen"};
                JOptionPane.showOptionDialog(PropertiesPanel.this, "Wollen Sie wirklich alle gespeicherten Daten des Programms löschen?", "Bestätigung",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                //todo alle daten löschen
            }
        });

        this.add(deleteALL);
        this.revalidate();
        this.repaint();
    }

}
