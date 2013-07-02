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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TissExam)) return false;

        TissExam tissExam = (TissExam) o;

        if (id != tissExam.id) return false;
        if (endRegistration != null ? !endRegistration.equals(tissExam.endRegistration) : tissExam.endRegistration != null)
            return false;
        if (lvanr != null ? !lvanr.equals(tissExam.lvanr) : tissExam.lvanr != null) return false;
        if (mode != null ? !mode.equals(tissExam.mode) : tissExam.mode != null) return false;
        if (name != null ? !name.equals(tissExam.name) : tissExam.name != null) return false;
        if (startRegistration != null ? !startRegistration.equals(tissExam.startRegistration) : tissExam.startRegistration != null)
            return false;
        if (tissExamState != tissExam.tissExamState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (lvanr != null ? lvanr.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mode != null ? mode.hashCode() : 0);
        result = 31 * result + (startRegistration != null ? startRegistration.hashCode() : 0);
        result = 31 * result + (endRegistration != null ? endRegistration.hashCode() : 0);
        result = 31 * result + (tissExamState != null ? tissExamState.hashCode() : 0);
        return result;
    }
}
