/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.util;

import javafx.scene.control.ToggleButton;

/**
 *
 * @author Rostand
 */
public class ToggleButtonSelectionUtil {
    
    public static void selectExclusiveOption(int option, ToggleButton... btns){
        for(int i = 0; i < btns.length; i++)
            btns[i].setSelected(i==option);
    }
    
    public static int getExclusiveSelectedOption(ToggleButton... buttons){
        for(int i=0; i < buttons.length; i++)
            if(buttons[i].isSelected())
                return i;
        return -1;
    }
}
