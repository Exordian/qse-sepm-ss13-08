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
            standardTitleFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("src/main/resources/segeoui.ttf"))).deriveFont(35f);
            standardTextFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("src/main/resources/segeoui.ttf"))).deriveFont(15f);
            standardButtonFont = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("src/main/resources/segeoui.ttf"))).deriveFont(13f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
