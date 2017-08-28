package ru.ilold.managers;


import ru.ilold.UserEntities.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class AuthentificateManager {
    @PersistenceContext(unitName = "tvshopPU")
    private EntityManager entityManager;

    public boolean isGranted(String login, String resource) {
        if(login.isEmpty() || resource.isEmpty()) {
            return false;
        }

        Resource res = entityManager.find(Resource.class, resource);
        if(res == null) {
            return false;
        }

        Credentials credentials = entityManager.find(Credentials.class, login);
        if(credentials == null) {
            return false;
        }
        List<CredentialsRole> rolesList = credentials.getRolesList();
        if(rolesList == null || rolesList.isEmpty()) {
            return false;
        }
        for(CredentialsRole credentialsRole : rolesList ) {
            Role role = credentialsRole.getRole();
            if(role == null) {
                continue;
            }
            List<Pravo> pravosList = role.getPravosList();
            for(Pravo pravo: pravosList) {
                if(pravo == null) {
                    continue;
                }

                Resource resourceEntity = pravo.getResource();
                if(resourceEntity.getId().equalsIgnoreCase(res.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

}
