/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.dao.hibernate;

import com.onsemi.onquiry.dao.UserDao;
import com.onsemi.onquiry.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

/**
 *
 * @author ffzwqy
 */
public class HibernateUserDao implements UserDao {

    private SessionFactory factory;
    
    public HibernateUserDao() {
        factory = new AnnotationConfiguration().configure().buildSessionFactory();
    }
    
    @Override
    public void addUser(User user) throws Exception {
        Session session = null;
        
        try {
            session = factory.openSession();
            session.getTransaction().begin();
            session.persist(user);
            session.getTransaction().commit();
        } catch(Exception ex) {
            session.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        } 
    }
    
    
    
}
