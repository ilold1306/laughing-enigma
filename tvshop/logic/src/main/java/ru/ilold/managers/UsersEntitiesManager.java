package ru.ilold.managers;


import ru.ilold.UserEntities.*;
import ru.ilold.etc.StatusMessage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class UsersEntitiesManager {
    @PersistenceContext(unitName = "tvshopPU")
    private EntityManager entityManager;

    public StatusMessage loginUser(Credentials credentials) {
        Credentials c = entityManager.find(Credentials.class, credentials.getEmail());
        if(c == null) {
            return new StatusMessage(false, "This user does not exist.");
        }

        if(credentials.getPasswordHash() != c.getPasswordHash()) {
            return new StatusMessage(false, "Password is incorrect");
        }

        User user = c.getUser();
        if(user == null) {
            return new StatusMessage(false, "User " + credentials.getEmail() +
                                    " does not exist");
        }

        return new StatusMessage(true, "Success", user);
    }

    public StatusMessage signupUser(User user, Credentials credentials) {
        if(entityManager.find(Credentials.class, credentials.getEmail()) != null) {
            return new StatusMessage(false, "User with email "
                    + credentials.getEmail() + " already exists");
        }
        if(user == null) {
            return new StatusMessage(false, "Set full information about you");
        }
        OrderBin orderBin = new OrderBin();
        orderBin.setCredentials(credentials);
        credentials.setOrderBin(orderBin);
        credentials.setUser(user);
        user.setCredentials(credentials);
        entityManager.persist(orderBin);
        entityManager.persist(credentials);
        entityManager.persist(user);
        return new StatusMessage(true, "Success", user);
    }

    public StatusMessage changeUserInfo(Credentials credentials, User user) {
        if(credentials == null) {
            return new StatusMessage(false, "Log in to use this function");
        }

        if(user == null) {
            return new StatusMessage(false, "Set fields to change");
        }

        entityManager.merge(user);
        return new StatusMessage(true, "Information was changed");
    }

    public StatusMessage changeEmail(Credentials oldCredentials, String newEmail) {
        Credentials c = entityManager.find(Credentials.class, oldCredentials.getEmail());
        if(c == null) {
            return new StatusMessage(false, "User with email "
                    + oldCredentials.getEmail() + " does not exist");
        }

        c.setEmail(newEmail);
        entityManager.merge(c);
        return new StatusMessage(true, "Email was changed");
    }

    public StatusMessage setRoleToUser(long userId, String code) {
        Credentials credentials = entityManager.find(Credentials.class, userId);
        if(credentials == null) {
            return new StatusMessage(false, "Such user does not exist");
        }
        Role role = entityManager.find(Role.class, code);
        if(role == null) {
            return new StatusMessage(false, "Such role does not exist");
        }
        CredentialsRole credentialsRole = new CredentialsRole();
        credentialsRole.setCredentials(credentials);
        credentialsRole.setRole(role);
        entityManager.persist(credentialsRole);
        return new StatusMessage(true, "Success", credentialsRole);
    }
}
