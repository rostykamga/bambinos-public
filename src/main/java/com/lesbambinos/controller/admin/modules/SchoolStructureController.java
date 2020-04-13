package com.lesbambinos.controller.admin.modules;

import com.lesbambinos.controller.FormController;
import com.lesbambinos.controller.admin.AdminModuleController;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Cycle;
import com.lesbambinos.entity.Level;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.model.ClasseModel;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.LevelModel;
import com.lesbambinos.model.SubsystemModel;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class SchoolStructureController extends AdminModuleController {

    @FXML private Button editSectionButton, deleteSectionButton, editCycleButton, deleteCycleButton, editLevelButton,
            deleteLevelButton, editClassButton, deleteClassButton;
    
    @FXML private ListView<Subsystem> sectionsList;
    @FXML private ListView<Cycle> cyclesList;
    @FXML private ListView<Level> levelsList;
    @FXML private ListView<Classe> classesList;
    
    private final SubsystemModel subsystemModel = new SubsystemModel();
    private final CycleModel cycleModel = new CycleModel();
    private final LevelModel levelModel = new LevelModel();
    private final ClasseModel classeModel = new ClasseModel();
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        super.initialize(url, rb);
                
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
    

    @Override
    protected Object _loadData() {
        Subsystem.SUBSYSTEMS.clear();
        Subsystem.SUBSYSTEMS.addAll(subsystemModel.getAll());
        
        Cycle.CYCLES.clear();
        Cycle.CYCLES.addAll(cycleModel.getAll());
        
        Level.LEVELS.clear();
        Level.LEVELS.addAll(levelModel.getAll());
        
        
        Classe.CLASSES.clear();
        Classe.CLASSES.addAll(classeModel.getAll());
        
        return null;
    }
    

    @Override
    protected String getModuleName() {
        return "Structure Etablissement";
    }

    @Override
    protected void _onDataLoaded(Object data) {
        sectionsList.getItems().setAll(Subsystem.SUBSYSTEMS);
        cyclesList.getItems().setAll(Cycle.CYCLES);
        levelsList.getItems().setAll( Level.LEVELS);
        classesList.getItems().setAll( Classe.CLASSES);
    }
    
}