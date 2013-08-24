/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.exception.OnQuiryServiceException;
import com.onsemi.onquiry.service.UserService;
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
    public void registerUser(User user) throws OnQuiryServiceException, Exception {
        //TODO: Perform other validations here
        logger.debug("registerUser(" + user.toString() + ")");
        
        entityManager.startTransaction();
        
        try {
            
            entityManager.persist(user);
            entityManager.commitTransaction();
        } catch(Exception exception) {
            entityManager.rollbackTransaction();
        } finally {
            entityManager.endTransaction();
        }
        
        
    }
    
}
