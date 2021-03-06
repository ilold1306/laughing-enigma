package ru.ilold.ProductEntities;


import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    private String name;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Product> productList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
