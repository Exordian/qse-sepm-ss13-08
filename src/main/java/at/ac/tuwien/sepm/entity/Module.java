package at.ac.tuwien.sepm.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Markus MUTH
 */
public class Module {
    private Integer id;
    private String name;
    private String description;
    private Boolean completeall;
    private List<MetaLVA> metaLvas;

    public List<MetaLVA> getMetaLvas() {
        return metaLvas;
    }

    public void setMetaLvas(List<MetaLVA> metaLvas) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        if (completeall != null ? !completeall.equals(module.completeall) : module.completeall != null) return false;
        if (description != null ? !description.equals(module.description) : module.description != null) return false;
        if (id != null ? !id.equals(module.id) : module.id != null) return false;
        if (name != null ? !name.equals(module.name) : module.name != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", completeall=" + completeall +
                '}';
    }
}
