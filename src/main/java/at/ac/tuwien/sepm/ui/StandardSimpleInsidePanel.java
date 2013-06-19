package at.ac.tuwien.sepm.ui;

import at.ac.tuwien.sepm.ui.template.PanelTube;
import com.toedter.calendar.JDateChooser;
import org.joda.time.DateTime;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    protected JTextField title;  //editable title
    protected JLabel title2;     //non-editable title

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
                PanelTube.backgroundPanel.showLastComponent();
            }
        });
        retButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        retButton.setOpaque(false);
        retButton.setContentAreaFilled(false);
        retButton.setBorderPainted(false);
        this.add(retButton);
        revalidate();
        repaint();
    }

    protected void addTitle(String s) {
        title2 = new JLabel(s);
        title2.setBounds((int)((size.getWidth()/2)-(image.getWidth(null)/2))+35, (int)(size.getHeight()/2-image.getHeight(null)/2)-42,image.getWidth(null),45);
        title2.setForeground(Color.WHITE);
        title2.setFont(standardTitleFont);
        this.add(title2);
        this.repaint();
        this.revalidate();
    }

    protected void changeTitle(String s) {
        if (title2 == null) {
            title.setText(s);
        } else {
            title2.setText(s);
        }
        revalidate();
        repaint();
    }

    protected void addEditableTitle(String s) {
        title = new JTextField(s);
        title.setBounds((int) ((size.getWidth() / 2) - (image.getWidth(null) / 2)) + 35, (int) (size.getHeight() / 2 - image.getHeight(null) / 2) - 42, image.getWidth(null), 45);
        title.setForeground(Color.WHITE);
        title.setFont(standardTitleFont);
        title.setOpaque(false);
        title.setBorder(null);
        title.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                StandardSimpleInsidePanel.this.repaint();
                StandardSimpleInsidePanel.this.revalidate();
            }
            public void removeUpdate(DocumentEvent e) {
                StandardSimpleInsidePanel.this.repaint();
                StandardSimpleInsidePanel.this.revalidate();
            }
            public void insertUpdate(DocumentEvent e) {
                StandardSimpleInsidePanel.this.repaint();
                StandardSimpleInsidePanel.this.revalidate();
            }
        });

        this.add(title);
        this.repaint();
        this.revalidate();
    }

    //converts spinner and datechooser to dateTime
    protected DateTime convertDateAndTime(JSpinner spinner, JDateChooser dateChooser) {
        Date time = (Date) spinner.getValue();
        GregorianCalendar timeCalendar = new GregorianCalendar();
        timeCalendar.setTime(time);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(dateChooser.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get( Calendar.HOUR_OF_DAY ) );
        calendar.set(Calendar.MINUTE, timeCalendar.get( Calendar.MINUTE ) );
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date newDate = calendar.getTime();
        return new DateTime(newDate);
    }
}
