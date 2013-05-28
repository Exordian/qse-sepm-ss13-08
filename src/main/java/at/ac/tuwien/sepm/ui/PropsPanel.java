package at.ac.tuwien.sepm.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@UI
public class PropsPanel extends StandardInsidePanel {
    public PropsPanel() {
        this.setLayout(null);
        this.setBounds(size);
        this.setOpaque(false);

        try {
            image = ImageIO.read(new File("src/main/resources/img/plainpanel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addTitle();
    }

    private void addTitle() {
        JLabel title = new JLabel("Eigenschaften");
        title.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+3, (int)(size.getHeight()/2-image.getHeight(null)/2)-42,225,45);
        title.setForeground(Color.WHITE);
        title.setFont(standardTitleFont);
        this.add(title);
    }
}
