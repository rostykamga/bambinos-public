/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.extra;



import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

public class ExtendedCheckBoxTreeCellFactory<T> implements Callback<TreeView<T>, TreeCell<T>>{
    @Override
    public TreeCell<T> call(TreeView<T> tv){
        return new ExtendedCheckBoxTreeCell<>();
    }
}
