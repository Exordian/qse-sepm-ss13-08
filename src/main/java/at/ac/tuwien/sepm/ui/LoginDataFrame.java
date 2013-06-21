package at.ac.tuwien.sepm.ui;



import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public class LoginDataFrame extends JPanel {
    private JTextField nameField;
    private JTextField pwField;

    private JButton eingeben;
    private JButton abbrechen;

    private JLabel nameLabel;
    private JLabel pwdLabel;

    public LoginDataFrame() {
        super(new MigLayout("insets 10"));

        nameLabel = new JLabel("Name:");
        this.add(nameLabel, "grow");

        nameField = new JTextField("");
        this.add(nameField, "grow, wrap");


        pwdLabel = new JLabel("Passwort:");
        this.add(pwdLabel, "grow");

        pwField = new JPasswordField("");
        this.add(pwField, "grow, wrap");


        eingeben = new JButton("Eingeben");
        eingeben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Window w = SwingUtilities.getWindowAncestor(LoginDataFrame.this);
                w.setVisible(false);
                w.dispose();
            }
        });
        this.add(eingeben, "grow");


        abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Window w = SwingUtilities.getWindowAncestor(LoginDataFrame.this);
                w.setVisible(false);
                w.dispose();
            }
        });
        this.add(abbrechen, "grow, wrap");
    }

    public String getName() {
        if (nameField.getText().equals("eingeben")) {
            return "";
        }
        return nameField.getText();
    }

    public String getPassword() {
        if (pwField.getText().equals("eingeben")) {
            return "";
        }
        return pwField.getText();
    }
}
