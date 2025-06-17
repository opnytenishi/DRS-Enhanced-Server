package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.Department;
import com.drs.drs_enhanced.model.Responder;
import com.drs.drs_enhanced.model.Supply;
import com.drs.drs_enhanced.model.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final static String responderEmail = "responder@gmail.com";
    private final static String responderPassword = "responder";

    /**
     * Authenticate a user by username and password.
     *
     * @param email The email entered by user
     * @param password The password entered by user
     * @return The User object if found, else null
     */
    public static User login(String email, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        User user = null;

        try {
            TypedQuery<User> query = em.createNamedQuery("User.login", User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            user = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Login failed: Invalid credentials.");
        } finally {
            em.close();
        }
        return user;
    }

    /**
     * Create a new user account
     *
     * @param user The user object with details entered by user
     * @return true if successful, false if username already exists.
     */
    public static boolean createUser(User user) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            // Check if username already exists
            TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
            query.setParameter("email", user.getEmail());

            if (!query.getResultList().isEmpty()) {
                return false;
            }

            // Create new user
            transaction.begin();
            em.persist(user);
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

    public static Responder getOrCreateResponder() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Responder responder = null;

        try {
            TypedQuery<Responder> query = em.createQuery(
                    "SELECT r FROM Responder r WHERE r.email = :email", Responder.class);
            query.setParameter("email", responderEmail);

            responder = query.getResultStream().findFirst().orElse(null);

            if (responder == null) {
                responder = new Responder();
                responder.setName("Responder");
                responder.setEmail(responderEmail);
                responder.setPassword(responderPassword);
                responder.setRegion("Central");

                transaction.begin();
                em.persist(responder);
                transaction.commit();
            }

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error" + e.getMessage());
            responder = null;
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

        return responder;
    }

    public static List<Department> getAllDepartments() {
        try (EntityManager em = JPAUtil.getEntityManager()) {
            return em.createNamedQuery(
                    "Department.findAll",
                    Department.class)
                    .getResultList();
        }
    }

    public static boolean assignSupplyToDepartment(Long deptId, Long supplyId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Department department = em.find(Department.class, deptId);
            Supply supply = em.find(Supply.class, supplyId);

            if (department == null || supply == null) {
                return false;
            }

            tx.begin();

            System.out.println("Supplies Count : " + department.getSupplies().size());

            if (!department.getSupplies().contains(supply)) {
                department.getSupplies().add(supply);
            } else {
                return false;
            }

            em.merge(department);
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

    public static List<Supply> getSuppliesForDepartment(Long deptId) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            Department dept = em.createNamedQuery("Department.findWithSuppliesById", Department.class)
                    .setParameter("deptId", deptId)
                    .getSingleResult();

            dept.getSupplies().size();

            return dept.getSupplies();

        } catch (NoResultException e) {
            return new ArrayList<>();
        } finally {
            em.close();
        }
    }

}
