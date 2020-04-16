/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller;

import com.lesbambinos.entity.AbstractEntity;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Rostand
 */
public abstract class FormController implements Initializable{
     
    @FXML protected Label formHeaderTitle;
    
    protected static final int STATUS_SUCCESS = 0;
    protected static final int STATUS_FAIL = 1;
    
    protected ResourceBundle R;
    protected AbstractEntity entity;
    protected int formStatus;
    protected String err_msg;
    protected boolean canceled;
   
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        R = rb;
    } 
    
    protected abstract void doSave();
    protected abstract boolean validateInput();
    protected abstract Alert getSuccessAlert();
    protected abstract Alert getFailAlert();
    
    public void setData(AbstractEntity e){
        entity = e;
    }

    public AbstractEntity getEntity() {
        return entity;
    }
    
    @FXML
    public void handleCancel(ActionEvent event) {
        canceled = true;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML 
    public void handleSave(ActionEvent event) {

        if (validateInput()) {

            doSave();
            if(formStatus == STATUS_FAIL) {
	            Alert alert = getFailAlert();
	            if(alert != null) {
	           	 alert.showAndWait();
	            }
            }
            else{
                canceled =false;
                ((Node) (event.getSource())).getScene().getWindow().hide();
                 Alert alert = getSuccessAlert();
                 if(alert != null) {
                	 alert.showAndWait();
                 }
            }
            
        }
    }
    
    @FXML
    public void closeAction(ActionEvent event) {
        canceled = true;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    public boolean isCanceled(){
        return canceled;
    }
    
    private static double xOffset, yOffset;
    
    public static AbstractEntity showForm(String form, AbstractEntity entity){
        
        try {
            FXMLLoader loader = new FXMLLoader(FormController.class.getResource("/fxml/"+form));
            Parent root = loader.load();
            FormController controller = loader.getController();
            controller.setData(entity);
            root.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            Stage stage = new Stage();
            
            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            Scene scene = new Scene(root);
            stage.setTitle("LES BAMBINOS :: Version 1.0");
            stage.getIcons().add(new Image("/images/logo.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            return controller.getEntity();
        } catch (IOException ex) {
            Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
        
}
