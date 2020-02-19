package com.lesbambinos.controller.registration;

import com.lesbambinos.controller.AbstractController;
import com.lesbambinos.controller.FormController;

import com.lesbambinos.entity.Registration;
import com.lesbambinos.model.RegistrationModel;

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
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableRow;

public class RegistrationController extends AbstractController {

    @FXML
    private TableView<Registration> registrationTable;
    @FXML private TableColumn<Registration, byte[]> pictureColumn;
    @FXML private TableColumn<Registration, String> registrationNumberColumn, firstnameColumn,
            lastnameColumn, classColumn, dobColumn, registrationDateColumn;
    
    @FXML private TextField searchField;
    @FXML private Button editButton, deleteButton;
    
    private ObservableList<Registration> REGISTRATIONLIST = FXCollections.observableArrayList();
    
    private RegistrationModel registrationModel;
   
    @Override
    protected void initUI() {
        
        registrationModel = new RegistrationModel();
        
        pictureColumn.setCellValueFactory((TableColumn.CellDataFeatures<Registration, byte[]> p)
                -> new SimpleObjectProperty<>(p.getValue().getStudent().getPicture()));
       
        registrationNumberColumn.setCellValueFactory((TableColumn.CellDataFeatures<Registration, String> p)
                -> new SimpleStringProperty(p.getValue().getStudent().getRegistrationNumber()));
       
        firstnameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Registration, String> p)
                -> new SimpleStringProperty(p.getValue().getStudent().getNames()));
        
        lastnameColumn.setCellValueFactory((TableColumn.CellDataFeatures<Registration, String> p)
                -> new SimpleStringProperty(p.getValue().getStudent().getSurnames()));
        
        classColumn.setCellValueFactory((TableColumn.CellDataFeatures<Registration, String> p)
                -> new SimpleStringProperty(p.getValue().getClasse().getShortname()));
        
        dobColumn.setCellValueFactory((TableColumn.CellDataFeatures<Registration, String> p)
                -> new SimpleStringProperty(p.getValue().getStudent().getDateOfBirth().toString()));
        
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
       
        registrationTable.setRowFactory(tv -> {
            TableRow<Registration> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        editAction(new ActionEvent(event.getSource(), event.getTarget()));
                    } catch (Exception ex) {
                        Logger.getLogger(RegistrationController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });
        
        
        registrationTable.setItems(REGISTRATIONLIST);

        filterData();

        editButton.disableProperty()
                .bind(Bindings.isEmpty(registrationTable.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(registrationTable.getSelectionModel().getSelectedItems()));
    }
    
    private void filterData() {

        FilteredList<Registration> searchedData = new FilteredList<>(REGISTRATIONLIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(registration -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (registration.getRegistrationDate().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registration.getStudent().getNames().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    else if (registration.getStudent().getSurnames().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Registration> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(registrationTable.comparatorProperty());
            registrationTable.setItems(sortedData);
        });
    }
    
    @Override
    protected void dataLoaded(Object data) {
        if (!REGISTRATIONLIST.isEmpty()) {
            REGISTRATIONLIST.clear();
        }
        REGISTRATIONLIST.addAll((ObservableList<Registration>)data);
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
        Registration selectedRegistration = registrationTable.getSelectionModel().getSelectedItem();
        FormController.showForm("registration/Form.fxml", selectedRegistration);
    }

    @FXML
    public void deleteAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Suppression de l'inscription");
        alert.setContentText("Etes vous sûse?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Registration selectedRegistration = registrationTable.getSelectionModel().getSelectedItem();

            registrationModel.delete(selectedRegistration);
            REGISTRATIONLIST.remove(selectedRegistration);
        }

        registrationTable.getSelectionModel().clearSelection();
    }
   

    @Override
    protected String getPageName() {
        return "Inscriptions";
    }

}