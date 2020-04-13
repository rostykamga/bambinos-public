package com.lesbambinos.controller.admin.modules;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.DiscountRequest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DiscountRequestFormController extends FormController {
    
    @FXML
    private TextField studentField, studentClassroomField, amountField, statusField, requestDateField, validatorField, validationDateField;
    @FXML private TextArea descriptionArea;
    @FXML private Button cancelButton;

    
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        cancelButton.setVisible(false);
        cancelButton.setManaged(false);
        
        statusField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(DiscountRequest.STATUS.ACCEPTED.equalsIgnoreCase(newValue)){
                statusField.getStyleClass().add("approved");
            }
            if(DiscountRequest.STATUS.PENDING.equalsIgnoreCase(newValue)){
                statusField.getStyleClass().add("pending");
            }
            if(DiscountRequest.STATUS.REJECTED.equalsIgnoreCase(newValue)){
                statusField.getStyleClass().add("rejected");
            }
        });
    }
    
    /**
     * Pour créer un level, on passe en paramètre la section
     * @param obj Pair of Subsystem and Level object
     */
    @Override
    public void setData(AbstractEntity obj){
        
        super.setData(obj);
        
        
        formHeaderTitle.setText("Détails demande de rabais" );
        
        if(entity != null){
            DiscountRequest request = (DiscountRequest)entity;
  
            studentField.setText(request.getStudent().getFullname());
            studentClassroomField.setText(request.getStudent().getClassroom().getShortname());
            descriptionArea.setText(request.getDescription());
            amountField.setText(request.getFixedAmount()+"");
            statusField.setText(request.getStatus());
            requestDateField.setText(String.valueOf(request.getRequestDate()));
            validatorField.setText(String.valueOf(request.getValidator() == null ? "" : request.getValidator().getFullname()));
            validationDateField.setText(String.valueOf(request.getValidationDate() == null? "" : request.getValidationDate()));
        }
    }
    
    
    
    @Override
    protected boolean validateInput() {

        return true;
    }

    @Override
    protected void doSave() {
        
            formStatus = STATUS_SUCCESS;
        
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
