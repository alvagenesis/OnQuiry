/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.dao.UserDao;
import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.exception.OnQuiryServiceException;
import com.onsemi.onquiry.service.UserService;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 *
 * @author ffzwqy
 */
public class HibernateUserService implements UserService {

    private UserDao userDao;
    Logger logger = Logger.getLogger(HibernateUserService.class);
    
    public HibernateUserService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public void registerUser(User user) throws OnQuiryServiceException, Exception {
        //TODO: Perform other validations here
        logger.debug("registerUser(" + user.toString() + ")");
        
        try {
            userDao.addUser(user);
        } catch(Exception ex) {
            logger.fatal("registerUser: " + ex.getLocalizedMessage());
            throw ex;
        }
        
    }
    
}
