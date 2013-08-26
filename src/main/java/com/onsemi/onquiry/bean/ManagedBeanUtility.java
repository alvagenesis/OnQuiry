/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.service.ServiceException;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ejay Canaria
 */
public class ManagedBeanUtility {

    /**
     * Performs a page redirect.
     * @param pageName 
     */
    public void redirectToPage(String pageName) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(pageName);
        } catch(IOException ioEx) {
            handleException(ioEx);
        }
    }
    
    /**
     * 
     * @return {@link HttpSession}
     */
    public HttpSession getHttpSession() {
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return httpServletRequest.getSession();
    }
    
    /**
     * 
     * @return {@link HttpServletRequest}
     */
    public HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    /**
     * Displays the exception to the page as a global message.
     * @param exception 
     */
    public void handleException(Exception exception) {
        handleException(exception, null);
    }
    
    /**
     * Displays the exception to the page for a specific client ID.
     * @param exception
     * @param clientId 
     */
    public void handleException(Exception exception, String clientId) {
        if(exception instanceof ServiceException) {
            displayWarningMessage(exception.getMessage(), clientId);
        } else {
            displayErrorMessage(exception.getMessage(), clientId);
        }
    }
    
    /*********************** Message Related Utilities ************************/
    /**
     * Displays information message as a global message.
     * @param message 
     */
    public void displayInformationMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message, null);
    }
    
    /**
     * Displays information message for a specific client ID.
     * @param message
     * @param clientId 
     */
    public void displayInformationMessage(String message, String clientId) {
        addMessage(FacesMessage.SEVERITY_INFO, message, clientId);
    }
    
    /**
     * Displays warning message as a global message.
     * @param message 
     */
    public void displayWarningMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, message, null);
    }
    
    /**
     * Displays warning message for a specific client ID.
     * @param message
     * @param clientId 
     */
    public void displayWarningMessage(String message, String clientId) {
        addMessage(FacesMessage.SEVERITY_WARN, message, clientId);
    }
    
    /**
     * Displays error message as a global message.
     * @param message 
     */
    public void displayErrorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, message, null);
    }
    
    /**
     * Displays error message for a specific client ID.
     * @param message
     * @param clientId 
     */
    public void displayErrorMessage(String message, String clientId) {
        addMessage(FacesMessage.SEVERITY_ERROR, message, clientId);
    }
    
    /**
     * Adds message to the current faces context instance which will 
     * be displayed on the page.
     * @param severity
     * @param message
     * @param clientId 
     */
    private void addMessage(Severity severity, String message, String clientId) {
        FacesMessage facesMessage = new FacesMessage(severity, message, message);
        FacesContext.getCurrentInstance().addMessage(clientId, facesMessage);
    }
    
}
