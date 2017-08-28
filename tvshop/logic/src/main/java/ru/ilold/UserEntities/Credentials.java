package ru.ilold.UserEntities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Credentials {
    @Id
    private String email;
    private int passwordHash;
    private int accessType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "credentials")
    private List<CredentialsRole> rolesList;

    @OneToOne(mappedBy = "credentials")
    private User user;

    @OneToOne(mappedBy = "credentials")
    private OrderBin orderBin;

    public List<CredentialsRole> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<CredentialsRole> rolesList) {
        this.rolesList = rolesList;
    }

    public OrderBin getOrderBin() {
        return orderBin;
    }

    public void setOrderBin(OrderBin orderBin) {
        this.orderBin = orderBin;
    }

    public int getAccessType() {
        return accessType;
    }

    public void setAccessType(int accessType) {
        this.accessType = accessType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }
}
