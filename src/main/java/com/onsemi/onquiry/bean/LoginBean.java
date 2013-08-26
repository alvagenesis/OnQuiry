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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ejay Canaria
 */
@ManagedBean
@ViewScoped
public class LoginBean extends AbstractBean implements Serializable {

    private String emailAddress;
    private String password;
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    public void checkLogin() {
        HttpServletRequest httpServletRequest = managedBeanUtility.getHttpServletRequest();
        HttpSession httpSession = httpServletRequest.getSession();
        if(httpSession.getAttribute("user") != null) {
            managedBeanUtility.redirectToPage("/home");
        }
    }
    
    public String login() {
        
        UserService userService = serviceFactory.createUserService();
        
        try {
            User user = userService.login(emailAddress, password);
            user.setPassword("");
            HttpServletRequest request = managedBeanUtility.getHttpServletRequest();
            request.getSession(true).setAttribute("user", user);
            return "/html/home?faces-redirect=true";
        } catch(Exception ex) {
            managedBeanUtility.handleException(ex,"login");
        }
        
        return null;
    }
    
    @Override
    public void clearFields() {
        this.emailAddress = "";
        this.password = "";
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
