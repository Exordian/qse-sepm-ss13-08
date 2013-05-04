package at.ac.tuwien.sepm.ui;

import javax.swing.*;

@UI
public class HelloWorldPanel extends JPanel{

    public HelloWorldPanel() {
        this.add(new JLabel("ohai"));
    }
}
