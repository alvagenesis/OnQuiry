package com.onsemi.onquiry.bean;

import com.onsemi.onquiry.service.ServiceFactory;
import com.onsemi.onquiry.service.hibernate.HibernateServiceFactory;

/**
 * Abstract bean that contains a service factory and managed bean utility.
 * 
 * @author Ejay Canaria
 */
public abstract class AbstractBean {
    
    protected ManagedBeanUtility managedBeanUtility = new ManagedBeanUtility();
    protected ServiceFactory serviceFactory = new HibernateServiceFactory();
    
    public abstract void clearFields();
}
