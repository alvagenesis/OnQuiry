/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author iamejay
 */
public class HibernateEntityManagerTest {

    public HibernateEntityManagerTest() {
    }

    @Test(expected = Exception.class)
    public void testDatabaseConnectionFailure() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        when(sessionFactory.openSession()).thenThrow(new Exception("Cannot connect to database!"));

        HibernateEntityManager hibernateEntityManager = new HibernateEntityManager(sessionFactory, User.class);
        hibernateEntityManager.startTransaction();
    }

    /**
     * If someone performs any DML related calls without starting the the
     * transaction, HibernateEntityManagerException shall be thrown.
     */
    @Test(expected = HibernateEntityManagerException.class)
    public void testUnStartedTransaction() throws Exception {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        when(sessionFactory.openSession()).thenReturn(session);

        HibernateEntityManager entityManager = new HibernateEntityManager(sessionFactory, User.class);
        entityManager.persist(new User());
    }

    /**
     * Test the transaction failure handling of the HibernateEntityManager. When
     * an un-handled exception occurred after the transaction has started, it
     * should be able to recover from that failure. Transaction shall rollback
     * and session shall close.
     *
     */
    @Test
    public void testTransactionFailureHandling() throws HibernateEntityManagerException {

        //Mocking setup. Below contains the expected behavior of the mock objects
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.isOpen()).thenReturn(Boolean.TRUE);

        when(session.beginTransaction()).thenReturn(tx);
        when(session.getTransaction()).thenReturn(tx);
        when(tx.isActive()).thenReturn(Boolean.TRUE);

        doThrow(new RuntimeException("An exception has occurred in Session.persist")).when(session).persist(new User());

        //Actual test here
        HibernateEntityManager entityManager = new HibernateEntityManager(sessionFactory, User.class);
        entityManager.startTransaction();

        try {
            entityManager.persist(new User());
            fail("Transaction should not be committed!");
            entityManager.commitTransaction();
        } catch (RuntimeException ex) {
            entityManager.rollbackTransaction();
        } finally {
            entityManager.endTransaction();
        }

        verify(tx).rollback();
        verify(session).close();
    }

    @Test
    public void testStartTransaction() {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);
        
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(tx.isActive()).thenReturn(Boolean.TRUE);
        
        HibernateEntityManager entityManager = new HibernateEntityManager(sessionFactory, User.class);
        entityManager.startTransaction();

        verify(sessionFactory).openSession();
        verify(session).beginTransaction();
    }
    
    
    @Test(expected = HibernateEntityManagerException.class)
    public void testSessionNotOpen() throws HibernateEntityManagerException {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        
        HibernateEntityManager<User> entityManager = new HibernateEntityManager(sessionFactory, User.class);
        entityManager.findOneResult("", null);
    }
    
}