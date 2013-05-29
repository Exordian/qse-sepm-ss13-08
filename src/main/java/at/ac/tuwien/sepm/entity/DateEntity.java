package at.ac.tuwien.sepm.entity;

import at.ac.tuwien.sepm.service.TimeFrame;
import org.joda.time.DateTime;

/**
 * @author Markus MUTH
 */
public class DateEntity implements Date {
    private Integer id;
    private String name;
    private String description;
    private Boolean isIntersectable;
    //private DateTime start;
    //private DateTime stop;
    private TimeFrame time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getIntersectable() {
        return isIntersectable;
    }

    public void setIntersectable(Boolean intersectable) {
        isIntersectable = intersectable;
    }

    /*
    public DateTime getStart() {
        return start;
    }

    public void setStart(int year, int month, int day, int hour, int minute, int second) {
        this.start = new DateTime(year, month, day, hour, minute, second);
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getStop() {
        return stop;
    }

    public void setStop(int year, int month, int day, int hour, int minute, int second) {
        this.stop = new DateTime(year, month, day, hour, minute, second);
    }

    public void setStop(DateTime stop) {
        this.stop = stop;
    }
    */

    public TimeFrame getTime() {
        return time;
    }

    public void setTime(TimeFrame time) {
        this.time = time;
    }

    /*
    public void setStart(DateTime start) {
        this.time.setFrom(start);
    }
    */

    @Override
    public DateTime getStart() {
        return time.from();
    }

    /*
    public void setStop(DateTime stop) {
        time.setTo(stop);
    }
    */
    @Override
    public DateTime getStop() {
        return time.from();
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isIntersectable=" + isIntersectable +
                ", start=" + time.to() +
                ", stop=" + time.from() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateEntity that = (DateEntity) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isIntersectable != null ? !isIntersectable.equals(that.isIntersectable) : that.isIntersectable != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (time.to() != null ? !time.to().equals(that.time.to()) : that.time.to() != null) return false;
        if (time.from() != null ? !time.from().equals(that.time.from()) : that.time.from() != null) return false;

        return true;
    }
}
