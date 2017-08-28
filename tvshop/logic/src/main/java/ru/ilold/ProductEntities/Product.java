package ru.ilold.ProductEntities;


import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String characteristics;
    private String photos;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
