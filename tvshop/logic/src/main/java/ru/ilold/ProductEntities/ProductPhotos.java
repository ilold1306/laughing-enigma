package ru.ilold.ProductEntities;


import javax.persistence.*;

@Entity
public class ProductPhotos {
    @Id
    private String path;

    @ManyToOne
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
