package ru.ilold.managers;


import ru.ilold.ProductEntities.Product;
import ru.ilold.ProductEntities.ProductInOrderBin;
import ru.ilold.UserEntities.OrderBin;
import ru.ilold.etc.StatusMessage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class OrderBinManager {
    @PersistenceContext(unitName = "tvshopPU")
    private EntityManager entityManager;

    public StatusMessage addProductToOrderBin(long productId, long orderBinId, long quantity) {
        OrderBin orderBin = entityManager.find(OrderBin.class, orderBinId);
        if(orderBin == null) {
            return new StatusMessage(false, "Order bin does not exist");
        }
        Product product = entityManager.find(Product.class, productId);
        if(product == null) {
            return new StatusMessage(false, "Product does not exist");
        }
        ProductInOrderBin productInOrderBin = new ProductInOrderBin();
        productInOrderBin.setBin(orderBin);
        productInOrderBin.setProduct(product);
        productInOrderBin.setQuantity(quantity);
        entityManager.persist(productInOrderBin);
        return new StatusMessage(true, product.getName() + " was added to bin");
    }

    public StatusMessage removeProductFromOrderBin(long productId, long orderBinId) {
        TypedQuery<ProductInOrderBin> query = entityManager.createQuery(
                "select q from ProductInOrderBin q where q.product.id = :productId and " +
                        "q.bin.id = :orderBinId",
                            ProductInOrderBin.class);
        if(query.getSingleResult() == null) {
            return new StatusMessage(false, "There is no such product in order bin");
        }
        entityManager.persist(query.getSingleResult());
        return new StatusMessage(true, "Product was removed");
    }

}