/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller;

import com.lesbambinos.AppException;
import com.lesbambinos.AuthenticatedEmployee;
import com.lesbambinos.BambinosSecurityManager;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

/**
 *
 * @author Rostand
 */
public abstract class AbstractController implements Initializable{
    
    @FXML protected VBox progressPane;
    @FXML protected Button menu;
    @FXML protected VBox drawer;
    @FXML protected Label userLabel, pageName;
    @FXML protected AnchorPane contentPane;
    
    
    protected double xOffset = 0;
    protected double yOffset = 0;
    protected AuthenticatedEmployee currentUser;
    protected ResourceBundle R;
    protected BooleanProperty dataloading = new SimpleBooleanProperty(false);
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        R = rb;
                
        initUI();
        final Task<Object> dataLoader = new Task<Object>() {
            @Override
            protected Object call() throws Exception {
                return loadData();
            }
        };
        dataLoader.setOnFailed((WorkerStateEvent event) -> {
            setProgressVisible(false);
            new AppException("Erreur Inattendue",
                    "Une erreur inattendue est survenue lors \ndu chargement des données",
                    dataLoader.getException()).show();
        });
        
        dataLoader.setOnSucceeded((WorkerStateEvent event) -> {
            setProgressVisible(false);
            try {
                dataLoaded(dataLoader.get());
            } catch (Exception ex) {
                new AppException("Erreur Inattendue",
                        "Une erreur inattendue est survenue lors \ndu chargement des données",
                        ex).show(); 
            }
        });
        setProgressVisible(true);
        new Thread(dataLoader).start();
        
    } 
    
    protected void checkPermissions() throws NoSuchFieldException{
        currentUser = BambinosSecurityManager.getAuthenticatedEmployee();

    }
    

     
    protected EventHandler<KeyEvent> getSearchFieldHandler(){
         return null;
    }
    
    protected abstract Object loadData();
    protected abstract void initUI();
    protected abstract void dataLoaded(Object data);
    protected abstract String getPageName();
    
    public void setProgressVisible(boolean visible){
    	if(progressPane != null)
    		progressPane.setVisible(visible);
    }
    
    public BooleanProperty dataloadingProperty(){
        return dataloading;
    }
}
