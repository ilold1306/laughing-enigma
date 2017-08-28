package ru.ilold.authbeans;

import ru.ilold.UserEntities.Credentials;
import ru.ilold.UserEntities.User;
import ru.ilold.etc.StatusMessage;
import ru.ilold.managers.UsersEntitiesManager;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class SignupBean implements Serializable {

    private String email;
    private User user = new User();
    private Credentials credentials = new Credentials();
    private String password;
    private StatusMessage statusMessage;

    @EJB
    private UsersEntitiesManager usersEntitiesManager;

    public StatusMessage getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(StatusMessage statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void doSignUp() {
        credentials.setPasswordHash(password.hashCode());
        if(password.isEmpty()){
            return;
        }
        if(credentials.getEmail().isEmpty()) {
            return;
        }
        statusMessage = usersEntitiesManager.signupUser(user, credentials);
    }
}
