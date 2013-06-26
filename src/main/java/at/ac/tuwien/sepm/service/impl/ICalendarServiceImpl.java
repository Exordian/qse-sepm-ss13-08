package at.ac.tuwien.sepm.service.impl;

import at.ac.tuwien.sepm.dao.DateDao;
import at.ac.tuwien.sepm.dao.LvaDateDao;
import at.ac.tuwien.sepm.entity.DateEntity;
import at.ac.tuwien.sepm.entity.LvaDate;
import at.ac.tuwien.sepm.service.ICalendarService;
import at.ac.tuwien.sepm.service.SemesterDateGenerator;
import at.ac.tuwien.sepm.service.ServiceException;
import at.ac.tuwien.sepm.service.TimeFrame;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.property.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeFieldType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Markus MUTH
 */

@Service
public class ICalendarServiceImpl implements ICalendarService {
    private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());

    private static final String WRONG_MIME_TYPE = "Die angegebene Datei hat kein gültiges Kalender-Format.";
    private static final String FILE_DOES_NOT_EXIST = "Die Datei existiert nicht.";
    private static final String FILE_NOT_FOUND = "Der Kalender konnte nicht importiert werden.";
    private static final String COULD_NOT_IMPORT_CALENDAR = "Der Kalender konnte nicht importiert werden.";
    private static final String COULD_NOT_EXPORT_CALENDAR = "Der Kalender konnte nicht gespeichert werden.";
    private static final String COULD_NOT_CREATE_DATE_1 = "Beim speichern des Termins '";
    private static final String COULD_NOT_CREATE_DATE_2 = "' ist ein Fehler aufgetreten.\n";
    private static final String PRODID = "at.ac.tuwien.sepm.group08";

    @Autowired
    private LvaDateDao lvaDateDao;

    @Autowired
    private DateDao dateDao;

    @Override
    public int icalImport(File cal) throws ServiceException {
        iCalImportValidator(cal);

        FileInputStream fis;
        CalendarBuilder builder;
        Calendar calendar;

        try {
            fis = new FileInputStream(cal);
            builder = new CalendarBuilder();
            calendar = builder.build(fis);
        } catch (FileNotFoundException e) {
            logger.info("Calendar not found.", e);
            throw new ServiceException(COULD_NOT_IMPORT_CALENDAR + " Die Datei konnte nicht gefunden werden.", e);
        } catch (ParserException e) {
            logger.info("Calender could not be parsed", e);
            throw new ServiceException(COULD_NOT_IMPORT_CALENDAR + " Die Datei ist beschädigt.", e);
        } catch (IOException e) {
            logger.info("Calender could not be read.", e);
            throw new ServiceException(COULD_NOT_IMPORT_CALENDAR, e);
        }
        if(calendar==null) {
            logger.error("calendar==null");
            throw new ServiceException(COULD_NOT_IMPORT_CALENDAR);
        }

        int failureCounter = 0;
        int datesCreated = 0;
        ArrayList<DateEntity> dates = new ArrayList<>();
        for (Object o : calendar.getComponents()) {
            Component component = (Component) o;
            if (component.getName().equals("VEVENT")) {
                int a = addEvent(dates, (VEvent) component);
                if (a >= 0) {
                    datesCreated += a;
                } else {
                    failureCounter += Math.abs(a);
                }
            }
        }

        if(failureCounter!=0) {
            String is = "sind ";
            if(failureCounter==1) {
                is = "ist ";
            }
            throw new ServiceException(COULD_NOT_IMPORT_CALENDAR + "\nEs " + is + failureCounter + " Fehler aufgetreten");
        }

        for(DateEntity d : dates) {
            try {
                dateDao.create(d);
            } catch (IOException e) {
                throw new ServiceException(COULD_NOT_CREATE_DATE_1 + d.getName() + COULD_NOT_CREATE_DATE_2 + e.getMessage(), e);
            }
        }

        logger.info(datesCreated + " dates successful imported");
        debug1(dates);
        return datesCreated;
    }

    @Override
    public boolean icalExport(File cal) throws ServiceException {
        cal = iCalExportValidator(cal);

        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId(PRODID));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        int datesCreated = 0;

        TimeFrame timeFrame = SemesterDateGenerator.getTimeFrame();
        for(LvaDate d : lvaDateDao.readyByTimeframeAndStudyprogress(timeFrame, true)) {
            VEvent event = new VEvent(new net.fortuna.ical4j.model.DateTime(d.getTime().from().getMillis()), new net.fortuna.ical4j.model.DateTime(d.getTime().to().getMillis()), d.getName());
            calendar.getComponents().add(event);
            datesCreated++;
            if(d.getDescription()!=null) {
                event.getProperties().add(new Description(d.getDescription()));
            }
            event.getProperties().add(new Transp("OPAQUE"));
            event.getProperties().add(new Clazz("PRIVATE"));
            calendar.getComponents().add(event);
            datesCreated++;
        }
        for(DateEntity d : dateDao.readInTimeframe(timeFrame.from(), timeFrame.to())) {
            VEvent event = new VEvent(new net.fortuna.ical4j.model.DateTime(d.getTime().from().getMillis()), new net.fortuna.ical4j.model.DateTime(d.getTime().to().getMillis()), d.getName());
            if(d.getDescription()!=null) {
                event.getProperties().add(new Description(d.getDescription()));
            }
            event.getProperties().add(new Transp("OPAQUE"));
            event.getProperties().add(new Clazz("PRIVATE"));
            calendar.getComponents().add(event);
            datesCreated++;
        }

        try {
            FileOutputStream fos = new FileOutputStream(cal);
            CalendarOutputter copt = new CalendarOutputter();
            copt.setValidating(false);
            copt.output(calendar, fos);
        } catch (FileNotFoundException e) {
            logger.info("Calendar not found anymore.", e);
            throw new ServiceException(COULD_NOT_EXPORT_CALENDAR, e);
        } catch (IOException e) {
            logger.info("IOException at creating iCalendar file.", e);
            throw new ServiceException(COULD_NOT_EXPORT_CALENDAR, e);
        } catch (net.fortuna.ical4j.model.ValidationException e) {
            logger.info("ValidationException at creating iCalendar file.", e);
            throw new ServiceException(COULD_NOT_EXPORT_CALENDAR, e);
        }

        logger.info(datesCreated + " successful exported.");
        return true;
    }

    /*
     * Validates if the given file is valid or not.
     * @param f The file to be validated.
     * @param isExisting <code>true</code> if the file must be existing, <code>false</code> otherwise.
     * @throws ServiceException If if the file is existing if it should not, if the file is not existing if it should,
     * if the File does have the wrong mime type.
     */
    /*
    public void iCalFileValidator(File f, boolean isExisting) throws ServiceException {
        // TODO test this class properly
        if (f == null) {
            logger.info("no file found.");
            throw new ServiceException("Keine Datei gefunden.");
        }
        if(f.exists()==!isExisting && isExisting && f.isFile()) {
            logger.info("not existing file.");
            throw new ServiceException("Die angegebene Datei existiert nicht.");
        }
        if(f.exists()==!isExisting && !isExisting && f.isFile()) {
            logger.info("failure at storing the calendar.");
            throw new ServiceException("Fehler beim Speichern des Kalenders.");
        }

        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        String mimeType = mimetypesFileTypeMap.getContentType(f);
        if(!mimeType.equals("text/calendar")) {
            logger.info("wrong mime type. needed: 'text/calendar'\tgiven: " + mimeType);
            throw new ServiceException("Die ausgewählte Datei hat das falsche Format.");
        }
    }
    */

    private File iCalExportValidator(File f) throws ServiceException {
        if(f == null) {
            throw new ServiceException(FILE_NOT_FOUND);
        }
        String ext = FilenameUtils.getExtension(f.getName());
        if(ext.equals("") || !ext.equals("ics")) {
            String nr = "";
            int i=1;
            String path = FilenameUtils.getFullPath(f.getAbsolutePath());
            String name = FilenameUtils.getBaseName(f.getAbsolutePath());
            logger.info("path='" + path + "'\tname='" + name + "'");
            do {
                String s = "file '" + f.getAbsolutePath() + "' renamed to '";
                f = new File(path + name + nr + ".ics");
                s += f.getAbsolutePath() + "'";
                logger.info(s);
                i++;
                nr = "" + i;
            } while(f.exists());
        }
        return f;
    }

    private void iCalImportValidator(File f) throws ServiceException {
        if(f == null) {
            throw new ServiceException(FILE_NOT_FOUND);
        } else if (!f.exists()) {
            throw new ServiceException(FILE_DOES_NOT_EXIST);
        } else if (!f.isFile()) {
            throw new ServiceException(FILE_NOT_FOUND);
        }

        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        mimetypesFileTypeMap.addMimeTypes("text/calendar ics iCal ifb iFBf ical");
        String mimeType = mimetypesFileTypeMap.getContentType(f);

        if (!mimeType.equals("text/calendar")) {
            throw new ServiceException(WRONG_MIME_TYPE);
        }
    }


    private int addEvent(ArrayList<DateEntity> dates, VEvent ev) {
        if(dates == null || ev == null) {
            logger.info("could not add event '" +  ev.getSummary().getValue() + "'\taddEvent 0");
            return -1;
        }

        // read all important values
        DtStart evstart = ev.getStartDate();
        DtEnd evend = ev.getEndDate();
        Summary evsummary = ev.getSummary();
        if(evstart == null || evend == null || evsummary == null || evstart.getDate() == null || evend.getDate() == null) {
            logger.info("could not add event '" + ev.getSummary().getValue() + "'\taddEvent 1");
            return -1;
        }
        Description evdescription = ev.getDescription();
        Transp evtransp = ev.getTransparency();
        Property evrruleProperty = ev.getProperty("RRULE");
        RRule evrrule = null;
        if (evrruleProperty instanceof RRule) {
            evrrule = (RRule) evrruleProperty;
        }

        // create generic values
        DateTime start = new DateTime(evstart.getDate().getTime());
        DateTime stop = new DateTime(evend.getDate().getTime());
        boolean intersectable = false;
        if (evtransp != null && evtransp.getValue().equals(Transp.TRANSPARENT.getValue())) {
            intersectable = true;
        }
        String description = "";
        if(ev.getDescription() != null) {
            description = ev.getDescription().getValue();
        }

        // set times if event is a all day event
        if(!(evstart.getDate() instanceof net.fortuna.ical4j.model.DateTime) && !(evstart.getDate() instanceof net.fortuna.ical4j.model.DateTime)) {
            start = new DateTime(start.getYear(),
                    start.getMonthOfYear(),
                    start.getDayOfMonth(),
                    start.hourOfDay().withMinimumValue().get(DateTimeFieldType.hourOfDay()),
                    start.minuteOfHour().withMinimumValue().get(DateTimeFieldType.minuteOfHour()),
                    start.secondOfMinute().withMinimumValue().get(DateTimeFieldType.secondOfMinute()),
                    start.millisOfSecond().withMinimumValue().get(DateTimeFieldType.millisOfSecond()));

            stop = new DateTime(stop.minusDays(1).getYear(),
                    stop.minusDays(1).getMonthOfYear(),
                    stop.minusDays(1).getDayOfMonth(),
                    stop.minusDays(1).hourOfDay().withMaximumValue().get(DateTimeFieldType.hourOfDay()),
                    stop.minusDays(1).minuteOfHour().withMaximumValue().get(DateTimeFieldType.minuteOfHour()),
                    stop.minusDays(1).secondOfMinute().withMaximumValue().get(DateTimeFieldType.secondOfMinute()),
                    stop.minusDays(1).millisOfSecond().withMaximumValue().get(DateTimeFieldType.millisOfSecond()));
            /*
            logger.info("'" + evsummary.getValue() + "'\tis a all day event\t" + start.getYear() + "-" + start.getMonthOfYear() + "-" + start.getDayOfMonth() + " " + start.getHourOfDay() + ":" + start.getMinuteOfHour() + ":" + start.getSecondOfMinute() + "." + start.getMillisOfSecond() +
                    "\t" + stop.getYear() + "-" + stop.getMonthOfYear() + "-" + stop.getDayOfMonth() + " " + stop.getHourOfDay() + ":" + stop.getMinuteOfHour() + ":" + stop.getSecondOfMinute() + "." + stop.getMillisOfSecond());
            */
        }
        TimeFrame timeFrame = new TimeFrame(start, stop);

        int succesfulDates = 1;
        // if the event represents a series
        if (evrrule != null) {
            if (evdescription == null)
            succesfulDates = addSeries(dates, evrrule, evsummary.getValue(), description, ev.getStartDate().getDate(), timeFrame, intersectable);
        } else { // if the event is a single event
            DateEntity entity = new DateEntity();
            entity.setName(evsummary.getValue());
            entity.setTime(timeFrame);
            entity.setDescription(description);
            entity.setIntersectable(intersectable);
            dates.add(entity);
        }

        logger.info(succesfulDates + " date(s) created\t0");
        return succesfulDates;
    }

    private int addSeries(ArrayList<DateEntity> dates, RRule rule, String name, String description, Date baseDate, TimeFrame time, boolean intersectable) {
        debug3(dates, rule, name, description, baseDate, time, intersectable);

        net.fortuna.ical4j.model.DateTime until;

        if(rule.getRecur().getUntil()==null) {
            if (rule.getRecur().getCount() >= 0) { // the duration of the series is represented by a max amount of single events
                long duration = 0;
                if (rule.getRecur().getFrequency().equals(Recur.SECONDLY)) {
                    duration = DateTimeConstants.MILLIS_PER_SECOND;
                } else if (rule.getRecur().getFrequency().equals(Recur.MINUTELY)) {
                    duration = DateTimeConstants.MILLIS_PER_MINUTE;
                } else if (rule.getRecur().getFrequency().equals(Recur.HOURLY)) {
                    duration = DateTimeConstants.MILLIS_PER_HOUR;
                } else if (rule.getRecur().getFrequency().equals(Recur.DAILY)) {
                    duration = DateTimeConstants.MILLIS_PER_DAY;
                } else if (rule.getRecur().getFrequency().equals(Recur.WEEKLY)) {
                    duration = DateTimeConstants.MILLIS_PER_WEEK;
                } else if (rule.getRecur().getFrequency().equals(Recur.MONTHLY)) {
                    duration = DateTimeConstants.MILLIS_PER_DAY*31;
                } else if (rule.getRecur().getFrequency().equals(Recur.YEARLY)) {
                    duration = DateTimeConstants.MILLIS_PER_DAY*366;
                }
                debug2(rule);
                until = new net.fortuna.ical4j.model.DateTime(baseDate.getTime() + (time.to().getMillis() - time.from().getMillis()) + 100 + (rule.getRecur().getCount() * duration));
            } else if (rule.getRecur().getFrequency() != null) {
                logger.info("could not add series '" +  name + "'\taddSeries 0");
                return -1;
            } else { // the way, how the duration of the series is represented, is not provided by this method
                logger.info("could not add series '" +  name + "'\taddSeries 1");
                return -1;
            }
        } else { // the duration of the series is represented by a start and stop date
            DateTime dt = new DateTime(rule.getRecur().getUntil().getTime());
            until = new net.fortuna.ical4j.model.DateTime(dt.getMillis() + time.to().getMillis() - time.from().getMillis());
        }

        // get all single events represented by this series
        DateList dateList = rule.getRecur().getDates(baseDate,
                new Period(new net.fortuna.ical4j.model.DateTime(baseDate), until),
                Value.DATE_TIME);

        // create all DateEntitys
        Iterator<Date> iterator = dateList.iterator();
        while(iterator.hasNext()) {
            long start = iterator.next().getTime();
            long stop = start + (time.to().getMillis() - time.from().getMillis());
            DateEntity entity = new DateEntity();
            entity.setName(name);
            entity.setDescription(description);
            entity.setTime(new TimeFrame(new DateTime(start), new DateTime(stop)));
            entity.setIntersectable(intersectable);
            dates.add(entity);
        }

        return dateList.size();
    }

    private void debug1(ArrayList<DateEntity> dates) {
        String s = "all " + dates.size() + " imported dates:";
        int i=0;
        for(DateEntity d : dates) {
            s += "\n\t\t(" + i + ")\t" + d;
            i++;
        }
        logger.info(s);
    }

    private void debug2(RRule rule) {
        String debug2 = "series will be calculated by 'COUNT'";
        if (rule.getRecur().getFrequency().equals(Recur.SECONDLY)) {
            debug2 += "\n\t\tfrequency=" + "SECONDLY";
        } else if (rule.getRecur().getFrequency().equals(Recur.MINUTELY)) {
            debug2 += "\n\t\tfrequency=" + "MINUTELY";
        } else if (rule.getRecur().getFrequency().equals(Recur.HOURLY)) {
            debug2 += "\n\t\tfrequency=" + "HOURLY";
        } else if (rule.getRecur().getFrequency().equals(Recur.DAILY)) {
            debug2 += "\n\t\tfrequency=" + "DAILY";
        } else if (rule.getRecur().getFrequency().equals(Recur.WEEKLY)) {
            debug2 += "\n\t\tfrequency=" + "WEEKLY";
        } else if (rule.getRecur().getFrequency().equals(Recur.MONTHLY)) {
            debug2 += "\n\t\tfrequency=" + "MONTHLY";
        } else if (rule.getRecur().getFrequency().equals(Recur.YEARLY)) {
            debug2 += "\n\t\tfrequency=" + "YEARLY";
        }
        NumberList secondList = rule.getRecur().getSecondList();
        NumberList minuteList = rule.getRecur().getMinuteList();
        NumberList hourList = rule.getRecur().getHourList();
        WeekDayList dayList = rule.getRecur().getDayList();
        NumberList weekNoList = rule.getRecur().getWeekNoList();
        NumberList monthDayList = rule.getRecur().getMonthDayList();
        NumberList monthList = rule.getRecur().getMonthList();
        NumberList yearDayList =  rule.getRecur().getYearDayList();
        NumberList setPosList = rule.getRecur().getSetPosList();
        debug2 += "\n\t\t(" + secondList.size() + ") secondList=" + secondList;
        debug2 += "\n\t\t(" + minuteList.size() + ") minuteList=" + minuteList;
        debug2 += "\n\t\t(" + hourList.size() + ") hourList=" + hourList;
        debug2 += "\n\t\t(" + dayList.size() + ") dayList=" + dayList;
        debug2 += "\n\t\t(" + weekNoList.size() + ") weekNoList=" + weekNoList;
        debug2 += "\n\t\t(" + monthDayList.size() + ") monthDayList=" + monthDayList;
        debug2 += "\n\t\t(" + monthList.size() + ") monthList=" + monthList;
        debug2 += "\n\t\t(" + yearDayList.size() + ") yearDayList=" + yearDayList;
        debug2 += "\n\t\t(" + setPosList.size() + ") setPosList=" + setPosList;
        logger.info(debug2);
    }

    private void debug3(ArrayList<DateEntity> dates, RRule rule, String name, String description, Date baseDate, TimeFrame time, boolean intersectable) {
        String debug3 = "trying to generate series ... name='" + name + "'\n";
        debug3 += "\t\tdates==null\t\t\t\t\t\t\t" +     (dates==null) + "\n";
        debug3 += "\t\trule==null\t\t\t\t\t\t\t" +      (rule==null) + "\n";
        debug3 += "\t\trule.getRecur()==null\t\t\t\t" + (rule.getRecur()==null) + "\n";
        debug3 += "\t\trule.getRecur().getUntil()==null\t" + (rule.getRecur().getUntil()==null) + "\n";
        debug3 += "\t\tname==null\t\t\t\t\t\t\t" +      (name==null) + "\n";
        debug3 += "\t\tdescription==null\t\t\t\t\t" +   (description==null) + "\n";
        debug3 += "\t\tbaseDate==null\t\t\t\t\t\t" +    (baseDate==null) + "\n";
        debug3 += "\t\ttime==null\t\t\t\t\t\t\t" +      (time==null);
        logger.info(debug3);
    }
}
