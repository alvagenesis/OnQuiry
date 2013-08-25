/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.exception.OnQuiryServiceException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 *
 * @author iamejay
 */
public class ManagedBeanUtility {

    
    public void handleException(Exception exception) {
        if(exception instanceof OnQuiryServiceException) {
            addWarningMessage(exception.getMessage());
        } else {
            addErrorMessage(exception.getMessage());
        }
    }
    
    /*********************** Message Related Utilities ************************/
    /**
     * 
     * @param message 
     */
    public void addInformationMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message);
    }
    
    /**
     * 
     * @param message 
     */
    public void addWarningMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, message);
    }
    
    /**
     * 
     * @param message 
     */
    public void addErrorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, message);
    }
    
    private void addMessage(Severity severity, String message) {
        FacesMessage facesMessage = new FacesMessage(severity, message, message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
}
