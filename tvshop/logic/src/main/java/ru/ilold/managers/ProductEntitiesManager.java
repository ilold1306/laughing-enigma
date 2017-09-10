package ru.ilold.managers;


import ru.ilold.ProductEntities.Category;
import ru.ilold.ProductEntities.Product;
import ru.ilold.ProductEntities.ProductPhotos;
import ru.ilold.etc.StatusMessage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class ProductEntitiesManager {
    @PersistenceContext(unitName = "tvshopPU")
    private EntityManager entityManager;
    public StatusMessage createCategory(String name) {
        Category category = entityManager.find(Category.class, name);
        if(category != null) {
            return new StatusMessage(false, "Category " + name + " already exists!", category);
        }
        category = new Category();
        category.setName(name);
        entityManager.persist(category);
        return new StatusMessage(true, "Success", category);
    }

    public StatusMessage createProduct(String categoryName, Product product, ProductPhotos productPhotos) {
        Category category = entityManager.find(Category.class, categoryName);
        if(category == null) {
            return new StatusMessage(false, "Category " + categoryName + " does not exist");
        }
        product.setCategory(category);
        productPhotos.setProduct(product);
        entityManager.persist(product);
        entityManager.persist(productPhotos);
        return new StatusMessage(true, "Product was created", product);
    }

    public StatusMessage removeCategory(String categoryName) {
        Category category = entityManager.find(Category.class, categoryName);
        if(category == null) {
            return new StatusMessage(false, "Category " + categoryName + " does not exist");
        }

        entityManager.remove(category);
        return new StatusMessage(true, "Category " + categoryName + " was removed");
    }

    public StatusMessage removeProduct(Product product) {
        if(entityManager.find(Product.class, product.getName()) == null) {
            return new StatusMessage(false, "This product does not exist");
        }

        entityManager.remove(product);
        return new StatusMessage(true, "Product " + product.getName() + " was removed");
    }

    public StatusMessage removeProduct(String productId) {
        Product product = entityManager.find(Product.class, productId);
        if(product == null) {

            return new StatusMessage(false, "Product does not exist!");
        }
        entityManager.remove(product);
        return new StatusMessage(true, "Product " + product.getName() + " was removed");
    }

    public List<Category> getCategories() {
        TypedQuery<Category> query = entityManager.createQuery("SELECT g from Category g", Category.class);
        return query.getResultList();
    }

    public StatusMessage saveImagePath(String productId, String imagePath) {
        Product pr = entityManager.find(Product.class, productId);
        if(pr == null) {
            return new StatusMessage(false, "Product does not exist");
        }
        ProductPhotos photos = entityManager.find(ProductPhotos.class, imagePath);
        int i = 1;
        String str = imagePath;
        while (photos != null) {
            photos.setPath(str);
            str = str + i + "";
            i++;
            photos = entityManager.find(ProductPhotos.class, str);
        }
        photos = new ProductPhotos();
        photos.setPath(str);
        photos.setProduct(pr);
        entityManager.persist(photos);
        return new StatusMessage(true, "Success", photos);
    }
}