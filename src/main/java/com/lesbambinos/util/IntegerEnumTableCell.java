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
 * @param <T>
 */
public class IntegerEnumTableCell<T> extends TableCell<T, Integer>{
    
    private final String[] values;
    
    public IntegerEnumTableCell(String... values){
        this.values = values;
    }
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        setText(empty ? null : values[item]);
    }
}
