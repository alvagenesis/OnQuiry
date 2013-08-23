/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.exception.OnQuiryServiceException;
import com.onsemi.onquiry.service.ServiceFactory;
import com.onsemi.onquiry.service.UserService;
import com.onsemi.onquiry.service.hibernate.HibernateServiceFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ffzwqy
 */

@ManagedBean
@ViewScoped
public class RegisterBean {
    
    private User user;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;
    
    public RegisterBean() {
        user = new User();
    }
    
    private ServiceFactory serviceFactory = new HibernateServiceFactory();
    
    public String register() {
        System.out.println("TEST");
        UserService userService = serviceFactory.createUserService();
        
        try {
            userService.registerUser(user);
        } catch(OnQuiryServiceException onsEx) {
            //TODO - create an exception handler
            onsEx.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return "register";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
