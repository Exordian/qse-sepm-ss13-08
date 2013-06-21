package at.ac.tuwien.sepm.ui.calender.cal;

import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.service.DateService;
import at.ac.tuwien.sepm.service.LVAService;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import at.ac.tuwien.sepm.service.impl.ValidationException;
import at.ac.tuwien.sepm.ui.SmallInfoPanel;
import at.ac.tuwien.sepm.ui.template.PanelTube;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
    private DateTime date;
    private int maxDateLabels;
    private TooMuchDatesLabel tmdl;
    private boolean isCurrent = false;
    private boolean showTime=false;
    private LVAService lvaService;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    private DateService dateService;

    public DayPanel(int maxDateLabels, DateService dateService, LVAService lvaService, boolean showTime) {
        super(new MigLayout("", "1[]1[]1[]1", "1[]"));
        this.dateService=dateService;
        this.lvaService=lvaService;
        this.showTime = showTime;
        dates = new ArrayList<DateLabel>();
        this.add(title, "wrap");
        this.add(datePanel, "grow, push, span ");
        this.setOpaque(true);
        this.tmdl = new TooMuchDatesLabel();
        this.tmdl.setFont(new Font("Arial", Font.PLAIN, 10));
        this.maxDateLabels=maxDateLabels;
        this.addMouseListeners();
    }

    public void setCurrent(boolean current) {
        this.isCurrent = current;
    }

    public boolean getCurrent() {
        return this.isCurrent;
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
        boolean freeday = false;
        deleteAllDates();
        for(DateLabel l : dates) {
            if (l.getDate() instanceof DateEntity)
                if (((DateEntity)l.getDate()).getDescription().equals("Dies ist ein freier Tag, das heisst: KEINE UNI YEAAH!!!")) {
                    l.changeColor(isCurrent);
                    datePanel.add(l, "w 100%, wrap");
                    this.revalidate();
                    this.repaint();
                    freeday = true;
                }
        }

        if(dates.size() > maxDateLabels) {
            for(int i=0; i<maxDateLabels-1; i++) {
                if (freeday) {
                    if (dates.get(i).getDate() instanceof DateEntity) {
                        dates.get(i).changeColor(isCurrent);
                        datePanel.add(dates.get(i), "w 100%, wrap");
                    }
                } else {
                    dates.get(i).changeColor(isCurrent);
                    datePanel.add(dates.get(i), "w 100%, wrap");
                }
            }
            tmdl.setText(dates.size()-maxDateLabels+1);
            datePanel.add(tmdl);

        } else {
            for(DateLabel l : dates) {
                if (freeday) {
                    if (l.getDate() instanceof DateEntity) {
                        l.changeColor(isCurrent);
                        datePanel.add(l, "w 100%, wrap");
                    }
                } else {
                    l.changeColor(isCurrent);
                    datePanel.add(l, "w 100%, wrap");
                }
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
                if (evt.getClickCount() == 1) {
                    PanelTube.calendarPanel.jumpToDate(date);
                    //new CalDisplayAllDatesFrame(getDates());
                }
            }
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
            newDate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    PanelTube.backgroundPanel.viewDate(null, date);
                }
            });
            newLvaDate.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    try {
                        if (!lvaService.readAll().isEmpty()) {
                            PanelTube.backgroundPanel.viewLvaDate(null,date);
                        } else {
                            PanelTube.backgroundPanel.viewInfoText("Es existieren noch keine Lehrveranstaltungen.", SmallInfoPanel.Error);
                        }
                    } catch (ServiceException e1) {
                        log.error(e1.getMessage());
                    } catch (ValidationException e1) {
                        log.error(e1.getMessage());
                    }
                }
            });
            free.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    DateEntity dateEntity = new DateEntity();
                    dateEntity.setName("Freier Tag");
                    dateEntity.setTime(new TimeFrame(new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0, 0, 0), new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 23, 59, 59, 0)));
                    dateEntity.setIntersectable(false);
                    dateEntity.setDescription("Dies ist ein freier Tag, das heisst: KEINE UNI YEAAH!!!");
                    try {
                        DayPanel.this.dateService.createDate(dateEntity);
                        PanelTube.backgroundPanel.viewInfoText("Der Tag wurde als frei markiert.", SmallInfoPanel.Success);
                    } catch (ServiceException e1) {
                        log.error(e1.getMessage());
                        PanelTube.backgroundPanel.viewInfoText("Der Tag konnte nicht als frei markiert werden.", SmallInfoPanel.Error);
                    }
                    PanelTube.calendarPanel.refresh();
                }
            });
        }
    }
}
