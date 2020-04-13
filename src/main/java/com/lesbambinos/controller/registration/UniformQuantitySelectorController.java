/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller.registration;

import com.lesbambinos.config.AppConfig;
import com.lesbambinos.entity.UniformType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Rostand
 */
public class UniformQuantitySelectorController implements Initializable {
    
    @FXML private TextField quantityField;
    @FXML private Label label;
    @FXML private Node root;
    
    private Map.Entry<UniformType, Integer> entry;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    protected void addAction(ActionEvent event){
        
        int value = Integer.parseInt(quantityField.getText());
        entry.setValue(++value);
        quantityField.setText(String.valueOf(entry.getValue()));
    }
    
    @FXML
    protected void substractAction(ActionEvent event){
        
        int value = Integer.parseInt(quantityField.getText());
        if(value > 0){
            entry.setValue(--value);
            quantityField.setText(String.valueOf(entry.getValue()));
        }
    }
    
    public void setQuantity(Integer value){
        entry.setValue(value);
        quantityField.setText(String.valueOf(entry.getValue()));
    }
    
    public int getQuantity(){
        return entry.getValue();
    }
    
    public UniformType getType(){
        return entry.getKey();
    }
    
    public void setDisable(boolean disable){
        root.setDisable(disable);
    }
    
    public boolean isDisable(){
        return root.isDisable();
    }
    
    
    public void setEntry(Map.Entry<UniformType, Integer> entry){
        this.entry = entry;
        label.setText(entry.getKey().getType());
        quantityField.setText(String.valueOf(entry.getValue()));
    }
    
    public static HBox createComponent(Map.Entry<UniformType, Integer> entry, List controllers){
        try {
            
            FXMLLoader loader = new FXMLLoader(AppConfig.class.getResource("/fxml/registration/UniformQuantitySelector.fxml"));
            HBox root = loader.load();
            UniformQuantitySelectorController controller = loader.getController();
            controller.setEntry(entry);
            controllers.add(controller);
            
            return root;
        } catch (IOException ex) {
            Logger.getLogger(UniformQuantitySelectorController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
