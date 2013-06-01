package at.ac.tuwien.sepm.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 01.06.13
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
public abstract class StandardSimpleInsidePanel extends StandardInsidePanel {
    protected JButton retButton;
    protected BackgroundPanel bgPanel;

    protected Rectangle simpleWhiteSpace = new Rectangle(98, 64, 922, 509);  //white space in simple inside panel

    protected void addImage() {
        try {
            image = ImageIO.read(ClassLoader.getSystemResource("img/plainpanel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void addReturnButton() {
        retButton = new JButton();
        retButton.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2)), (int)(size.getHeight()/2-image.getHeight(null)/2)-33, 25, 25);

        try {
            Image img = ImageIO.read(ClassLoader.getSystemResource("img/backbutton.png"));
            retButton.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        retButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
                bgPanel.showLastComponent();
            }
        });
        retButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retButton.setOpaque(false);
        retButton.setContentAreaFilled(false);
        retButton.setBorderPainted(false);
        this.add(retButton);
    }

    protected void addTitle(String s) {
        JLabel title = new JLabel(s);
        title.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+35, (int)(size.getHeight()/2-image.getHeight(null)/2)-42,257,45);
        title.setForeground(Color.WHITE);
        title.setFont(standardTitleFont);
        this.add(title);
    }

    public void setBgPanel(BackgroundPanel bgPanel) {
        this.bgPanel=bgPanel;
    }
}
