/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.dao.hibernate.HibernateUserDao;
import com.onsemi.onquiry.service.ServiceFactory;
import com.onsemi.onquiry.service.UserService;

/**
 *
 * @author ffzwqy
 */
public class HibernateServiceFactory implements ServiceFactory {

    @Override
    public UserService createUserService() {
        return new HibernateUserService(new HibernateUserDao());
    }
    
    
    
}
