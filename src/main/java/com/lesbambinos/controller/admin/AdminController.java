package com.lesbambinos.controller.admin;

import com.lesbambinos.controller.AbstractController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class AdminController extends AbstractController {

    @FXML private TabPane tabPane;
    
    private static final String[] MODULES = {"schoolstructure", "tuition", "discountrequest"};
    private final Map<String, AdminModuleController> moduleControllersMap = new HashMap<>();
    
    @Override
    protected void initUI(){
        
        loadAdminModules();
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            AdminModuleController controller = moduleControllersMap.get(newValue.getText());
            if(controller != null)
                controller.loadData();
        });
    }
    
    
    /**
     * Loads each admin module in its own tab
     */
    private void loadAdminModules(){
        
        AdminModuleController firstModuleController = null;
        
        for(String module : MODULES){
            try {
                String moduleMainPage = "/fxml/admin/modules/"+module+"/Main.fxml";

                FXMLLoader loader = new FXMLLoader(getClass().getResource(moduleMainPage));
           
                Parent root = loader.load();
                AdminModuleController controller = loader.getController();
                if(firstModuleController == null)
                    firstModuleController = controller;
                moduleControllersMap.put(controller.getModuleName(), controller);
                Tab tab = new Tab(controller.getModuleName(), root);
                tabPane.getTabs().add(tab);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        
        if(firstModuleController != null){
            firstModuleController.loadData();
        }
    }
    
    
    @Override
    protected String getPageName() {
        return "Admin";
    }

    @Override
    protected Object loadData() {
        return null;
    }

    @Override
    protected void dataLoaded(Object data) {
    }   
}