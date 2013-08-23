/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.dao;

import com.onsemi.onquiry.entity.User;

/**
 *
 * @author ffzwqy
 */
public interface UserDao {
    
    void addUser(User user) throws Exception;
    
}
