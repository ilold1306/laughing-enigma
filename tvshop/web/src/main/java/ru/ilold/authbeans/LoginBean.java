package ru.ilold.authbeans;


import ru.ilold.UserEntities.Credentials;
import ru.ilold.UserEntities.User;
import ru.ilold.etc.StatusMessage;
import ru.ilold.managers.UsersEntitiesManager;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private User user;
    private String email;
    private String password;
    private String message;
    private StatusMessage statusMessage = new StatusMessage(false, "");
    private String logged;

    @EJB
    private UsersEntitiesManager usersEntitiesManager;

    public String getLogged() {
        return logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
    }

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void doLogin() throws IOException {
        if(email == null || password == null) {
            message = "Email or password field is empty";
            return;
        }
        Credentials credentials = new Credentials();
        credentials.setEmail(email);
        credentials.setPasswordHash(password.hashCode());
        statusMessage = usersEntitiesManager.loginUser(credentials);
        user = (User) statusMessage.getObject();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/web/pages/main.xhtml");
    }
}
