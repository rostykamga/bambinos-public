//package com.lesbambinos.controller.employee;
//
//import com.lesbambinos.controller.AbstractController;
//import static com.lesbambinos.interfaces.EmployeeInterface.EMPLOYEELIST;
//
//import java.net.URL;
//import java.util.Optional;
//import java.util.ResourceBundle;
//
//import com.lesbambinos.entity.Employee;
//import com.lesbambinos.interfaces.EmployeeInterface;
//import com.lesbambinos.model.EmployeeModel;
//
//import javafx.beans.binding.Bindings;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Rectangle2D;
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
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//public class EmployeeController  extends AbstractController implements EmployeeInterface {
//
//    @FXML
//    private TableView<Employee> employeeTable;
//    @FXML
//    private TableColumn<Employee, Long> idColumn;
//    @FXML
//    private TableColumn<Employee, String> firstnameColumn, lastnameColumn, usernameColumn,
//            passwordColumn, phoneColumn, addressColumn;
//    @FXML
//    private TextField searchField;
//    @FXML
//    private Button editButton,permissionsButton, deleteButton;
//    private EmployeeModel model;
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        super.initialize(location, resources);
//        model = new EmployeeModel();
//        loadData();
//
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
//        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
//        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
//
//        employeeTable.setItems(EMPLOYEELIST);
//
//        filterData();
//
//        editButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(employeeTable.getSelectionModel().getSelectedItems()));
//        permissionsButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(employeeTable.getItems()));
//        deleteButton
//                .disableProperty()
//                .bind(Bindings.isEmpty(employeeTable.getSelectionModel().getSelectedItems()));
//    }
//
//    private void filterData() {
//        FilteredList<Employee> searchedData = new FilteredList<>(EMPLOYEELIST, e -> true);
//        searchField.setOnKeyReleased(e -> {
//            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
//                searchedData.setPredicate(employee -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (employee.getPhone().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    } else if (employee.getUserName().toLowerCase().contains(lowerCaseFilter)) {
//                        return true;
//                    }
//                    return false;
//                });
//            });
//
//            SortedList<Employee> sortedData = new SortedList<>(searchedData);
//            sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
//            employeeTable.setItems(sortedData);
//        });
//    }
//
//    private void loadData() {
//
//        if (!EMPLOYEELIST.isEmpty()) {
//            EMPLOYEELIST.clear();
//        }
//        EMPLOYEELIST.addAll(model.getEmployees());
//    }
//
//    public void addAction(ActionEvent event) throws Exception {
//
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/employee/Add.fxml"));
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
//        stage.setTitle("New Employee");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @FXML
//    public void editAction(ActionEvent event) throws Exception {
//
//        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
//        int selectedEmployeeId = employeeTable.getSelectionModel().getSelectedIndex();
//        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/employee/Edit.fxml")));
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
//        controller.setEmployee(selectedEmployee, selectedEmployeeId);
//        employeeTable.getSelectionModel().clearSelection();
//
//    }
//  
//    @FXML
//    public void editPermissionsAction(ActionEvent event)throws Exception{
//        ObservableList<Employee> employees = employeeTable.getItems();
//        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/employee/Permissions.fxml")));
//        
//        Parent root = loader.load();
//        
//        PermissionsController controller = loader.getController();
//        controller.setEmployees(employees);
//        
//        Scene scene = new Scene(root);
//        
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
//        stage.setTitle("Employees Permissions");
//        stage.getIcons().add(new Image("/images/logo.png"));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setScene(scene);
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setWidth(primaryScreenBounds.getWidth()-20);
//        stage.setHeight(primaryScreenBounds.getHeight()-20);
//        stage.show();
//    }
//    
//    @FXML
//    public void deleteAction(ActionEvent event) {
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Remove");
//        alert.setHeaderText("Remove Employee");
//        alert.setContentText("Are you sure?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.get() == ButtonType.OK) {
//            Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
//
//            model.deleteEmployee(selectedEmployee);
//            EMPLOYEELIST.remove(selectedEmployee);
//        }
//
//        employeeTable.getSelectionModel().clearSelection();
//    }
//}
