/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.service.ServiceFactory;
import com.onsemi.onquiry.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iamejay
 */
public class HibernateUserServiceTest {
    
    public HibernateUserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }


    /**
     * Test of registerUser method, of class HibernateUserService.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        User user = new User();
        user.setEmailAddress("ejay@onquiry.com");
        user.setFirstName("ejay");
        user.setLastName("canaria");
        user.setPassword("password");
        
        ServiceFactory serviceFactory = new HibernateServiceFactory();
        UserService userService = serviceFactory.createUserService();
        
        try {
            userService.registerUser(user);
        } catch(Exception ex) {
            ex.printStackTrace();
            fail("testRegisterUser encounter the following exception: " + ex.getMessage());
        }
        
    }
}