/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller.admin;

import com.lesbambinos.MainApp;
import com.lesbambinos.config.AppConfig;
import com.lesbambinos.util.AppUtils;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author rostand
 */
public class ConfigController implements Initializable {
    
    
    //database fields
    @FXML private TextField hostField, portField, dbnameField, userField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> dbmsCombobox;
    
    //ticket printing settings components
    @FXML private Accordion accordion;
    
    @FXML private TitledPane databasePanel;
    
    private boolean isStartupConfig = true;
    private Window parentStage;
    
    private  AppConfig currentConfig;
    private final AppConfig defauConfig = new AppConfig();
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        accordion.setExpandedPane(databasePanel);
        setData(defauConfig);
    }   
    
    public void setInitialConfig(AppConfig config, boolean isStartupConfig, Window parentStage){
        
        this.parentStage = parentStage;
        this.isStartupConfig =  isStartupConfig;
        
        if(isStartupConfig){
            accordion.setExpandedPane(databasePanel);
        }
        
        setData(config == null? new AppConfig() : config);
    }
    
    private void setData(AppConfig conf){
        if(!defauConfig.equals(conf))
            currentConfig = new AppConfig(conf);
        
        hostField.setText(String.valueOf(conf.getHost()));
        portField.setText(String.valueOf(conf.getPort()));
        dbnameField.setText(String.valueOf(conf.getDbname()));
        userField.setText(String.valueOf(conf.getUser()));
        passwordField.setText(conf.getPassword());
        dbmsCombobox.setValue(conf.getDbms());

    }
    
    
   
    
    double xOffset = 0;
    double yOffset = 0;
   
    
   
    
    @FXML
    public void applyAction(ActionEvent event) {
        
        if(validateInput()){
            
            
            AppConfig config = AppConfig.getCurrentConfig();
            if(config == null)
                config = new AppConfig();
            
            config.setDbms(dbmsCombobox.getValue());
            config.setDbname(dbnameField.getText());
            config.setHost(hostField.getText());
            config.setDbname(dbnameField.getText());
            config.setPassword(passwordField.getText());
            config.setPort(Integer.parseInt(portField.getText()));
            config.setUser(userField.getText());
            
            //if db settings changed
            if(!config.equals(currentConfig) && !isStartupConfig){
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Database configuration has changed !\n"
                        + "If you proceed with changes, you will be disconnected."
                        + "\nDo you want to contiune?", no, yes);
                
                alert.setTitle("Confirm changes");
                alert.setHeaderText("Database configuration changed");
                
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.orElse(no) == no) {
                    return;
                }
            }
            
            try {
                AppConfig.saveConfig(config, AppConfig.CONFIG_FILE_PATH);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Configuratoin updated!");
                alert.setContentText("Configuration is updated successfully !");
                alert.showAndWait();
                ((Node) (event.getSource())).getScene().getWindow().hide();
                
                if(!config.equals(currentConfig) && !isStartupConfig){
                    if(parentStage != null)
                        parentStage.hide();
                    MainApp.reload();
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Configuration error");
                alert.setHeaderText("An error occured");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    public void cancelAction(ActionEvent event) {
        if(currentConfig!=null)
            setData(currentConfig);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    public void defaultConfigAction(ActionEvent event) {
        if(defauConfig!= null)
            setData(defauConfig);
    }
    
    @FXML
    public void closeAction(ActionEvent event) {
        if(isStartupConfig){
            Platform.exit();
            System.exit(0);
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    private boolean validateInput() {

        String errorMessage = "";

        if (!AppUtils.isValidPositiveInteger(portField.getText())) {
            errorMessage += "Invalid input for port number !\n";
        }
        if (AppUtils.isEmptyOrNullString(hostField.getText())) {
            errorMessage += "Invalid input for db host !\n";
        }
        if (AppUtils.isEmptyOrNullString(dbnameField.getText())) {
            errorMessage += "Invalid input for db name !\n";
        }
        if (AppUtils.isEmptyOrNullString(userField.getText())) {
            errorMessage += "Invalid input for username !\n";
        }
                
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
    
}