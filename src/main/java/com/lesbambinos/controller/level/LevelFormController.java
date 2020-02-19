package com.lesbambinos.controller.level;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.entity.Cycle;
import com.lesbambinos.entity.Level;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.LevelModel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LevelFormController extends FormController {
    
    @FXML
    private TextField nameField;
    @FXML private ComboBox<Cycle> cycleCombo;
    @FXML private ComboBox<Integer> levelCombobox;
    @FXML
    private TextArea descriptionArea;

    
    private CycleModel cycleModel;
    private LevelModel levelModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        cycleModel = new CycleModel();
        levelModel =  new LevelModel();
        
        for(int i =0; i< 10; i++)
            levelCombobox.getItems().add(i);
        
        cycleCombo.setItems(Cycle.CYCLES);
        Cycle.CYCLES.clear();
        Cycle.CYCLES.addAll(cycleModel.getAll());
    }
    
    /**
     * Pour créer un cycle, on passe en paramètre la section
     * @param obj Pair of Subsystem and Cycle object
     */
    @Override
    public void setData(AbstractEntity obj){
        
        super.setData(obj);
        
        formHeaderTitle.setText(entity == null? "Ajout Niveau d'étude" : "Modification Niveau d'étude");
        
        if(entity != null){
            Level level = (Level)entity;
            cycleCombo.setValue(level.getCycle());
            levelCombobox.setValue(level.getLevel());
            nameField.setText(level.getLevelname());
            descriptionArea.setText(level.getDescription());
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Le Champ nom ne doit pas être vide!\n";
        }
        
        if (cycleCombo.getValue()== null) {
            errorMessage += "Choisir un cycle!\n";
        }
        if (levelCombobox.getValue()== null) {
            errorMessage += "Choisir un niveau!\n";
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
        Level level = new Level(
                    nameField.getText(),
                    levelCombobox.getValue(),
                    descriptionArea.getText(),
                    cycleCombo.getValue()
            );
        
        try{
            if(entity != null){
                level.setId(entity.getId());
                levelModel.update(level);
            }
            else{
                levelModel.save(level);
            }
            
            Level.LEVELS.clear();
            Level.LEVELS.addAll(levelModel.getAll());
            
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
        alert.setHeaderText(entity == null? "Niveau d'études crée!" : "Informations du niveau mise à jour");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(entity == null? "Le Niveau n'a pas été crée!" : "Echec de la mise à jour");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

}
