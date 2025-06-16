package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Alert;
import com.drs.drs_enhanced.model.Shelter;
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
    
    public static boolean getAllAlertByRegion(String region) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Alert> query = em.createNamedQuery("Alert.findByRegion", Alert.class);
            query.setParameter("region", region);
            return !query.getResultList().isEmpty();
        } catch (NoResultException e) {
            System.out.println("Get alerts by region failed");
            return false;
        } finally {
            em.close();
        }
    }

}
