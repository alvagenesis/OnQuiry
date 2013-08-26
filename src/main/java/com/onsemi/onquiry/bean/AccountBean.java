/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.service.UserService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

/**
 * This bean is used to hold information related to the
 * currently logged in user.
 * 
 * @author Ejay Canaria
 */
@ManagedBean
@SessionScoped
public class AccountBean extends AbstractBean implements Serializable {
    
    private User user;
    private String oldPassword;
    private String newPassword;
    
    @PostConstruct
    public void init() {
        //This will retrieve the User object that is set by the login screen.
        HttpSession httpSession = managedBeanUtility.getHttpSession();
        this.user = (User) httpSession.getAttribute("user");
    }
    
    public void changePassword() {
        UserService userService = serviceFactory.createUserService();
        
        try {
            userService.changePassword(user.getId(), oldPassword, newPassword);
            managedBeanUtility.displayInformationMessage("Password successfully changed.", "changePassword");
        } catch(Exception exception) {
            managedBeanUtility.handleException(exception, "changePassword");
        }
    }
    
    public void logout() {
        HttpSession httpSession = managedBeanUtility.getHttpSession();
        httpSession.invalidate();
        managedBeanUtility.redirectToPage("/");
        
    }
    
    @Override
    public void clearFields() {
        //TODO - Clear fields here
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
