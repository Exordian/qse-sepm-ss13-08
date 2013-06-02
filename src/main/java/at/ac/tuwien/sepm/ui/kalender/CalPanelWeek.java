package at.ac.tuwien.sepm.ui.kalender;

import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.ui.UI;
import net.miginfocom.swing.MigLayout;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

import java.util.Locale;

/**
 * @author Markus MUTH
 */

@UI
public class CalPanelWeek extends CalAbstractView implements CalendarInterface {
    private MigLayout layout;

    public CalPanelWeek() {
        super(1);
        super.firstDay = DateTime.now();
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

    @Override
    protected void initPanel() {
        maxDateLabels=30;
        initWeekNames((int)whiteSpaceCalendar.getWidth(), (int)whiteSpaceCalendar.getHeight());
        initDayPanels();
        setDays();
    }

    public void setDays() {
        int actStart, actStop, preStart, preStop, postStart, postStop, dow, preYear, postYear, preMonth, postMonth;
        int actDay = firstDay.getDayOfMonth();
        DateTime d = (new DateTime(firstDay.getYear(), firstDay.getMonthOfYear(), 1, 0, 0, 0));
        DateTime e = (new DateTime(firstDay.getYear(), firstDay.getMonthOfYear(), firstDay.dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth()), 0, 0, 0));
        int dowa;
        int dowb;

        String actFirstDayName = firstDay.toString("E", Locale.US);

        dow = 0;
        if(actFirstDayName.toUpperCase().equals("TUE")) {
            dow = 1;
        } else if (actFirstDayName.toUpperCase().equals("WED")) {
            dow = 2;
        } else if (actFirstDayName.toUpperCase().equals("THU")) {
            dow = 3;
        } else if (actFirstDayName.toUpperCase().equals("FRI")) {
            dow = 4;
        } else if (actFirstDayName.toUpperCase().equals("SAT")) {
            dow = 5;
        } else if (actFirstDayName.toUpperCase().equals("SUN")) {
            dow = 6;
        }

        actFirstDayName = d.toString("E", Locale.US);
        dowa = 0;
        if(actFirstDayName.toUpperCase().equals("TUE")) {
            dowa = 1;
        } else if (actFirstDayName.toUpperCase().equals("WED")) {
            dowa = 2;
        } else if (actFirstDayName.toUpperCase().equals("THU")) {
            dowa = 3;
        } else if (actFirstDayName.toUpperCase().equals("FRI")) {
            dowa = 4;
        } else if (actFirstDayName.toUpperCase().equals("SAT")) {
            dowa = 5;
        } else if (actFirstDayName.toUpperCase().equals("SUN")) {
            dowa = 6;
        }

        actFirstDayName = e.toString("E", Locale.US);
        dowb = 0;
        if(actFirstDayName.toUpperCase().equals("TUE")) {
            dowb = 1;
        } else if (actFirstDayName.toUpperCase().equals("WED")) {
            dowb = 2;
        } else if (actFirstDayName.toUpperCase().equals("THU")) {
            dowb = 3;
        } else if (actFirstDayName.toUpperCase().equals("FRI")) {
            dowb = 4;
        } else if (actFirstDayName.toUpperCase().equals("SAT")) {
            dowb = 5;
        } else if (actFirstDayName.toUpperCase().equals("SUN")) {
            dowb = 6;
        }

        preYear=1;
        preMonth=0;
        preStart=1;
        preStop=0;
        postYear=0;
        postMonth=0;
        postStart=1;
        postStop=0;
        actStart = firstDay.getDayOfMonth()-dow;
        actStop = firstDay.getDayOfMonth()+6-dow;

        if(actDay-dow < 1) {  // there will also be days of the previous week
            if(firstDay.getMonthOfYear()==1) {
                preYear = firstDay.getYear()-1;
                preMonth = 12;
                preStart = (new DateTime(preYear, preMonth, 1, 0, 0, 0, 0)).dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth())-dowa+1;
                preStop = (new DateTime(preYear, preMonth, 1, 0, 0, 0, 0)).dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth());
            } else {
                preYear = firstDay.getYear();
                preMonth = firstDay.getMonthOfYear()-1;
                preStart = (new DateTime(preYear, preMonth, 1, 0, 0, 0, 0)).dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth())-dowa+1;
                preStop = (new DateTime(preYear, preMonth, 1, 0, 0, 0, 0)).dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth());
            }
            actStart = 1;
            actStop = actStart+6-dowa;
        } else if (actDay+(6-dow) > firstDay.dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth())) { // there will also be displayed days of the next month
            if(firstDay.getMonthOfYear()==12) {
                postYear = firstDay.getYear()+1;
                postMonth = 1;
                postStart = 1;
                postStop = 6-dow;
            } else {
                postYear = firstDay.getYear();
                postMonth = firstDay.getMonthOfYear()+1;
                postStart = 1;
                postStop = 6-dow;
            }
            actStop = firstDay.dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth());
            actStart = actStop-dowb;
        }

        int a=0;
        for(int i=preStart; i<=preStop; i++) {
            days[a].setDate(new DateTime(preYear, preMonth, i, 0, 0, 0, 0));
            days[a].setBackground(COLOR_OF_NOT_ACTUAL_MONTH);
            a++;
        }

        for(int i=actStart; i<=actStop; i++) {
            days[a].setDate(new DateTime(firstDay.getYear(), firstDay.getMonthOfYear(), i, 0, 0, 0, 0));
            days[a].setBackground(COLOR_OF_ACTUAL_MONTH);
            a++;
        }

        for(int i=postStart; i<=postStop; i++) {
            days[a].setDate(new DateTime(postYear, postMonth, i, 0, 0, 0, 0));
            days[a].setBackground(COLOR_OF_NOT_ACTUAL_MONTH);
            a++;
        }
    }

    @Override
    public void next() throws ServiceException {
        firstDay = firstDay.plusDays(7);
        setDays();
        setDates();
        repaint();
        revalidate();
    }

    @Override
    public void last() throws ServiceException {
        firstDay = firstDay.minusDays(7);
        setDays();
        setDates();
        repaint();
        revalidate();
    }

    @Override
    public String getTimeIntervalInfo() {
        /*
        return String s = firstDay.minusDays(firstDay.getDayOfWeek()-1).getDayOfMonth() + "." + firstDay.minusDays(firstDay.getDayOfWeek()-1).getMonthOfYear() + "." + firstDay.minusDays(firstDay.getDayOfWeek()-1).getYear()
                + " bis " +
                firstDay.plusDays(7-firstDay.getDayOfWeek()).getDayOfMonth() + "." + firstDay.plusDays(7-firstDay.getDayOfWeek()).getMonthOfYear() + "." + firstDay.plusDays(7-firstDay.getDayOfWeek()).getYear();
        */
        return firstDay.minusDays(firstDay.getDayOfWeek()-1).getDayOfMonth() + "." + firstDay.minusDays(firstDay.getDayOfWeek()-1).getMonthOfYear()
                + " bis " +
                firstDay.plusDays(7-firstDay.getDayOfWeek()).getDayOfMonth() + "." + firstDay.plusDays(7-firstDay.getDayOfWeek()).getMonthOfYear();
    }

    @Override
    public void semester() {
        // TODO
    }
}
