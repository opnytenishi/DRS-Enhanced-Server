package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Shelter;
import jakarta.persistence.*;
import java.util.List;

public class ShelterService {
    /**
     * Create a new shelter
     * 
     * @param shelter The shelter object with details entered by user
     * @return true if successful, false if shelter already exists.
     */
    public static boolean createShelter(Shelter shelter) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(shelter);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error Shelter: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
    
    public static List<Shelter> getAllShelters() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createNamedQuery(
                    "Shelter.findAll", 
                    Shelter.class)
                     .getResultList();
        }
    }

}
