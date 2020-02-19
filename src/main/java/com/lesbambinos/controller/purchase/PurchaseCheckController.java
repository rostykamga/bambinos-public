///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.lesbambinos.controller.purchase;
//
//import com.lesbambinos.auth.SecurityManager;
//import com.lesbambinos.config.AppConfig;
//import com.lesbambinos.entity.Employee;
//import com.lesbambinos.entity.Purchase;
//import com.lesbambinos.entity.PurchaseCheck;
//import com.lesbambinos.entity.PurchaseProduct;
//import com.lesbambinos.model.PurchaseCheckModel;
//import com.lesbambinos.model.PurchaseProductModel;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//
///**
// * FXML Controller class
// *
// * @author rostand
// */
//public class PurchaseCheckController implements Initializable {
//
//    @FXML private Label supplierLabel, dateLabel;
//    @FXML private TableView<PurchaseCheck> productsTable;
//    @FXML private TableColumn<PurchaseCheck, String> productColumn, barcodeColumn;
//    @FXML private TableColumn<PurchaseCheck, Double> quantityColumn;
//    @FXML private Button saveButton;
//    @FXML private TextField searchField;
//    
//    private PurchaseProductModel purchaseProductModel;
//    private PurchaseCheckModel purchaseCheckModel;
//    private ObservableList<PurchaseCheck> purchaseCheckList = FXCollections.observableArrayList();
//    private Purchase purchase;
//    private  Employee employee;
//    private Map<Long, Double> initialQuantities = new HashMap<>();
//    /**
//     * Initializes the controller class.
//     * @param url
//     * @param rb
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        
//        employee = SecurityManager.getAuthenticatedEmployee().getEmployeeEntity();
//        
//        purchaseProductModel = new PurchaseProductModel();
//        purchaseCheckModel = new PurchaseCheckModel();
//        
//        productColumn.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseCheck, String> pc)
//                -> new SimpleStringProperty(pc.getValue().getPurchaseProduct().getProductName()));
//        
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
//        
//        quantityColumn.setCellFactory((TableColumn<PurchaseCheck, Double> param) -> new DoubleValueTableCell());
//    }   
//    
//    private void filterData() {
//        FilteredList<PurchaseCheck> searchedData = new FilteredList<>(purchaseCheckList, e -> true);
//
//        searchField.setOnKeyReleased(e -> {
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//                searchedData.setPredicate(pcheck -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (pcheck.getPurchaseProduct().getProductName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (pcheck.getBarcode().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
//                    
//                    return false;
//                });
//            });
//
//            SortedList<PurchaseCheck> sortedData = new SortedList<>(searchedData);
//            sortedData.comparatorProperty().bind(productsTable.comparatorProperty());
//            productsTable.setItems(sortedData);
//        });
//    }
//    
//    public void setPurchase(Purchase purchase){
//        supplierLabel.setText(purchase.getSupplier().getName());
//        dateLabel.setText(purchase.getDate());
//        this.purchase = purchase;
//        resetTable();
//        
//    }
//    
//    private void resetTable(){
//        
//        purchaseCheckList.clear();
//        
//        ObservableList<PurchaseProduct> purchaseProducts  = purchaseProductModel.getPurchaseProductByPurchaseId(purchase.getId());
//        
//        purchaseProducts.stream().map((pproduct) -> {
//            PurchaseCheck pcheck = purchaseCheckModel
//                    .getPurchaseCheckByPurchaseProducAndEmployeetId(employee.getId(), pproduct.getId());
//            if(pcheck == null){
//                pcheck = new PurchaseCheck();
//                pcheck.setBarcode(pproduct.getBarcode());
//                pcheck.setEmployee(employee);
//                pcheck.setPurchaseProduct(pproduct);
//                pcheck.setQuantity(0);
//            }
//            else{
//                initialQuantities.put(pcheck.getId(), pcheck.getQuantity());
//            }
//            return pcheck;
//        }).forEachOrdered((pcheck) -> {
//            purchaseCheckList.add(pcheck);
//        });
//        productsTable.setItems(purchaseCheckList);
//        filterData();
//    }
//    
//    private long generatePurchaseCheckId(Employee e){
//        StringBuilder sb = new StringBuilder();
//        sb.append(System.currentTimeMillis());
//        sb.append(String.format("%03d", e.getId()));
//        sb.append(String.format("%03d", AppConfig.getCurrentConfig().getMachineId()));
//        //System.out.println(sb);
//        return Long.parseLong(sb.toString());
//    }
//    
//    @FXML
//    public void cancelAction(ActionEvent event) {
//        resetTable();
//        productsTable.refresh();
//    }
//    
//    @FXML
//    public void saveAction(ActionEvent event) {
//        
//        productsTable.getItems().forEach((pcheck) -> {
//            double purchaseCheckQty = pcheck.getQuantity();
//            
//            if(pcheck.getId() != null && pcheck.getId() > 0){//update
//                purchaseCheckModel.updatePurchaseCheck(pcheck);
//            }
//            else{
//                pcheck.setId(generatePurchaseCheckId(employee));
//                purchaseCheckModel.savePurchaseCheck(pcheck);
//            }
//            
//            Double previousPurchaseCheckQty = initialQuantities.get(pcheck.getId());
//            
//            double purchaseProductRealQty = purchaseProductModel.
//                    getPurchaseProduct(pcheck.getPurchaseProduct().getId()).getRealQuantity();
//            
//            if(previousPurchaseCheckQty != null)
//                pcheck.getPurchaseProduct().setRealQuantity(purchaseProductRealQty -previousPurchaseCheckQty + purchaseCheckQty);
//            else
//                pcheck.getPurchaseProduct().setRealQuantity(purchaseProductRealQty + purchaseCheckQty);
//            
//            purchaseProductModel.updatePurchaseProduct(pcheck.getPurchaseProduct());
//            
//        });
//        
//        ((Stage) saveButton.getScene().getWindow()).close();
//        
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Successful");
//        alert.setHeaderText("Purchase product checked !");
//        alert.setContentText("Purchase product checked successfully");
//        alert.showAndWait();
//    }
//    
//    @FXML
//    public void closeAction(ActionEvent event) {
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//    }
//      
//}
//
//class DoubleValueTableCell extends TableCell<PurchaseCheck, Double>{
//    
//    
//    private final TextField editor= new TextField();
//    private String previousValue;
//  
//    
//    /**
//     * Creates a new cell factory that displays a toogle switch to edit the cells
//     * 
//     */
//    public DoubleValueTableCell(){
//        
//        this.getStyleClass().add("text-field-table-cell");
//        editor.setAlignment(Pos.CENTER);
//        
//        editor.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
//        
//        editor.focusedProperty().addListener(
//            (ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean focused) 
//                        -> {
//            if (!focused) {
//                processEdit();
//            }
//            else{
//                startEdit();
//            }
//        });        
//    }
//    
//    private void processEdit() {
//        String text = editor.getText().trim();
//        try{
//            double qty = Double.parseDouble(text);
//            commitEdit(qty);
//        }
//        catch(NumberFormatException ex){
//            cancelEdit();
//        }
//    }
//    
//    @Override
//    public void commitEdit(Double value){
//        if(!isEditing()) return;
//        
//        getTableView().getItems().get(getIndex()).setQuantity(value);        
//    }
//    
//    @Override
//    public void startEdit() {
//        if (!isEmpty()) {
//            super.startEdit();
//            previousValue = editor.getText();
//            editor.selectAll();
//        }
//    }
//
//    @Override
//    public void cancelEdit() {
//        super.cancelEdit();
//        editor.setText(previousValue);
//    }
//    
//    @Override
//    public void updateItem(Double item, boolean empty) {
//        
//        super.updateItem(item, empty);
//        
//        if (empty || item==null) {
//            setGraphic(null);
//            setText(null);
//        } 
//        else {
//            setGraphic(editor);
//            editor.setText(String.valueOf(item));
//        }
//
//    }
//}