package ru.ilold.managers;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class RolesManager {
    @PersistenceContext(unitName = "tvshopPU")
    private EntityManager entityManager;

    public void createNewRole(String code) {

    }
}
