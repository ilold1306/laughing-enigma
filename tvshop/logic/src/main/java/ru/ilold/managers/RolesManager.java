package ru.ilold.managers;


import ru.ilold.UserEntities.*;
import ru.ilold.etc.StatusMessage;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class RolesManager {
    @PersistenceContext(unitName = "tvshopPU")
    private EntityManager entityManager;

    public StatusMessage createNewRole(String code) {
        if(code.isEmpty()) {
            return new StatusMessage(false, "String is empty!");
        }
        Role role = entityManager.find(Role.class, code);
        if(role != null) {
            return new StatusMessage(false, "This role already exists");
        }
        role = new Role();
        role.setCode(code);
        entityManager.persist(role);
        return new StatusMessage(true, "Success", role);
    }

    public StatusMessage createNewResource(String id) {
        if(id.isEmpty()) {
            return new StatusMessage(false, "String is empty");
        }

        Resource resource = entityManager.find(Resource.class, id);

        if(resource != null) {
            return new StatusMessage(false, "This resource is already in database");
        }

        resource = new Resource();
        resource.setId(id);
        entityManager.persist(resource);
        return new StatusMessage(true, "Success", resource);
    }

    public StatusMessage createRight(String code, String resId) {
        Role role = entityManager.find(Role.class, code);
        if(role == null) {
            return new StatusMessage(false, "Role was not found");
        }
        Resource resource = entityManager.find(Resource.class, resId);
        if(resource == null) {
            return new StatusMessage(false, "Resource was not found");
        }
        TypedQuery<Pravo> query = entityManager.createQuery("select c from Pravo c where " +
                "c.role=:role and c.resource = :resource",
                Pravo.class).setParameter("role", role).setParameter("resource", resource);
        List<Pravo> list = query.getResultList();
        if(!list.isEmpty()) {
            return new StatusMessage(false, "V ochko sebe svoi prava zasun'");
        }
        Pravo pravo = new Pravo();
        pravo.setRole(role);
        pravo.setResource(resource);
        entityManager.persist(pravo);
        return new StatusMessage(true, "Success", pravo);
    }
    public StatusMessage deleteRole(String code) {
        Role role = entityManager.find(Role.class, code);
        if(role == null) {
            return new StatusMessage(false, "There is no such role in db");
        }
        entityManager.remove(role);
        return new StatusMessage(true, "Success");
    }
    public StatusMessage deleteResource(String resId) {
        Resource resource = entityManager.find(Resource.class, resId);
        if(resource == null) {
            return new StatusMessage(false, "There is no such resource in db");
        }
        entityManager.remove(resource);
        return new StatusMessage(true, "Success");
    }
    public StatusMessage removeRight(long id) {
        Pravo pravo = entityManager.find(Pravo.class, id);
        if(pravo == null) {
            return new StatusMessage(false, "There is no such right in db");
        }
        entityManager.remove(pravo);
        return new StatusMessage(true, "Success");
    }

    public List<Role> getRolesList() {
        TypedQuery<Role> query = entityManager.createQuery("SELECT c FROM Role c", Role.class);
        return query.getResultList();
    }

    public List<Resource> getResourcesList() {
        TypedQuery<Resource> query = entityManager.createQuery("SELECT c    FROM Resource c", Resource.class);
        return query.getResultList();
    }

    public StatusMessage grantRights(String login, String roleCode) {
        if(login.isEmpty()) {
            return new StatusMessage(false, "Enter login");
        }
        if(roleCode.isEmpty()) {
            return new StatusMessage(false, "Enter role");
        }
        Credentials credentials = entityManager.find(Credentials.class, login);
        if(credentials == null) {
            return new StatusMessage(false, "User was not found");
        }
        Role role = entityManager.find(Role.class, roleCode);
        if(role == null) {
            return new StatusMessage(false, "Role was not found");
        }
        CredentialsRole credentialsRole = new CredentialsRole();
        credentialsRole.setCredentials(credentials);
        credentialsRole.setRole(role);
        entityManager.persist(credentialsRole);
        return new StatusMessage(true, roleCode + "'s rights were granted to " + credentials.getEmail(), credentialsRole);
    }
}
