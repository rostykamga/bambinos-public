package com.lesbambinos.controller.uniform;

import com.lesbambinos.auth.BambinosSecurityManager;
import com.lesbambinos.controller.AbstractController;
import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.Uniform;
import com.lesbambinos.entity.UniformOrder;
import com.lesbambinos.entity.UniformPurchase;
import com.lesbambinos.entity.UniformSize;
import com.lesbambinos.entity.UniformType;
import com.lesbambinos.model.StudentModel;
import com.lesbambinos.model.UniformModel;
import com.lesbambinos.model.UniformOrderModel;
import com.lesbambinos.model.UniformPurchaseModel;
import com.lesbambinos.model.UniformSizeModel;
import com.lesbambinos.model.UniformTypeModel;
import com.lesbambinos.util.AppUtils;
import com.lesbambinos.util.BooleanTableCell;
import com.lesbambinos.util.DialogUtils;
import static com.lesbambinos.util.DialogUtils.showError;
import com.lesbambinos.util.IntegerEnumTableCell;
import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.PropertyValueFactory;

public class UniformController extends AbstractController {

    @FXML private TabPane tabpane;
    // =========== Commandes Tab (Order Tab) ================
      
    @FXML private Button deliverOrderButton;
    @FXML private TableView<UniformOrder> ordersTableview;
    @FXML private TableColumn<UniformOrder, String> registrationColumn;
    @FXML private TableColumn<UniformOrder, String> studentFullnameColumn;
    @FXML private TableColumn<UniformOrder, String> classroomColumn;
    @FXML private TableColumn<UniformOrder, String> uniformTypeColumn;
    @FXML private TableColumn<UniformOrder, String> uniformSizeColumn;
    @FXML private TableColumn<UniformOrder, Boolean> uniformGenderColumn;
    @FXML private TableColumn<UniformOrder, String> quantityColumn;
    @FXML private TableColumn<UniformOrder, Boolean> paidColumn;
    @FXML private TableColumn<UniformOrder, Boolean> deliveredColumn;
    @FXML private TableColumn<UniformOrder, String> orderDateColumn;
    @FXML private TableColumn<UniformOrder, String> deliveryDate;
    @FXML private TableColumn<UniformOrder, String> orderSavingEmployee;
    @FXML private TableColumn<UniformOrder, String> orderDeliveryEmployee;
    
    @FXML private RadioButton allPaidFilter, paidFIlter, unpaidFilter, allDeliveredFilter, deliveredFilter, undeliveredFilter;
    
    // ======================== STCOK TAB ==========================
    @FXML private TableView<Uniform> uniformsTableview;
    @FXML private TableColumn<Uniform, Long> idColumn;
    @FXML private TableColumn<Uniform, String> typeColumn;
    @FXML private TableColumn<Uniform, String> sizeColumn;
    @FXML private TableColumn<Uniform, Boolean> genderColumn;
    @FXML private TableColumn<Uniform, Integer> uniformQuantityColumn;
    @FXML private TableColumn<Uniform, Integer> uniformUnitPriceColumn;
    @FXML private TableColumn<Uniform, Integer> uniformPurchasePriceColumn;
    @FXML private TableColumn<Uniform, Integer> downstockColumn;
    @FXML private TableColumn<Uniform, Integer> upstockColumn;
    @FXML private Button editUniformButton, purchaseUniformButton;
    
    //==================== PURCHASE TAB ===================
    @FXML private ListView<UniformType> uniformTypesListview;
    @FXML private ListView<UniformSize> uniformSizesListview;
    @FXML private Button editUniformTypeButton, deleteUniformTypeButton;
    @FXML private Button editUniformSizeButton, deleteUniformSizeButton;
    
    @FXML private TableView<UniformPurchase> uniformPurchasesTable;
    @FXML private TableColumn<UniformPurchase, Long> purchaseIdColumn;
    @FXML private TableColumn<UniformPurchase, Uniform> purchaseUniformColumn;
    @FXML private TableColumn<UniformPurchase, Integer> purchaseQuantityColumn;
    @FXML private TableColumn<UniformPurchase, Integer> purchaseUnitPriceColumn;
    @FXML private TableColumn<UniformPurchase, Integer> purchaseTotalColumn;
    @FXML private TableColumn<UniformPurchase, Integer> purchaseStatusColumn;
    @FXML private TableColumn<UniformPurchase, String> purchaseDateColumn;
    @FXML private TableColumn<UniformPurchase, String> purchaseReceptionColumn;
    @FXML private TableColumn<UniformPurchase, String> purchaserColumn;
    @FXML private TableColumn<UniformPurchase, String> receptorColumn;
    @FXML private Button markAsReceivedButton, cancelPurchaseButton;
    
    
    private final UniformTypeModel uniformTypeModel = new UniformTypeModel();
    private final UniformSizeModel uniformSizeModel = new UniformSizeModel();
    private final UniformOrderModel uniformOrderModel = new UniformOrderModel();
    private final UniformPurchaseModel uniformPurchaseModel = new UniformPurchaseModel();
    private final UniformModel uniformModel = new UniformModel();
    private final StudentModel studentModel = new StudentModel();
    
    private final Predicate<UniformOrder> noFilterPredicate = (UniformOrder t) -> true;
    private final Predicate<UniformOrder> allDeliveredPredicate  = (UniformOrder t) -> t.isDelivered();
    private final Predicate<UniformOrder> allUndeliveredPredicate = (UniformOrder t) -> !t.isDelivered();
    private final Predicate<UniformOrder> showPaidAll = UniformOrder::isPaid;
    private final Predicate<UniformOrder> showPaidDelivered = (UniformOrder t) -> t.isPaid() && t.isDelivered();
    private final Predicate<UniformOrder> showPaidNonDelivered = (UniformOrder t) -> t.isPaid() && !t.isDelivered();
    private final Predicate<UniformOrder> showNonPaidAll = (UniformOrder t) -> !t.isPaid();
    private final Predicate<UniformOrder> showNonPaidDelivered = (UniformOrder t) -> !t.isPaid() && t.isDelivered();
    private final Predicate<UniformOrder> showNonPaidNonDelivered = (UniformOrder t) -> !t.isPaid() && !t.isDelivered();
    
    private final ObservableList<UniformPurchase> UNIFORM_PURCHASES = FXCollections.observableArrayList();
    private final ObservableList<Uniform> UNIFORM_STOCK = FXCollections.observableArrayList();
    private final ObservableList<UniformOrder> UNIFORM_ORDERS = FXCollections.observableArrayList();
    private final ObservableList<UniformType> UNIFORM_TYPES = FXCollections.observableArrayList();
    private final ObservableList<UniformSize> UNIFORM_SIZES = FXCollections.observableArrayList();
    private FilteredList<UniformOrder> filteredList;
    
    private final BooleanProperty canDeliver = new SimpleBooleanProperty(false);
    private final BooleanProperty canProccessPurchase = new SimpleBooleanProperty(false);
   
    @Override
    protected void initUI() {
        
        tabpane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            uniformPurchasesTable.refresh();
            uniformsTableview.refresh();
            ordersTableview.refresh();
        });
        
        // =================== FIRST TAB ==============================
        
        // uniform order table columns
        
        registrationColumn.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, String> p)
                -> new SimpleStringProperty(p.getValue().getStudent().getRegistrationNumber()));
       
        studentFullnameColumn.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, String> p)
                -> new SimpleStringProperty(p.getValue().getStudent().getFullname()));
       
        classroomColumn.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, String> param) -> 
                new SimpleStringProperty(
                        param.getValue().getStudent().
                                getClassroom().
                                getShortname()));
        
        uniformTypeColumn.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, String> p)
                -> new SimpleStringProperty(p.getValue().getUniform().getType().getType()));
                
        uniformSizeColumn.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, String> p)
                -> new SimpleStringProperty(p.getValue().getUniform().getSize().getUniformSize()));
        
        uniformGenderColumn.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, Boolean> p)
                -> new SimpleBooleanProperty(p.getValue().getUniform().getGender()));
        uniformGenderColumn.setCellFactory(cf ->new BooleanTableCell<UniformOrder>("Garçon", "Fille"));
        
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("paid"));
        paidColumn.setCellFactory(cf ->new BooleanTableCell<UniformOrder>("Oui", "Non"));
        
        deliveredColumn.setCellValueFactory(new PropertyValueFactory<>("delivered"));
        deliveredColumn.setCellFactory(cf ->new BooleanTableCell<UniformOrder>("Oui", "Non"));
        
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        deliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        orderSavingEmployee.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, String> p)
                -> new SimpleStringProperty(p.getValue().getCreator().getFullname()));
        orderDeliveryEmployee.setCellValueFactory((TableColumn.CellDataFeatures<UniformOrder, String> p)
                -> new SimpleStringProperty(p.getValue().getDeliverer() == null ? null : p.getValue().getDeliverer().getFullname()));
        
        // buttons disabled
        deliverOrderButton.disableProperty().bind(
                Bindings.or(
                        Bindings.isNull(ordersTableview.getSelectionModel().selectedItemProperty()),
                        canDeliver.not())
        );
        
        ordersTableview.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends UniformOrder> observable, UniformOrder oldValue, UniformOrder selected) -> {
            canDeliver.set(selected == null ? null : !selected.isDelivered() && selected.isPaid());
        });
        
        editUniformTypeButton.disableProperty().bind(Bindings.isNull(uniformTypesListview.getSelectionModel().selectedItemProperty()));
        deleteUniformTypeButton.disableProperty().bind(editUniformTypeButton.disableProperty());
        
        editUniformSizeButton.disableProperty().bind(Bindings.isNull(uniformSizesListview.getSelectionModel().selectedItemProperty()));
        deleteUniformSizeButton.disableProperty().bind(editUniformSizeButton.disableProperty());
        
        // =========================== Second tab ==========================
        // Sotck table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Uniform, String> p)
                -> new SimpleStringProperty(p.getValue().getType().getType()));
        sizeColumn.setCellValueFactory((TableColumn.CellDataFeatures<Uniform, String> p)
                -> new SimpleStringProperty(p.getValue().getSize().getUniformSize()));
        
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderColumn.setCellFactory(cf ->new BooleanTableCell<Uniform>("Garçon", "Fille"));
        
        uniformQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
        uniformUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        uniformPurchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        downstockColumn.setCellValueFactory(new PropertyValueFactory<>("downStock"));
        upstockColumn.setCellValueFactory(new PropertyValueFactory<>("upStock"));
        
        editUniformButton.disableProperty().bind(Bindings.isNull(uniformsTableview.getSelectionModel().selectedItemProperty()));
        purchaseUniformButton.disableProperty().bind(editUniformButton.disableProperty());
        
        // ========================== Third Tab ======================
        purchaseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        purchaseUniformColumn.setCellValueFactory(new PropertyValueFactory<>("uniform"));
        purchaseQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchaseUnitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        purchaseTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        purchaseStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        purchaseStatusColumn.setCellFactory(cf ->new IntegerEnumTableCell<UniformPurchase>("En Cours", "Reçu", "Annulé"));
        
        purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        purchaseReceptionColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        purchaserColumn.setCellValueFactory(new PropertyValueFactory<>("purchaser"));
        receptorColumn.setCellValueFactory(new PropertyValueFactory<>("receptor"));
        
        markAsReceivedButton.disableProperty().bind(
                Bindings.or(
                        Bindings.isNull(uniformPurchasesTable.getSelectionModel().selectedItemProperty()),
                        canProccessPurchase.not())
        );
        uniformPurchasesTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends UniformPurchase> observable, UniformPurchase oldValue, UniformPurchase selected) -> {
            canProccessPurchase.set(selected == null ? false : selected.getStatus() == UniformPurchase.Status.PENDING);
        });
        cancelPurchaseButton.disableProperty().bind(markAsReceivedButton.disableProperty());
    }
    
    @FXML
    protected void deliverOrderAction(ActionEvent event){
        UniformOrder order = ordersTableview.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Livraison Uniforme");
        alert.setHeaderText("Vous êtes sur le point de marquer cette commande comme livrée !");
        alert.setContentText("Assurez vous de l'authenticité du reçu présenté, et \nd'avoir remis la bonne taille d'uniforme à l'élève\nContinuer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                order.setDelivered(true);
                order.setDeliverer(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity());
                order.setDeliveryDate(new Date());
                uniformOrderModel.update(order);
                
                int index = UNIFORM_STOCK.indexOf( order.getUniform());
                Uniform uniform = UNIFORM_STOCK.get(index);
                uniform.setQuantityInStock(uniform.getQuantityInStock() - order.getQuantity());
               
                uniformModel.update(uniform);
                ordersTableview.getSelectionModel().clearSelection();
                ordersTableview.refresh();
            }
            catch(Exception ex){
                ex.printStackTrace();
                showError("erreur", "Une erreur est survenue lors de l'opération ", ex.getMessage());
            }
        }
        
    }
    //====================================
    @FXML
    public void editUniformAction(ActionEvent event){
        Uniform selected = uniformsTableview.getSelectionModel().getSelectedItem();
        FormController.showForm("uniforme/Form.fxml", selected);
        uniformsTableview.refresh();
        uniformsTableview.getSelectionModel().clearSelection();
    }
    @FXML
    public void purchaseUniformAction(ActionEvent event){
        Uniform selected = uniformsTableview.getSelectionModel().getSelectedItem();
        String qty = DialogUtils.prompt("Commande d'uniformes", "Entrer la quantité d'uniformes à confectionner", "Quantité: ", null);
        if(AppUtils.isValidPositiveInteger(qty)){
            
            int quantity = Integer.parseInt(qty);
            
            UniformPurchase purchase = new UniformPurchase();
            purchase.setPurchaseDate(new Date());
            purchase.setQuantity(quantity);
            purchase.setUnitPrice(selected.getUnitPrice());
            purchase.setTotal(quantity * selected.getUnitPrice());
            purchase.setStatus(UniformPurchase.Status.PENDING);
            purchase.setUniform(selected);
            purchase.setPurchaser(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity());
            uniformPurchaseModel.save(purchase);
            UNIFORM_PURCHASES.add(purchase);
            DialogUtils.showSuccess("Done !", "Ordre effectué!", "L'ordre de fabrication a été placé avec succès!");
            uniformsTableview.getSelectionModel().clearSelection();
        }
        else{
           DialogUtils.showError("erreur", "Entrée invalide", "Quantité entrée invalide!");
        }
    }
    //========================================
    
    @FXML
    public void markAsReceivedAction(ActionEvent event){
        UniformPurchase purchase = uniformPurchasesTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Reception Stock d'Uniformes");
        alert.setHeaderText("Vous êtes sur le point de marquer cette ordre comme reçue !");
        alert.setContentText("Assurez vous d'avoir vérifié le stock d'uniformes ainsi que sa qualité.\n Continuer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                purchase.setStatus(UniformPurchase.Status.DELIVERED);
                purchase.setReceptor(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity());
                purchase.setDeliveryDate(new Date());
                
                uniformPurchaseModel.update(purchase);
                uniformPurchasesTable.refresh();
                
                int index = UNIFORM_STOCK.indexOf(purchase.getUniform());
                Uniform uniform = UNIFORM_STOCK.get(index);
                uniform.setQuantityInStock(uniform.getQuantityInStock()+purchase.getQuantity());
                uniformModel.update(uniform);
                uniformPurchasesTable.getSelectionModel().clearSelection();
            }
            catch(Exception ex){
                ex.printStackTrace();
                showError("erreur", "Une erreur est survenue lors de l'opération ", ex.getMessage());
            }
        }
    }
    
    @FXML
    public void cancelPurchaseAction(ActionEvent event){
        UniformPurchase purchase = uniformPurchasesTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Annulation Commande de confection");
        alert.setHeaderText("Vous êtes sur le point d'annuler cette commande de confection d'uniformes !");
        alert.setContentText("Cette action est irréversible !\n Continuer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                purchase.setStatus(UniformPurchase.Status.CANCELLED);
                purchase.setReceptor(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity());
                
                uniformPurchaseModel.update(purchase);
                uniformPurchasesTable.refresh();
                uniformPurchasesTable.getSelectionModel().clearSelection();
            }
            catch(Exception ex){
                ex.printStackTrace();
                showError("erreur", "Une erreur est survenue lors de l'opération ", ex.getMessage());
            }
        }
    }
    
    @FXML
    public void addUniformTypeAction(ActionEvent event){
        String type = DialogUtils.prompt("Nouveau Type d'uniforme", "Entrer le type d'uniforme", "Type", null);
        if(type != null && type.trim().isEmpty()){
            DialogUtils.showError("Erreur !", "Entrée invalide", "Type d'uniforme invalide");
        }
        else{
            try{
                UniformType utype = new UniformType();
                utype.setType(type);
                uniformTypeModel.save(utype);
                UNIFORM_TYPES.add(utype);
                
                // creates all uniform with size and gender
                boolean[]genders = {true, false};
                UNIFORM_SIZES.forEach(size->{
                    for(boolean gender : genders){
                        Uniform uniform = new Uniform();
                        uniform.setDownStock(5);
                        uniform.setGender(gender);
                        uniform.setSize(size);
                        uniform.setType(utype);
                        uniform.setUpStock(100);
                        uniformModel.save(uniform);
                        UNIFORM_STOCK.add(uniform);
                    }
                });
            }
            catch(Exception ex){
                DialogUtils.showError("Erreur !", "Une erreur est survenue lors de l'enregistrement", ex.getMessage());
            }
        }
    }
    @FXML
    public void editUniformTypeAction(ActionEvent event){
        UniformType selected = uniformTypesListview.getSelectionModel().getSelectedItem();
        String type = DialogUtils.prompt(
                "Mise à jour Type d'uniforme",
                "Entrer le nouveau type d'uniforme",
                "Type",
                selected.getType());
        if(type != null && type.trim().isEmpty()){
            DialogUtils.showError("Erreur !", "Entrée invalide", "Type d'uniforme invalide");
        }
        else{
            try{
                selected.setType(type);
                uniformTypeModel.update(selected);
                uniformTypesListview.refresh();
            }
            catch(Exception ex){
                DialogUtils.showError("Erreur !", "Une erreur est survenue lors de l'enregistrement", ex.getMessage());
            }
        }
    }
    @FXML
    public void deleteUniformTypeAction(ActionEvent event){
        UniformType selected = uniformTypesListview.getSelectionModel().getSelectedItem();
        uniformTypesListview.getSelectionModel().clearSelection();
        DialogUtils.showAndDelete(
                "Suppression du type "+selected.getType(),
                selected,
                UNIFORM_TYPES,
                uniformTypeModel);
    }
    @FXML
    public void addUniformSizeAction(ActionEvent event){
        String size = DialogUtils.prompt("Nouvelle taille d'uniforme", "Entrer la taille de l'uniforme", "Taille", null);
        if(size != null && size.trim().isEmpty()){
            DialogUtils.showError("Erreur !", "Entrée invalide", "Taille d'uniforme invalide");
        }
        else{
            try{
                UniformSize uSize = new UniformSize();
                uSize.setUniformSize(size);
                uniformSizeModel.save(uSize);
                UNIFORM_SIZES.add(uSize);
                
                // creates all uniform with size and gender
                boolean[]genders = {true, false};
                UNIFORM_TYPES.forEach(type->{
                    for(boolean gender : genders){
                        Uniform uniform = new Uniform();
                        uniform.setDownStock(5);
                        uniform.setGender(gender);
                        uniform.setSize(uSize);
                        uniform.setType(type);
                        uniform.setUpStock(100);
                        uniformModel.save(uniform);
                        UNIFORM_STOCK.add(uniform);
                    }
                });
            }
            catch(Exception ex){
                DialogUtils.showError("Erreur !", "Une erreur est survenue lors de l'enregistrement", ex.getMessage());
            }
        }
    }
    @FXML
    public void editUniformSizeAction(ActionEvent event){
        UniformSize selected = uniformSizesListview.getSelectionModel().getSelectedItem();
        String size = DialogUtils.prompt(
                "Mise à jour taille d'uniforme",
                "Entrer la nouvelle taille",
                "Taille",
                selected.getUniformSize());
        if(size != null && size.trim().isEmpty()){
            DialogUtils.showError("Erreur !", "Entrée invalide", "Taille d'uniforme invalide");
        }
        else{
            try{
                selected.setUniformSize(size);
                uniformSizeModel.update(selected);
                uniformSizesListview.refresh();
            }
            catch(Exception ex){
                DialogUtils.showError("Erreur !", "Une erreur est survenue lors de l'enregistrement", ex.getMessage());
            }
        }
    }
    @FXML
    public void deleteUniformSizeAction(ActionEvent event){
        UniformSize selected = uniformSizesListview.getSelectionModel().getSelectedItem();
        uniformSizesListview.getSelectionModel().clearSelection();
        DialogUtils.showAndDelete(
                "Suppression de la taille "+selected.getUniformSize(),
                selected,
                UNIFORM_SIZES,
                uniformSizeModel);
    }

    @FXML
    public void filterDataAction(ActionEvent event){

        if(allPaidFilter.isSelected() && allDeliveredFilter.isSelected())
            filteredList.setPredicate(noFilterPredicate);
        else if(allPaidFilter.isSelected() && deliveredFilter.isSelected())
            filteredList.setPredicate(allDeliveredPredicate);
        else if(allPaidFilter.isSelected() && undeliveredFilter.isSelected())
            filteredList.setPredicate(allUndeliveredPredicate);
        
        else if(paidFIlter.isSelected() && allDeliveredFilter.isSelected())
            filteredList.setPredicate(showPaidAll);
        else if(paidFIlter.isSelected() && deliveredFilter.isSelected())
            filteredList.setPredicate(showPaidDelivered);
        else if(paidFIlter.isSelected() && undeliveredFilter.isSelected())
            filteredList.setPredicate(showPaidNonDelivered);
        
        else if(unpaidFilter.isSelected() && allDeliveredFilter.isSelected())
            filteredList.setPredicate(showNonPaidAll);
        else if(unpaidFilter.isSelected() && deliveredFilter.isSelected())
            filteredList.setPredicate(showNonPaidDelivered);
        else if(unpaidFilter.isSelected() && undeliveredFilter.isSelected())
            filteredList.setPredicate(showNonPaidNonDelivered);
    }
    
    @Override
    protected void dataLoaded(Object data) {
        filteredList = new FilteredList<>(UNIFORM_ORDERS, showPaidNonDelivered);
        
        ordersTableview.setItems(filteredList);
        uniformSizesListview.setItems(UNIFORM_SIZES);
        uniformTypesListview.setItems(UNIFORM_TYPES);
        uniformsTableview.setItems(UNIFORM_STOCK);
        uniformPurchasesTable.setItems(UNIFORM_PURCHASES);
    }

    @Override
    protected Object loadData() {
        
        UNIFORM_SIZES.clear();
        UNIFORM_TYPES.clear();
        UNIFORM_ORDERS.clear();
        UNIFORM_PURCHASES.clear();
        UNIFORM_STOCK.clear();
        
        UNIFORM_SIZES.addAll(uniformSizeModel.getAll());
        UNIFORM_TYPES.addAll(uniformTypeModel.getAll());
        UNIFORM_ORDERS.addAll(uniformOrderModel.getAll());
        UNIFORM_PURCHASES.addAll(uniformPurchaseModel.getAll());
        UNIFORM_STOCK.addAll(uniformModel.getAll());
        
        return null;
    }
    

    @Override
    protected String getPageName() {
        return "Uniformes";
    }

}
