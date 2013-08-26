package com.onsemi.onquiry.service;

import com.onsemi.onquiry.entity.User;

/**
 *
 * @version 1.0
 * @author Ejay Canaria
 */
public interface UserService {
    
    /**
     * Registers a user
     * @param user
     * @throws Exception 
     */
    void registerUser(User user) throws Exception;

    /**
     * Checks if email address is already used
     * @param emailAddress
     * @return
     * @throws Exception 
     */
    boolean isUsedEmailAddress(String emailAddress) throws Exception;
    
    /**
     * Performs login validation using email address and password.
     * A service exception will be thrown, if email address and password
     * is not found in the database. Otherwise, a user entity will
     * be returned.
     * @param emailAddress
     * @param password
     * @return
     * @throws Exception 
     */
    User login(String emailAddress, String password) throws Exception;
    
    /**
     * Updates the password of a user.
     * A service exception will be thrown if the old password
     * is invalid. Otherwise, the password will be updated
     * using the new password given.
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @throws Exception 
     */
    void changePassword(Long userId, String oldPassword, String newPassword) throws Exception;
}
