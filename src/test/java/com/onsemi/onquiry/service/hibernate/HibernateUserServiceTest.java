/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.service.ServiceException;
import com.onsemi.onquiry.service.ServiceUtility;
import com.onsemi.onquiry.service.UserService;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
     * Test of testFailedUserRegistrationUsedEmailAddress method, of class
     * HibernateUserService.
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
        } catch (Exception ex) {
            //OnQuiryServiceException will be thrown because the email address is already used.
            //If other type of exception is thrown, test should fail.
            if (!(ex instanceof ServiceException)) {
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
        } catch (Exception ex) {
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

    @Test
    public void testLoginSuccess() {

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);
        Query query = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(session.getTransaction()).thenReturn(tx);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(tx.isActive()).thenReturn(Boolean.TRUE);
        when(session.getNamedQuery(User.FIND_USER_BY_EMAIL_AND_PASSWORD)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(new User());

        HibernateEntityManager<User> entityManager = new HibernateEntityManager<User>(sessionFactory, User.class);

        HibernateUserService userService = new HibernateUserService(entityManager);

        try {
            User user = userService.login("ejay@onquiry.com", "test");
            assertNotNull(user);
        } catch (Exception ex) {
            fail("No exception is expected, but found: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Test(expected = ServiceException.class)
    public void testLoginFailed() throws ServiceException {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);
        Query query = mock(Query.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(session.getTransaction()).thenReturn(tx);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(tx.isActive()).thenReturn(Boolean.TRUE);
        when(session.getNamedQuery(User.FIND_USER_BY_EMAIL_AND_PASSWORD)).thenReturn(query);
        
        HibernateEntityManager<User> entityManager = new HibernateEntityManager<User>(sessionFactory, User.class);

        HibernateUserService userService = new HibernateUserService(entityManager);
        //UserService userService = new HibernateServiceFactory().createUserService();
        
        try {
            User user = userService.login("ejay@onquiry.com", "test");
        } catch (ServiceException onqEx) {
            throw onqEx;
        } catch (Exception ex) {
            fail("OnQuiryServiceException is expected, but found: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    @Test(expected = ServiceException.class)
    public void testWrongPasswordInChangePassword() throws Exception {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(session.getTransaction()).thenReturn(tx);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(tx.isActive()).thenReturn(Boolean.TRUE);
        
        User user = new User();
        user.setPassword("correctOldPassword");
        when(session.get(User.class, Long.valueOf(1))).thenReturn(user);
        
        HibernateEntityManager<User> entityManager = new HibernateEntityManager<User>(sessionFactory, User.class);
        UserService userService = new HibernateUserService(entityManager);
        
        userService.changePassword(Long.valueOf(1), "wrongOldPassword", "newPassword");
    }
    
    @Test
    public void testSuccessChangePassword() throws Exception {
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction tx = mock(Transaction.class);

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);
        when(session.getTransaction()).thenReturn(tx);
        when(session.isOpen()).thenReturn(Boolean.TRUE);
        when(tx.isActive()).thenReturn(Boolean.TRUE);
        
        User user = new User();
        user.setPassword(ServiceUtility.encryptPassword("correctOldPassword"));
        when(session.get(User.class, Long.valueOf(1))).thenReturn(user);
        
        HibernateEntityManager<User> entityManager = new HibernateEntityManager<User>(sessionFactory, User.class);
        UserService userService = new HibernateUserService(entityManager);
        
        userService.changePassword(Long.valueOf(1), "correctOldPassword", "newPassword");
    }
}