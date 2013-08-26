/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iamejay
 */
public class ServiceUtilityTest {
    
    public ServiceUtilityTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testEncryptPassword() {
        String test = ServiceUtility.encryptPassword("test");
        String test2 = ServiceUtility.encryptPassword("test");
        
        assertEquals(test2, test);
    }
}