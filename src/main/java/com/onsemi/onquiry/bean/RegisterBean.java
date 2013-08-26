/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.service.UserService;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ejay Canaria
 */

@ManagedBean
@ViewScoped
public class RegisterBean extends AbstractBean implements Serializable {
    
    private User user = new User();
    
    public RegisterBean() {
    }
    
    public String register() {
        System.out.println("TEST");
        UserService userService = serviceFactory.createUserService();
        
        try {
            userService.registerUser(user);
            
            managedBeanUtility.displayInformationMessage("Your account was successfully added.","register");
        } catch(Exception ex) {
            managedBeanUtility.handleException(ex,"register");
        }
        
        clearFields();
        
        return null;
    }
    
    @Override
    public void clearFields() {
        this.user.setEmailAddress("");
        this.user.setFirstName("");
        this.user.setLastName("");
        this.user.setPassword("");
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
