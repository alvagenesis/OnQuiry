package com.onsemi.onquiry.service.hibernate;

import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;

/**
 * An implementation of the DAO layer using hibernate.
 * 
 * @version 1.0
 * @author Ejay Canaria
 */
public class HibernateEntityManager<T> {
    
    private SessionFactory sessionFactory;
    private Class entityClass;
    private Session session;
    
    /**
     * 
     * @param sessionFactory
     * @param entityClass 
     */
    public HibernateEntityManager(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }
    
    /**
     * Check if session is already open and transaction is active.
     * @throws HibernateEntityManagerException 
     */
    private void checkSessionAndTransaction() throws HibernateEntityManagerException {
        if(session == null || !session.isOpen() || !session.getTransaction().isActive()) {
            throw new HibernateEntityManagerException("Session is not yet open and transaction has not yet begun. Please start the transaction first.");
        }
    }
    
    /**
     * Check if session is already open.
     * @throws HibernateEntityManagerException 
     */
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
    
    /**
     * Closes an open session.
     */
    public void closeSession() {
        if(session != null && session.isOpen()) {
            session.close();
        }
    }
    
    /**
     * Rollback an active transaction.
     */
    public void rollbackTransaction() {
        if(session != null && session.getTransaction() != null || session.getTransaction().isActive()) {
            session.getTransaction().rollback();
        }
    }
    
    /**
     * Iterate to the given parameters and populate it on the query.
     * @param query
     * @param parameters 
     */
    private void populateBindVariableInQuery(Query query, Map<String, Object> parameters) {
        for(Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    
    /***************************** DML Related Codes ***************************/
    
    /**
     * Persists an entity in the database.
     * @param entity
     * @throws HibernateEntityManagerException 
     */
    public void persist(T entity) throws HibernateEntityManagerException {
        checkSessionAndTransaction();
        session.persist(entity);
    }
    
    /**
     * Finds an entity using its id.
     * @param id
     * @return
     * @throws HibernateEntityManagerException 
     */
    public T findResultById(Long id) throws HibernateEntityManagerException {
        checkSession();
        T entity = (T) session.get(entityClass, id);
        return entity;
    }
    
    /**
     * Retrieves a single entity using named query and parameters. 
     * Make sure your query will only return one unique result, or else
     * this method will throw an exception. Unique result is usually retrieved
     * using a unique field in the field of an entity.
     * @param namedQuery
     * @param parameters
     * @return
     * @throws HibernateEntityManagerException 
     */
    public T findOneResult(String namedQuery, Map<String, Object> parameters) throws HibernateEntityManagerException {
        
        checkSession();
        
        Query query = session.getNamedQuery(namedQuery);
        populateBindVariableInQuery(query, parameters);
        T result = (T) query.uniqueResult();
        
        return result;
    }
    
    /**
     * Retrieves a list of entity using named query and parameters.
     * Use this method when you have a conditions or parameters that
     * will result to retrieval of multiple records.
     * @param namedQuery
     * @param parameters
     * @return
     * @throws HibernateEntityManagerException 
     */
    public List<T> findMultipleResult(String namedQuery, Map<String,Object> parameters) throws HibernateEntityManagerException {
        checkSession();
        
        Query query = session.getNamedQuery(namedQuery);
        populateBindVariableInQuery(query, parameters);
        List<T> results = query.list();
        return results;
    }
    
    /**
     * Turns an entity into a persistent state and update the changes in database.
     * @param entity
     * @throws HibernateEntityManagerException 
     */
    public void update(T entity) throws HibernateEntityManagerException {
        checkSessionAndTransaction();
        session.update(entity);
    }
    
    /**
     * Deletes an entity from the database
     * @param entity
     * @throws HibernateEntityManagerException 
     */
    public void delete(T entity) throws HibernateEntityManagerException {
        checkSessionAndTransaction();
        session.delete(entity);
    }
    
    /**
     * Deletes an entity using its id as an identifier.
     * @param id
     * @throws HibernateEntityManagerException 
     */
    public void deleteById(Long id) throws HibernateEntityManagerException {
        checkSessionAndTransaction();
        T entity = (T) session.get(entityClass, id);
        session.delete(entity);
    }
    
}
