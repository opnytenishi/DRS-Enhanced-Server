package com.drs.drs_enhanced.service;

import com.drs.drs_enhanced.JPAUtil;
import com.drs.drs_enhanced.model.User;
import jakarta.persistence.*;

public class UserService {
    
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
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
}
