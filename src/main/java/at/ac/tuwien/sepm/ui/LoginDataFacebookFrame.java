package at.ac.tuwien.sepm.ui;



import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginDataFacebookFrame extends JPanel {
    private JTextField keyField;

    private JButton eingeben;
    private JButton abbrechen;

    private JLabel keyLabel;

    public LoginDataFacebookFrame() {
        super(new MigLayout("insets 10"));

        keyLabel = new JLabel("API Key:");
        this.add(keyLabel, "grow");

        keyField = new JTextField("");
        this.add(keyField, "grow, wrap");

        eingeben = new JButton("Eingeben");
        eingeben.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Window w = SwingUtilities.getWindowAncestor(LoginDataFacebookFrame.this);
                w.setVisible(false);
                w.dispose();
            }
        });
        this.add(eingeben, "grow");


        abbrechen = new JButton("Abbrechen");
        abbrechen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Window w = SwingUtilities.getWindowAncestor(LoginDataFacebookFrame.this);
                w.setVisible(false);
                w.dispose();
            }
        });
        this.add(abbrechen, "grow, wrap");
    }

    public String getKey() {
        return keyField.getText();
    }
}
