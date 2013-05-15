package at.ac.tuwien.sepm.ui;

import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

@UI
public class MainFrame extends JFrame {

    @Autowired
    public MainFrame(HelloWorldPanel helloWorldPanel) {
        super("Studiums Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.add(helloWorldPanel, BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
