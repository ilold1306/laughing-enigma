package ru.ilold.ProductEntities;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Product {
    @Id
    private String name;
    @Size
    private String characteristics;
    private String photos;

    @OneToMany
    private List<ProductPhotos> productPhotos;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductInOrderBin> productInOrders;

    public List<ProductInOrderBin> getProductInOrders() {
        return productInOrders;
    }

    public void setProductInOrders(List<ProductInOrderBin> productInOrders) {
        this.productInOrders = productInOrders;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public List<ProductPhotos> getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(List<ProductPhotos> productPhotos) {
        this.productPhotos = productPhotos;
    }
}
