/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pemilu.Model;

/**
 *
 * @author user
 */
public class Login {
    
    private String password;
    private String NIK;
    
    public Login(){};
    
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
            if(p.trim()!= null && !p.isEmpty()){
                throw new Exception("Password tidak boleh kosong");
            } else if (p.length() > 5){
                throw new Exception("Password panjangnya kurang dari 6");
            }   
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
