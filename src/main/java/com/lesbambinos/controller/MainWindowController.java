/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.controller;

import com.lesbambinos.auth.AuthenticatedEmployee;
import com.lesbambinos.auth.BambinosSecurityManager;
import com.lesbambinos.config.AppConfig;
import com.lesbambinos.controller.login.LoginController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class MainWindowController implements Initializable{
    
    @FXML protected VBox progressPane;
    @FXML protected Button menu;
    @FXML protected VBox drawer;
    @FXML protected Label userLabel, pageName;
    @FXML protected AnchorPane contentPane;
    
    @FXML public Button homeButton;
    @FXML public Button adminButton;
    @FXML public Button classesButton;
    @FXML public Button registrationButton;
    @FXML public Button uniformsButton;
    @FXML public Button cashButton;
    @FXML public Button staffButton;
    @FXML public Button reportButton;
    @FXML public Button configButton;
    
    protected double xOffset = 0;
    protected double yOffset = 0;
    protected AuthenticatedEmployee currentUser;
    private ChangeListener<Boolean> dataloadingListener;
    private AbstractController currentController;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if(userLabel != null)
            userLabel.setText(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity().getUserName());
             
        dataloadingListener = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean loading) -> {
            setProgressVisible(loading);
        };
        
        drawerAction();
        
        homeButton.fire();
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
    
     protected void drawerAction() {

        TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
        menu.setOnAction((ActionEvent evt) -> {
            if (drawer.getTranslateX() != 0) {
                openNav.play();
                menu.getStyleClass().remove("hamburger-button");
                menu.getStyleClass().add("open-menu");
            } else {
                closeNav.setToX(-(drawer.getWidth()));
                closeNav.play();
                menu.getStyleClass().remove("open-menu");
                menu.getStyleClass().add("hamburger-button");
            }
        });
    }

    
    @FXML
    public void homeAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Home.fxml", "Accueil", event);
    }
    
    @FXML
    public void adminAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Admin.fxml", "Admin", event);
    }
    
    @FXML
    public void classesAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Classe.fxml", "Classes", event);
    }
    
    @FXML
    public void registrationAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Student.fxml", "Inscriptions", event);
    }
    
    @FXML
    public void uniformsAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Uniforme.fxml", "Uniformes", event);
    }

    @FXML
    public void cashAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Caisse.fxml", "Caisse", event);
    }
    
    @FXML
    public void staffAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Employee.fxml", "Personnel", event);
    }

    @FXML
    public void reportAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Report.fxml", "Etats", event);
    }
    
    @FXML
    public void configAction(ActionEvent event) throws Exception {
        Window s = ((Button)event.getSource()).getScene().getWindow();
        AppConfig.showConfigPanel(false, s);
    }

    
    private Node getChildrenById(Parent parent, String childId){
        Stack<Parent> allNodes = new Stack();
        allNodes.add(parent);
        
        while(!allNodes.isEmpty()){
            Parent p = allNodes.pop();
            if(childId.equalsIgnoreCase(p.getId()))
                return p;
            else{
                for(Node node : p.getChildrenUnmodifiable()){
                    if(node instanceof Parent)
                        allNodes.push((Parent)node);
                }
            }
        }
        return null;
    }
    
    protected void windows(String path, String title, ActionEvent event) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent parent = loader.load();
        AbstractController controller = loader.getController();
        if(currentController !=null)
            currentController.dataloadingProperty().removeListener(dataloadingListener);
        
        currentController = controller;
        controller.dataloadingProperty().addListener(dataloadingListener);
        
        contentPane.getChildren().clear();
        contentPane.getChildren().add(parent);
        AnchorPane.setBottomAnchor(parent, 0.0);
        AnchorPane.setLeftAnchor(parent, 0.0);
        AnchorPane.setRightAnchor(parent, 0.0);
        AnchorPane.setTopAnchor(parent, 0.0);
        
       pageName.setText(controller.getPageName());
        
        // set the selected button style
        String selectedBtnId = ((Button)event.getSource()).getId();
        if(selectedBtnId != null){
            Node n = getChildrenById(parent, selectedBtnId);
            if(n!=null)
                n.getStyleClass().add("menu-buttons-selected");
        }
        menu.fire();
    }
    

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
        stage.setTitle("LES BAMBINOS:: Version 1.0");
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
