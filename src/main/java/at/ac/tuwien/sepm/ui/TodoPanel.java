package at.ac.tuwien.sepm.ui;

import javax.swing.*;
import java.awt.*;

@UI
public class TodoPanel extends StandardInsidePanel {
    public TodoPanel() {
        JLabel meins = new JLabel("bluasdffffafdsafasdfsadfasfdsafsadfdsafsafsdafasdfb");
        meins.setBounds(250,250, 205, 30);
        meins.setForeground(Color.WHITE);
        meins.setFont(new Font("SansSerif", Font.PLAIN, 35));
        meins.setVisible(true);
        //meins.setSize(100, 100);
        this.add(meins);
    }
}
