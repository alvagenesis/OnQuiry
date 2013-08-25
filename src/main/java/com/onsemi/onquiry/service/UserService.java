/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.exception.OnQuiryServiceException;

/**
 *
 * @author ffzwqy
 */
public interface UserService {
    
    void registerUser(User user) throws Exception;
    boolean isUsedEmailAddress(String emailAddress) throws Exception;
    
}
