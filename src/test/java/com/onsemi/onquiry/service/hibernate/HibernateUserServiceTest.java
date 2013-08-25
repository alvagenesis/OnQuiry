/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.exception.OnQuiryServiceException;
import com.onsemi.onquiry.service.UserService;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author iamejay
 */
@RunWith(MockitoJUnitRunner.class)
public class HibernateUserServiceTest {
    
    public HibernateUserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }


    /**
     * Test of testFailedUserRegistrationUsedEmailAddress method, of class HibernateUserService.
     */
    @Test
    public void testFailedUserRegistrationUsedEmailAddress() {
  
        User user = new User();
        user.setEmailAddress("ejay@onquiry.com");
        user.setFirstName("ejay");
        user.setLastName("canaria");
        user.setPassword("password");
        
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);
        Query query = mock(Query.class);
        
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(tx.isActive()).thenReturn(Boolean.TRUE);
        when(session.getNamedQuery(User.FIND_USER_BY_EMAIL)).thenReturn(query);
        
        HibernateEntityManager<User> entityManager = new HibernateEntityManager<User>(sessionFactory, User.class);
        
        UserService userService = new HibernateUserService(entityManager);
        UserService userServiceSpy = spy(userService);
        
        //Failed registration due to email address already used.
        try {
            doReturn(true).when(userServiceSpy).isUsedEmailAddress("ejay@onquiry.com");
            userServiceSpy.registerUser(user);
            fail("An OnQuiryServiceException shall be thrown, this line should not be reached");
        } catch(Exception ex) {
            //OnQuiryServiceException will be thrown because the email address is already used.
            //If other type of exception is thrown, test should fail.
            if(!(ex instanceof OnQuiryServiceException)) {
                fail("Only OnQuiryServiceException is expected, but this exception occurred: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        
        
    }
    
    @Test
    public void testSuccessUserRegistration() {
        
        User user = new User();
        user.setEmailAddress("ejay@onquiry.com");
        user.setFirstName("ejay");
        user.setLastName("canaria");
        user.setPassword("password");
        
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);
        Query query = mock(Query.class);
        
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(session.getTransaction()).thenReturn(tx);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(tx.isActive()).thenReturn(Boolean.TRUE);
        when(session.getNamedQuery(User.FIND_USER_BY_EMAIL)).thenReturn(query);
        
        HibernateEntityManager<User> entityManager = new HibernateEntityManager<User>(sessionFactory, User.class);
        
        HibernateUserService userService = new HibernateUserService(entityManager);
        HibernateUserService userServiceSpy = spy(userService);
    
        //Success registration
        try {
            doReturn(false).when(userServiceSpy).isUsedEmailAddress(user.getEmailAddress());
            userServiceSpy.registerUser(user);
        } catch(Exception ex) {
            fail("The following exception has occurred: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }
    
    
    @Test
    public void testIsUsedEmailAddress() throws Exception {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);
        Query query = mock(Query.class);
        
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(session.getNamedQuery(User.FIND_USER_BY_EMAIL)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(new User());
        
        HibernateEntityManager<User> entityManager = new HibernateEntityManager<User>(sessionFactory, User.class);
        UserService userService = new HibernateUserService(entityManager);
        
        assertTrue(userService.isUsedEmailAddress("thisemailistaken@onquiry.com"));
        
        when(query.uniqueResult()).thenReturn(null);
    
        assertFalse(userService.isUsedEmailAddress("thisemailisavailable@onquiry.com"));
    }
    
    
}