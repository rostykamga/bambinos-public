//package com.lesbambinos.controller.report;
//
//import com.lesbambinos.controller.AbstractController;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import com.lesbambinos.entity.Invoice;
//import com.lesbambinos.interfaces.ReportInterface;
//import com.lesbambinos.model.InvoiceModel;
//
//import javafx.animation.TranslateTransition;
//import javafx.beans.binding.Bindings;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.VBox;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.util.Duration;
//
//public class ReportController extends AbstractController implements ReportInterface {
//
//    @FXML
//    private TableView<Invoice> reportTable;
//    @FXML
//    private TableColumn<Invoice, Long> idColumn;
//    @FXML
//    private TableColumn<Invoice, String> employeeColumn, dateColumn;
//    @FXML
//    private TableColumn<Invoice, Double> totalColumn, vatColumn, discountColumn, 
//            payableColumn, paidColumn, returnedColumn;
//    @FXML
//    private TextField searchField;
//    @FXML
//    private Button viewButton;
//    private InvoiceModel model;
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
//        model = new InvoiceModel();
//        
//        loadData();
//
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        employeeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Invoice, String> p)
//                -> new SimpleStringProperty(p.getValue().getEmployee().getUserName()));
//        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
//        vatColumn.setCellValueFactory(new PropertyValueFactory<>("vat"));
//        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
//        payableColumn.setCellValueFactory(new PropertyValueFactory<>("payable"));
//        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
//        returnedColumn.setCellValueFactory(new PropertyValueFactory<>("returned"));
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//        reportTable.setItems(REPORTLIST);
//
//        filterData();
//
//        viewButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(reportTable.getSelectionModel().getSelectedItems()));
//    }
//
//    private void filterData() {
//        FilteredList<Invoice> searchedData = new FilteredList<>(REPORTLIST, e -> true);
//        searchField.setOnKeyReleased(e -> {
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//                searchedData.setPredicate(report -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    return report.getEmployee().getUserName().toLowerCase().contains(lowerCaseFilter);
//                });
//            });
//
//            SortedList<Invoice> sortedData = new SortedList<>(searchedData);
//            sortedData.comparatorProperty().bind(reportTable.comparatorProperty());
//            reportTable.setItems(sortedData);
//        });
//    }
//    
//    private void loadData(){
//    
//        if (!REPORTLIST.isEmpty()) {
//            REPORTLIST.clear();
//        }
//        REPORTLIST.addAll(model.getInvoices());
//    }
//    
//   
//    @FXML
//    public void viewAction(ActionEvent event) throws IOException {
//
//        Invoice selectedInvoice = reportTable.getSelectionModel().getSelectedItem();
//        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/report/View.fxml")));
//        ViewController controller = new ViewController();
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
//        stage.setTitle("View Details");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//        controller.setReport(selectedInvoice);
//        reportTable.getSelectionModel().clearSelection();
//    }
//}
