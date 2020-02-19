//package com.lesbambinos.controller.purchase;
//
//import com.lesbambinos.auth.Permissions;
//import com.lesbambinos.auth.SecurityManager;
//import com.lesbambinos.controller.AbstractController;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//import com.lesbambinos.entity.Purchase;
//import com.lesbambinos.entity.PurchaseProduct;
//import com.lesbambinos.interfaces.PurchaseInterface;
//import com.lesbambinos.model.PurchaseModel;
//import com.lesbambinos.model.PurchaseProductModel;
//import java.io.File;
//import java.util.prefs.Preferences;
//
//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.SplitPane;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.FileChooser;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.stage.Window;
//
//public class PurchaseController extends AbstractController implements PurchaseInterface {
//    
//    @FXML
//    private TableView<Purchase> purchaseTable;
//    @FXML
//    private TableColumn<Purchase, Long> idColumn;
//    @FXML
//    private TableColumn<Purchase, String> supplierColumn, dateColumn;
//    @FXML
//    private TableColumn<Purchase, Double> totalColumn;
//
//    
//    @FXML TableView<PurchaseProduct> purchaseDetailsTable;
//    @FXML private TableColumn<PurchaseProduct, Long> productIdColumn;
//    @FXML private TableColumn<PurchaseProduct, String> employeesColumn;
//    @FXML private TableColumn<PurchaseProduct, String> productNameColumn, productChineseNameColumn;
//    @FXML private TableColumn<PurchaseProduct, Double> prodQuantityColumn, prodSalePriceColumn, 
//            prodPurchasePriceColumn, prodWholesalePriceColumn, realQuantityColumn, prodTotalColumn;
//    
//    @FXML private Label collapseButton;
//    @FXML private Button importButton, checkButton;
//    @FXML private SplitPane splitPane;
//    
//    private static final String DOWN ="\u25BC";
//    private static final String UP ="\u25B2";
//    
//    
//    @FXML private TextField searchField;
//
//    private PurchaseModel purchaseModel;
//    private PurchaseProductModel purchaseProductModel;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        
//        super.initialize(location, resources);
//        
//        collapseButton.setText(DOWN);
//        
//        purchaseModel = new PurchaseModel();
//        purchaseProductModel = new PurchaseProductModel();
//        
//        loadData();
//        
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        supplierColumn.setCellValueFactory((TableColumn.CellDataFeatures<Purchase, String> p)
//                -> new SimpleStringProperty(p.getValue().getSupplier().getName()));
//        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
//        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
//        
//        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId2"));
//        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
//        productChineseNameColumn.setCellValueFactory(new PropertyValueFactory<>("productChineseName"));
//        prodQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        realQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("realQuantity"));
//        prodSalePriceColumn.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
//        prodPurchasePriceColumn.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
//        prodWholesalePriceColumn.setCellValueFactory(new PropertyValueFactory<>("wholeSalePrice"));
//        prodTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
//        
//       
//        purchaseDetailsTable.itemsProperty().addListener(new ChangeListener<ObservableList<PurchaseProduct>>() {
//            @Override
//            public void changed(ObservableValue<? extends ObservableList<PurchaseProduct>> observable, ObservableList<PurchaseProduct> oldValue, ObservableList<PurchaseProduct> newValue) {
//                boolean disable = newValue==null || newValue.isEmpty();
//                checkButton.setDisable(disable);
//            }
//        });
//        //checkButton.disableProperty().bind(purchaseTable.getSelectionModel().selectedItemProperty().isNull());
//        checkButton.setDisable(true);
//        purchaseTable.getSelectionModel().selectedItemProperty().addListener(
//                (observable, oldValue, selectedPurchase) -> loadPurchaseDetails(selectedPurchase));
//                
//        purchaseTable.setItems(PURCHASELIST);
//        
//        filterData();
//        
//    }
//    
//    @Override
//    protected void checkPermissions() throws NoSuchFieldException{
//        super.checkPermissions();
//        SecurityManager.controlAceessRight(importButton, currentUser.hasPermission(Permissions.ImportSupOrdRight));
//    }
//    
//    private void loadPurchaseDetails(Purchase purchase){
//        
//        purchaseDetailsTable.setItems(null);
//        if(purchase!=null)
//            purchaseDetailsTable.setItems(purchaseProductModel.getPurchaseProductByPurchaseId(purchase.getId()));
//    }
//
//    private void filterData() {
//        FilteredList<Purchase> searchedData = new FilteredList<>(PURCHASELIST, e -> true);
//
//        searchField.setOnKeyReleased(e -> {
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//                searchedData.setPredicate(purchase -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (purchase.getSupplier().getName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (purchase.getDate().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
//                    
//                    return false;
//                });
//            });
//
//            SortedList<Purchase> sortedData = new SortedList<>(searchedData);
//            sortedData.comparatorProperty().bind(purchaseTable.comparatorProperty());
//            purchaseTable.setItems(sortedData);
//        });
//    }
//    
//    private void loadData() {
//    
//        if(!PURCHASELIST.isEmpty()){
//            PURCHASELIST.clear();
//        }
//        
//        PURCHASELIST.addAll(purchaseModel.getPurchases());
//    }
//    
//    @FXML
//    public void minimizeDetailsAction(){
//        if(collapseButton.getText().equalsIgnoreCase(DOWN)){
//            splitPane.setDividerPositions(0.925);
//            collapseButton.setText(UP);
//        }
//        else{
//            splitPane.setDividerPositions(0.5);
//            collapseButton.setText(DOWN);
//        }
//    }
//    
//    @FXML
//    public void importAction(ActionEvent event) throws Exception{
//        
//        FileChooser fileChooser = new FileChooser();
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excels files (*.xlsx)", "*.xlsx");
//        fileChooser.getExtensionFilters().add(extFilter);
//        fileChooser.setTitle("Import purchases");
//        
//        Preferences prefs =  Preferences.userRoot();
//        String prevPath = prefs.get("qitech.pos.last_imported_location", null);
//        if(prevPath!=null){
//            if(!File.separator.equals("/"))
//                prevPath = prevPath.replace("/", "\\");
//            File prevFolder = new File(prevPath);
//            if(prevFolder.exists())
//                fileChooser.setInitialDirectory(prevFolder);
//        }
//        Window stage = ((Node)event.getSource()).getScene().getWindow();
//        
//        File selectedFile = fileChooser.showOpenDialog(stage);
//        if(selectedFile == null )
//            return;
//        
//        String selectedFileParent = selectedFile.getParent();
//        if(!File.separator.equals("/"))
//            selectedFileParent = selectedFileParent.replace("\\", "/");
//        prefs.put("qitech.pos.last_imported_location", selectedFileParent);
//        
//        showImportForm(selectedFile);
//    }
//    
//    @FXML
//    public void checkAction(ActionEvent event) throws Exception{
//        
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/purchase/PurchaseCheck.fxml"));
//        Parent root = loader.load();
//        PurchaseCheckController controller = loader.getController();
//        controller.setPurchase(purchaseTable.getSelectionModel().getSelectedItem());
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
//        stage.setTitle("Check Purchase Products");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//        this.purchaseDetailsTable.refresh();
//    }
//    
//    private void showImportForm(File f) throws Exception{
//        
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/purchase/Import.fxml"));
//        Parent root = loader.load();
//        ImportController importController = loader.getController();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setOnShown((e)->importController.createImportConfigFromExcel(f));
//        root.setOnMousePressed((MouseEvent e) -> {
//            xOffset = e.getSceneX();
//            yOffset = e.getSceneY();
//        });
//        root.setOnMouseDragged((MouseEvent e) -> {
//            stage.setX(e.getScreenX() - xOffset);
//            stage.setY(e.getScreenY() - yOffset);
//        });
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("Import Purchase");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//    }
//    
//    @FXML
//    public void addAction(ActionEvent event) throws Exception {
//    
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/purchase/Add.fxml"));
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
//        stage.setTitle("New Purchase");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//    }
//    
//    @FXML
//    public void addProductAction(ActionEvent event) throws Exception {
//        
//    }
//}
