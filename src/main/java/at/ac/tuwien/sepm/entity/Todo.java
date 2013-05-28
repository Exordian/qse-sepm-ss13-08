package at.ac.tuwien.sepm.entity;

/**
 * @author Markus MUTH
 */
public class Todo {
    private Integer id;
    private LVA lva;
    private String name;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (description != null ? !description.equals(todo.description) : todo.description != null) return false;
        if (done != null ? !done.equals(todo.done) : todo.done != null) return false;
        if (id != null ? !id.equals(todo.id) : todo.id != null) return false;
        if (name != null ? !name.equals(todo.name) : todo.name != null) return false;

        return true;
    }
}
