package ru.ilold.ProductEntities;


import ru.ilold.UserEntities.OrderBin;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

@Entity
public class ProductInOrderBin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @DefaultValue("1")
    private long quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    private OrderBin bin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderBin getBin() {
        return bin;
    }

    public void setBin(OrderBin bin) {
        this.bin = bin;
    }
}
