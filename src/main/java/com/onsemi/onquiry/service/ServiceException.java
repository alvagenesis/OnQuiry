package com.onsemi.onquiry.service;

/**
 *
 * An exception that is thrown by the service layer when 
 * business rule related validation.
 * 
 * @author Ejay Canaria
 */
public class ServiceException extends Exception {
    
    public ServiceException(String message) {
        super(message);
    }
}
