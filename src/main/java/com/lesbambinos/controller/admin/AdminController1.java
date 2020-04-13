package com.lesbambinos.controller.admin;

import com.lesbambinos.config.AppConfig;
import com.lesbambinos.controller.AbstractController;
import com.lesbambinos.controller.FormController;
import com.lesbambinos.controller.tuition.InstallmentPerLevelFormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Cycle;
import com.lesbambinos.entity.Level;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.entity.Tuition;
import com.lesbambinos.entity.InstallmentPerLevelCollection;
import com.lesbambinos.model.AbstractEntityModel;
import com.lesbambinos.model.ClasseModel;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.LevelModel;
import com.lesbambinos.model.SubsystemModel;
import com.lesbambinos.model.TuitionModel;
import com.lesbambinos.model.InstallmentPerLevelModel;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AdminController1 extends AbstractController {

    @FXML private Button editSectionButton, deleteSectionButton, editCycleButton, deleteCycleButton, editLevelButton,
            deleteLevelButton, editClassButton, deleteClassButton;
    @FXML private Button  editTuitionButton, deleteTuitionButton, editInstallmentAmountButton;
    
    @FXML private TreeView structureTreeView;
    @FXML private ListView<Subsystem> sectionsList;
    @FXML private ListView<Cycle> cyclesList;
    @FXML private ListView<Level> levelsList;
    @FXML private ListView<Classe> classesList;
    
    @FXML private ListView<Tuition> tuitionsListView;
    
    @FXML private TabPane tabPane;
    @FXML private Tab tuitionPane;
    @FXML private Label tuitionTitle;
    @FXML private VBox tuitionDetailPane;
    
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
    private final ObservableList<InstallmentPerLevelCollection> levelInstallmentList = FXCollections.observableArrayList();
    
    private final SubsystemModel subsystemModel = new SubsystemModel();
    private final CycleModel cycleModel = new CycleModel();
    private final LevelModel levelModel = new LevelModel();
    private final ClasseModel classeModel = new ClasseModel();
    private final TuitionModel tuitionModel = new TuitionModel();
    private final InstallmentPerLevelModel installmentPerLevelModel = new InstallmentPerLevelModel();
    
    private final ObjectProperty<Cycle> selectedCycle = new SimpleObjectProperty<>();

    private final TreeItem<String> root = new TreeItem<>("Sections");
    
    private static final String[] MODULES = {"discountrequest"};
    private final Map<String, AdminModuleController> moduleControllersMap = new HashMap<>();
    
    @Override
    protected void initUI(){
        
        structureTreeView.setRoot(root);
        root.setExpanded(true);
        structureTreeView.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            handleTreeItemSelection(newValue);
        });
                
        hidableColumns = new TableColumn[]{installmentColumn1, installmentColumn2, installmentColumn3, installmentColumn4,
            installmentColumn5, installmentColumn6};
        
        installmentPerLevelTableview.setItems(levelInstallmentList);
        
        installmentPerLevelTableview.setRowFactory(tv -> {
            TableRow<InstallmentPerLevelCollection> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        editInstallmentAmountAction(new ActionEvent(event.getSource(), event.getTarget()));
                    } catch (Exception ex) {
                        Logger.getLogger(AdminController1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        
        editSectionButton.disableProperty().bind(
                Bindings.isEmpty(sectionsList.getSelectionModel().getSelectedItems()));
        deleteSectionButton.disableProperty().bind(
                Bindings.isEmpty(sectionsList.getSelectionModel().getSelectedItems()));
       
        editCycleButton.disableProperty().bind(
                Bindings.isEmpty(cyclesList.getSelectionModel().getSelectedItems()));
        deleteCycleButton.disableProperty().bind(
                Bindings.isEmpty(cyclesList.getSelectionModel().getSelectedItems()));
        
        editLevelButton.disableProperty().bind(
                Bindings.isEmpty(levelsList.getSelectionModel().getSelectedItems()));
        deleteLevelButton.disableProperty().bind(
                Bindings.isEmpty(levelsList.getSelectionModel().getSelectedItems()));
        
        editClassButton.disableProperty().bind(
                Bindings.isEmpty(classesList.getSelectionModel().getSelectedItems()));
        deleteClassButton.disableProperty().bind(
                Bindings.isEmpty(classesList.getSelectionModel().getSelectedItems()));
        
         editTuitionButton.disableProperty().bind(
                Bindings.isEmpty(tuitionsListView.getSelectionModel().getSelectedItems()));
        deleteTuitionButton.disableProperty().bind(
                Bindings.isEmpty(tuitionsListView.getSelectionModel().getSelectedItems()));
       
        tuitionsListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tuition> observable, Tuition oldValue, Tuition newValue) -> {
            handleTuitionSelected(newValue);
        });
        
        loadAdminModules();
        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) -> {
            if(newValue == tuitionPane)
                loadSchoolStructure();
            else{
                AdminModuleController controller = moduleControllersMap.get(newValue.getText());
                if(controller != null)
                    controller.loadData();
            }
        });
        
        tuitionDetailPane.setManaged(false);
        tuitionDetailPane.setVisible(false);
        installmentPerLevelTableview.visibleProperty().bind(Bindings.isNotNull(selectedCycle));
        editInstallmentAmountButton.disableProperty().bind(Bindings.isNull(selectedCycle));
    }
    
    private void loadAdminModules(){
        
        for(String module : MODULES){
            try {
                String moduleMainPage = "/fxml/admin/modules/"+module+"/Main.fxml";

                FXMLLoader loader = new FXMLLoader(getClass().getResource(moduleMainPage));
           
                Parent root = loader.load();
                AdminModuleController controller = loader.getController();
                moduleControllersMap.put(controller.getModuleName(), controller);
                Tab tab = new Tab(controller.getModuleName(), root);
                tabPane.getTabs().add(tab);
            } catch (IOException ex) {
                Logger.getLogger(AdminController1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
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
        for(Subsystem section : sectionsList.getItems()){
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
    protected String getPageName() {
        return "Admin";
    }

    @Override
    protected void dataLoaded(Object data) {
        sectionsList.setItems(Subsystem.SUBSYSTEMS);
        cyclesList.setItems(Cycle.CYCLES);
        levelsList.setItems( Level.LEVELS);
        classesList.setItems( Classe.CLASSES);
        tuitionsListView.setItems(Tuition.TUITIONLIST);
    }
    
    @Override
    protected Object loadData(){
        
        Subsystem.SUBSYSTEMS.clear();
        Subsystem.SUBSYSTEMS.addAll(subsystemModel.getAll());
        
        Cycle.CYCLES.clear();
        Cycle.CYCLES.addAll(cycleModel.getAll());
        
        Level.LEVELS.clear();
        Level.LEVELS.addAll(levelModel.getAll());
        
        
        Classe.CLASSES.clear();
        Classe.CLASSES.addAll(classeModel.getAll());
        
        
        Tuition.TUITIONLIST.clear();
        Tuition.TUITIONLIST.addAll(tuitionModel.getAll());        
        return null;
    }
    
    @FXML
    protected void addSectionAction(ActionEvent event){
        FormController.showForm("subsystem/Form.fxml", null);
    }
    @FXML
    protected void editSectionAction(ActionEvent event){
        edit("subsystem/Form.fxml", sectionsList);
    }
    @FXML
    protected void deleteSectionAction(ActionEvent event){
        Subsystem selected = sectionsList.getSelectionModel().getSelectedItem();
        sectionsList.getSelectionModel().clearSelection();
        delete(
                "Suppression du sous système "+selected.getSubsystemname()+".\nCette suppression entrainera celle"
                        + "des cycles, niveaux et classes de ce sous système",
                selected,
                Subsystem.SUBSYSTEMS,
                subsystemModel);
        loadData();
    }
        
    @FXML
    protected void addCycleAction(ActionEvent event){
        FormController.showForm("cycle/Form.fxml", null);
    }
    @FXML
    
    protected void editCycleAction(ActionEvent event){
        edit("cycle/Form.fxml", cyclesList);
    }
    @FXML
    protected void deleteCycleAction(ActionEvent event){
        Cycle selected = cyclesList.getSelectionModel().getSelectedItem();
        cyclesList.getSelectionModel().clearSelection();
        delete(
                "Suppression du cycle "+selected.getCyclename()+".\n"
                        + "La suppression du cycle engendrera celle des niveaux d'études\n"
                        + "et des classes de ce cycle.",
                selected,
                Cycle.CYCLES,
                cycleModel);
        loadData();
    }
    
    @FXML
    protected void addLevelAction(ActionEvent event){
        FormController.showForm("level/Form.fxml", null);
    }
    @FXML
    protected void editLevelAction(ActionEvent event){
         edit("level/Form.fxml", levelsList);
    }
    @FXML
    protected void deleteLevelAction(ActionEvent event){
        Level selected = levelsList.getSelectionModel().getSelectedItem();
        levelsList.getSelectionModel().clearSelection();
        delete(
                "Suppression du niveau "+selected.getLevelname()+".\n"
                        + "La suppression du niveau entrainera celle des classe de ce niveau",
                selected,
                Level.LEVELS,
                levelModel);
        loadData();
    }
    
    @FXML
    protected void addClassAction(ActionEvent event){
        FormController.showForm("classe/Form.fxml", null);
    }
    @FXML
    protected void editClassAction(ActionEvent event){
        edit("classe/Form.fxml", classesList);
    }
    @FXML
    protected void deleteClassAction(ActionEvent event){
        Classe selected = classesList.getSelectionModel().getSelectedItem();
        classesList.getSelectionModel().clearSelection();
        delete(
                "Suppression de la classe "+selected.getFullname(),
                selected,
                Classe.CLASSES,
                classeModel);
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
    
    private void delete(String message, AbstractEntity selected, ObservableList list, AbstractEntityModel model){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText(message);
        alert.setContentText("Etes vous sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try{
                model.delete(selected);
                list.clear();
                list.addAll(model.getAll());
            }
            catch(Exception ex){
                LOG.log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }
    
    private void edit(String formname, ListView listview ){
        AbstractEntity entity = (AbstractEntity)listview.getSelectionModel().getSelectedItem();
        int index = listview.getItems().indexOf(entity);
        
       
        FormController.showForm(formname, entity);
        
        if(index!= -1)
            listview.getSelectionModel().select(index);
    }

    
    private static final Logger LOG = Logger.getLogger(AdminController1.class.getName());
   
}