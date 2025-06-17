package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Department;
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
    
    public static boolean assignTeamToIncident(Incident incomingIncident) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Incident incident = em.find(Incident.class, incomingIncident.getId());
            if (incident == null || incident.getAssignedDepartment() != null) {
                return false;
            }
            Department managedDept = em.find(Department.class, incomingIncident.getAssignedDepartment().getUserId());
            if (managedDept == null) {
                return false;
            }
            tx.begin();
            incident.setAssignedDepartment(managedDept);
            em.merge(incident);
            tx.commit();

            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            return false;

        } finally {
            em.close();
        }
    }
    
    public static List<Incident> getIncidentsForDepartment(Long deptId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createNamedQuery("Incident.findByDepartmentId", Incident.class)
                     .setParameter("deptId", deptId)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    
    public static boolean markIncidentComplete(Long incidentId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Incident i = em.find(Incident.class, incidentId);
            if (i == null) {
                return false;
            }

            tx.begin();
            i.setCompleted(true);
            em.merge(i);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

}
