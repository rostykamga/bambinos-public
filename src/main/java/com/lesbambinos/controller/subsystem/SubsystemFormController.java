package com.lesbambinos.controller.subsystem;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.model.SubsystemModel;
import com.lesbambinos.util.AppUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SubsystemFormController extends FormController {
    
    @FXML
    private TextField nameField, registrationPrefixField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private SubsystemModel subsystemModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        subsystemModel = new SubsystemModel();
    }
    
    @Override
    public void setData(AbstractEntity e){
        super.setData(e);
        formHeaderTitle.setText(e == null? "Ajout Section" : "Modification Section");
        
        if(entity != null){
            Subsystem section = (Subsystem)entity;
            nameField.setText(section.getSubsystemname());
            registrationPrefixField.setText(section.getRegprefix());
            descriptionArea.setText(section.getDescription());
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Le Champ nom ne doit pas être vide!\n";
        }
        if (AppUtils.isEmptyOrNullString(registrationPrefixField.getText())) {
            errorMessage += "Le Champ préfix matricule ne doit pas être vide!\n";
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
        Subsystem subsystem = new Subsystem(
                    nameField.getText(),
                    registrationPrefixField.getText(),
                    descriptionArea.getText()
            );
        
        try{
            if(entity != null){
                subsystem.setId(((AbstractEntity)entity).getId());
                subsystemModel.update(subsystem);
            }
            else{
                subsystemModel.save(subsystem);
            }
            Subsystem.SUBSYSTEMS.clear();
            Subsystem.SUBSYSTEMS.addAll(subsystemModel.getAll());
            formStatus = STATUS_SUCCESS;
        }
        catch(Exception ex){
            formStatus = STATUS_FAIL;
            err_msg = ex.getMessage();
        }
    }

    @Override
    protected Alert getSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération effectuée avec succès");
        alert.setHeaderText(entity == null? "Sous système crée!" : "Sous système mis à jour");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(entity == null? "Le  Sous système n'a pas été crée!" : "Echec de la mise à jour");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

}
