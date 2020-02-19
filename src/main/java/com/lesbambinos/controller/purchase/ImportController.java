///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.lesbambinos.controller.purchase;
//
//import com.lesbambinos.entity.Purchase;
//import com.lesbambinos.entity.PurchaseProduct;
//import com.lesbambinos.entity.Supplier;
//import com.lesbambinos.interfaces.PurchaseInterface;
//import com.lesbambinos.model.PurchaseModel;
//import com.lesbambinos.model.PurchaseProductModel;
//import com.lesbambinos.model.SupplierModel;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.ResourceBundle;
//import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.lesbambinos.config.AppConfig;
//import java.lang.reflect.Type;
//
///**
// * FXML Controller class
// *
// * @author rostand
// */
//public class ImportController implements Initializable, PurchaseInterface {
//    
//    static final String PRODUCT_ID = "Product ID";
//    static final String BARCODE = "Barcode";
//    static final String PRODUCT_NAME = "Product Name";
//    static final String PRODUCT_CHINESE_NAME = "Chinese Product Name";
//    static final String QUANTITY = "Quantity";
//    static final String SALE_PRICE = "Sale Price";
//    static final String PURCHASE_PRICE = "Purchase Price";
////    static final String WHOLESALE_PRICE = "Wholesale Price";
//    static final String TOTAL = "Total";
//
//    static String[] purchaseProductColumns = {PRODUCT_ID, BARCODE, PRODUCT_NAME , PRODUCT_CHINESE_NAME, QUANTITY,
//        SALE_PRICE, PURCHASE_PRICE,  TOTAL};
//    final ObservableList<ImportController.POICellWrapper> options = FXCollections.observableArrayList();
//    
//    @FXML ComboBox<Supplier> supplierBox;
//    @FXML TableView<ImportConfig> mappingsTable;
//    @FXML TableColumn<ImportConfig, POICellWrapper> excelColumn;
//    @FXML TableColumn<ImportConfig, String> purchaseProductColumn;
//    
//    @FXML Button importButton;
//    
//    private SupplierModel supplierModel;
//    private PurchaseModel purchaseModel;
//    private PurchaseProductModel purchaseProductModel;
//    private File excelFile;
//    private Iterator<Row> rowsIterator;
//    
//    /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        supplierModel = new SupplierModel();
//        purchaseModel = new PurchaseModel();
//        purchaseProductModel = new PurchaseProductModel();
//        
//        supplierBox.setItems(supplierModel.getSuppliers());
//        
//        purchaseProductColumn.setCellValueFactory(new PropertyValueFactory<>("purcharseProductField"));
//        excelColumn.setCellValueFactory((TableColumn.CellDataFeatures<ImportConfig, POICellWrapper> param) -> param.getValue().columnProperty());
//        excelColumn.setCellFactory((TableColumn<ImportConfig, POICellWrapper> param) -> new ComboTableCell(options));
//        options.add(POICellWrapper.IGNORED_CELL);
//    }  
//    
//    
//    public void createImportConfigFromExcel(File f){
//        excelFile = f;
//        try{
//            FileInputStream fip = new FileInputStream(excelFile); 
//
//            // Getting the workbook instance for XLSX file 
//            XSSFWorkbook workbook = new XSSFWorkbook(fip);
//            
//            Sheet worksheet = workbook.getSheetAt(0);
//            rowsIterator = worksheet.iterator();
//            ObservableList<ImportConfig> items = mappingsTable.getItems();
//            
//            Map<String, Integer> importConfigs =  loadImportConfig();
//            Row headerRow = rowsIterator.next();
//            Iterator<Cell> cellIterator = headerRow.iterator();
//            while (cellIterator.hasNext()) {
//                Cell currentCell = cellIterator.next();
//                options.add(new POICellWrapper(currentCell));
//            }
//                
//            if(importConfigs.isEmpty()){
//                for(String field : purchaseProductColumns)
//                    items.add(new ImportConfig(null, field));
//            }
//            else{
//                importConfigs.entrySet().forEach(entry->{
//                    int colIndex = entry.getValue();
//                    POICellWrapper cell = colIndex == -1? POICellWrapper.IGNORED_CELL : new POICellWrapper(headerRow.getCell(colIndex));
//                    items.add(new ImportConfig(cell, entry.getKey()));
//                });
//            }
//            
//        }
//        catch(Exception ex){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error opening file");
//            alert.setHeaderText("Error importing purchase file");
//            alert.setContentText(ex.getMessage());
//            alert.show();
//        }
//    }
//    
//    @FXML
//    public void handleImport(ActionEvent event) {
//        
//        if (validateInput()) {
//            
//            List<ImportConfig> importEntry = mappingsTable.getItems();
//            
//            Purchase purchase = null;
//            double purchaseTotal = 0.0;
//            if(rowsIterator.hasNext()){
//                purchase = new Purchase(supplierBox.getValue());
//            }
//            
//            List<PurchaseProduct> purchaseProducts = new ArrayList<>();
//            Map<Integer, String> importErrors = new HashMap<>();
//                        
//            while (rowsIterator.hasNext()) {
//
//                Row currentRow = rowsIterator.next();
//                
//                PurchaseProduct purchaseProduct = new PurchaseProduct();
//                purchaseProduct.setPurchase(purchase);
//                
//                try{
//                    for(ImportConfig entry : importEntry){
//                        if(entry.getColumn() == POICellWrapper.IGNORED_CELL)
//                            continue;
//                        Cell cell = currentRow.getCell(entry.getColumn().getColumnIndex());
//                        
//                        switch(entry.purcharseProductField){
//                            case PRODUCT_ID :
//                                
//                                purchaseProduct.setProductId2(getStringCellValue(cell));
//                                break;
//                            case PRODUCT_NAME:
//                                purchaseProduct.setProductName(cell.getStringCellValue());
//                                break;
//                            case PRODUCT_CHINESE_NAME:
//                                purchaseProduct.setProductChineseName(cell.getStringCellValue());
//                                break;
//                            case BARCODE:
//                                purchaseProduct.setBarcode(cell.getStringCellValue());
//                                break;
//                            case QUANTITY:
//                                purchaseProduct.setQuantity(getDoubleCellValue(cell));
//                                break;
//                            case SALE_PRICE:
//                                purchaseProduct.setSalePrice(getDoubleCellValue(cell));
//                                break;
//                            case PURCHASE_PRICE:
//                                purchaseProduct.setPurchasePrice(getDoubleCellValue(cell));
//                                break;
////                            case WHOLESALE_PRICE:
////                                purchaseProduct.setWholeSalePrice(cell.getNumericCellValue());
////                                break;
//                            case TOTAL:
//                                double total = getDoubleCellValue(cell);
//                                purchaseTotal += total;
//                                purchaseProduct.setTotal(total);
//                                break;
//                        }
//                        purchaseProducts.add(purchaseProduct);
//                    }
//                }
//                catch(Exception ex){
//                    if(ex.getMessage() != null)
//                        importErrors.put(currentRow.getRowNum(), ex.getMessage());
//                }
//            }
//            
//            try{
//                //Now start db writes
//                purchase.setTotal(purchaseTotal);
//
//                purchaseProductModel.bacthSavePurchaseProducts(purchase, purchaseProducts);
//                
//                PURCHASELIST.clear();
//                PURCHASELIST.addAll(purchaseModel.getPurchases());
//                saveImportConfig();
//                showImportResult(importErrors, purchaseProducts.size());
//            }
//            catch(Exception ex){
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Invalid Fields");
//                alert.setHeaderText("Please correct invalid fields");
//                alert.setContentText(ex.getMessage());
//                alert.showAndWait();
//            }
//        }
//    }
//    
//    private double getDoubleCellValue(Cell cell){
//        return cell.getCellType()== CellType.NUMERIC ? 
//                cell.getNumericCellValue() : 
//                Double.parseDouble(cell.getStringCellValue());
//    }
//    private String getStringCellValue(Cell cell){
//        return cell.getCellType()== CellType.STRING ? 
//                cell.getStringCellValue(): 
//                ""+cell.getNumericCellValue();
//    }
//    
//    private Map<String, Integer> loadImportConfig(){
//        
//        Map<String, Integer> outcome = new HashMap<>();
//        
//        File configFile = new File(AppConfig.IMPORT_CONFIG_FILE_PATH);
//        if(configFile.exists()){
//            try {
//                BufferedReader bf = new BufferedReader(new FileReader(configFile));
//                StringBuilder sb = new StringBuilder();
//                String line=null;
//                while((line=bf.readLine())!=null)
//                    sb.append(line);
//                Gson gson = new Gson();
//                Type type = new TypeToken<HashMap<String, Integer>>(){}.getType();
//                outcome = gson.fromJson(sb.toString(), type);
//            } catch (Exception ex) {
//                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return outcome;
//    }
//    
//    private void saveImportConfig(){
//        
//        File configFile = new File(AppConfig.IMPORT_CONFIG_FILE_PATH);
//        if(!configFile.exists()){
//            configFile.getParentFile().mkdirs();
//        }
//        
//        Map<String, Integer> mappings = new HashMap<>();
//        mappingsTable.getItems().forEach(importConf->{
//            int colIndex = importConf.getColumn() == POICellWrapper.IGNORED_CELL ? -1 : importConf.getColumn().getColumnIndex();
//            mappings.put(importConf.purcharseProductField, colIndex);
//        });
//        
//        try {
//            FileWriter writer = new FileWriter(configFile, false);
//            Gson gson = new Gson();
//            String jsonString = gson.toJson(mappings);
//            writer.append(jsonString);
//            writer.flush();
//            writer.close();
//            
//        } catch (IOException ex) {
//            Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    private void showImportResult(Map<Integer, String> importErrors, int inserted){
//        
//        StringBuilder sb = new StringBuilder();
//        sb.append(inserted).append(" Purchase product(s) imported successfully");
//        String headerText = "Purchase Import Completed";
//        String title = "Successful";
//        Alert.AlertType type = Alert.AlertType.INFORMATION;
//        if(!importErrors.isEmpty()){
//            sb.append("\n").append("Errors").append("\n");
//            importErrors.entrySet().forEach(entry->{
//                sb.append("Line ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
//            });
//            
//            headerText = "Purcharse Import completed with some errors";
//            title = "Warning";
//            type = Alert.AlertType.WARNING;
//            
//        }
//        
//        String meessage = sb.toString();
//        ((Stage) importButton.getScene().getWindow()).close();
//
//        Alert alert = new Alert(type);
//        alert.setTitle(title);
//        alert.setHeaderText(headerText);
//        alert.setContentText(meessage);
//        alert.showAndWait();
//    }
//    
//    @FXML
//    public void handleCancel(ActionEvent event) {
//        mappingsTable.getItems().forEach((item)->item.setColumn(null));
//        supplierBox.valueProperty().setValue(null);
//    }
//
//    private boolean validateInput() {
//
//        String errorMessage = "";
//
//        if (supplierBox.getValue() == null) {
//            errorMessage += "No valid Suppllier!\n";
//        }
//        
//        
//        Set<POICellWrapper> selected = new HashSet<>();
//        for(ImportConfig entry : mappingsTable.getItems()){
//            if(selected.contains(entry.getColumn()) && entry.getColumn()!= POICellWrapper.IGNORED_CELL)
//                errorMessage += "Duplicated choice for "+entry.purcharseProductField+"!\n";
//            else
//                selected.add(entry.getColumn());
//        }
//        
//        errorMessage = mappingsTable.getItems()
//                .stream()
//                .filter((item) -> (item.getColumn() == null)).map((item) -> "Invalid choice for "+item.purcharseProductField+"!\n")
//                .reduce(errorMessage, String::concat);
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
//    
//    @FXML
//    public void closeAction(ActionEvent event) {
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//    }
//    
//    public static class POICellWrapper{
//        public static final POICellWrapper IGNORED_CELL = new POICellWrapper(null);
//        
//        public final Cell cell;
//
//        public POICellWrapper(Cell cell){
//            this.cell = cell;
//        }
//
//        public Cell getCell() {
//            return cell;
//        }
//
//
//        public String getValue(){
//            return cell.getStringCellValue();
//        }
//
//        public int getColumnIndex(){
//            return cell.getColumnIndex();
//        }
//
//        @Override
//        public String toString(){
//            if(this == IGNORED_CELL)
//                return "Do not import";
//            
//            char col = (char)('A'+getColumnIndex());
//            return String.format("Column %c (%s)", col, getValue());
//        }
//
//        @Override
//        public int hashCode() {
//            int hash = 7;
//            return hash;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            if (this == obj) {
//                return true;
//            }
//            if (obj == null) {
//                return false;
//            }
//            if (getClass() != obj.getClass()) {
//                return false;
//            }
//            final POICellWrapper other = (POICellWrapper) obj;
//            if (!Objects.equals(this.cell, other.cell)) {
//                return false;
//            }
//            return true;
//        }
//    }
//
//    public static class ImportConfig{
//        public String purcharseProductField;
//        private final ObjectProperty<POICellWrapper> column = new SimpleObjectProperty<>();
//
//        public POICellWrapper getColumn() {
//            return column.get();
//        }
//
//        public void setColumn(POICellWrapper value) {
//            column.set(value);
//        }
//
//        public ObjectProperty columnProperty() {
//            return column;
//        }
//        
//
//        public ImportConfig(POICellWrapper column, String purcharseProductField) {
//            this.column.set(column);
//            this.purcharseProductField = purcharseProductField;
//        }
//
//        public String getPurcharseProductField() {
//            return purcharseProductField;
//        }
//
//        public void setPurcharseProductField(String purcharseProductField) {
//            this.purcharseProductField = purcharseProductField;
//        }
//
//    }
//    
//}
//
//
//class ComboTableCell extends TableCell<ImportController.ImportConfig, ImportController.POICellWrapper>{
//    
//    private final ComboBox<ImportController.POICellWrapper> editor;
//
//    public ComboTableCell(ObservableList<ImportController.POICellWrapper> options){
//        editor= new ComboBox();
//        editor.setPrefWidth(300);
//        editor.setPromptText("-Select-");
//        editor.setItems(options);
//        editor.valueProperty().addListener(new ChangeListener<ImportController.POICellWrapper>() {
//            @Override
//            public void changed(ObservableValue<? extends ImportController.POICellWrapper> observable, ImportController.POICellWrapper oldValue, ImportController.POICellWrapper newValue) {
//                
//                ImportController.ImportConfig selectedRow  =  getTableView().getItems().get(getIndex());
//                
//                selectedRow.setColumn(newValue);
//                
//                selectedRow.columnProperty().addListener(new ChangeListener() {
//                    @Override
//                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                        Node grap = getTableColumn().getGraphic();
//                        if(grap instanceof ComboBox)
//                            ((ComboBox)grap).setValue(newValue);
//                    }
//                });
//            }
//        });
//    }
//    
//    
//    @Override
//    public void updateItem(ImportController.POICellWrapper item, boolean empty) {
//        
//        super.updateItem(item, empty);
//        
//        if (empty) {
//            setGraphic(null);
//            setText(null);
//        } 
//        else {
//            editor.setValue(item);
//            setGraphic(editor);
//        }
//
//    }
//}
