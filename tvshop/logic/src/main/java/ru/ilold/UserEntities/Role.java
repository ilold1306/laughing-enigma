package ru.ilold.UserEntities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RoleEntity")
public class Role {
    @Id
    private String code;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Pravo> pravosList;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CredentialsRole> credentialsRoles;

    public List<Pravo> getPravosList() {
        return pravosList;
    }

    public void setPravosList(List<Pravo> pravosList) {
        this.pravosList = pravosList;
    }

    public List<CredentialsRole> getCredentialsRoles() {
        return credentialsRoles;
    }

    public void setCredentialsRoles(List<CredentialsRole> credentialsRoles) {
        this.credentialsRoles = credentialsRoles;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
