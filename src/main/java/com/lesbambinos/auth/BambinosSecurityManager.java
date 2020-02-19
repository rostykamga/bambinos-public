/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.auth;


import com.lesbambinos.auth.exception.InvalidCredentialsException;
import com.lesbambinos.auth.exception.UnknownUserException;
import com.lesbambinos.model.EmployeeModel;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 *
 * @author Rostand
 */
public class BambinosSecurityManager {
    
    private static AuthenticatedEmployee instance;
    
    public static AuthenticatedEmployee getAuthenticatedEmployee(){
        return instance;
    }
    
    
    public static AuthenticatedEmployee authenticate(String username, String password)throws InvalidCredentialsException, UnknownUserException{
        EmployeeModel model = new EmployeeModel();
        if (model.checkUser(username)) {

            if (model.checkPassword(username, password)) {
                return instance = new AuthenticatedEmployee(model.getEmployeeByUsername(username));
            } 
            else {
                throw new InvalidCredentialsException("Mot de passe erroné!");
            }
        }
        throw new UnknownUserException("Utilisateur inconnu!");
    }
    
    public static void logoutEmployee(AuthenticatedEmployee empl){
        instance.logout();
        instance = null;
    }
    
    public static void controlAceessRight(Node node, boolean accessible){
        if(node instanceof TextField){
            ((TextField)node).setDisable(!accessible);
        }
        else{
            node.setManaged(accessible);
            node.setVisible(accessible);
        }
    }
}
