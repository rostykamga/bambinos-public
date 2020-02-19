/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.extra;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;


public class ExtendedCheckBoxTreeCell<T> extends CheckBoxTreeCell<T> implements ChangeListener<Boolean>{
    protected SimpleBooleanProperty linkedDisabledProperty;

    @Override
    public void updateItem(T item, boolean empty){
        super.updateItem(item, empty);

        if(item != null){
            TreeItem<T> treeItem = treeItemProperty().getValue();
            if(treeItem != null){
                if(treeItem instanceof ExtendedCheckBoxTreeItem<?>){
                    ExtendedCheckBoxTreeItem<T> checkItem = (ExtendedCheckBoxTreeItem<T>)treeItem;
                    if(checkItem != null){                               
                        if(linkedDisabledProperty != null) {
                            linkedDisabledProperty.removeListener(this);
                            linkedDisabledProperty = null;
                        }

                        linkedDisabledProperty = checkItem.disabledProperty;                        
                        linkedDisabledProperty.addListener(this);

                        setDisable(linkedDisabledProperty.get());
                    }
                }
            }           
        }
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldVal, Boolean newVal){       
        setDisable(newVal);
    }
}