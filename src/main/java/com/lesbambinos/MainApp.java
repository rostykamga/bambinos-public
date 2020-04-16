package com.lesbambinos;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApp extends Application {

    private Stage  primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Platform.setImplicitExit(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Classe.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("LES BAMBINOS :: Version 1.0");
        primaryStage.getIcons().add(new Image("/images/logo.png"));
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        
        primaryStage.show();
    }

    
    @Override
    public void stop(){
        if(HibernateUtil.getSessionFactory() != null)
            HibernateUtil.getSessionFactory().close();
    }
    
    public static void main(String[] args) {
        if(HibernateUtil.setSessionFactory())
            launch(args);
        else{
            System.out.println("Erreur de lancement de l'application");
        }
    }

}
