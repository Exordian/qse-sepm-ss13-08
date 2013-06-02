package at.ac.tuwien.sepm.ui.kalender;

import at.ac.tuwien.sepm.service.ServiceException;
//import at.ac.tuwien.sepm.ui.StandardInsidePanel;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;


import java.util.Locale;

/**
 * @author Markus MUTH
 */

@UI
public class CalPanelMonth extends CalAbstractView implements CalendarInterface {
    private MigLayout layout;
    private int preMonthDays;

    private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

    public CalPanelMonth() {
        super(5);
        super.firstDay = new DateTime(DateTime.now().getYear(), DateTime.now().getMonthOfYear(), 1, 0, 0, 0, 0);
        layout = new MigLayout("", "1[]1[]1[]1", "1[]");
        loadFonts();
        setSize((int)whiteSpaceCalendar.getWidth(),(int)whiteSpaceCalendar.getHeight());
        setLocation(CalStartCoordinateOfWhiteSpace);
        this.setLayout(layout);
        this.setVisible(true);

        initPanel();
        try {
            setDates();
        } catch (ServiceException e) {
            // TODO do something useful
            e.printStackTrace();
        }

        this.repaint();
        this.revalidate();
    }

    public void initPanel () {
        maxDateLabels = 5;
        initWeekNames((int)whiteSpaceCalendar.getWidth(), (int)whiteSpaceCalendar.getHeight());
        initDayPanels();
        setDays();
    }

    protected void setDays() {
        int actStart, actStop, preStart, preStop, postStart, postStop, preMonthYear, preMonthMonth, postMonthYear, postMonthMonth;

        if(firstDay.getMonthOfYear()==1) {
            preMonthMonth = 12;
            preMonthYear = firstDay.getYear()-1;
            postMonthMonth = 2;
            postMonthYear = firstDay.getYear();
        } else if (firstDay.getMonthOfYear()==12) {
            preMonthMonth = 11;
            preMonthYear = firstDay.getYear();
            postMonthMonth = 1;
            postMonthYear = firstDay.getYear()+1;
        } else {
            preMonthMonth = firstDay.getMonthOfYear()-1;
            preMonthYear = firstDay.getYear();
            postMonthMonth = firstDay.getMonthOfYear()+1;
            postMonthYear = firstDay.getYear();
        }

        String actFirstDayName = firstDay.toString("E", Locale.US);

        preMonthDays = 0;
        if(actFirstDayName.toUpperCase().equals("TUE")) {
            preMonthDays = 1;
        } else if (actFirstDayName.toUpperCase().equals("WED")) {
            preMonthDays = 2;
        } else if (actFirstDayName.toUpperCase().equals("THU")) {
            preMonthDays = 3;
        } else if (actFirstDayName.toUpperCase().equals("FRI")) {
            preMonthDays = 4;
        } else if (actFirstDayName.toUpperCase().equals("SAT")) {
            preMonthDays = 5;
        } else if (actFirstDayName.toUpperCase().equals("SUN")) {
            preMonthDays = 6;
        }

        actStart = 1;
        actStop = firstDay.dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth());
        if(actStop + preMonthDays > AMOUNT_DAYS_SHOWN) {
            actStop = AMOUNT_DAYS_SHOWN - preMonthDays;
        }

        preStart = (new DateTime(preMonthYear, preMonthMonth, 1, 0, 0, 0)).dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth())-preMonthDays+1;
        preStop = (new DateTime(preMonthYear, preMonthMonth, 1, 0, 0, 0)).dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth());

        postStart = 0;
        postStop = 0;
        if((actStop + preMonthDays) < AMOUNT_DAYS_SHOWN) {
            postStart = (new DateTime(postMonthYear, postMonthMonth, 1, 0, 0, 0)).dayOfMonth().withMinimumValue().get(DateTimeFieldType.dayOfMonth());
            postStop = AMOUNT_DAYS_SHOWN - actStop - preMonthDays;
        }

        int a = 0;
        for(int i=preStart; i<=preStop; i++) {
            days[a].setDate(new DateTime(preMonthYear, preMonthMonth, i, 0, 0, 0, 0));
            days[a].setBackground(COLOR_OF_NOT_ACTUAL_MONTH);
            a++;
        }

        for(int i=actStart; i<=actStop; i++) {
            days[a].setDate(new DateTime(firstDay.getYear(), firstDay.getMonthOfYear(), i, 0, 0, 0, 0));
            days[a].setBackground(COLOR_OF_ACTUAL_MONTH);
            a++;
        }

        if((actStop + preMonthDays) < AMOUNT_DAYS_SHOWN) {
            for(int i=postStart; i<=postStop; i++) {
                days[a].setDate(new DateTime(postMonthYear, postMonthMonth, i, 0, 0, 0, 0));
                days[a].setBackground(COLOR_OF_NOT_ACTUAL_MONTH);
                a++;
            }
        }
    }

    /*
    protected void initPanel() {
        int startPre=29;
        int stopPre=30;

        int startAct=1;
        int stopAct=31;

        int startPost=1;
        int stopPost=9;

        int[] days = new int[42];
        boolean[] act = new boolean[42];

        String date1 = "VO - Mathe";
        String date2 = "VO - Mathe";
        String date3 = "VO - Mathe";
        String date4 = "UE - Mathe";
        String date5 = "UE - Mathe";

        int d1 = 2;
        int d2 = 9;
        int d3 = 16;
        int d4 = 9;
        int d5 = 13;


        int o=0;
        while(o<days.length) {
            for(int d=startPre; d<=stopPre; d++) {
                days[o] = d;
                log.info("day: " + days[o]);
                o++;
            }
            for(int d=startAct; d<=stopAct; d++) {
                days[o] = d;
                act[o]=true;
                log.info("day: " + days[o]);
                o++;
            }
            for(int d=startPost; d<=stopPost; d++) {
                days[o] = d;
                log.info("day: " + days[o]);
                o++;
            }
        }

        JLabel mo = new JLabel("Montag");
        mo.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        mo.setForeground(Color.BLACK);
        mo.setOpaque(true);
        mo.setBackground(Color.WHITE);
        mo.setVerticalAlignment(JLabel.CENTER);
        mo.setHorizontalAlignment(JLabel.CENTER);
        JLabel tu = new JLabel("Dienstag");
        tu.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        tu.setForeground(Color.BLACK);
        tu.setOpaque(true);
        tu.setBackground(Color.WHITE);
        tu.setVerticalAlignment(JLabel.CENTER);

        tu.setHorizontalAlignment(JLabel.CENTER);
        JLabel we = new JLabel("Mittwoch");
        we.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        we.setForeground(Color.BLACK);
        we.setOpaque(true);
        we.setBackground(Color.WHITE);
        we.setVerticalAlignment(JLabel.CENTER);
        we.setHorizontalAlignment(JLabel.CENTER);
        JLabel th = new JLabel("Donnerstag");
        th.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        th.setForeground(Color.BLACK);
        th.setOpaque(true);
        th.setBackground(Color.WHITE);
        th.setVerticalAlignment(JLabel.CENTER);
        th.setHorizontalAlignment(JLabel.CENTER);
        JLabel fr = new JLabel("Freitag");
        fr.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        fr.setForeground(Color.BLACK);
        fr.setOpaque(true);
        fr.setBackground(Color.WHITE);
        fr.setVerticalAlignment(JLabel.CENTER);
        fr.setHorizontalAlignment(JLabel.CENTER);
        JLabel sa = new JLabel("Samstag");
        sa.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        sa.setForeground(Color.BLACK);
        sa.setOpaque(true);
        sa.setBackground(Color.WHITE);
        sa.setVerticalAlignment(JLabel.CENTER);
        sa.setHorizontalAlignment(JLabel.CENTER);
        JLabel su = new JLabel("Sonntag");
        su.setMinimumSize(new Dimension(((int)this.getWidth()/7)-1,17));
        su.setForeground(Color.BLACK);
        su.setOpaque(true);
        su.setBackground(Color.WHITE);
        su.setVerticalAlignment(JLabel.CENTER);
        su.setHorizontalAlignment(JLabel.CENTER);

        this.add(mo);
        this.add(tu);
        this.add(we);
        this.add(th);
        this.add(fr);
        this.add(sa);
        this.add(su, "wrap");

        for(int i=0; i<dom.length; i++) {
            dom[i] = new JPanel(new MigLayout());
            dom[i].setMinimumSize(new Dimension((this.getWidth()/7)-1,(int)((this.getHeight()/5)-3.4)));
            if(act[i]){
                dom[i].setBackground(Color.WHITE);
            }
            JLabel l = new JLabel("" + days[i]);
            l.setFont(calendarDaysFont);
            dom[i].add(l, "wrap");
        }

        for (int y=0; y<dom.length; y=y+7) {
            JLabel l = new JLabel();
            for(int x=0; x<6; x++) {
                this.add(dom[x + y]);
                log.info("--> " + (x + y));
            }
            this.add(dom[y + 6], "wrap");
            log.info("--> " + (y + 6));
        }

        JLabel l1 = new JLabel(date1);
        JLabel l2 = new JLabel(date2);
        JLabel l3 = new JLabel(date3);
        JLabel l4 = new JLabel(date4);
        JLabel l5 = new JLabel(date5);

        l1.setFont(calendarDatesFont);
        l2.setFont(calendarDatesFont);
        l3.setFont(calendarDatesFont);
        l4.setFont(calendarDatesFont);
        l5.setFont(calendarDatesFont);

        dom[stopPre-startPre+d1].add(l1,"wrap");
        dom[stopPre-startPre+d2].add(l2,"wrap");
        dom[stopPre-startPre+d3].add(l3,"wrap");
        dom[stopPre-startPre+d4].add(l4,"wrap");
        dom[stopPre-startPre+d5].add(l5,"wrap");

        for(int i=0; i<dom.length; i++) {
            dom[i].revalidate();
            dom[i].repaint();
        }

        this.repaint();
        this.revalidate();
    }*/

    public void semester() {
        //todo für die jcombobox in der man das semester auswählen kann in calendarpanel
    }

    public void next() throws ServiceException {
        if (firstDay.getMonthOfYear()==12) {
            this.firstDay = new DateTime(firstDay.getYear()+1, 1, 1, 0, 0, 0, 0);
        } else {
            this.firstDay = new DateTime(firstDay.getYear(), firstDay.getMonthOfYear()+1, 1, 0, 0, 0, 0);
        }
        setDays();
        setDates();
        repaint();
        revalidate();
    }

    public void last() throws ServiceException {
        if(firstDay.getMonthOfYear()==1) {
            this.firstDay = new DateTime(firstDay.getYear()-1, 12, 1, 0, 0, 0, 0);
        } else {
            this.firstDay = new DateTime(firstDay.getYear(), firstDay.getMonthOfYear()-1, 1, 0, 0, 0, 0);
        }
        setDays();
        setDates();
        repaint();
        revalidate();
    }

    @Override
    public String getTimeIntervalInfo() {
        return firstDay.monthOfYear().getAsText(Locale.GERMANY) + " " + firstDay.getYear();
    }
}
