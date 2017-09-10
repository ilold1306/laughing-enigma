package ru.ilold.adminbeans;


import ru.ilold.ProductEntities.Category;
import ru.ilold.etc.StatusMessage;
import ru.ilold.managers.ProductEntitiesManager;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;

@Named
@RequestScoped
public class CategoryBean {

    @EJB
    private ProductEntitiesManager productEntitiesManager;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void createCategory() {
        if(name.isEmpty()) {
            return;
        }

        StatusMessage statusMessage = productEntitiesManager.createCategory(name);
        if(!statusMessage.isStatus()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/web/pages/errorpage.xhtml");
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
    public List<Category> getCategories() {
        return productEntitiesManager.getCategories();
    }
}
