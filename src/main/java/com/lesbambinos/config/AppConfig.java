/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lesbambinos.controller.admin.ConfigController;
import com.lesbambinos.util.AppUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 *
 * @author rostand
 */
public class AppConfig {
    public static final String APP_FOLDER = System.getProperty("user.home") + File.separator + ".bambinos";
    public static final String CONFIG_FILE_PATH = APP_FOLDER + File.separator+"config.json";
    public static final String MYSQL = "MySQL";
    public static final String POSTGRESQL = "PostgreSQL";
    
    private static AppConfig currentConfig;
    
    public  static synchronized AppConfig getCurrentConfig(){
        if(currentConfig == null)
            currentConfig = loadConfig(CONFIG_FILE_PATH);
        
        return currentConfig;
    }
    
    public static void saveConfig(AppConfig posConfig, String filename) throws IOException{
        
        AppConfig toSave = new AppConfig(posConfig);
        
        if(! AppUtils.isEmptyOrNullString(toSave.getPassword()))
            toSave.setPassword(AppUtils.encrypt(toSave.getPassword()));
        
        File configFile = new File(filename);
        if(!configFile.exists()){
            configFile.getParentFile().mkdirs();
        }
        
        FileWriter writer = new FileWriter(configFile, false);
        Gson gson = new Gson();
        String jsonString = gson.toJson(toSave);
        writer.append(jsonString);
        writer.flush();
        writer.close();
        currentConfig = loadConfig(filename);
    }
    
    private static AppConfig loadConfig(String filename){
        
        File configFile = new File(filename);
        if(configFile.exists()){
            try {
                BufferedReader bf = new BufferedReader(new FileReader(configFile));
                StringBuilder sb = new StringBuilder();
                String line=null;
                while((line=bf.readLine())!=null)
                    sb.append(line);
                Gson gson = new Gson();
                Type type = new TypeToken<AppConfig>(){}.getType();
                AppConfig config =   gson.fromJson(sb.toString(), type);
                if(config !=null){
                    String decryptedPassword = AppUtils.isEmptyOrNullString(config.getPassword()) ? 
                        null :  AppUtils.decrypt(config.getPassword());
                    config.setPassword(decryptedPassword);
                }
                return config;
            } catch (Exception ex) {
                Logger.getLogger(AppConfig.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
        
        return null;
    }
    
    private String dbms = MYSQL;
    private String host = "localhost";
    private int port = 3306;
    private String dbname = "bambinos";
    private String user = "root";
    private String password;

    public AppConfig() {
    }
    
    public AppConfig(AppConfig other) {
        this.dbms = other.dbms;
        this.dbname = other.dbname;
        this.host = other.host;
        this.password = other.password;
        this.port = other.port;
        this.user = other.user;
    }
    

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.dbms);
        hash = 67 * hash + Objects.hashCode(this.host);
        hash = 67 * hash + this.port;
        hash = 67 * hash + Objects.hashCode(this.dbname);
        hash = 67 * hash + Objects.hashCode(this.user);
        hash = 67 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppConfig other = (AppConfig) obj;
        if (this.port != other.port) {
            return false;
        }
        if (!Objects.equals(this.dbms, other.dbms)) {
            return false;
        }
        if (!Objects.equals(this.host, other.host)) {
            return false;
        }
        if (!Objects.equals(this.dbname, other.dbname)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
    
    
    
    public String getDriver(){
        
        switch(dbms){
            case MYSQL:
                return "com.mysql.jdbc.Driver";
        }
        throw new UnsupportedOperationException("Not yet implementation for "+dbms);
    }
    
    public  String getUrl(){
        
        String protocol;
        switch(dbms){
            case MYSQL:
                protocol =  "jdbc:mysql://";
                break;
            case POSTGRESQL:
                protocol =  "jdbc:postgresql://";
                break;
            default:
                throw new UnsupportedOperationException("Not yet implementation for "+dbms);
        }
        
        return protocol + host+ ":"+port+"/"+dbname;
    }
    
    public String getDialect(){
        switch(dbms){
            case MYSQL :
                return "org.hibernate.dialect.MySQLDialect";
            case POSTGRESQL:
                return "org.hibernate.dialect.PostgreSQLDialect";
        }
        throw new UnsupportedOperationException("Not yet implementation for "+dbms);
    }
    
    private static double xOffset = 0;
    private static double yOffset = 0;
    
    public static void showConfigPanel(boolean isStartup, Window parent) throws IOException{
        
        FXMLLoader loader = new FXMLLoader(AppConfig.class.getResource("/fxml/Config.fxml"));
        Parent root = loader.load();
        ConfigController controller = loader.getController();
        controller.setInitialConfig(getCurrentConfig(), isStartup, parent);
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
        stage.setTitle("QITECH POS SYSTEM :: Version 1.0");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
