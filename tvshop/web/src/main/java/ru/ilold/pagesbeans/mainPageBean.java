package ru.ilold.pagesbeans;


import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class mainPageBean {
    private String error;
    private String included;
    public void redirectToLogin() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/web/auth/login.xhtml");
        } catch (IOException e) {
            error = e.getMessage();
        }
    }

    public String getIncluded() {
        return included;
    }

    public void setIncluded(String included) {
        this.included = included;
    }

    public void redirectToSignUp() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/web/auth/signup.xhtml");
        } catch (IOException e) {
            error = e.getMessage();
        }
    }
}
