package at.ac.tuwien.sepm.ui.calender;

import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.ui.UI;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
public class DayPanel extends JPanel {
    private JLabel title = new JLabel();
    private JPanel datePanel = new JPanel(new MigLayout("", "1[]1[]1[]1", "1[]"));
    private List<DateLabel> dates;
    private DateTime date;
    private int maxDateLabels;
    private TooMuchDatesLabel tmdl;
    private boolean isActual = false;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    private DateService dateService;

    public DayPanel(int maxDateLabels, DateService dateService) {
        super(new MigLayout("", "1[]1[]1[]1", "1[]"));
        this.dateService=dateService;
        dates = new ArrayList<DateLabel>();
        this.add(title, "wrap");
        this.add(datePanel, "grow, push, span ");
        this.setOpaque(true);
        this.tmdl = new TooMuchDatesLabel();
        this.tmdl.setFont(new Font("Arial", Font.PLAIN, 10));
        this.maxDateLabels=maxDateLabels;
        this.addMouseListeners();
    }

    public void setActual(boolean actual) {
        this.isActual = actual;
    }

    public boolean getActual() {
        return this.isActual;
    }

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
                dates.get(i).changeColor(isActual);
                datePanel.add(dates.get(i), "w 100%, wrap");
            }
            tmdl.setText(dates.size()-maxDateLabels+1);
            datePanel.add(tmdl);
        } else {
            for(DateLabel l : dates) {
                l.changeColor(isActual);
                datePanel.add(l, "w 100%, wrap");
            }
        }
        this.revalidate();
        this.repaint();
    }

    private void deleteAllDates() {
        datePanel.removeAll();
        this.remove(tmdl);
    }
    /*
    public JPanel getDatePanel() {
        return datePanel;
    }
    */
    public List<DateLabel> getDates () {
        return dates;
    }

    private void addMouseListeners(){
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==3) {
                    PopUpMenu menu = new PopUpMenu();
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
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
        private JLabel text = new JLabel();

        CalDisplayAllDatesFrame(List<DateLabel> dates) {
            this.dates = dates;
            this.setMinimumSize(new Dimension(250, 50));
            this.setLocation(500, 500);
            text.setText("Here will all " + this.dates.size() + " dates be displayed ...");
            this.setMinimumSize(new Dimension((int)(text.getSize().getWidth()), (int)(text.getSize().getHeight())));
            this.add(text);
            this.pack();
            this.revalidate();
            this.repaint();
            this.setVisible(true);
        }
    }

    private class PopUpMenu extends JPopupMenu {
        private JMenuItem newDate;
        private JMenuItem free;
        private JMenuItem newLvaDate;

        public PopUpMenu(){
            newDate = new JMenuItem("Neuer Termin");
            free = new JMenuItem("Als freien Tag markieren");
            newLvaDate = new JMenuItem("Neuer Lva Termin");
            add(newDate);
            add(newLvaDate);
            add(free);
            addActionListeners();
        }

        private void addActionListeners() {
            newDate.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    PanelTube.backgroundPanel.viewDate(null);
                }
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
            newLvaDate.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    PanelTube.backgroundPanel.viewLvaDate(null);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            free.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {
                    DateEntity dateEntity = new DateEntity();
                    dateEntity.setName("Freier Tag");
                    dateEntity.setTime(new TimeFrame(new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0, 0, 0), new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 23, 59, 59, 0)));
                    dateEntity.setIntersectable(false);
                    dateEntity.setDescription("Dies ist ein freier Tag, das heisst: KEINE UNI YEAAH!!!");
                    try {
                        DayPanel.this.dateService.createDate(dateEntity);
                    } catch (ServiceException e1) {
                        log.error(e1.getMessage());
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }
    }
}
