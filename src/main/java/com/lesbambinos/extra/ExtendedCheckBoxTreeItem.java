/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.extra;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBoxTreeItem;


public class ExtendedCheckBoxTreeItem<T> extends CheckBoxTreeItem<T>
{
    public SimpleBooleanProperty disabledProperty = new SimpleBooleanProperty(false);

    public ExtendedCheckBoxTreeItem(T t)
    {
        super(t);
    }

    public boolean isDisabled()
    {
        return disabledProperty.get();
    }

    public void setDisabled(boolean disabled)
    {
        disabledProperty.set(disabled);
    }
}