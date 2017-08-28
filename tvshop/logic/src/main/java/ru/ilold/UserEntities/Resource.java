package ru.ilold.UserEntities;


import javax.persistence.*;
import java.util.List;

@Entity
public class Resource {
    @Id
    private String id;

    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Pravo> resourcesList;

    public List<Pravo> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(List<Pravo> resourcesList) {
        this.resourcesList = resourcesList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
