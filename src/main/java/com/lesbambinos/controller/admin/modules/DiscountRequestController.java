package com.lesbambinos.controller.admin.modules;

import com.lesbambinos.controller.admin.AdminModuleController;
import com.lesbambinos.entity.DiscountRequest;
import com.lesbambinos.model.DiscountRequestModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.cell.PropertyValueFactory;

public class DiscountRequestController extends AdminModuleController {

    // =========== Top Pane ================
    @FXML private RadioButton showAllRadio, showPendingRadio, showAcceptedRadio, showRejectedRadio;
    
    @FXML private TableView<DiscountRequest> discountRequestsTableview;
    
    @FXML private TableColumn<DiscountRequest, Long> idColumn;
    @FXML private TableColumn<DiscountRequest, String> studentColumn;
    @FXML private TableColumn<DiscountRequest, String> classroomColumn;
    @FXML private TableColumn<DiscountRequest, String> descriptionColumn;
    @FXML private TableColumn<DiscountRequest, Double> amountColumn;
    @FXML private TableColumn<DiscountRequest, String> statusColumn;
    @FXML private TableColumn<DiscountRequest, String> requestDateColumn;
    @FXML private TableColumn<DiscountRequest, String> validatorColumn;
    @FXML private TableColumn<DiscountRequest, String> validationDateColumn;
    
    @FXML private Button acceptButton, rejectButton, detailsButton;
    
    private final ObservableList<DiscountRequest> DISCOUNT_REQUESTS = FXCollections.observableArrayList();
    private FilteredList<DiscountRequest> filteredList;
    
    private final DiscountRequestModel discountRequestModel = new DiscountRequestModel();
             
    private Predicate<DiscountRequest> showAllPredicate;
    private Predicate<DiscountRequest> showAcceptedPredicate;
    private Predicate<DiscountRequest> showRejectedPredicate;
    private Predicate<DiscountRequest> showPendingPredicate;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        super.initialize(url, rb);
                
        showAllPredicate = (DiscountRequest t) -> true;
        showAcceptedPredicate = (DiscountRequest t) -> DiscountRequest.STATUS.ACCEPTED.equalsIgnoreCase(t.getStatus());
        showRejectedPredicate = (DiscountRequest t) -> DiscountRequest.STATUS.REJECTED.equalsIgnoreCase(t.getStatus());
        showPendingPredicate = (DiscountRequest t) -> DiscountRequest.STATUS.PENDING.equalsIgnoreCase(t.getStatus());
        
        showAllRadio.setSelected(true);
        filteredList = new FilteredList<>(DISCOUNT_REQUESTS, showAllPredicate);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
       
        studentColumn.setCellValueFactory((TableColumn.CellDataFeatures<DiscountRequest, String> p)
                -> new SimpleStringProperty(p.getValue().getStudent().getNames()+" "+p.getValue().getStudent().getSurnames()));
        
        classroomColumn.setCellValueFactory((TableColumn.CellDataFeatures<DiscountRequest, String> p)
                -> new SimpleStringProperty(p.getValue().getClassroom().getShortname()));
        
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("fixedAmount"));
        
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        requestDateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        
        validatorColumn.setCellValueFactory((TableColumn.CellDataFeatures<DiscountRequest, String> p)
                -> new SimpleStringProperty(p.getValue().getValidator() == null? null: p.getValue().getValidator().getFirstName()+" "+p.getValue().getValidator().getLastName()));
        
        validationDateColumn.setCellValueFactory(new PropertyValueFactory<>("validationDate"));
        
        acceptButton.disableProperty().bind(Bindings.isNull(discountRequestsTableview.getSelectionModel().selectedItemProperty()));
        rejectButton.disableProperty().bind(Bindings.isNull(discountRequestsTableview.getSelectionModel().selectedItemProperty()));
        detailsButton.disableProperty().bind(Bindings.isNull(discountRequestsTableview.getSelectionModel().selectedItemProperty()));
        
        discountRequestsTableview.setItems(filteredList);
    }
    
    
   @FXML protected void showAllAction(ActionEvent event){
       filteredList.setPredicate(showAllPredicate);
   }
   
   @FXML protected void showPendingAction(ActionEvent event){
       filteredList.setPredicate(showPendingPredicate);
   }
   
   @FXML protected void showAcceptedAction(ActionEvent event){
       filteredList.setPredicate(showAcceptedPredicate);
   }
   @FXML protected void showRejectedAction(ActionEvent event){
       filteredList.setPredicate(showRejectedPredicate);
   }
   
   @FXML protected void acceptAction(ActionEvent event){
   }
   @FXML protected void rejectAction(ActionEvent event){
   }
   @FXML protected void detailAction(ActionEvent event){
   }
   

    @Override
    protected Object _loadData() {
        return discountRequestModel.getAll();
    }
    

    @Override
    protected String getModuleName() {
        return "Gestion des Rabais";
    }

    @Override
    protected void _onDataLoaded(Object data) {
        ObservableList<DiscountRequest> list = (ObservableList<DiscountRequest>)data;
        DISCOUNT_REQUESTS.setAll(list);
    }

}