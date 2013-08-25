/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.exception.OnQuiryServiceException;
import com.onsemi.onquiry.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author ffzwqy
 */
public class HibernateUserService implements UserService {
    
    Logger logger = Logger.getLogger(HibernateUserService.class);
    HibernateEntityManager<User> entityManager;
    
    public HibernateUserService(HibernateEntityManager<User> entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void registerUser(User user) throws Exception {
        //TODO: Perform other validations here
        logger.debug("registerUser(" + user.toString() + ")");
        
        if(isUsedEmailAddress(user.getEmailAddress())) {
            throw new OnQuiryServiceException("Email address " + user.getEmailAddress() + " is already used.");
        }
        
        entityManager.startTransaction();
        
        try {
            
            entityManager.persist(user);
            entityManager.commitTransaction();
        } catch(Exception exception) {
            entityManager.rollbackTransaction();
            logger.fatal("registerUser", exception);
            throw exception;
        } finally {
            entityManager.closeSession();
        }
        
    }

    @Override
    public boolean isUsedEmailAddress(String emailAddress) throws Exception {
        
        logger.debug("isUsedEmailAddress(" + emailAddress + ")");
        
        entityManager.openSession();
        
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("emailAddress", emailAddress);
        
        try {
            User user = entityManager.findOneResult(User.FIND_USER_BY_EMAIL, parameter);
            if(user != null) {
                return true;
            }
        } catch(Exception exception) {
            logger.fatal("isUsedEmailAddress", exception);
            throw exception;
        } finally {
            entityManager.closeSession();
        }
        
        return false;
    }
    
}
