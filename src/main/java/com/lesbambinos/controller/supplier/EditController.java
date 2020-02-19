//package com.lesbambinos.controller.supplier;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import com.lesbambinos.entity.Supplier;
//import com.lesbambinos.interfaces.SupplierInterface;
//import com.lesbambinos.model.SupplierModel;
//import com.lesbambinos.util.AppUtils;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//public class EditController implements Initializable, SupplierInterface {
//
//    @FXML
//    private TextField supplierField, phoneField, cifField, contactNameField;
//    @FXML
//    private TextArea addressArea;
//    @FXML
//    private Button saveButton;
//    private long selectedSupplierId;
//    private SupplierModel supplierModel;
//    private Supplier supplier;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        supplierModel = new SupplierModel();
//        resetValues();
//    }
//
//    private void resetValues() {
//
//        supplierField.setText("");
//        phoneField.setText("");
//        addressArea.setText("");
//    }
//    
//    public void setSupplier(Supplier supplier, long selectedSupplierId){
//        this.supplier = supplier;
//        this.selectedSupplierId = selectedSupplierId;
//        setData();
//    }
//    
//    private void setData(){
//        supplierField.setText(supplier.getName());
//        phoneField.setText(supplier.getPhone());
//        addressArea.setText(supplier.getAddress());
//    }
//
//    @FXML
//    public void handleCancel(ActionEvent event) {
//        resetValues();
//    }
//
//    @FXML
//    public void handleSave(ActionEvent event) {
//
//        if (validateInput()) {
//
//            Supplier editedSupplier = new Supplier(
//                    supplier.getId(),
//                    supplierField.getText(),
//                    cifField.getText(),
//                    contactNameField.getText(),
//                    phoneField.getText(),
//                    addressArea.getText()
//            );
//
//            supplierModel.updateSuplier(editedSupplier);
//            SUPPLIERLIST.set((int) selectedSupplierId, editedSupplier);
//
//            ((Stage) saveButton.getScene().getWindow()).close();
//
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Successful");
//            alert.setHeaderText("Supplier Updated!");
//            alert.setContentText("Supplier is updated successfully");
//            alert.showAndWait();
//        }
//    }
//
//    private boolean validateInput() {
//
//        String errorMessage = "";
//
//        if (AppUtils.isEmptyOrNullString(supplierField.getText())) {
//            errorMessage += "No valid first name!\n";
//        }
//        if (AppUtils.isEmptyOrNullString(cifField.getText())) {
//            errorMessage += "No valid  CIF!\n";
//        }
//        if (AppUtils.isEmptyOrNullString(contactNameField.getText())) {
//            errorMessage += "No valid  contact name!\n";
//        }
//
//        if (AppUtils.isEmptyOrNullString(phoneField.getText())) {
//            errorMessage += "No valid phone number!\n";
//        }
//
//        if (AppUtils.isEmptyOrNullString(addressArea.getText())) {
//            errorMessage += "No email address!\n";
//        }
//
//
//        if (errorMessage.length() == 0) {
//            return true;
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Invalid Fields");
//            alert.setHeaderText("Please correct invalid fields");
//            alert.setContentText(errorMessage);
//            alert.showAndWait();
//
//            return false;
//        }
//    }
//    
//    @FXML
//    public void closeAction(ActionEvent event) {
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//    }
//}
