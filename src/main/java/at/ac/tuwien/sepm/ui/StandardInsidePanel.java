package at.ac.tuwien.sepm.ui;

import javax.swing.*;
import java.awt.*;

@UI
public abstract class StandardInsidePanel extends JPanel {
    protected Image image;
    protected final Rectangle size = new Rectangle(67, 50, 1119, 639);

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, (int)((size.getWidth()/2)-(image.getWidth(null)/2)), (int)(size.getHeight()/2-image.getHeight(null)/2), null);
        }
    }
}
