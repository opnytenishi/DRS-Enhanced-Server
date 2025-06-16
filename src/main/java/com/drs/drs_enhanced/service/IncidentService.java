package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Incident;
import jakarta.persistence.*;
import java.util.List;

public class IncidentService {
    /**
     * Create a incident report
     * 
     * @param incident The incident object with details entered by user
     * @return true if successful, false if username already exists.
     */
    public static boolean createIncidentReport(Incident incident) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(incident);
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
    
    public static List<Incident> getUnassignedIncidents() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createNamedQuery(
                    "Incident.findUnassigned", 
                    Incident.class)
                     .getResultList();
        }
    }
}
