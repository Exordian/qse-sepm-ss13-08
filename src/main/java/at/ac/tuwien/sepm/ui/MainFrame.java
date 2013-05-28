package at.ac.tuwien.sepm.ui;

import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

@UI
public class MainFrame extends JFrame {
    private BGPanel bgPanel;

    @Autowired
    public MainFrame(BGPanel bgPanel) {
        super("Studiums Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.bgPanel = bgPanel;
        //f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        init();
    }

    private void init() {
        this.setPreferredSize(new Dimension(1200, 728));
        //this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon("src/main/resources/img/icon.jpg").getImage());
        this.add(bgPanel);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
