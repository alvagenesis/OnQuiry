/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.service.ServiceFactory;
import com.onsemi.onquiry.service.UserService;
import com.onsemi.onquiry.service.hibernate.HibernateServiceFactory;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ffzwqy
 */

@ManagedBean
@ViewScoped
public class RegisterBean implements Serializable {
    
    private User user = new User();
    private ManagedBeanUtility managedBeanUtility = new ManagedBeanUtility();;
    
    public RegisterBean() {
    }
    
    private ServiceFactory serviceFactory = new HibernateServiceFactory();
    
    public String register() {
        System.out.println("TEST");
        UserService userService = serviceFactory.createUserService();
        
        try {
            userService.registerUser(user);
            managedBeanUtility.addInformationMessage("Hi " + user.getFirstName() + "! Your account was successfully added.");
        } catch(Exception ex) {
            managedBeanUtility.handleException(ex);
        }
        
        return "register";
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
