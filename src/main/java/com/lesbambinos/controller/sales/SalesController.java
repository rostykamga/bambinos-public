//package com.lesbambinos.controller.sales;
//
//import com.lesbambinos.auth.Permissions;
//import com.lesbambinos.auth.SecurityManager;
//import com.lesbambinos.controller.AbstractController;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import com.lesbambinos.entity.Sale;
//import com.lesbambinos.interfaces.SaleInterface;
//import com.lesbambinos.model.SalesModel;
//
//import javafx.beans.binding.Bindings;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//
//public class SalesController extends AbstractController implements SaleInterface {
//
//    @FXML
//    private TableView<Sale> salesTable;
//    @FXML
//    private TableColumn<Sale, Long> idColumn;
//    @FXML
//    private TableColumn<Sale, String> productColumn, dateColumn, invoiceColumn;
//    @FXML
//    private TableColumn<Sale, Double> quantityColumn, returnedColumn, priceColumn, finalPriceColumn, totalColumn;
//    @FXML
//    private TextField searchField;
//    @FXML
//    private Button deleteButton;
//    private SalesModel model;
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
//        
//        model = new SalesModel();
//        
//        loadData();
//
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//
//        productColumn.setCellValueFactory((TableColumn.CellDataFeatures<Sale, String> p) -> 
//                new SimpleStringProperty(p.getValue().getProduct().getProductName()));
//        
//        invoiceColumn.setCellValueFactory((TableColumn.CellDataFeatures<Sale, String> p) -> 
//                new SimpleStringProperty(p.getValue().getInvoice().getId()));
//        
//        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        returnedColumn.setCellValueFactory(new PropertyValueFactory<>("returned"));
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//        finalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
//        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//        salesTable.setItems(SALELIST);
//        
//        filterData();
//        
//        deleteButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(salesTable.getSelectionModel().getSelectedItems()));
//    }
//    
//
//    private void filterData() {
//        FilteredList<Sale> searchedData = new FilteredList<>(SALELIST, e -> true);
//        searchField.setOnKeyReleased(e -> {
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//                searchedData.setPredicate(sale -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (sale.getProduct().getProductName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (sale.getProduct().getCategory().getType().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
//                    else if (sale.getInvoice().getId().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
//                    
//                    return false;
//                });
//            });
//
//            SortedList<Sale> sortedData = new SortedList<>(searchedData);
//            sortedData.comparatorProperty().bind(salesTable.comparatorProperty());
//            salesTable.setItems(sortedData);
//        });
//    }
//
//    private void loadData(){
//    
//        if (!SALELIST.isEmpty()) {
//            SALELIST.clear();
//        }
//        SALELIST.addAll(model.getSales());
//    }
//    
//    
//    @FXML
//    public void deleteAction(ActionEvent event) throws Exception {
//    }
//    
//}
