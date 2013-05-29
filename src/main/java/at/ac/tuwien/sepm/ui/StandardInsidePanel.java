package at.ac.tuwien.sepm.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@UI
public abstract class StandardInsidePanel extends JPanel {
    protected Image image;
    protected final Rectangle size = new Rectangle(67, 50, 1119, 639);
    protected Font standardTitleFont;
    protected Font standardTextFont;
    protected Font standardButtonFont;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (standardTitleFont==null || standardTextFont==null || standardButtonFont==null) {
            loadFonts();
        }
        if (image != null) {
            g.drawImage(image, (int)((size.getWidth()/2)-(image.getWidth(null)/2)), (int)(size.getHeight()/2-image.getHeight(null)/2), null);
        }
    }

    private void loadFonts() {
        try {
            standardTitleFont = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResource("segeoui.ttf").openStream()).deriveFont(Font.PLAIN, 35.0f);
            standardTextFont = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResource("segeoui.ttf").openStream()).deriveFont(Font.PLAIN, 15.0f);
            standardButtonFont = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResource("segeoui.ttf").openStream()).deriveFont(Font.PLAIN, 13.0f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
