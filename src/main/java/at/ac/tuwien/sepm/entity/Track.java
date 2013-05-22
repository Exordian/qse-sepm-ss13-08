package at.ac.tuwien.sepm.entity;

import org.joda.time.DateTime;

/**
 * @author Markus MUTH
 */
public class Track {
    private Integer id;
    private LVA lva;
    private String name;
    private String description;
    private DateTime start;
    private DateTime stop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LVA getLva() {
        return lva;
    }

    public void setLva(LVA lva) {
        this.lva = lva;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(int year, int month, int day, int hour, int minute, int second) {
        start = new DateTime(year, month, day, hour, minute, second);
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getStop() {
        return stop;
    }

    public void setStop(int year, int month, int day, int hour, int minute, int second) {
        stop = new DateTime(year, month, day, hour, minute, second);
    }

    public void setStop(DateTime stop) {
        this.stop = stop;
    }
}
