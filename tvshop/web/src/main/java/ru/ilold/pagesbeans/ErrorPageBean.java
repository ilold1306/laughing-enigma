package ru.ilold.pagesbeans;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class ErrorPageBean implements Serializable {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
