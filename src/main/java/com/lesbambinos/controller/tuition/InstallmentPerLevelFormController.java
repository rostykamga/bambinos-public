package com.lesbambinos.controller.tuition;


import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.InstallmentPerLevelCollection;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.model.InstallmentPerLevelModel;
import com.lesbambinos.model.LevelModel;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InstallmentPerLevelFormController extends FormController {
    
    @FXML private TableView<InstallmentPerLevelCollection> installmentPerLevelTableview;
    @FXML private TableColumn<InstallmentPerLevelCollection, String> levelColumn;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn1;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn2;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn3;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn4;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn5;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn6;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> totalColumn;
    
    private TableColumn[] hidableColumns;
    private ObservableList<InstallmentPerLevelCollection> levelInstallmentList = FXCollections.observableArrayList();

    
    private LevelModel levelModel;
    private InstallmentPerLevelModel installementPerlevelModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        levelModel = new LevelModel();
        installementPerlevelModel =  new InstallmentPerLevelModel();
        
        hidableColumns = new TableColumn[]{installmentColumn1, installmentColumn2, installmentColumn3, installmentColumn4,
            installmentColumn5, installmentColumn6};
        
        installmentPerLevelTableview.setItems(levelInstallmentList);
        
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        
        installmentColumn1.setCellValueFactory(new PropertyValueFactory<>("amount1"));
        installmentColumn1.setCellFactory((TableColumn<InstallmentPerLevelCollection, Double> param) -> new EditingCell("Montant de la première tranche"));        
        installmentColumn1.setOnEditCommit((TableColumn.CellEditEvent<InstallmentPerLevelCollection, Double> event) -> {
            installmentPerLevelTableview.refresh();
            event.getRowValue().setAmount1(event.getNewValue());
        });
        installmentColumn2.setCellValueFactory(new PropertyValueFactory<>("amount2"));
        installmentColumn2.setCellFactory((TableColumn<InstallmentPerLevelCollection, Double> param) -> new EditingCell("Montant de la deuxième tranche"));        
        installmentColumn2.setOnEditCommit((TableColumn.CellEditEvent<InstallmentPerLevelCollection, Double> event) -> {
            installmentPerLevelTableview.refresh();
            event.getRowValue().setAmount2(event.getNewValue());
        });
        
        installmentColumn3.setCellValueFactory(new PropertyValueFactory<>("amount3"));
        installmentColumn3.setCellFactory((TableColumn<InstallmentPerLevelCollection, Double> param) -> new EditingCell("Montant de la troisième tranche"));        
        installmentColumn3.setOnEditCommit((TableColumn.CellEditEvent<InstallmentPerLevelCollection, Double> event) -> {
            installmentPerLevelTableview.refresh();
            event.getRowValue().setAmount3(event.getNewValue());
        });
        
        installmentColumn4.setCellValueFactory(new PropertyValueFactory<>("amount4"));
        installmentColumn4.setCellFactory((TableColumn<InstallmentPerLevelCollection, Double> param) -> new EditingCell("Montant de la quatrième tranche"));        
        installmentColumn4.setOnEditCommit((TableColumn.CellEditEvent<InstallmentPerLevelCollection, Double> event) -> {
            installmentPerLevelTableview.refresh();
            event.getRowValue().setAmount4(event.getNewValue());
        });
        
        installmentColumn5.setCellValueFactory(new PropertyValueFactory<>("amount5"));
        installmentColumn5.setCellFactory((TableColumn<InstallmentPerLevelCollection, Double> param) -> new EditingCell("Montant de la cinquième tranche"));        
        installmentColumn5.setOnEditCommit((TableColumn.CellEditEvent<InstallmentPerLevelCollection, Double> event) -> {
            installmentPerLevelTableview.refresh();
            event.getRowValue().setAmount5(event.getNewValue());
        });
        
        installmentColumn6.setCellValueFactory(new PropertyValueFactory<>("amount6"));
        installmentColumn6.setCellFactory((TableColumn<InstallmentPerLevelCollection, Double> param) -> new EditingCell("Montant de la sixième tranche"));        
        installmentColumn6.setOnEditCommit((TableColumn.CellEditEvent<InstallmentPerLevelCollection, Double> event) -> {
            installmentPerLevelTableview.refresh();
            event.getRowValue().setAmount6(event.getNewValue());
        });
        
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        formHeaderTitle.setText("Edition des montants");
    }
    
    /**
     * Pour créer un level, on passe en paramètre la section
     * @param obj Pair of Subsystem and Level object
     */
    @Override
    public void setData(AbstractEntity obj){
        super.setData(obj);
    }

    public ObservableList<InstallmentPerLevelCollection> getLevelInstallmentList() {
        return levelInstallmentList;
    }
    
    
    public void setDataList(ObservableList<InstallmentPerLevelCollection> data){
        data.forEach(iplc->{levelInstallmentList.add(iplc.copy());});
        //levelInstallmentList.setAll(data);
        //installmentPerLevelTableview.setItems(data);
        int size = data.get(0).size();
        
        for(int i= 0; i< size; i++){
                    hidableColumns[i].setVisible(true);
        }
        for(int i= size; i< hidableColumns.length; i++){
            hidableColumns[i].setVisible(false);
        }
    }
    
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";
        
        int i = 1;
        for(InstallmentPerLevelCollection iplc : installmentPerLevelTableview.getItems()){
            if(!iplc.isValid()){
                errorMessage+= "Ligne "+i+" toutes les valeurs doivent être renseignées!\n";
            }
            i++;
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
        
        try{
            installmentPerLevelTableview.getItems().forEach((iplc) -> {
                iplc.getInstallmentsPerLevels().forEach((ipl) -> {
                    installementPerlevelModel.saveOrUpdate(ipl);
                });
            });
            
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
        alert.setHeaderText("Informations mises à jour");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreur");
        alert.setHeaderText("Echec de la mise à jour");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

}


class EditingCell extends TableCell<InstallmentPerLevelCollection, Double> {

   private TextField textField;

   public EditingCell(String tooltipText){
       this.setTooltip(new Tooltip(tooltipText));
   }

   @Override
   public void startEdit() {
       if (!isEmpty()) {
           super.startEdit();
           createTextField();
           setText(null);
           setGraphic(textField);
           textField.selectAll();
       }
   }

   @Override
   public void cancelEdit() {
       super.cancelEdit();

       setText(String.valueOf(getItem()));
       setGraphic(null);
   }

   @Override
   public void updateItem(Double item, boolean empty) {
       super.updateItem(item, empty);

       if (empty) {
           setText(null);
           setGraphic(null);
       } else {
           if (isEditing()) {
               if (textField != null) {
                   textField.setText(getString());
               }
               setText(null);
               setGraphic(textField);
           } else {
               setText(getString());
               setGraphic(null);
           }
       }
   }

   private void createTextField() {
       textField = new TextField(getString());
       textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
       textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent t) {
               if (t.getCode() == KeyCode.ENTER || t.getCode() == KeyCode.TAB) {
                try{
                    commitEdit(Double.parseDouble(textField.getText()));
                    TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                    if (nextColumn != null) {
                        getTableView().edit(getTableRow().getIndex(), nextColumn);
                    }
                }
                catch(NumberFormatException ex){
                    cancelEdit();
                }
            } 
            else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            } 
           }
       });
       
       textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
           @Override
           public void changed(ObservableValue<? extends Boolean> arg0, 
               Boolean arg1, Boolean arg2) {
                   if (!arg2) {
                       try{
                           commitEdit(Double.parseDouble(textField.getText()));
                       }
                       catch(NumberFormatException ex){
                           cancelEdit();
                       }
                   }
           }
       });
   }

   private String getString() {
       return getItem() == null ? "" : String.valueOf(getItem());
   }
   
   private TableColumn<InstallmentPerLevelCollection, ?> getNextColumn(boolean forward) {
        List<TableColumn<InstallmentPerLevelCollection, ?>> columns = new ArrayList<>();
        for (TableColumn<InstallmentPerLevelCollection, ?> column : getTableView().getColumns()) {
                columns.addAll(getLeaves(column));
        }
        // There is no other column that supports editing.
        if (columns.size() < 2) {
                return null;
        }
        int currentIndex = columns.indexOf(getTableColumn());
        int nextIndex = currentIndex;
        if (forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                        nextIndex = 0;
                }
        } else {
                nextIndex--;
                if (nextIndex < 0) {
                        nextIndex = columns.size() - 1;
                }
        }
        return columns.get(nextIndex);
    }
   
    private List<TableColumn<InstallmentPerLevelCollection, ?>> getLeaves(
        TableColumn<InstallmentPerLevelCollection, ?> root) {
        List<TableColumn<InstallmentPerLevelCollection, ?>> columns = new ArrayList<>();
        if (root.getColumns().isEmpty()) {
            // We only want the leaves that are editable.
            if (root.isEditable()) {
                    columns.add(root);
            }
            return columns;
        } else {
            for (TableColumn<InstallmentPerLevelCollection, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
            }
            return columns;
        }
    }
}
