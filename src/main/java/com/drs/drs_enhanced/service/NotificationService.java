package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Notification;
import jakarta.persistence.*;
import java.util.List;

public class NotificationService {
    /**
     * Create a new Notification
     * 
     * @param Notification The Notification object with details entered by user
     * @return true if successful, false if Notification already exists.
     */
    public static boolean createNotification(Notification notification) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(notification);
            transaction.commit();
            return true;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error Notification: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
    
    public static List<Notification> getAllNotifications() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createNamedQuery(
                    "Notification.findAll", 
                    Notification.class)
                     .getResultList();
        }
    }

}
