/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service.hibernate;

import com.onsemi.onquiry.entity.User;
import com.onsemi.onquiry.service.ServiceFactory;
import com.onsemi.onquiry.service.UserService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author ffzwqy
 */
public class HibernateServiceFactory implements ServiceFactory {
    
    @Override
    public UserService createUserService() {
        SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        HibernateEntityManager entityManager = new HibernateEntityManager(sessionFactory, User.class);
        return new HibernateUserService(entityManager);
    }
    
    
    
}
