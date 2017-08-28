package ru.ilold.managers;


import ru.ilold.ProductEntities.Category;
import ru.ilold.ProductEntities.Product;
import ru.ilold.etc.StatusMessage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

@Stateless
@LocalBean
public class ProductEntitiesManager {
    @PersistenceContext(unitName = "tvshopPU")
    private EntityManager entityManager;

    public StatusMessage createCategory(@NotNull String name) {
        Category category = entityManager.find(Category.class, name);
        if(category != null) {
            return new StatusMessage(false, "Category " + name + " already exists!", category);
        }
        try {
            category.setName(name);
        } catch (NullPointerException e) {
            return new StatusMessage(false, "Name was not set");
        }
        entityManager.persist(category);
        return new StatusMessage(true, "Success", category);
    }

    public StatusMessage createProduct(String categoryName, Product product) {
        Category category = entityManager.find(Category.class, categoryName);
        if(category == null) {
            return new StatusMessage(false, "Category " + categoryName + " does not exist");
        }
        product.setCategory(category);
        entityManager.persist(product);
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
        if(entityManager.find(Product.class, product.getId()) == null) {
            return new StatusMessage(false, "This product does not exist");
        }

        entityManager.remove(product);
        return new StatusMessage(true, "Product " + product.getName() + " was removed");
    }

}
