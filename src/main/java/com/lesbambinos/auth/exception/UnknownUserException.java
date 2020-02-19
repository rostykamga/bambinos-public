/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.auth.exception;

/**
 *
 * @author Rostand
 */
public class UnknownUserException extends Exception{
    
    public UnknownUserException(){
        super();
    }
    
    public UnknownUserException(String message){
        super(message);
    } 
}
