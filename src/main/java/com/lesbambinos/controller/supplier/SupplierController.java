//package com.lesbambinos.controller.supplier;
//
//import com.lesbambinos.controller.AbstractController;
//import java.net.URL;
//import java.util.Optional;
//import java.util.ResourceBundle;
//
//import com.lesbambinos.entity.Supplier;
//import com.lesbambinos.interfaces.SupplierInterface;
//import com.lesbambinos.model.SupplierModel;
//
//import javafx.beans.binding.Bindings;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//public class SupplierController extends AbstractController implements SupplierInterface {
//
//    @FXML
//    private TableView<Supplier> supplierTable;
//    @FXML
//    private TableColumn<Supplier, Long> idColumn;
//    @FXML
//    private TableColumn<Supplier, String> nameColumn, cifColumn, contactNameColumn,phoneColumn, addressColumn;
//    @FXML
//    private TextField searchField;
//    @FXML
//    private Button editButton, deleteButton;
//    private SupplierModel model;
//    
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
//        model = new SupplierModel();
//        
//        loadData();
//
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        cifColumn.setCellValueFactory(new PropertyValueFactory<>("cif"));
//        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
//
//        supplierTable.setItems(SUPPLIERLIST);
//
//        filterData();
//        
//        editButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(supplierTable.getSelectionModel().getSelectedItems()));
//        deleteButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(supplierTable.getSelectionModel().getSelectedItems()));
//    }
//
//    private void filterData() {
//        FilteredList<Supplier> searchedData = new FilteredList<>(SUPPLIERLIST, e -> true);
//        searchField.setOnKeyReleased(e -> {
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//                searchedData.setPredicate(supplier -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (supplier.getName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (supplier.getAddress().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (supplier.getPhone().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
//                    return false;
//                });
//            });
//
//            SortedList<Supplier> sortedData = new SortedList<>(searchedData);
//            sortedData.comparatorProperty().bind(supplierTable.comparatorProperty());
//            supplierTable.setItems(sortedData);
//        });
//    }
//
//    private void loadData(){
//    
//        if (!SUPPLIERLIST.isEmpty()) {
//            SUPPLIERLIST.clear();
//        }
//        
//        SUPPLIERLIST.addAll(model.getSuppliers());
//    }
//    
//    @FXML
//    public void addAction(ActionEvent event) throws Exception {
//    
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/supplier/Add.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        root.setOnMousePressed((MouseEvent e) -> {
//            xOffset = e.getSceneX();
//            yOffset = e.getSceneY();
//        });
//        root.setOnMouseDragged((MouseEvent e) -> {
//            stage.setX(e.getScreenX() - xOffset);
//            stage.setY(e.getScreenY() - yOffset);
//        });
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("New Supplier");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @FXML
//    public void editAction(ActionEvent event) throws Exception {
//
//        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
//        int selectedSupplierId = supplierTable.getSelectionModel().getSelectedIndex();
//        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/supplier/Edit.fxml")));
//        EditController controller = new EditController();
//        loader.setController(controller);
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        root.setOnMousePressed((MouseEvent e) -> {
//            xOffset = e.getSceneX();
//            yOffset = e.getSceneY();
//        });
//        root.setOnMouseDragged((MouseEvent e) -> {
//            stage.setX(e.getScreenX() - xOffset);
//            stage.setY(e.getScreenY() - yOffset);
//        });
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("Update Details");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//        controller.setSupplier(selectedSupplier, selectedSupplierId);
//        supplierTable.getSelectionModel().clearSelection();
//    }
//
//    @FXML
//    public void deleteAction(ActionEvent event) {
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Remove");
//        alert.setHeaderText("Remove Supplier");
//        alert.setContentText("Are you sure?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK) {
//            Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
//
//            model.deleteSuplier(selectedSupplier);
//            SUPPLIERLIST.remove(selectedSupplier);
//        }
//
//        supplierTable.getSelectionModel().clearSelection();
//    }
//    
//}
