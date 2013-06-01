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
    protected final Rectangle size = new Rectangle(67, 50, 1119, 639);  //x, y, width and height of panel (von oben links im tab bis unten rechts im tab)

    protected final Rectangle whiteSpaceCalendar = new Rectangle(903, 459); //only width and height
    protected final Point CalStartCoordinateOfWhiteSpace = new Point(38, 58);  //x, y of cal whitespace

    protected final Rectangle whiteSpaceStud = new Rectangle(1014, 517);  //only width and height
    protected final Point StudStartCoordinateOfWhiteSpace = new Point(51, 79); //x, y of stud whitespace

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
            standardTextFont = temp.deriveFont(17f);
            standardButtonFont = temp.deriveFont(14f);
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
