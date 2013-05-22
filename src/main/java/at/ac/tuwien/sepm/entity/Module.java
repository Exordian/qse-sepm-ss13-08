package at.ac.tuwien.sepm.entity;

import java.util.ArrayList;

/**
 * @author Markus MUTH
 */
public class Module {
    private Integer id;
    private String name;
    private String description;
    private Boolean completeall;
    private ArrayList<MetaLVA> metaLvas;

    public ArrayList<MetaLVA> getMetaLvas() {
        return metaLvas;
    }

    public void setMetaLvas(ArrayList<MetaLVA> metaLvas) {
        this.metaLvas = metaLvas;
    }

    public boolean isCompleteall() {
        return completeall;
    }

    public void setCompleteall(boolean completeall) {
        this.completeall = completeall;
    }

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

    public Boolean getCompleteall() {
        return completeall;
    }

    public void setCompleteall(Boolean completeall) {
        this.completeall = completeall;
    }
}
