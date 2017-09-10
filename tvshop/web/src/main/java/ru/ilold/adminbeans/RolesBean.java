package ru.ilold.adminbeans;


import ru.ilold.UserEntities.Resource;
import ru.ilold.UserEntities.Role;
import ru.ilold.etc.StatusMessage;
import ru.ilold.managers.RolesManager;
import ru.ilold.pagesbeans.ErrorPageBean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class RolesBean {
    private String name;
    private String code;

    @EJB
    private RolesManager rolesManager;

    @Inject
    private ErrorPageBean errorPageBean;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void createRole() {
        if(name.isEmpty()) {
            addMessage("Error!", "Input role's name");
            return;
        }
        StatusMessage statusMessage = rolesManager.createNewRole(name);
        if(!statusMessage.isStatus()) {
            addMessage("Error", statusMessage.getMessage());
        }
    }

    public void createResource() {
        if(name.isEmpty()) {
            addMessage("Error!", "Input resource's name");
            return;
        }
        StatusMessage statusMessage = rolesManager.createNewResource(name);
        if(!statusMessage.isStatus()) {
            addMessage("Error!", statusMessage.getMessage());
        }
    }

    public void createRight() {
        if(code.isEmpty()) {
            addMessage("Error!", "Select role");
            return;
        }
        if(name.isEmpty()) {
            addMessage("Error!", "Input resource's name");
            return;
        }
        StatusMessage statusMessage = rolesManager.createRight(code, name);
        if(!statusMessage.isStatus()) {
            addMessage("Error!", statusMessage.getMessage());
        }
    }

    public List<String> getRolesList() {
        List<String> namesList = new ArrayList<>();
        List<Role> rolesList = rolesManager.getRolesList();
        for(Role role : rolesList) {
            namesList.add(role.getCode());
        }
        return namesList;
    }

    public List<String> getResourcesList() {
        List<String> namesList = new ArrayList<>();
        List<Resource> resourcesList = rolesManager.getResourcesList();
        for(Resource resource : resourcesList) {
            namesList.add(resource.getId());
        }
        return namesList;
    }


    public void grantRightsToUser() {
        if(name.isEmpty()) {
            addMessage("Error!", "Login is empty");
        }
        StatusMessage statusMessage = rolesManager.grantRights(name, code);
        if(!statusMessage.isStatus()) {
            addMessage("Error!", statusMessage.getMessage());
        } else {
            addMessage("Success!", statusMessage.getMessage());
        }
    }

    public static void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void addInfoMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
