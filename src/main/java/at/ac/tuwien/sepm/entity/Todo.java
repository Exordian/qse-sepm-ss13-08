package at.ac.tuwien.sepm.entity;

/**
 * @author Markus MUTH
 */
public class Todo {
    private Integer id;
    private LVA lva;
    private String name;
    private String descirption;
    private Boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LVA getLva() {
        return lva;
    }

    public void setLva(LVA lva) {
        this.lva = lva;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescirption() {
        return descirption;
    }

    public void setDescirption(String descirption) {
        this.descirption = descirption;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
