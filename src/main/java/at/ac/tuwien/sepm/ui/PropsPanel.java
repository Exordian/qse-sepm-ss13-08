package at.ac.tuwien.sepm.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

@UI
public class PropsPanel extends JPanel {
    private Image image;
    private final Rectangle size = new Rectangle(67, 50, 1119, 639);

    public PropsPanel() {
        this.setLayout(null);
        this.setBounds(size);
        this.setOpaque(false);

        try {
            image = ImageIO.read(new File("src/main/resources/img/plainpanel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JLabel title = new JLabel("Eigenschaften");
        title.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+3, (int)(size.getHeight()/2-image.getHeight(null)/2)-42,225,45);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.PLAIN, 35));
        this.add(title);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, (int)((size.getWidth()/2)-(image.getWidth(null)/2)), (int)(size.getHeight()/2-image.getHeight(null)/2), null);
        }
    }
}
