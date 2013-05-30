package at.ac.tuwien.sepm.entity;

import at.ac.tuwien.sepm.service.TimeFrame;
import org.joda.time.DateTime;

/**
 * @author Markus MUTH
 */
public class LvaDate implements Date {

    private Integer id;
    private Integer lva;
    private String name;
    private String description;
    private LvaDateType type;
    private String room;
    private Integer result;
    //private DateTime start;
    //private DateTime stop;
    private TimeFrame time;
    private Boolean attendanceRequired;
    private Boolean wasAttendant;

    public LvaDate(){
    }

    public LvaDate(TimeFrame time){
        setTime(time);
    }

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
        this.time.setFrom(stop);
    }
    */
    @Override
    public DateTime getStop() {
        return time.to();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LvaDate lvaDate = (LvaDate) o;

        if (attendanceRequired != null ? !attendanceRequired.equals(lvaDate.attendanceRequired) : lvaDate.attendanceRequired != null)
            return false;
        if (description != null ? !description.equals(lvaDate.description) : lvaDate.description != null) return false;
        if (id != null ? !id.equals(lvaDate.id) : lvaDate.id != null) return false;
        if (lva != null ? !lva.equals(lvaDate.lva) : lvaDate.lva != null) return false;
        if (name != null ? !name.equals(lvaDate.name) : lvaDate.name != null) return false;
        if (result != null ? !result.equals(lvaDate.result) : lvaDate.result != null) return false;
        if (room != null ? !room.equals(lvaDate.room) : lvaDate.room != null) return false;
        if (time != null ? !time.equals(lvaDate.time) : lvaDate.time != null) return false;
        //if (stop != null ? !stop.equals(lvaDate.stop) : lvaDate.stop != null) return false;
        if (type != lvaDate.type) return false;
        if (wasAttendant != null ? !wasAttendant.equals(lvaDate.wasAttendant) : lvaDate.wasAttendant != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "LvaDate{" +
                "id=" + id +
                ", lva=" + lva +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", room='" + room + '\'' +
                ", result=" + result +
                ", start=" + time.from() +
                ", stop=" + time.to() +
                ", attendanceRequired=" + attendanceRequired +
                ", wasAttendant=" + wasAttendant +
                '}';
    }
}
