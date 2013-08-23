/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.dao.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ffzwqy
 */
public abstract class HibernateBaseDao<T> {
    
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("onquiry");
    

}
