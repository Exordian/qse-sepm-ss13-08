package at.ac.tuwien.sepm.service;

import at.ac.tuwien.sepm.service.Semester;
import at.ac.tuwien.sepm.service.TimeFrame;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;

/**
 * @author Markus MUTH
 */

public class SemesterDateGenerator {

    private static Logger logger = LogManager.getLogger(SemesterDateGenerator.class.getSimpleName());
    private static final int WINTER_SEMESTER_START_MONTH = 10;
    private static final int WINTER_SEMESTER_STOP_MONTH = 2;
    private static final int SOMMER_SEMESTER_START_MONTH = 3;
    private static final int SOMMER_SEMESTER_STOP_MONTH = 7;

    /**
     * Return the start and stop day of the specified semester as a TimeFrame. The summer semester goes from
     * <code>year</code>-03-01 till <code>year</code>-07-31. The winter semester goes from <code>year</code>-10-01 till
     * <code>(year+1)</code>-02-[28|29].
     * @param year The year.
     * @param s The Semester. Must be one of <code>Semester.S</code> or <code>Semester.W</code>
     * @return A <code>TimeFrame</code> containing the start- and stop date of the specified semester or <code>null</code>
     * the specified Semester is invalid.
     */

    public static TimeFrame getTimeFrame(int year, Semester s) {
        DateTime start;
        DateTime stop;

        if(s.equals(Semester.S)) {
            start = new DateTime(year, SOMMER_SEMESTER_START_MONTH, 1, 0, 0);
            stop = new DateTime(year, SOMMER_SEMESTER_STOP_MONTH, 1, 0, 0);
        } else if(s.equals(Semester.W)) {
            start = new DateTime(year, WINTER_SEMESTER_START_MONTH, 1, 0, 0);
            stop = new DateTime(year+1, WINTER_SEMESTER_STOP_MONTH, 1, 0, 0);
        } else {
            return null;
        }

        start = new DateTime(start.getYear(),
                start.getMonthOfYear(),
                start.dayOfMonth().withMinimumValue().get(DateTimeFieldType.dayOfMonth()),
                start.hourOfDay().withMinimumValue().get(DateTimeFieldType.hourOfDay()),
                start.minuteOfHour().withMinimumValue().get(DateTimeFieldType.minuteOfHour()),
                start.secondOfMinute().withMinimumValue().get(DateTimeFieldType.secondOfMinute()),
                start.millisOfSecond().withMinimumValue().get(DateTimeFieldType.millisOfSecond()));

        stop = new DateTime(stop.getYear(),
                stop.getMonthOfYear(),
                stop.dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth()),
                stop.hourOfDay().withMaximumValue().get(DateTimeFieldType.hourOfDay()),
                stop.minuteOfHour().withMaximumValue().get(DateTimeFieldType.minuteOfHour()),
                stop.secondOfMinute().withMaximumValue().get(DateTimeFieldType.secondOfMinute()),
                stop.millisOfSecond().withMaximumValue().get(DateTimeFieldType.millisOfSecond()));

        return new TimeFrame(start, stop);
    }

    /**
     * Return the start and stop day of the specified semester where the given date is in as a TimeFrame. The summer
     * semester goes from <code>year</code>-03-01 till <code>year</code>-07-31. The winter semester goes from
     * <code>year</code>-10-01 till <code>(year+1)</code>-02-[28|29].
     * @param date Any date of the semester.
     * @return A <code>TimeFrame</code> containing the start- and stop date of the specified semester or <code>null</code>
     * the specified Semester is invalid or the given date is <code>null</code> or the given date is not in range of a
     * semester.
     */
    public static TimeFrame getTimeFrame(DateTime date) {
        if(date == null) {
            return null;
        }

        int yearStart = date.getYear();
        int yearStop = 0;
        int monthStart = date.getMonthOfYear();
        int monthStop=0;

        if(monthStart==1 || monthStart==2) { // winter semester
            yearStop = yearStart;
            yearStart = yearStop - 1;
            monthStart = WINTER_SEMESTER_START_MONTH;
            monthStop = WINTER_SEMESTER_STOP_MONTH;
        } else if(monthStart == 10 || monthStart == 11 || monthStart == 12) { // winter semester
            yearStop = yearStart + 1;
            monthStart = WINTER_SEMESTER_START_MONTH;
            monthStop = WINTER_SEMESTER_STOP_MONTH;
        } else if (monthStart == 3 || monthStart == 4 || monthStart == 5 || monthStart == 6 || monthStart == 7) { // summer semester
            yearStop = yearStart;
            monthStart = SOMMER_SEMESTER_START_MONTH;
            monthStop = SOMMER_SEMESTER_STOP_MONTH;
        } else {
            return null;
        }

        DateTime start = new DateTime(yearStart, monthStart, 1, 0, 0);
        DateTime stop = new DateTime(yearStop, monthStop, 1, 0, 0);

        start = new DateTime(start.getYear(),
                start.getMonthOfYear(),
                start.dayOfMonth().withMinimumValue().get(DateTimeFieldType.dayOfMonth()),
                start.hourOfDay().withMinimumValue().get(DateTimeFieldType.hourOfDay()),
                start.minuteOfHour().withMinimumValue().get(DateTimeFieldType.minuteOfHour()),
                start.secondOfMinute().withMinimumValue().get(DateTimeFieldType.secondOfMinute()),
                start.millisOfSecond().withMinimumValue().get(DateTimeFieldType.millisOfSecond()));

        stop = new DateTime(stop.getYear(),
                stop.getMonthOfYear(),
                stop.dayOfMonth().withMaximumValue().get(DateTimeFieldType.dayOfMonth()),
                stop.hourOfDay().withMaximumValue().get(DateTimeFieldType.hourOfDay()),
                stop.minuteOfHour().withMaximumValue().get(DateTimeFieldType.minuteOfHour()),
                stop.secondOfMinute().withMaximumValue().get(DateTimeFieldType.secondOfMinute()),
                stop.millisOfSecond().withMaximumValue().get(DateTimeFieldType.millisOfSecond()));

        return new TimeFrame(start, stop);
    }

    /**
     * Calls esterDateGenerator.getTimeFrame(DateTime date) with the actual date.
     * @return @see SemesterDateGenerator.getTimeFrame(DateTime date).
     */
    public static TimeFrame getTimeFrame() {
        return getTimeFrame(DateTime.now());
    }
}
