package at.ac.tuwien.sepm.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, (int)((size.getWidth()/2)-(image.getWidth(null)/2)), (int)(size.getHeight()/2-image.getHeight(null)/2), null);
        }
    }

    protected void loadFonts() {
        try {
            Font temp = Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemResource("segoeui.ttf").openStream());
            standardTitleFont = temp.deriveFont(35f);
            standardTextFont = temp.deriveFont(15f);
            standardButtonFont = temp.deriveFont(13f);
            log.info("Font wurde geladen.");
        } catch (FontFormatException e) {
            log.info("Probleme beim Font laden.");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("Probleme beim Font laden.");
            e.printStackTrace();
        }
    }

    protected void init() {
        this.setLayout(null);
        this.setBounds(size);
        this.setOpaque(false);
        loadFonts();
    }
}
