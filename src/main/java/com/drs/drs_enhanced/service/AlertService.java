package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Alert;
import jakarta.persistence.*;
import java.util.List;

public class AlertService {
    /**
     * Create a new Alert
     * 
     * @param Alert The Notification object with details entered by user
     * @return true if successful, false if Alert already exists.
     */
    public static boolean createAlert(Alert alert) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(alert);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error Alert: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
    
    public static List<Alert> getAllAlerts() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createNamedQuery(
                    "Alert.findAll", 
                    Alert.class)
                     .getResultList();
        }
    }

}
