///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.lesbambinos.controller.employee;
//
//import com.lesbambinos.auth.AuthenticatedUser;
//import com.lesbambinos.auth.SecurityManager;
//import com.lesbambinos.entity.Employee;
//import com.lesbambinos.model.EmployeeModel;
//import java.lang.reflect.Field;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.ResourceBundle;
//import javafx.beans.value.ObservableValue;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//
///**
// * FXML Controller class
// *
// * @author Rostand
// */
//public class PermissionsController implements Initializable {
//
//    @FXML private TableView<Employee> employeesTable1;
//    @FXML private TableView<Employee> employeesTable2;
//    @FXML private TableColumn<Employee, Integer> idCol;
//    @FXML private TableColumn<Employee, String> usernameCol;
//    
//    @FXML private TableColumn<Employee, Integer> posCol;
//    @FXML private TableColumn<Employee, Integer> ImportSupOrdRightCol;
//    @FXML private TableColumn<Employee, Integer> UsePDARightCol;
//    @FXML private TableColumn<Employee, Integer> UsePDAAddProdRightCol;
//    @FXML private TableColumn<Employee, Integer> UsePDAmatchOrderRightCol;
//    @FXML private TableColumn<Employee, Integer> SaleRightCol;
//    @FXML private TableColumn<Employee, Integer> ReturnProdRightCol;
//    @FXML private TableColumn<Employee, Integer> ModStockRightCol;
//    @FXML private TableColumn<Employee, Integer> ModProdRightCol;
//    @FXML private TableColumn<Employee, Integer> ModSuppRightCol;
//    @FXML private TableColumn<Employee, Integer> ModCategRightCol;
//    @FXML private TableColumn<Employee, Integer> WholesaleRightCol;
//    @FXML private TableColumn<Employee, Integer> RetailSaleRightCol;
//    @FXML private TableColumn<Employee, Integer> EmployeeRightCol;
//    
//    @FXML private Button saveButton;
//    
//    private EmployeeModel employeeModel;
//    //<copied, original>
//    private final Map<Employee, Employee> employeeMap = new HashMap<>();
//    /**
//     * Initializes the controller class.
//     * @param url
//     * @param rb
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        
//        employeeModel = new EmployeeModel();
//        
//        // Settings cells value factories
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
//        
//        posCol.setCellValueFactory(new PropertyValueFactory<>("PosRight"));
//        ImportSupOrdRightCol.setCellValueFactory(new PropertyValueFactory<>("ImportSupOrdRight"));
//        UsePDARightCol.setCellValueFactory(new PropertyValueFactory<>("UsePDARight"));
//        UsePDAAddProdRightCol.setCellValueFactory(new PropertyValueFactory<>("UsePDAAddProdRight"));
//        UsePDAmatchOrderRightCol.setCellValueFactory(new PropertyValueFactory<>("UsePDAmatchOrderRight"));
//        SaleRightCol.setCellValueFactory(new PropertyValueFactory<>("SaleRight"));
//        ReturnProdRightCol.setCellValueFactory(new PropertyValueFactory<>("ReturnProdRight"));
//        ModStockRightCol.setCellValueFactory(new PropertyValueFactory<>("ModStockRight"));
//        ModProdRightCol.setCellValueFactory(new PropertyValueFactory<>("ModProdRight"));
//        ModSuppRightCol.setCellValueFactory(new PropertyValueFactory<>("ModSuppRight"));
//        ModCategRightCol.setCellValueFactory(new PropertyValueFactory<>("ModCategRight"));
//        WholesaleRightCol.setCellValueFactory(new PropertyValueFactory<>("WholesaleRight"));
//        RetailSaleRightCol.setCellValueFactory(new PropertyValueFactory<>("RetailSaleRight"));
//        EmployeeRightCol.setCellValueFactory(new PropertyValueFactory<>("EmployeeRight"));
//        
//        // Setting cells factories
//        posCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("PosRight"));
//        ImportSupOrdRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("ImportSupOrdRight"));
//        UsePDARightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("UsePDARight"));
//        UsePDAAddProdRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("UsePDAAddProdRight"));
//        UsePDAmatchOrderRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("UsePDAmatchOrderRight"));
//        SaleRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("SaleRight"));
//        ReturnProdRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("ReturnProdRight"));
//        ModStockRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("ModStockRight"));
//        ModProdRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("ModProdRight"));
//        ModSuppRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("ModSuppRight"));
//        ModCategRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("ModCategRight"));
//        WholesaleRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("WholesaleRight"));
//        RetailSaleRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("RetailSaleRight"));
//        EmployeeRightCol.setCellFactory((TableColumn<Employee, Integer> param) -> new PermissionTableCell("EmployeeRight"));
//        
//        employeesTable1.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//        employeesTable2.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//    } 
//    
//    
//    private void updatePermissions(Employee source, Employee target){
//        target.setEmployeeRight(source.getEmployeeRight());
//        target.setImportSupOrdRight(source.getImportSupOrdRight());
//        target.setModCategRight(source.getModCategRight());
//        target.setModProdRight(source.getModProdRight());
//        target.setModStockRight(source.getModStockRight());
//        target.setModSuppRight(source.getModSuppRight());
//        target.setPosRight(source.getPosRight());
//        target.setRetailSaleRight(source.getRetailSaleRight());
//        target.setReturnProdRight(source.getReturnProdRight());
//        target.setSaleRight(source.getSaleRight());
//        target.setUsePDAAddProdRight(source.getUsePDAAddProdRight());
//        target.setUsePDARight(source.getUsePDARight());
//        target.setUsePDAmatchOrderRight(source.getUsePDAmatchOrderRight());
//        target.setWholesaleRight(source.getWholesaleRight());
//    }
//
//    void setEmployees(ObservableList<Employee> employees) {
//        //The employee list is cloned so that unsaved changes on that list will not
//        //impact on the list of employees presents in the staff table
//        employees.forEach((employee)->{
//            Employee copy = new Employee(employee);
//            employeesTable1.getItems().add(copy);
//            employeesTable2.getItems().add(copy);
//            employeeMap.put(copy, employee);
//        });
//    }
//    
//    @FXML
//    public void handleSave(ActionEvent event) {
//        
//        // uses the model to update each modified employee
//        PermissionTableCell.EDITED_EMPLOYEES.forEach((employeeCopy)->{
//            Employee original = employeeMap.get(employeeCopy);
//            updatePermissions(employeeCopy, original);
//            employeeModel.updateEmployee(original);
//        });
//        
//        // then clears the modified employees list
//        PermissionTableCell.EDITED_EMPLOYEES.clear();
//        employeeMap.clear();
//        
//        // and finally close the window
//        ((Stage) saveButton.getScene().getWindow()).close();
//        
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Successful");
//        alert.setHeaderText("Employee Permissions Updated!");
//        alert.setContentText("Employee permissions updated successfully");
//        alert.showAndWait();
//    }
//    
//    @FXML
//    public void closeAction(ActionEvent event) {
//        ((Node) (event.getSource())).getScene().getWindow().hide();
//        PermissionTableCell.EDITED_EMPLOYEES.clear();
//        employeeMap.clear();
//    }
//}
//
///**
// * My custom TableCell to convert integers to boolean edited by checkboxes, used to 
// * assign permissions to uses
// * @author Rostand
// */
//class PermissionTableCell extends TableCell<Employee, Integer>{
//    
//    public final static List<Employee> EDITED_EMPLOYEES = new ArrayList<>();
//    
//    private final CheckBox editor= new CheckBox();
//    
//    /**
//     * Creates a new cell factory that displays a toogle switch to edit the cells
//     * 
//     */
//    public PermissionTableCell(final String fieldName){
//        setText(null);
//        
//        editor.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean permitted) -> {
//            try{
//                Class employeeClass = Employee.class;
//                Field field = employeeClass.getDeclaredField(fieldName);
//                field.setAccessible(true);
//                Employee selectedEmployee =  getTableView().getItems().get(getIndex());
//                field.set(selectedEmployee, permitted?1:0);
//                
//                if(!EDITED_EMPLOYEES.contains(selectedEmployee))
//                    EDITED_EMPLOYEES.add(selectedEmployee);
//            }
//            catch(IllegalAccessException | NoSuchFieldException ex){
//                System.out.println(ex);
//            }
//        });
//    }
//    
//    @Override
//    public void updateItem(Integer item, boolean empty) {
//        
//        super.updateItem(item, empty);
//        
//        if (empty || item==null) {
//            setGraphic(null);
//            setText(null);
//        } 
//        else {
//            setGraphic(editor);
//            editor.setSelected(item==1);
//            Employee selectedEmployee =  getTableView().getItems().get(getIndex());
//            AuthenticatedUser emp = SecurityManager.getAuthenticatedEmployee();
//            
//            // disallows a user to edit its own permissions, and also to edit admin permissions
//            editor.setDisable(emp.isSameEmployeeAs(selectedEmployee) || AuthenticatedUser.isAdmin(selectedEmployee));
//        }
//
//    }
//}
