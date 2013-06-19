package at.ac.tuwien.sepm.entity;

import org.joda.time.DateTime;

public class TissExam {
    private int id;
    private String lvanr;
    private String name;
    private String mode;
    private DateTime startRegistration;
    private DateTime endRegistration;
    private TissExamState tissExamState;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getLvanr() {
        return lvanr;
    }

    public void setLvanr(String lvanr) {
        this.lvanr = lvanr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public DateTime getStartRegistration() {
        return startRegistration;
    }

    public void setStartRegistration(DateTime startRegistration) {
        this.startRegistration = startRegistration;
    }

    public DateTime getEndRegistration() {
        return endRegistration;
    }

    public void setEndRegistration(DateTime endRegistration) {
        this.endRegistration = endRegistration;
    }

    public TissExamState getTissExamState() {
        return tissExamState;
    }

    public void setTissExamState(TissExamState tissExamState) {
        this.tissExamState = tissExamState;
    }
}
