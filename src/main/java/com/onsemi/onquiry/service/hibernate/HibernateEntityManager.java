/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

/**
 *
 * @author iamejay
 */
public class HibernateEntityManager<T> {
    
    private SessionFactory sessionFactory;
    private Class entityClass;
    private Session session;
    
    public HibernateEntityManager(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }
    
    private void checkSessionAndTransaction() throws HibernateEntityManagerException {
        if(session == null || !session.isOpen() || !session.getTransaction().isActive()) {
            throw new HibernateEntityManagerException("Session is not yet open and transaction has not yet begun. Please start the transaction first.");
        }
    }
    
    private void checkSession() throws HibernateEntityManagerException {
        if(session == null || !session.isOpen()) {
            throw new HibernateEntityManagerException("Session is not yet open. Please open the session first.");
        }
    }
    
    /**
     * Opens a session and begin a transaction. 
     * Used in the following DML statements: INSERT, UPDATE, DELETE
     */
    public void startTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }
    
    /**
     * Opens a session. Used in SELECT DML statement.
     */
    public void openSession() {
        session = sessionFactory.openSession();
    }
    /**
     * Commits an active transaction.
     */
    public void commitTransaction() {
        if(session != null && session.getTransaction() != null && session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }
    }
    
    public void endTransaction() {
        if(session != null && session.isOpen()) {
            session.close();
        }
    }
    
    public void rollbackTransaction() {
        if(session != null && session.getTransaction() != null || session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
    }
    
    private void populateBindVariableInQuery(Query query, Map<String, Object> parameters) {
        
        for(Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
    /***************************** DML Related Codes ***************************/
    
    public void persist(T entity) throws HibernateEntityManagerException {
        checkSessionAndTransaction();
        session.persist(entity);
    }
    
    public T findOneResult(String namedQuery, Map<String, Object> parameters) throws HibernateEntityManagerException {
        
        checkSession();
        
        Query query = session.getNamedQuery(namedQuery);
        populateBindVariableInQuery(query, parameters);
        T entity = (T) query.uniqueResult();
        
        return entity;
    }
    
    
    
    
    
}
