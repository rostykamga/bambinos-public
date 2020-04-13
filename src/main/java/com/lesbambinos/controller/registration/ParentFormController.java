package com.lesbambinos.controller.registration;


import com.lesbambinos.controller.AutocompletableForm;
import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Parent;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.entity.StudentsParent;
import com.lesbambinos.extra.AutocompleteTextField;
import com.lesbambinos.model.ParentModel;
import com.lesbambinos.util.AppUtils;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ParentFormController extends FormController implements AutocompletableForm<Parent>{
    
    @FXML private TextField professionField, phoneField1, phoneField2, placeField, religionField;
    @FXML private ComboBox<String> roleCombobox;
    @FXML private GridPane componentGridpane;
    
    private AutocompleteTextField nameField;
    private ParentModel parentModel = new ParentModel();
    private Parent existingParent;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        nameField = new AutocompleteTextField(this, parentModel);
        
        nameField.getStyleClass().add("accessible-textfield");
        GridPane.setMargin(nameField, new Insets(0, 10, 0, 0));
        componentGridpane.add(nameField, 1, 0);
       
        roleCombobox.getItems().addAll("Père", "Mère", "Soeur/Frere", "Tuteur", "Nourice", "Chauffeur", "Gardien", "Autre");
    }
    
    @Override
    public void setData(AbstractEntity e){
        super.setData(e);
        formHeaderTitle.setText("Edition parent");
        
        if(entity != null){
            StudentsParent sdParent = (StudentsParent)entity;
            //nameField.setAutocomplete(false);
            nameField.setText(sdParent.getParent().getFullname());
            professionField.setText(sdParent.getParent().getProfession());
            phoneField1.setText(sdParent.getParent().getPhone1());
            phoneField2.setText(sdParent.getParent().getPhone2());
            placeField.setText(sdParent.getParent().getResidence());
            religionField.setText(sdParent.getParent().getReligion());
            roleCombobox.setValue(sdParent.getParentRole());
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (AppUtils.isEmptyOrNullString(nameField.getText())) {
            errorMessage += "Le Champ nom ne doit pas être vide!\n";
        }
        if (AppUtils.isEmptyOrNullString(phoneField1.getText())) {
            errorMessage += "Le Champ Tél 1 ne doit pas être vide!\n";
        }
        if (AppUtils.isEmptyOrNullString(placeField.getText())) {
            errorMessage += "Le Champ quartier de résidence ne doit pas être vide!\n";
        }
        if (roleCombobox.getValue() == null) {
            errorMessage += "Le Champ rôle ne doit pas être vide!\n";
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
            Parent parent =  new Parent(
                nameField.getText(),
                professionField.getText(),
                phoneField1.getText(),
                phoneField2.getText(),
                religionField.getText(),
                placeField.getText()
            );
            
            if(existingParent != null)
                parent.setId(existingParent.getId());
            
            entity = new StudentsParent(parent, null, roleCombobox.getValue());
        }
        else{
            StudentsParent stdP = (StudentsParent)entity;
            
            stdP.setParentRole(roleCombobox.getValue());
            
            if(existingParent != null)
                stdP.setParent(existingParent);
            
            stdP.getParent().setFullname(nameField.getText());
            stdP.getParent().setPhone1(phoneField1.getText());
            stdP.getParent().setPhone2(phoneField2.getText());
            stdP.getParent().setProfession(professionField.getText());
            stdP.getParent().setReligion(religionField.getText());
            stdP.getParent().setResidence(placeField.getText());
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

    @Override
    public void handleAutocompletion(Parent parent) {
        if(entity != null){
            StudentsParent sdParent = (StudentsParent)entity;
            sdParent.setParent(parent);
        }
        else{
            existingParent = parent;
        }
        
        //nameField.setAutocomplete(false);
       nameField.setText(parent.getFullname());
       professionField.setText(parent.getProfession());
       phoneField1.setText(parent.getPhone1());
       phoneField2.setText(parent.getPhone2());
       placeField.setText(parent.getResidence());
       religionField.setText(parent.getReligion());
        
    }

}
