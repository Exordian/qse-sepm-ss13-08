package at.ac.tuwien.sepm.entity;

/**
 * @author Markus MUTH
 */
public class InCurriculum {
    private Integer curriculum;
    private Integer module;
    private Boolean olbigatory;

    public Integer getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(Integer curriculum) {
        this.curriculum = curriculum;
    }

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public Boolean getOlbigatory() {
        return olbigatory;
    }

    public void setOlbigatory(Boolean olbigatory) {
        this.olbigatory = olbigatory;
    }
}
