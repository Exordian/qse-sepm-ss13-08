package at.ac.tuwien.sepm.ui.kalender;

import at.ac.tuwien.sepm.entity.Date;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.TimeFrame;
import net.miginfocom.swing.MigLayout;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Markus MUTH
 */
public class DateLabel extends JLabel{
    private static final int MAX_TEXT_LENGTH=20;
    private Date date;

    public DateLabel(Date date) {
        this.date=date;
        if(date.getName()==null || date.getName().equals("") || consistsOf(' ', date.getName())){
            this.setText("...");
        } else {
            this.setText(cutText(date.getName()));
        }
        this.setFont(new Font("Arial", Font.PLAIN, 10));
        this.addMouseListener(new PrivateMouseListener());
    }

    public DateTime getStart() {
        return date.getTime().from();
    }

    public DateTime getStop() {
        return date.getTime().to();
    }

    public TimeFrame getTime() {
        return date.getTime();
    }

    private boolean consistsOf(char c, String s) {
        for(int i=0; i<s.length(); i++) {
            if(c != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private String cutText(String text) {
        if(text.length() > MAX_TEXT_LENGTH) {
            text = text.substring(0,17) + "...";
        }
        return text;
    }

    private Date getDate() {
        return date;
    }

    class PrivateMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                UpdateDateFrameFactory.createFrame(getDate());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    private static class UpdateDateFrameFactory extends JFrame {
        private static void createFrame(Date d) {
            if(d instanceof DateEntity) {
                new UpdateDateFrame((DateEntity)d);
            } else if (d instanceof LvaDate) {
                new UpdateLvaDateFrame((LvaDate)d);
            }
        }

        private static class UpdateDateFrame extends JFrame {
            // TODO implement this class correctly
            private DateEntity date;
            private JLabel label0=new JLabel();
            private JLabel label1=new JLabel();

            public UpdateDateFrame(DateEntity d) {
                this.setLayout(new MigLayout());
                this.date = d;
                this.setVisible(true);
                this.setMinimumSize(new Dimension(400, 100));
                this.setLocation(500,500);
                label0.setText("UpdateDateFrame ...");
                label1.setText("Name of date: " + date.getName());
                this.add(label0, "wrap");
                this.add(label1);
            }
        }

        private static class UpdateLvaDateFrame extends JFrame {
            // TODO implement this class correctly
            private LvaDate date;
            private JLabel label0=new JLabel();
            private JLabel label1=new JLabel();

            public UpdateLvaDateFrame(LvaDate d) {
                this.setLayout(new MigLayout());
                this.date = d;
                this.setVisible(true);
                this.setMinimumSize(new Dimension(400,100));
                this.setLocation(500,500);
                label0.setText("UpdateLvaDateFrame ...");
                label1.setText("Name of date: " + date.getName());
                this.add(label0, "wrap");
                this.add(label1);
            }
        }
    }
}
