package com.lesbambinos.controller.classe;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.entity.Level;
import com.lesbambinos.model.ClasseModel;
import com.lesbambinos.model.LevelModel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClasseFormController extends FormController {
    
    @FXML
    private TextField shortnameField, fullnameField;
    @FXML private ComboBox<Level> levelCombo;
    @FXML
    private TextArea descriptionArea;

    
    private LevelModel levelModel;
    private ClasseModel classeModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        levelModel = new LevelModel();
        classeModel =  new ClasseModel();
        
        levelCombo.setItems(Level.LEVELS);
        Level.LEVELS.clear();
        Level.LEVELS.addAll(levelModel.getAll());
    }
    
    /**
     * Pour créer un level, on passe en paramètre la section
     * @param obj Pair of Subsystem and Level object
     */
    @Override
    public void setData(AbstractEntity obj){
        
        super.setData(obj);
        
        formHeaderTitle.setText(entity == null? "Ajout Classe" : "Modification Classe");
        
        if(entity != null){
            Classe classe = (Classe)entity;
            levelCombo.setValue(classe.getLevel());
            shortnameField.setText(classe.getShortname());
            fullnameField.setText(classe.getFullname());
            descriptionArea.setText(classe.getDescription());
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (shortnameField.getText() == null || shortnameField.getText().length() == 0) {
            errorMessage += "Le Champ nom court ne doit pas être vide!\n";
        }
        if (fullnameField.getText() == null || fullnameField.getText().length() == 0) {
            errorMessage += "Le Champ nom complet ne doit pas être vide!\n";
        }
        
        if (levelCombo.getValue()== null) {
            errorMessage += "Choisir un niveau d'études!\n";
        }

//        if (descriptionArea.getText() == null || descriptionArea.getText().length() == 0) {
//            errorMessage += "La description ne doit pas être vide!\n";
//        }

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
        Classe classe = new Classe(
                    shortnameField.getText(),
                    fullnameField.getText(),
                    descriptionArea.getText(),
                    levelCombo.getValue()
            );
        
        try{
            if(entity != null){
                classe.setId(entity.getId());
                classeModel.update(classe);
            }
            else{
                classeModel.save(classe);
            }
            
            Classe.CLASSES.clear();
            Classe.CLASSES.addAll(classeModel.getAll());
            
            formStatus = STATUS_SUCCESS;
        }
        catch(Exception ex){
            formStatus = STATUS_FAIL;
            err_msg = ex.getMessage();
            if(err_msg==null || err_msg.isEmpty()){
                if(ex.getCause() !=null)
                    err_msg = ex.getCause().getMessage();
            }
        }
    }

    @Override
    protected Alert getSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération effectuée avec succès");
        alert.setHeaderText(entity == null? "Nouvelle classe crée!" : "Informations de la classe mise à jour");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(entity == null? "La classe n'a pas été crée!" : "Echec de la mise à jour");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

}
