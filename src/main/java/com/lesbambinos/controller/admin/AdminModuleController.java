/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller.admin;

import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.model.AbstractEntityModel;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 *
 * @author Rostand
 */
public abstract class AdminModuleController implements Initializable{
    
    @FXML protected VBox progressPane, mainPane;
    
    public AdminModuleController() {
        this.logger = Logger.getLogger(getClass().getName());
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        mainPane.disableProperty().bind(progressPane.visibleProperty());
    }
    
    protected Task<Object> createTask(){
        
        Task<Object> dataloader = new Task() {
            @Override
            protected Object call() throws Exception {
                return _loadData();
            }
        };
        
        dataloader.setOnCancelled(event->setProgressVisible(false));
        dataloader.setOnFailed(event->setProgressVisible(false));
        dataloader.setOnSucceeded(event->{
            try {
                _onDataLoaded(dataloader.get());
            } catch (InterruptedException | ExecutionException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            setProgressVisible(false);
        });
        return dataloader;
    }
    
    public void loadData(){
        setProgressVisible(true);
        new Thread(createTask()).start();
    }
    
    protected abstract String getModuleName();
    protected abstract Object _loadData();
    protected abstract void _onDataLoaded(Object data);
    
    @FXML
    protected void refreshAction(ActionEvent event){
        loadData();
    }
    
    protected void setProgressVisible(boolean visible){
        progressPane.setVisible(visible);
    }
    
    protected void edit(String formname, ListView listview ){
        AbstractEntity entity = (AbstractEntity)listview.getSelectionModel().getSelectedItem();
        int index = listview.getItems().indexOf(entity);
       
        FormController.showForm(formname, entity);
        
        if(index!= -1)
            listview.getSelectionModel().select(index);
    }
    
    protected void delete(String message, AbstractEntity selected, ObservableList list, AbstractEntityModel model){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(message);
        alert.setContentText("Etes vous sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                model.delete(selected);
                list.clear();
                list.addAll(model.getAll());
            }
            catch(Exception ex){
                logger.log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }
    
    protected  final Logger logger;
}
