package com.lesbambinos.controller.registration;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Attachment;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.util.AppUtils;
import java.io.File;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class AttachmentFormController extends FormController{
    
    @FXML private TextField descriptionField, filePathField;
    
    private File selectedFile;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
    
    @Override
    public void setData(AbstractEntity e){
        super.setData(e);
        
        if(entity != null){
            Attachment att = (Attachment)entity;
            descriptionField.setText(att.getDescription());
            filePathField.setText(att.getFilename());
        }
    }
    
    @FXML
    protected void openFileAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Tous les fichiers", "*.*");
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Selectionner un fichier");
        
        Preferences prefs =  Preferences.userRoot();
        String prevPath = prefs.get("bambinos.last_imported_logo", null);
        if(prevPath!=null){
            if(!File.separator.equals("/"))
                prevPath = prevPath.replace("/", "\\");
            File prevFolder = new File(prevPath);
            if(prevFolder.exists())
                fileChooser.setInitialDirectory(prevFolder);
        }
        Window stage = ((Node)event.getSource()).getScene().getWindow();
        
        selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null ){
            String selectedFileParent = selectedFile.getParent();
            filePathField.setText(selectedFile.getAbsolutePath());
            if(!File.separator.equals("/"))
                selectedFileParent = selectedFileParent.replace("\\", "/");
            prefs.put("bambinos.last_imported_logo", selectedFileParent);
        }
        
        
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (AppUtils.isEmptyOrNullString(descriptionField.getText())) {
            errorMessage += "Le Champ description ne doit pas être vide!\n";
        }
        if (AppUtils.isEmptyOrNullString(filePathField.getText())) {
            errorMessage += "Le chemin du fichier ne doit pas être vide!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les erreurs ci-dessous");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    @Override
    protected void doSave() {
        
        if(entity == null){
           
            entity = new Attachment();
            ((Attachment)entity).setDescription(descriptionField.getText());
            ((Attachment)entity).setFilename(selectedFile.getName());
            ((Attachment)entity).setContent(AppUtils.file2ByteArray(selectedFile));
        }
        else{
            Attachment att = (Attachment)entity;
            att.setDescription(descriptionField.getText());
            if(!att.getFilename().equalsIgnoreCase(filePathField.getText())){
                att.setFilename(selectedFile.getName());
                att.setContent(AppUtils.file2ByteArray(selectedFile));
            }
        }
    }

    @Override
    protected Alert getSuccessAlert() {
        return null;
    }

    @Override
    protected Alert getFailAlert() {
        return null;
    }

}
