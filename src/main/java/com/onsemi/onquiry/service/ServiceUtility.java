/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onsemi.onquiry.service;

import java.security.MessageDigest;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import sun.misc.BASE64Encoder;


/**
 *
 * @author Ejay Canaria
 */
public class ServiceUtility {
    
    
    public static String encryptPassword(String password) {
        String passwordHash = "";
        
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes("UTF-8"));
            byte[] raw = messageDigest.digest();
            passwordHash = (new BASE64Encoder()).encode(raw);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return passwordHash;
    }
    
}
