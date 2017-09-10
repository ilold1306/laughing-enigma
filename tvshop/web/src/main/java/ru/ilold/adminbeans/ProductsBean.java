package ru.ilold.adminbeans;


import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import ru.ilold.JackRepo;
import ru.ilold.ProductEntities.Category;
import ru.ilold.ProductEntities.Product;
import ru.ilold.ProductEntities.ProductPhotos;
import ru.ilold.etc.StatusMessage;
import ru.ilold.files.JackRepository;
import ru.ilold.managers.ProductEntitiesManager;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ProductsBean implements Serializable {
    private String categoryName;
    private String name;
    private String characteristics;
    private String id;
    private UploadedFile file;
    private byte[] contents;

    @EJB
    private ProductEntitiesManager productEntitiesManager;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void upload(FileUploadEvent event) {
        UploadedFile f = event.getFile();
        contents = f.getContents();
        file = f;
    }

    public void createCategory() {
        if(categoryName.isEmpty()) {
            RolesBean.addMessage("Error!", "Input category name");
            return;
        }
        StatusMessage statusMessage = productEntitiesManager.createCategory(categoryName);
        if(!statusMessage.isStatus()) {
            RolesBean.addMessage("Error!", statusMessage.getMessage());
        } else {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Success!", "Category was created");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        }
    }
    public void createProduct() {
        if(name.isEmpty()) {
            RolesBean.addMessage("Error!", "Input product name");
            return;
        }
        if(categoryName.isEmpty()) {
            RolesBean.addMessage("Error!", "Select category");
            return;
        }
        if(file == null) {
            RolesBean.addMessage("Error!", "Select photo");
            return;
        }
        if(contents.length == 0) {
            RolesBean.addMessage("Error!", "Select photo");
            return;
        }
        StatusMessage s = JackRepository.deployFile(contents, file.getContentType());
        if(!s.isStatus()) {
            RolesBean.addMessage("Error!", s.getMessage());
            return;
        }
        ProductPhotos productPhotos = new ProductPhotos();
        productPhotos.setPath(s.getMessage());
        Product product = new Product();
        product.setName(name);
        product.setCharacteristics(characteristics);
        StatusMessage statusMessage = productEntitiesManager.createProduct(categoryName, product, productPhotos);
        if(!statusMessage.isStatus()) {
            RolesBean.addMessage("Error!", "Input product name");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Success!", "Product was created"));
        }
    }
    public void deleteProduct() {
        StatusMessage statusMessage = productEntitiesManager.removeProduct(id);
        if(!statusMessage.isStatus()) {
            RolesBean.addMessage("Error!", statusMessage.getMessage());
        } else {
            RolesBean.addInfoMessage("Success!", statusMessage.getMessage());
        }
    }
    public List<String> getCategoriesList() {
        List<String> list = new ArrayList<>();
        List<Category> categories = productEntitiesManager.getCategories();
        for(Category category : categories) {
            list.add(category.getName());
        }
        return list;
    }

    public void removeCategory() {
        if(categoryName.isEmpty()) {
            RolesBean.addMessage("Error!", "Select category");
        }
        StatusMessage statusMessage = productEntitiesManager.removeCategory(categoryName);
        if(!statusMessage.isStatus()) {
            RolesBean.addMessage("Error!", statusMessage.getMessage());
            return;
        }
        RolesBean.addInfoMessage("Success!", statusMessage.getMessage());
    }
}