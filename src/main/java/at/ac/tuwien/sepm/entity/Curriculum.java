package at.ac.tuwien.sepm.entity;

import java.util.HashMap;

/**
 * @author Markus MUTH
 */
public class Curriculum {
    private Integer id;
    private String studyNumber;
    private String name;
    private String description;
    private String academicTitle;
    private Integer ectsChoice;
    private Integer ectsFree;
    private Integer ectsSoftskill;
    private HashMap<Module, Boolean> modules;

    public HashMap<Module, Boolean> getModules() {
        return modules;
    }

    public void setModules(HashMap<Module, Boolean> modules) {
        this.modules = modules;
    }

    public void setEctsSoftskill(Integer ectsSoftskill) {
        this.ectsSoftskill = ectsSoftskill;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStudyNumber(String studyNumber) {
        this.studyNumber = studyNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }

    public void setEctsChoice(Integer ectsChoice) {
        this.ectsChoice = ectsChoice;
    }

    public void setEctsFree(Integer ectsFree) {
        this.ectsFree = ectsFree;
    }

    public Integer getId() {
        return id;
    }

    public String getStudyNumber() {
        return studyNumber;
    }

    public String getName() {
        return name;
    }

    public Integer getEctsChoice() {
        return ectsChoice;
    }

    public Integer getEctsSoftskill() {
        return ectsSoftskill;
    }

    public Integer getEctsFree() {
        return ectsFree;
    }

    public String getAcademicTitle() {
        return academicTitle;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Curriculum that = (Curriculum) o;

        if (academicTitle != null ? !academicTitle.equals(that.academicTitle) : that.academicTitle != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (ectsChoice != null ? !ectsChoice.equals(that.ectsChoice) : that.ectsChoice != null) return false;
        if (ectsFree != null ? !ectsFree.equals(that.ectsFree) : that.ectsFree != null) return false;
        if (ectsSoftskill != null ? !ectsSoftskill.equals(that.ectsSoftskill) : that.ectsSoftskill != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (studyNumber != null ? !studyNumber.equals(that.studyNumber) : that.studyNumber != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Curriculum{" +
                "ectsSoftskill=" + ectsSoftskill +
                ", id=" + id +
                ", studyNumber='" + studyNumber + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", academicTitle='" + academicTitle + '\'' +
                ", ectsChoice=" + ectsChoice +
                ", ectsFree=" + ectsFree +
                '}';
    }
}
