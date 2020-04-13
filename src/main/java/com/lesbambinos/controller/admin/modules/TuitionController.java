package com.lesbambinos.controller.admin.modules;

import com.lesbambinos.config.AppConfig;
import com.lesbambinos.controller.FormController;
import com.lesbambinos.controller.admin.AdminController;
import com.lesbambinos.controller.admin.AdminModuleController;
import com.lesbambinos.controller.tuition.InstallmentPerLevelFormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Cycle;
import com.lesbambinos.entity.DiscountRequest;
import com.lesbambinos.entity.InstallmentPerLevelCollection;
import com.lesbambinos.entity.Level;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.entity.Tuition;
import com.lesbambinos.model.AbstractEntityModel;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.DiscountRequestModel;
import com.lesbambinos.model.InstallmentPerLevelModel;
import com.lesbambinos.model.LevelModel;
import com.lesbambinos.model.SubsystemModel;
import com.lesbambinos.model.TuitionModel;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableRow;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TuitionController extends AdminModuleController {

    // =========== Top Pane ================
    @FXML private Button  editTuitionButton, deleteTuitionButton, editInstallmentAmountButton;
    @FXML private ListView<Tuition> tuitionsListView;
    @FXML private Label tuitionTitle;
    @FXML private VBox tuitionDetailPane;
    @FXML private TreeView structureTreeView;
    
    @FXML private TableView<InstallmentPerLevelCollection> installmentPerLevelTableview;
    @FXML private TableColumn<InstallmentPerLevelCollection, String> levelColumn;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn1;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn2;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn3;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn4;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn5;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> installmentColumn6;
    @FXML private TableColumn<InstallmentPerLevelCollection, Double> totalColumn;
    
    private final ObjectProperty<Cycle> selectedCycle = new SimpleObjectProperty<>();
    private TableColumn[] hidableColumns;
    private final ObservableList<InstallmentPerLevelCollection> levelInstallmentList = FXCollections.observableArrayList();
    private final ObservableList<Subsystem> sectionsList = FXCollections.observableArrayList();
    
    private final TuitionModel tuitionModel = new TuitionModel();
    private final InstallmentPerLevelModel installmentPerLevelModel = new InstallmentPerLevelModel();
    
    private final SubsystemModel subsystemModel = new SubsystemModel();
    private final CycleModel cycleModel = new CycleModel();
    private final LevelModel levelModel = new LevelModel();
    
    private final TreeItem<String> root = new TreeItem<>("Sections");
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        super.initialize(url, rb);
                
        structureTreeView.setRoot(root);
        root.setExpanded(true);
        structureTreeView.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            handleTreeItemSelection(newValue);
        });
                
        hidableColumns = new TableColumn[]{installmentColumn1, installmentColumn2, installmentColumn3, installmentColumn4,
            installmentColumn5, installmentColumn6};
        
        installmentPerLevelTableview.setItems(levelInstallmentList);
        
        installmentPerLevelTableview.setRowFactory( tv -> {
            TableRow<InstallmentPerLevelCollection> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        editInstallmentAmountAction(new ActionEvent(event.getSource(), event.getTarget()));
                    } catch (Exception ex) {
                        Logger.getLogger(AdminController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                }
            });
            return row ;
        });
        
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        
        installmentColumn1.setCellValueFactory(new PropertyValueFactory<>("amount1"));
        installmentColumn2.setCellValueFactory(new PropertyValueFactory<>("amount2"));
        installmentColumn3.setCellValueFactory(new PropertyValueFactory<>("amount3"));
        installmentColumn4.setCellValueFactory(new PropertyValueFactory<>("amount4"));
        installmentColumn5.setCellValueFactory(new PropertyValueFactory<>("amount5"));
        installmentColumn6.setCellValueFactory(new PropertyValueFactory<>("amount6"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        
        editTuitionButton.disableProperty().bind(
                Bindings.isEmpty(tuitionsListView.getSelectionModel().getSelectedItems()));
        deleteTuitionButton.disableProperty().bind(
                Bindings.isEmpty(tuitionsListView.getSelectionModel().getSelectedItems()));
       
        tuitionsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tuition> observable, Tuition oldValue, Tuition newValue) -> {
            handleTuitionSelected(newValue);
        });
        
        tuitionDetailPane.setManaged(false);
        tuitionDetailPane.setVisible(false);
        installmentPerLevelTableview.visibleProperty().bind(Bindings.isNotNull(selectedCycle));
        editInstallmentAmountButton.disableProperty().bind(Bindings.isNull(selectedCycle));
    }
    
    
    private void handleTuitionSelected(Tuition newValue) {
        if(newValue==null){
            tuitionDetailPane.setManaged(false);
            tuitionDetailPane.setVisible(false);
        }
        else{
            tuitionDetailPane.setManaged(true);
            tuitionDetailPane.setVisible(true);
            tuitionTitle.setText(newValue.getTitle());
            structureTreeView.getSelectionModel().clearSelection();
        }
    }
    
     @FXML
    protected void addTuitionAction(ActionEvent event){
        FormController.showForm("tuition/Form.fxml", null);
    }
    @FXML
    protected void editTuitionAction(ActionEvent event){
        edit("tuition/Form.fxml", tuitionsListView);
    }
    @FXML
    protected void deleteTuitionAction(ActionEvent event){
        Tuition selected = tuitionsListView.getSelectionModel().getSelectedItem();
        tuitionsListView.getSelectionModel().clearSelection();
        delete("Suppression du sous système "+selected.getTitle()+".\nCette suppression entrainera celle"
                        + "des cycles, niveaux et classes de ce sous système",
                selected,
                Tuition.TUITIONLIST,
                tuitionModel);
    }
    
    private double yOffset = 0.0;
    private double xOffset = 0.0;
    
    @FXML
    protected void editInstallmentAmountAction(ActionEvent evt){
        try {
            FXMLLoader loader = new FXMLLoader(AppConfig.class.getResource("/fxml/tuition/InstallmentPerLevelForm.fxml"));
            Parent parent = loader.load();
            InstallmentPerLevelFormController controller = loader.getController();
            controller.setDataList(levelInstallmentList);
            parent.setOnMousePressed((MouseEvent event) -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            Stage stage = new Stage();
            
            parent.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            Scene scene = new Scene(parent);
            stage.setTitle("LES BAMBINOS :: Version 1.0");
            stage.getIcons().add(new Image("/images/logo.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            if(!controller.isCanceled()){
                levelInstallmentList.setAll(controller.getLevelInstallmentList());
                installmentPerLevelTableview.refresh();
            }
        } catch (IOException ex) {
            Logger.getLogger(FormController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    private void handleTreeItemSelection(Object item){
        
        selectedCycle.set(null);
        // Hide all first
        for (TableColumn hidableColumn : hidableColumns) {
            hidableColumn.setVisible(false);
        }
        
        if(item!= null && item instanceof TreeItem){
            Object value = ((TreeItem)item).getValue();
            if(value instanceof Cycle){
                Cycle cycle = (Cycle)value;
                ObservableList<Level> levels = levelModel.getLevelsByCycleId(cycle.getId());
                if(levels.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Niveaux introuvables");
                    alert.setHeaderText("Aucun niveau pour le cycle");
                    alert.setContentText("Aucun niveau d'études n'a été définit pour le cycle");
                    alert.showAndWait();
                    return;
                }
                selectedCycle.set(cycle);
                Tuition tuition = tuitionsListView.getSelectionModel().getSelectedItem();
                for(int i= 0; i< tuition.getNbInstallment(); i++){
                    hidableColumns[i].setVisible(true);
                }
                for(int i= tuition.getNbInstallment(); i< hidableColumns.length; i++){
                    hidableColumns[i].setVisible(false);
                }
                ObservableList<InstallmentPerLevelCollection> cycleInst = installmentPerLevelModel.getInstallmentPerCycle(tuition, cycle);
                levelInstallmentList.setAll(cycleInst);
            }
        }
    }
    
    
    private void loadSchoolStructure(){
        
        clearNode(root);
        for(Subsystem section : sectionsList){
            TreeItem sectionNode = new TreeItem(section);
            for(Cycle cycle : cycleModel.getCyclesBySectionId(section.getId())){
                TreeItem cycleNode = new TreeItem(cycle);
                sectionNode.getChildren().add(cycleNode);
                sectionNode.setExpanded(true);
            }
            root.getChildren().add(sectionNode);
        }
    }
    
    private void clearNode(TreeItem item){
        if(item.getChildren().isEmpty())
            return;
        else{
            for(Object obj : item.getChildren()){
                TreeItem child = (TreeItem)obj;
                clearNode(child);
            }
            item.getChildren().clear();
        }
    }
   

    @Override
    protected Object _loadData() {
        
        Subsystem.SUBSYSTEMS.clear();
        Subsystem.SUBSYSTEMS.addAll(subsystemModel.getAll());
        
        Tuition.TUITIONLIST.clear();
        Tuition.TUITIONLIST.addAll(tuitionModel.getAll());        
        return null;
    }
    
    @Override
    protected void _onDataLoaded(Object data) {
        tuitionsListView.getItems().setAll(Tuition.TUITIONLIST);
        sectionsList.setAll(Subsystem.SUBSYSTEMS);
        loadSchoolStructure();
    }
    

    @Override
    protected String getModuleName() {
        return "Gestion des Frais";
    }

}