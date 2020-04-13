package com.lesbambinos.controller.classe;

import com.lesbambinos.controller.AbstractController;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Cycle;
import com.lesbambinos.entity.Level;

import com.lesbambinos.entity.Student;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.model.ClasseModel;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.LevelModel;
import com.lesbambinos.model.StudentModel;
import com.lesbambinos.model.SubsystemModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClasseController extends AbstractController {

    // =========== Left Pane ================
    @FXML private Accordion accordion;
    @FXML private TextField searchField;
   
    
    //============== Middle pane ==========================
    @FXML private TextField classeField, nbGirlsField, nbBoysField, nbTotalField;
    @FXML private Label teachersLabel;
    @FXML private Button studentDetailsButton, sendSMSButton, printFormButton, openMarkSheetsButton;
    
    @FXML private TableView<Student> studentsTableview;
    
    @FXML private TableColumn<Student, byte[]> pictureColumn;
    @FXML private TableColumn<Student, String> registrationNumberColumn;
    @FXML private TableColumn<Student, String> firstnameColumn;
    @FXML private TableColumn<Student, String> lastnameColumn;
    @FXML private TableColumn<Student, String> dobColumn;
    @FXML private TableColumn<Student, String> pobColumn;
    @FXML private TableColumn<Student, String> sexColumn;
    @FXML private TableColumn<Student, String> addressColumn;
    
    private final Map<Subsystem, List<Classe>> ALL_CLASSES_MAP  = new HashMap<>();
    private final ObservableList<Student> STUDENT_LIST = FXCollections.observableArrayList();
    private StudentModel studentModel;
    private final SubsystemModel subsystemModel = new SubsystemModel();
    private final CycleModel cycleModel = new CycleModel();
    private final LevelModel levelModel = new LevelModel();
    private final ClasseModel classeModel = new ClasseModel();
   
    @Override
    protected void initUI() {
        
        studentModel = new StudentModel();
        
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
       
        registrationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
       
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("names"));
        
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("surnames"));
                
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        
        pobColumn.setCellValueFactory(new PropertyValueFactory<>("placeOfBirth"));
        
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sex"));
        
        studentDetailsButton.disableProperty().bind(Bindings.isNull(studentsTableview.getSelectionModel().selectedItemProperty()));
        sendSMSButton.disableProperty().bind(Bindings.isNull(studentsTableview.getSelectionModel().selectedItemProperty()));
        
        studentsTableview.setItems(STUDENT_LIST);
        filterData();
    }
    
    private void filterData() {
        FilteredList<Student> searchedData = new FilteredList<>(STUDENT_LIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(student -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (student.getNames().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (student.getPlaceOfBirth().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (student.getSurnames().toLowerCase().contains(lowerCaseFilter)) {
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
    
    @FXML
    public void sendSMSAction(ActionEvent event){
        
    }
    @FXML
    public void studentDetailsAction(ActionEvent event){
        
    }
    @FXML
    public void openMarkSheetsAction(ActionEvent event){
        
    }
    @FXML
    public void printFormAction(ActionEvent event){
        
    }
    
    private void showClassroomDetails(Classe classe){
      
       if(classe != null){
           STUDENT_LIST.setAll(studentModel.getStudentByClass(classe));
           
           int nbGarcons = 0;
           nbGarcons = STUDENT_LIST.stream().filter((std) -> (std.getSex())).map((_item) -> 1).reduce(nbGarcons, Integer::sum);
           nbBoysField.setText(String.valueOf(nbGarcons));
           nbGirlsField.setText(String.valueOf(STUDENT_LIST.size() - nbGarcons));
           nbTotalField.setText(String.valueOf(STUDENT_LIST.size()));
           classeField.setText(classe.getShortname());
       }
    }

    
    @Override
    protected void dataLoaded(Object data) {
        ALL_CLASSES_MAP.entrySet().forEach(entry->{
            ListView<Classe> classesListView = new ListView<>();
            classesListView.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean focused) {
                    if(!focused)
                        classesListView.getSelectionModel().clearSelection();
                }
            });
            classesListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Classe> observable, Classe oldValue, Classe selectedClassroom) -> {
                showClassroomDetails(selectedClassroom);
            });
            TitledPane sectionPane = new TitledPane(entry.getKey().toString(), classesListView);
            classesListView.getItems().addAll(entry.getValue());
            accordion.getPanes().add(sectionPane);
        });
    }

    @Override
    protected Object loadData() {
        Subsystem.SUBSYSTEMS.clear();
        Subsystem.SUBSYSTEMS.addAll(subsystemModel.getAll());
        
        for(Subsystem section : Subsystem.SUBSYSTEMS){
            List<Classe> classes = new ArrayList<>();
            
            for(Cycle cycle : cycleModel.getCyclesBySectionId(section.getId())){
                for(Level level : levelModel.getLevelsByCycleId(cycle.getId()))
                    classes.addAll(classeModel.getClasseByLevelId(level.getId()));
            }
            ALL_CLASSES_MAP.put(section, classes);
        }
        
        return null;
    }
    

    @Override
    protected String getPageName() {
        return "Classes";
    }

}