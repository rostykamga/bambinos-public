/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller;

import com.lesbambinos.AppException;
import com.lesbambinos.auth.AuthenticatedEmployee;
import com.lesbambinos.auth.BambinosSecurityManager;
import com.lesbambinos.controller.drawer.DrawerController;
import com.lesbambinos.controller.login.LoginController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Rostand
 */
public abstract class AbstractController implements Initializable{
    
    @FXML protected VBox progressPane;
    @FXML protected Button menu;
    @FXML protected VBox drawerContainer;
    @FXML protected Label userLabel, pageName;
    
    protected double xOffset = 0;
    protected double yOffset = 0;
    protected DrawerController drawerController;
    protected AuthenticatedEmployee currentUser;
    protected ResourceBundle R;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        R = rb;
        //loads the drawer
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Drawer.fxml"));
            VBox root = (VBox)loader.load();
            drawerController = loader.getController();
            
            VBox.setVgrow(root, Priority.ALWAYS);
            drawerContainer.getChildren().clear();
            drawerContainer.getChildren().add(root);
            
            //checkPermissions();
                        
        } catch (IOException ex) {
            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        catch (NoSuchFieldException ex) {
//            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        if(userLabel != null)
            userLabel.setText(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity().getUserName());
                
        pageName.setText(getPageName());
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

//        SecurityManager.controlAceessRight(drawerController.adminButton, currentUser.hasPermission(Permissions.AdminRigth));
//        SecurityManager.controlAceessRight(drawerController.staffButton, currentUser.hasPermission(Permissions.EmployeeRight));
//        SecurityManager.controlAceessRight(drawerController.categoryButton, currentUser.hasPermission(Permissions.ModCategRight));
//        SecurityManager.controlAceessRight(drawerController.productButton, currentUser.hasPermission(Permissions.ModProdRight));
//        SecurityManager.controlAceessRight(drawerController.purchaseButton, currentUser.hasPermission(Permissions.ModStockRight));
//        SecurityManager.controlAceessRight(drawerController.supplierButton, currentUser.hasPermission(Permissions.ModSuppRight));
//        SecurityManager.controlAceessRight(drawerController.posButton, currentUser.hasPermission(Permissions.PosRight));
//        SecurityManager.controlAceessRight(drawerController.salesButton, currentUser.hasPermission(Permissions.SaleRight));
//        SecurityManager.controlAceessRight(drawerController.configButton, currentUser.hasPermission(Permissions.AdminRigth));
    }
    
    protected abstract Object loadData();
    protected abstract void initUI();
    protected abstract void dataLoaded(Object data);
    protected abstract String getPageName();
    

    @FXML
    public void logoutAction(ActionEvent event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("QITECH POS:: Version 1.0");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setOnShown((e)->{loginController.focusUsernameTextfield();});
        stage.show();
    }
    
    public void setProgressVisible(boolean visible){
    	if(progressPane != null)
    		progressPane.setVisible(visible);
    }
}
