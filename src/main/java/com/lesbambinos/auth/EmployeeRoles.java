/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.auth;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rostand
 */
public class EmployeeRoles {
    
    public static final String ADMIN_ROLE = "Administrateur"; 
    public static final String DIRECTOR_ROLE = "Directeur"; 
    public static final String TEACHER_ROLE = "Enseignant"; 
    public static final String BURSAR_ROLE = "Econome"; 
    public static final String GUEST_ROLE = "Invité"; 
    
    public static ObservableList<String> ROLES = FXCollections.observableArrayList(
            ADMIN_ROLE,
            DIRECTOR_ROLE,
            TEACHER_ROLE,
            BURSAR_ROLE,
            GUEST_ROLE);

}
