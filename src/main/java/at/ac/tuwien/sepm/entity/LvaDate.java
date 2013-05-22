package at.ac.tuwien.sepm.entity;

import org.joda.time.DateTime;

/**
 * @author Markus MUTH
 */
public class LvaDate {

    private Integer id;
    private Integer lva;
    private String name;
    private String description;
    private LvaDateType type;
    private String room;
    private Integer result;
    private DateTime start;
    private DateTime stop;
    private Boolean attendanceRequired;
    private Boolean wasAttendant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLva() {
        return lva;
    }

    public void setLva(Integer lva) {
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

    public LvaDateType getType() {
        return type;
    }

    public void setType(LvaDateType type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

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

    public Boolean getAttendanceRequired() {
        return attendanceRequired;
    }

    public void setAttendanceRequired(Boolean attendanceRequired) {
        this.attendanceRequired = attendanceRequired;
    }

    public Boolean getWasAttendant() {
        return wasAttendant;
    }

    public void setWasAttendant(Boolean wasAttendant) {
        this.wasAttendant = wasAttendant;
    }
}
