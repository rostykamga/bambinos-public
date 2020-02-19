package com.lesbambinos.controller.tuition;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Installment;
import com.lesbambinos.entity.Tuition;
import com.lesbambinos.model.InstallmentModel;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.model.TuitionModel;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TuitionFormController extends FormController {
    
    private static final int MAX_INSTALLEMENT = 6;
    
    @FXML private TextField nameField;
    @FXML private ComboBox<Integer> nbInstallmentCombo;
    @FXML private TextArea descriptionArea;
    @FXML private TableView<Installment> installmentTableview;
    @FXML private TableColumn<Installment, Integer> orderColumn;
    @FXML private TableColumn<Installment, Date> alertDateColumn;
    @FXML private TableColumn<Installment, Date> deadlineColumn;
    
    private FilteredList<Installment> installmentList;
    private final ObservableList<Installment> allInstallments = FXCollections.observableArrayList();

    
    private TuitionModel tuitionModel;
    private InstallmentModel installmentModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        for(int i = 1; i <= MAX_INSTALLEMENT; i++){
            nbInstallmentCombo.getItems().add(i);
            allInstallments.add(new Installment(i, null, null, null));
        }
        
        // empty list
        installmentList = new FilteredList<>(allInstallments,  t->false);
        installmentTableview.setItems(installmentList);
        
        tuitionModel =  new TuitionModel();
        installmentModel = new InstallmentModel();
        
        orderColumn.setCellValueFactory(new PropertyValueFactory<>("installmentNumber"));
        alertDateColumn.setCellValueFactory(new PropertyValueFactory<>("alertDate"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        
        alertDateColumn.setCellFactory((TableColumn<Installment, Date> param) -> new LocalDateTableCell(true));
        deadlineColumn.setCellFactory((TableColumn<Installment, Date> param) -> new LocalDateTableCell(false));
        
        nbInstallmentCombo.valueProperty().addListener((ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) -> {
            installmentList.setPredicate(t->{return t.getInstallmentNumber()<= newValue;});
        });
    }
    
    /**
     * Pour créer un level, on passe en paramètre la section
     * @param obj Pair of Subsystem and Level object
     */
    @Override
    public void setData(AbstractEntity obj){
        
        super.setData(obj);
        
        formHeaderTitle.setText(entity == null? "Nouveau Frais" : "Modification Frais");
        
        if(entity != null){
            Tuition tuition = (Tuition)entity;
            nameField.setText(tuition.getTitle());
            descriptionArea.setText(tuition.getDescription());
            
            allInstallments.clear();
            nbInstallmentCombo.getItems().clear();
            
            ObservableList<Installment> installements = installmentModel.getInstallmentsByTuitionId(tuition.getId());
            for(int i = 1; i <= installements.size(); i++){
                nbInstallmentCombo.getItems().add(i);
                allInstallments.add(installements.get(i-1));
            }
            for(int i = installements.size()+1; i <= MAX_INSTALLEMENT; i++){
                nbInstallmentCombo.getItems().add(i);
                allInstallments.add(new Installment(i, null, null, null));
            }
             
            nbInstallmentCombo.setValue(tuition.getNbInstallment());
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Le Champ libellé ne doit pas être vide!\n";
        }
        
        if(nbInstallmentCombo.getValue() == null){
            errorMessage += "Choisir le nombre de tranches!\n";
        }
        else{
            for(int i=0 ; i< nbInstallmentCombo.getValue(); i++){
                Installment inst = allInstallments.get(i);
                if(inst.getDeadline()==null || inst.getAlertDate() ==null){
                     errorMessage += "Entrer les dates d'alerte et limite de la tranche "+(i+1)+"!\n";
                }
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Champs invalides");
            alert.setHeaderText("Veuillez corriger les erreurs ci-dessous");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }

    @Override
    protected void doSave() {
        Tuition tuition = new Tuition(
                    nameField.getText(),
                    nbInstallmentCombo.getValue(),
                    descriptionArea.getText()
            );
        
        try{
            if(entity != null){
                tuition.setId(entity.getId());
                tuitionModel.update(tuition);
            }
            else{
                tuitionModel.save(tuition);
            }
            
            for(int i=0; i < tuition.getNbInstallment(); i++){
                Installment inst = allInstallments.get(i);
                if(inst.getTuition() == null){
                    inst.setTuition(tuition);
                    installmentModel.save(inst);
                }
                else{
                   installmentModel.update(inst); 
                }
            }
            
            Tuition.TUITIONLIST.clear();
            Tuition.TUITIONLIST.addAll(tuitionModel.getAll());
            
            formStatus = STATUS_SUCCESS;
        }
        catch(Exception ex){
            formStatus = STATUS_FAIL;
            err_msg = ex.getMessage();
            if(err_msg==null || err_msg.isEmpty()){
                if(ex.getCause() !=null)
                    err_msg = ex.getCause().getMessage();
            }
        }
    }

    @Override
    protected Alert getSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération effectuée avec succès");
        alert.setHeaderText(entity == null? "Enregistré!" : "Informations mise à jour");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText(entity == null? "Erreur d'enregistrement!" : "Echec de la mise à jour");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

}

class LocalDateTableCell extends TableCell<Installment, Date>{
    private final DatePicker datePicker = new DatePicker();
    private final boolean alertDate;
    
    public LocalDateTableCell(boolean alertDate){
        this.alertDate = alertDate;
        datePicker.valueProperty().addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
            Date date = Date.from(newValue.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if(alertDate)
                ((Installment) getTableRow().getItem()).setAlertDate(date);
            else
                ((Installment) getTableRow().getItem()).setDeadline(date);
        });
    }
    @Override
    protected void updateItem(Date item, boolean empty) {
       
        super.updateItem(item, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        }
        else{
            LocalDate ldate =  item==null? null : Instant.ofEpochMilli(item.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            datePicker.setValue(ldate);
            setGraphic(datePicker);
        }
        
    }
}
