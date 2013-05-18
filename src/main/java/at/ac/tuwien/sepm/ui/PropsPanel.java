package at.ac.tuwien.sepm.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@UI
public class PropsPanel extends JPanel {
    private Image image;

    public PropsPanel() {
        this.setLayout(null);
        this.setBounds(160, 122, 930, 518);
        this.setOpaque(false);

        try {
            image = ImageIO.read(new File("src/main/resources/img/plainpanel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel text = new JLabel("Eigenschaften");
        text.setBounds(10,0,100,100);
        this.add(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }
}
