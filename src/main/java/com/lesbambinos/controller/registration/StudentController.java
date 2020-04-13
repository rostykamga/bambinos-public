package com.lesbambinos.controller.registration;

import com.lesbambinos.controller.AbstractController;
import com.lesbambinos.controller.FormController;

import com.lesbambinos.entity.Student;
import com.lesbambinos.model.StudentModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;

public class StudentController extends AbstractController {

    @FXML
    private TableView<Student> studentsTableview;
    @FXML private TableColumn<Student, byte[]> pictureColumn;
    @FXML private TableColumn<Student, String> registrationNumberColumn, firstnameColumn,
            lastnameColumn, classColumn, dobColumn, registrationDateColumn;
    
    @FXML private TextField searchField;
    @FXML private Button editButton, deleteButton;
    
    private ObservableList<Student> STUDENTS_LIST = FXCollections.observableArrayList();
    
    private StudentModel registrationModel;
   
    @Override
    protected void initUI() {
        
        registrationModel = new StudentModel();
        
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
       
        registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
       
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("names"));
        
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("surnames"));
        
        classColumn.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> p)
                -> new SimpleStringProperty(p.getValue().getClassroom().getShortname()));
        
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
       
        studentsTableview.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        editAction(new ActionEvent(event.getSource(), event.getTarget()));
                    } catch (Exception ex) {
                        Logger.getLogger(StudentController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });
        
        
        studentsTableview.setItems(STUDENTS_LIST);

        filterData();

        editButton.disableProperty()
                .bind(Bindings.isEmpty(studentsTableview.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(studentsTableview.getSelectionModel().getSelectedItems()));
    }
    
    private void filterData() {

        FilteredList<Student> searchedData = new FilteredList<>(STUDENTS_LIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(student -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (student.getRegistrationDate().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (student.getNames().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    else if (student.getSurnames().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    else if (student.getRegistrationNumber().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Student> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(studentsTableview.comparatorProperty());
            studentsTableview.setItems(sortedData);
        });
    }
    
    @Override
    protected void dataLoaded(Object data) {
        if (!STUDENTS_LIST.isEmpty()) {
            STUDENTS_LIST.clear();
        }
        STUDENTS_LIST.addAll((ObservableList<Student>)data);
    }

    @Override
    protected Object loadData() {
        return registrationModel.getAll();
    }


    @FXML
    public void addAction(ActionEvent event) throws Exception {
        FormController.showForm("registration/Form.fxml", null);
    }

    @FXML
    public void editAction(ActionEvent event) throws Exception {
        Student selectedStudent = studentsTableview.getSelectionModel().getSelectedItem();
        FormController.showForm("registration/Form.fxml", selectedStudent);
    }

    @FXML
    public void deleteAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Suppression de l'inscription");
        alert.setContentText("Etes vous sûse?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Student selectedStudent = studentsTableview.getSelectionModel().getSelectedItem();

            registrationModel.delete(selectedStudent);
            STUDENTS_LIST.remove(selectedStudent);
        }

        studentsTableview.getSelectionModel().clearSelection();
    }
   

    @Override
    protected String getPageName() {
        return "Inscriptions";
    }

}