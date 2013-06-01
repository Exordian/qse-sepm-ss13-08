package at.ac.tuwien.sepm.ui.kalender;

import net.miginfocom.swing.MigLayout;
import org.joda.time.DateTime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
public class DayPanel extends JPanel {
    private JLabel title = new JLabel();
    private JPanel datePanel = new JPanel(new MigLayout("", "1[]1[]1[]1", "1[]"));
    private List<DateLabel> dates;
    private Font tmdlFont = new Font("Arial", Font.PLAIN, 10);
    private DateTime date;
    private int maxDateLabels;
    private TooMuchDatesLabel tmdl;
    /*
    public DayPanel () {
        super(new MigLayout("", "1[]1[]1[]1", "1[]"));
        dates = new ArrayList<DateLabel>();
    }
    */
    public DayPanel(int maxDateLabels) {
        super(new MigLayout("", "1[]1[]1[]1", "1[]"));
        dates = new ArrayList<DateLabel>();
        this.add(title, "wrap");
        this.add(datePanel);
        this.setOpaque(true);
        this.tmdl = new TooMuchDatesLabel();
        this.tmdl.setFont(tmdlFont);
        this.maxDateLabels=maxDateLabels;
    }

    /*
    public void addDateLabel(DateLabel date) {

    }
    */
    /*
    public void draw() {

    }
    */
    public void setDate(DateTime date) {
        this.date = date;
        this.title.setText(" " + date.getDayOfMonth());
    }

    public DateTime getDate () {
        return new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0, 0, 0);
    }

    public void setDateLabels(List<DateLabel> dates) {
        this.dates = dates;
        this.datePanel.setBackground(this.getBackground());
    }

    public void refreshDates() {
        deleteAllDates();
        if(dates.size() > maxDateLabels) {
            for(int i=0; i<maxDateLabels-1; i++) {
                datePanel.add(dates.get(i), "wrap");
            }
            tmdl.setText(dates.size()-maxDateLabels+1);
            datePanel.add(tmdl);
        } else {
            for(DateLabel l : dates) {
                datePanel.add(l, "wrap");
            }
        }
        this.revalidate();
        this.repaint();
    }

    private void deleteAllDates() {
        datePanel.removeAll();
        this.remove(tmdl);
    }

    public JPanel getDatePanel() {
        return datePanel;
    }

    public List<DateLabel> getDates () {
        return dates;
    }

    private class TooMuchDatesLabel extends JLabel {
        public TooMuchDatesLabel () {
            setText("");
            addMouseListener(new PrivateMouseListener());
        }

        void setText(int i) {
            this.setText("And " + i + " more ...");
        }

        class PrivateMouseListener extends MouseAdapter {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    new CalDisplayAllDatesFrame(getDates());
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
    }

    private class CalDisplayAllDatesFrame extends JFrame {
        // TODO implement this frame or panel or whatever ...
        private List<DateLabel> dates;
        private MigLayout layout = new MigLayout();

        CalDisplayAllDatesFrame(List<DateLabel> dates) {
            this.setVisible(true);
            this.dates = dates;
            this.setMinimumSize(new Dimension(250, 50));
            this.setLocation(500,500);
            init();
        }

        private void init() {
            this.add(new JLabel("Here will all " + dates.size() + "dates be displayed ..."));
        }
    }
}
