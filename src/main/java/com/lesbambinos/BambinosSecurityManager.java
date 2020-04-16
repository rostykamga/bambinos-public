/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos;

import com.lesbambinos.model.EmployeeModel;

/**
 *
 * @author Rostand
 */
public class BambinosSecurityManager {
    private static EmployeeModel model =  new EmployeeModel();
    private static AuthenticatedEmployee instance = new AuthenticatedEmployee(model.getEmployees().get(0));
    
    public static AuthenticatedEmployee getAuthenticatedEmployee(){
        return instance;
    }
    
}
