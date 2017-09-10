package ru.ilold.UserEntities;


import ru.ilold.ProductEntities.ProductInOrderBin;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import java.util.List;

@Entity
public class OrderBin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @OneToMany(mappedBy = "bin")
    private List<ProductInOrderBin> productInOrders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProductInOrderBin> getProductInOrders() {
        return productInOrders;
    }

    public void setProductInOrders(List<ProductInOrderBin> productInOrders) {
        this.productInOrders = productInOrders;
    }
}
