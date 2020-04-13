package com.lesbambinos.controller.admin.modules;

import com.lesbambinos.auth.BambinosSecurityManager;
import com.lesbambinos.controller.FormController;
import com.lesbambinos.controller.admin.AdminModuleController;
import com.lesbambinos.entity.DiscountRequest;
import com.lesbambinos.entity.Uniform;
import com.lesbambinos.entity.UniformOrder;
import com.lesbambinos.model.DiscountRequestModel;
import com.lesbambinos.util.AppUtils;
import com.lesbambinos.util.DialogUtils;
import static com.lesbambinos.util.DialogUtils.showError;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableRow;
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
    
    private final BooleanProperty requestIsFinalized = new SimpleBooleanProperty(false);
    
   
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
        
        acceptButton.disableProperty().bind(Bindings.or(requestIsFinalized, Bindings.isNull(discountRequestsTableview.getSelectionModel().selectedItemProperty())));
        rejectButton.disableProperty().bind(Bindings.or(requestIsFinalized, Bindings.isNull(discountRequestsTableview.getSelectionModel().selectedItemProperty())));
        detailsButton.disableProperty().bind(Bindings.isNull(discountRequestsTableview.getSelectionModel().selectedItemProperty()));
        
        discountRequestsTableview.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends DiscountRequest> observable, DiscountRequest oldValue, DiscountRequest newValue) -> {
            requestIsFinalized.set(newValue == null? true : newValue.isFinalized());
        });
        
        discountRequestsTableview.setItems(filteredList);
        
        discountRequestsTableview.setRowFactory( tv -> {
            TableRow<DiscountRequest> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        detailAction(new ActionEvent(event.getSource(), event.getTarget()));
                    } catch (Exception ex) {
                        Logger.getLogger(DiscountRequestController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });

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
       
       DiscountRequest request = discountRequestsTableview.getSelectionModel().getSelectedItem();
       
       String input = DialogUtils.prompt("Montant du rabais", "Veuillez entrer le montant du rabais à accorder", "Montant: ", request.getFixedAmount()+"");
       
       if(!AppUtils.isValidPositiveDouble(input)){
           DialogUtils.showError("Erreur", "Entrée invalide !", "Le montant du rabais accordé doit être supérieur à zero");
           return;
       }
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Approbation d'une demande de rabais");
        alert.setContentText("Etes-vous sûre de vouloir accorder une demande de rabais\nde "+input+" à l'élève "+request.getStudent().getFullname()+"?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                request.setFixedAmount(Double.parseDouble(input));
                request.setStatus(DiscountRequest.STATUS.ACCEPTED);
                request.setValidationDate(new Date());
                request.setValidator(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity());
                
                discountRequestModel.update(request);
                discountRequestsTableview.refresh();
                discountRequestsTableview.getSelectionModel().clearSelection();
            }
            catch(Exception ex){
                ex.printStackTrace();
                showError("erreur", "Une erreur est survenue lors de l'opération ", ex.getMessage());
            }
        }
   }
   
   @FXML protected void rejectAction(ActionEvent event){
       discountRequestsTableview.getSelectionModel().clearSelection();
   }
   
   @FXML protected void detailAction(ActionEvent event){
       FormController.showForm("admin/modules/discountrequest/Form.fxml", discountRequestsTableview.getSelectionModel().getSelectedItem());
   }
   

    @Override
    protected Object _loadData() {
        return discountRequestModel.getAll();
    }
    

    @Override
    protected String getModuleName() {
        return "Demandes des Rabais";
    }

    @Override
    protected void _onDataLoaded(Object data) {
        ObservableList<DiscountRequest> list = (ObservableList<DiscountRequest>)data;
        DISCOUNT_REQUESTS.setAll(list);
        discountRequestsTableview.getSelectionModel().clearSelection();
    }

}