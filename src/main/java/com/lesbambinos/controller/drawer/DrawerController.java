package com.lesbambinos.controller.drawer;

import com.lesbambinos.config.AppConfig;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Rostand
 */
public class DrawerController implements Initializable {

    @FXML public VBox root;
    
    @FXML public Button homeButton;
    @FXML public Button adminButton;
    @FXML public Button classesButton;
    @FXML public Button registrationButton;
//    @FXML public Button posButton;
//    @FXML public Button productButton;
//    @FXML public Button categoryButton;
    @FXML public Button cashButton;
//    @FXML public Button purchaseButton;
//    @FXML public Button salesButton;
    @FXML public Button staffButton;
    @FXML public Button reportButton;
    @FXML public Button configButton;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
        windows("/fxml/Classes.fxml", "Classes", event);
    }
    
    @FXML
    public void registrationAction(ActionEvent event) throws Exception {
        
        windows("/fxml/Registration.fxml", "Inscriptions", event);
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
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.strings");
        double width = ((Node) event.getSource()).getScene().getWidth();
        double height = ((Node) event.getSource()).getScene().getHeight();

        Parent parent = FXMLLoader.load(getClass().getResource(path), bundle);
        Scene scene = new Scene(parent, width, height);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setScene(scene);
        
        // set the selected button style
        String selectedBtnId = ((Button)event.getSource()).getId();
        if(selectedBtnId != null){
            Node n = getChildrenById(parent, selectedBtnId);
            if(n!=null)
                n.getStyleClass().add("menu-buttons-selected");
        }
        
        stage.show();
    }
}
