package com.lesbambinos;

import com.lesbambinos.config.AppConfig;
import com.lesbambinos.controller.login.LoginController;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.jboss.logging.Logger;

public class MainApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    private Stage  primaryStage;
    
    private Task<Boolean> createTask(){
        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                return HibernateUtil.setSessionFactory();
            }
        };
        task.setOnFailed(e->{
            Logger.getLogger(MainApp.class).log(Logger.Level.ERROR, null, task.getException());
            Platform.exit();
            System.exit(0);
        });
        
        task.setOnSucceeded((e->{
            primaryStage.close();
            try{
                if(task.getValue()){
                    showLogin(new Stage());
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("An error has occured!");
                    alert.setHeaderText("Database Connection Error!");
                    alert.setContentText("Please check configuration");
                    alert.showAndWait();
                    AppConfig.showConfigPanel(true, null);
                    new Thread(createTask()).start();
                    primaryStage.show();
                }
            }
            catch(IOException ex){
                Logger.getLogger(MainApp.class).log(Logger.Level.ERROR, null, ex);
            }
        }));
        return task;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Platform.setImplicitExit(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Splash.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setWidth(450);
        primaryStage.setWidth(400);
        primaryStage.setTitle("LES BAMBINOS :: Version 1.0");
        primaryStage.getIcons().add(new Image("/images/logo.png"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        
        new Thread(createTask()).start();
        primaryStage.show();
    }

    private void showLogin(Stage stage) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("LES BAMBINOS :: Version 1.0");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setOnShown((e)->{loginController.focusUsernameTextfield();});
        Platform.setImplicitExit(true);
        stage.show();
    }
    
    public static void reload() throws Exception{
        if(HibernateUtil.getSessionFactory() != null)
            HibernateUtil.getSessionFactory().close();
        new MainApp().start(new Stage());
    }
    
    @Override
    public void stop(){
        if(HibernateUtil.getSessionFactory() != null)
            HibernateUtil.getSessionFactory().close();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
