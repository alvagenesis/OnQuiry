/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.dao.hibernate;

import com.onsemi.onquiry.dao.UserDao;
import com.onsemi.onquiry.entity.User;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ffzwqy
 */
public class HibernateUserDaoTest {
    
    public HibernateUserDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * Test of addUser method, of class HibernateUserDao.
     */
    @Test
    public void testAddUser() {
        UserDao userDao = new HibernateUserDao();
        User user = new User();
        user.setUsername("User 1");
        user.setPassword("password");
        user.setEmailAddress("user1@host.com");
        user.setFirstName("firstname1");
        user.setLastName("lastname1");
        try {
            userDao.addUser(user);
        } catch(Exception ex) {
            ex.printStackTrace();
            fail("testAddUser fail because of the following exception: " + ex.getMessage());
        }
        
    }
}