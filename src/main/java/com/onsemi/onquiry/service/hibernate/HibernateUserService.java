package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.service.ServiceException;
import com.onsemi.onquiry.service.ServiceUtility;
import com.onsemi.onquiry.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * A Hibernate implementation of the UserService class
 * 
 * @author Ejay Canaria
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
            throw new ServiceException("Email address " + user.getEmailAddress() + " is already used.");
        }
        
        entityManager.startTransaction();
        
        try {
            user.setPassword(ServiceUtility.encryptPassword(user.getPassword()));
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

    @Override
    public User login(String emailAddress, String password) throws Exception {
        logger.debug("login(" + emailAddress + "," + password + ")");
        
        entityManager.openSession();
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("emailAddress", emailAddress);
        parameter.put("password", ServiceUtility.encryptPassword(password));
        
        try {
            
            User user = entityManager.findOneResult(User.FIND_USER_BY_EMAIL_AND_PASSWORD, parameter);
            if(user == null) {
                throw new ServiceException("Invalid email address or password");
            }
            
            return user;
        } catch(ServiceException serviceException) {
            logger.warn("login: " + serviceException);
            throw serviceException;
        } catch(Exception exception) {
            logger.fatal("login", exception);
            throw exception;
        } finally {
            entityManager.closeSession();
        }

    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) throws Exception {
        logger.debug("changePassword(" + id + ")");
        
        oldPassword = ServiceUtility.encryptPassword(oldPassword);
        newPassword = ServiceUtility.encryptPassword(newPassword);
        
        entityManager.startTransaction();
        
        try {
            User user = entityManager.findResultById(id);
            
            if(!oldPassword.equals(user.getPassword())) {
                throw new ServiceException("Invalid old password.");
            }
            
            user.setPassword(newPassword);
            
            entityManager.commitTransaction();
            
        } catch(ServiceException serviceException) {
            entityManager.rollbackTransaction();
            logger.warn("update: " + serviceException.getMessage());
        } catch(Exception exception) {
            entityManager.rollbackTransaction();
            logger.fatal("update", exception);
            throw exception;
        } finally {
            entityManager.closeSession();
        }
    }
    
    
    
}
