package com.lesbambinos.controller.cycle;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.entity.Cycle;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.SubsystemModel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CycleFormController extends FormController {
    
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<Subsystem> subsystemCombo;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private CycleModel cycleModel;
    
    private SubsystemModel subsystemModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        cycleModel = new CycleModel();
        subsystemModel = new SubsystemModel();
        
        subsystemCombo.setItems(Subsystem.SUBSYSTEMS);
        Subsystem.SUBSYSTEMS.clear();
        Subsystem.SUBSYSTEMS.addAll(subsystemModel.getAll());
    }
    
    /**
     * Pour créer un cycle, on passe en paramètre la section
     * @param obj Pair of Subsystem and Cycle object
     */
    @Override
    public void setData(AbstractEntity obj){
        
        super.setData(obj);
        
        formHeaderTitle.setText(entity == null? "Ajout Cycle" : "Modification Cycle");
        
        if(entity != null){
            Cycle cycle = (Cycle)entity;
            subsystemCombo.setValue(cycle.getSubsystem());
            nameField.setText(cycle.getCyclename());
            descriptionArea.setText(cycle.getDescription());
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Le Champ nom ne doit pas être vide!\n";
        }
        
        if (subsystemCombo.getValue()== null) {
            errorMessage += "Choisir un sous système!\n";
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
        Cycle cycle = new Cycle(
                    nameField.getText(),
                    descriptionArea.getText(),
                    subsystemCombo.getValue()
            );
        
        try{
            if(entity != null){
                cycle.setId(entity.getId());
                cycleModel.update(cycle);
            }
            else{
                cycleModel.save(cycle);
            }
            
            Cycle.CYCLES.clear();
            Cycle.CYCLES.addAll(cycleModel.getAll());
            
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
        alert.setHeaderText(entity == null? "Nouveau cycle crée!" : "Informations du cycle mise à jour");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(entity == null? "Le cycle n'a pas été crée!" : "Echec de la mise à jour");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

}
