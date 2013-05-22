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
}
