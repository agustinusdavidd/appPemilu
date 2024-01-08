/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pemilu.Controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author user
 */
public class LoginController {
    
    private String password;
    private String NIK;
    
    public LoginController(){};
    
    public void setNIK(String n) throws Exception {
        if(n.length()<16){
            throw new Exception("NIK terlalu pendek");
        } else if (isContainLetter(n)){
            throw new Exception("NIK harus berupa huruf");
        } else if (n.length()>16){
            throw new Exception("NIK terlalu panjang");
        } else {
            NIK = n;
        }
    }
    
    public void setPassword(String p) throws Exception {
        if(p.trim()!= null && !p.isEmpty() && p.length() > 5){
            password = p;
        } else {
            if(p.trim()== null && p.isEmpty()){
                throw new Exception("Password tidak boleh kosong");
            } else if (p.length() > 5){
                throw new Exception("Password panjangnya kurang dari 6");
            }   
        }
    }

    private String hash256(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (int i = 0; i < encodedHash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedHash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }
    
    private boolean isContainLetter(String n) {
        for (char c : n.toCharArray()){
            if(Character.isLetter(c)){
                return true;
            }
        }
        return false;
    }
}
