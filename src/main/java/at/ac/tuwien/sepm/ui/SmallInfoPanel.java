package at.ac.tuwien.sepm.ui;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 19.06.13
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
@UI
public class SmallInfoPanel extends StandardInsidePanel {
    private JLabel infoText;
    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());
    private static final int MAX_LETTERS = 200;
    public static int Error = 1;
    public static int Info = 2;
    public static int Warning = 3;
    public static int Success = 4;

    public SmallInfoPanel() {
        this.setLayout(null);
        this.setOpaque(false);
        // this.setBounds(600-270,5,540,40);
        this.setBounds(600-270,5,900,40);
        loadFonts();
        changeImage(1);
        loadText();
        repaint();
        revalidate();
    }

    private void loadText() {
        infoText = new JLabel("dummy");
        infoText.setForeground(new Color(198, 205, 199));
        infoText.setBounds(47,0, 843, 40);
        this.add(infoText);
    }

    public void setInfoText(String s, int nmb) {
        if(s.length() <= MAX_LETTERS) {
            infoText.setText(s);
        } else {
            infoText.setText("info text zu lang");
        }
        infoText.setFont(standardButtonFont);
        changeImage(nmb);
    }

    private void changeImage(int nmb) {
        try{
            switch(nmb) {
                case 1:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/error.png"));
                    break;
                case 2:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/info.png"));
                    break;
                case 3:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/warning.png"));
                    break;
                case 4:
                    image = ImageIO.read(ClassLoader.getSystemResource("img/success.png"));
                    break;
                default:
                    break;
            }
            this.repaint();
        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0,0, null);
        }
    }
}
