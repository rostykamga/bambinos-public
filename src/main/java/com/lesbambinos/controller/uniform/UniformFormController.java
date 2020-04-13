package com.lesbambinos.controller.uniform;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Uniform;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.model.UniformModel;
import com.lesbambinos.util.AppUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UniformFormController extends FormController {
    
    public static final String MASCULIN = "Garçon";
    public static final String FEMININ = "Fille";
    
    @FXML
    private TextField sizeField, typeField, genderField, unitPriceField, purchasePriceField, quantityField, downStockField, upStockField;
    
    private UniformModel uniformModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        uniformModel =  new UniformModel();
    }
    
    /**
     * Pour créer un level, on passe en paramètre la section
     * @param obj Pair of Subsystem and Level object
     */
    @Override
    public void setData(AbstractEntity obj){
        
        super.setData(obj);
        
        formHeaderTitle.setText("Mise à jour de l'Uniforme");
        
        if(entity != null){
            Uniform uniform = (Uniform)entity;
            sizeField.setText(uniform.getSize().getUniformSize());
            typeField.setText(uniform.getType().getType());
            genderField.setText(uniform.getGender()? MASCULIN : FEMININ);
            unitPriceField.setText(String.valueOf(uniform.getUnitPrice()));
            purchasePriceField.setText(String.valueOf(uniform.getPurchasePrice()));
            quantityField.setText(String.valueOf(uniform.getQuantityInStock()));
            downStockField.setText(String.valueOf(uniform.getDownStock()));
            upStockField.setText(String.valueOf(uniform.getUpStock()));
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (!AppUtils.isValidPositiveInteger(unitPriceField.getText())) {
            errorMessage += "Prix unitaire invalide!\n";
        }
        if (!AppUtils.isValidPositiveInteger(purchasePriceField.getText())) {
            errorMessage += "Prix de confection invalide!\n";
        }
        if (!AppUtils.isValidPositiveOrNullInteger(quantityField.getText())) {
            errorMessage += "Quantité en stock invalide!\n";
        }
        if (!AppUtils.isValidPositiveInteger(downStockField.getText())) {
            errorMessage += "Stock minimal invalide!\n";
        }
        if (!AppUtils.isValidPositiveInteger(upStockField.getText())) {
            errorMessage += "Stock maximal invalide!\n";
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
        
        try{
            Uniform uniform = (Uniform)entity;
            uniform.setDownStock(Integer.parseInt(downStockField.getText()));
            uniform.setPurchasePrice(Integer.parseInt(purchasePriceField.getText()));
            uniform.setQuantityInStock(Integer.parseInt(quantityField.getText()));
            uniform.setUnitPrice(Integer.parseInt(unitPriceField.getText()));
            uniform.setUpStock(Integer.parseInt(upStockField.getText()));
            
            uniformModel.update(uniform);
            
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
        alert.setHeaderText("Informations de l'uniforme mise à jour");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText("Echec de la mise à jour");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

}
