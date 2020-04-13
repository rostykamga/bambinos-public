/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.util;

import javafx.scene.control.TableCell;

/**
 *
 * @author Rostand
 */
public class BooleanTableCell<T> extends TableCell<T, Boolean>{
    
    private final String trueValue, falseValue;
    
    public BooleanTableCell(String trueValue, String falseValue){
        this.trueValue = trueValue;
        this.falseValue = falseValue;
    }
    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : item ? trueValue :falseValue);
    }
}
