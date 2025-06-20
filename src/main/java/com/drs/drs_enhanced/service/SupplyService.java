package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Supply;
import jakarta.persistence.*;
import java.util.List;

public class SupplyService {

    /**
     * Create a new supply
     *
     * @param supply The supply object with details entered by user
     * @return true if successful, false if supply already exists.
     */
    public static boolean createSupply(Supply supply) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(supply);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Get All supplies added
     *
     * @return list of supplies if exists, empty list if no supplies
     */
    public static List<Supply> getAllSupplies() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createNamedQuery(
                    "Supply.findAll",
                    Supply.class)
                    .getResultList();
        }
    }

}
