package com.lesbambinos.controller.registration;


import com.lesbambinos.controller.AutocompletableForm;
import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Cycle;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.entity.Level;
import com.lesbambinos.entity.Registration;
import com.lesbambinos.entity.Student;
import com.lesbambinos.entity.StudentsParent;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.extra.AutocompleteTextField;
import com.lesbambinos.model.ClasseModel;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.LevelModel;
import com.lesbambinos.model.ParentModel;
import com.lesbambinos.model.RegistrationModel;
import com.lesbambinos.model.StudentModel;
import com.lesbambinos.model.StudentsParentModel;
import com.lesbambinos.model.SubsystemModel;
import com.lesbambinos.util.AppUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Date;
import java.util.Optional;
import java.util.prefs.Preferences;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class RegistrationFormController extends FormController implements AutocompletableForm<Student>{
    
    private static final String MASCULIN = "Masculin";
    private static final String FEMININ = "Féminin";
    
    @FXML private ImageView pictureImageView;
    @FXML private TextField registrationNumberField,  pobField;
    @FXML private ComboBox<String> sexCombo;
    @FXML private DatePicker dobDatePicker;
    @FXML private TextArea healthTextArea1, healthTextArea2;
    @FXML private ComboBox<Subsystem> sectionCombobox;
    @FXML private ComboBox<Cycle> cycleCombobox;
    @FXML private ComboBox<Level> levelCombobox;
    @FXML private ComboBox<Classe> classeCombobox;
    @FXML private TableView<StudentsParent> parentsTableview;
    @FXML private TableColumn<StudentsParent, String> parentNameCol;
    @FXML private TableColumn<StudentsParent, String> parentProfessionCol;
    @FXML private TableColumn<StudentsParent, String> parentTelCol1;
    @FXML private TableColumn<StudentsParent, String> parentTelCol2;
    @FXML private TableColumn<StudentsParent, String> parentRoleCol;
    @FXML private TableColumn<StudentsParent, String> parentResidenceCol;
    @FXML private TableColumn<StudentsParent, String> parentReligionCol;
    
    @FXML private Button editParentButton, deleteParentButton;
    @FXML private GridPane studentInfoGridpane;
    
    private AutocompleteTextField firstnameField, lastnameField;
    
    private ClasseModel classeModel;
    private SubsystemModel subsystemModel;
    private LevelModel levelModel;
    private CycleModel cycleModel;
    private StudentModel studentModel;
    private ParentModel parentModel;
    private StudentsParentModel studentsParentModel;
    private RegistrationModel registrationModel;
    
    private Student existingStudent;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        levelModel = new LevelModel();
        classeModel =  new ClasseModel();
        subsystemModel = new SubsystemModel();
        cycleModel = new CycleModel();
        studentModel = new StudentModel();
        parentModel = new ParentModel();
        studentsParentModel = new StudentsParentModel();
        registrationModel = new RegistrationModel();
        
        firstnameField = new AutocompleteTextField(this, studentModel);
        lastnameField = new AutocompleteTextField(this, studentModel);
        
        firstnameField.getStyleClass().add("accessible-textfield");
        lastnameField.getStyleClass().add("accessible-textfield");
        GridPane.setMargin(firstnameField, new Insets(0, 10, 0, 0));
        GridPane.setMargin(lastnameField, new Insets(0, 10, 0, 0));
        studentInfoGridpane.add(firstnameField, 1, 3);
        studentInfoGridpane.add(lastnameField, 1, 4);
        
        parentNameCol.setCellValueFactory((TableColumn.CellDataFeatures<StudentsParent, String> p)
                -> new SimpleStringProperty(p.getValue().getParent().getFullname()));
        parentProfessionCol.setCellValueFactory((TableColumn.CellDataFeatures<StudentsParent, String> p)
                -> new SimpleStringProperty(p.getValue().getParent().getProfession()));
        parentTelCol1.setCellValueFactory((TableColumn.CellDataFeatures<StudentsParent, String> p)
                -> new SimpleStringProperty(p.getValue().getParent().getPhone1()));
        parentTelCol2.setCellValueFactory((TableColumn.CellDataFeatures<StudentsParent, String> p)
                -> new SimpleStringProperty(p.getValue().getParent().getPhone2()));
        parentRoleCol.setCellValueFactory(new PropertyValueFactory<>("parentRole"));
       
        parentResidenceCol.setCellValueFactory((TableColumn.CellDataFeatures<StudentsParent, String> p)
                -> new SimpleStringProperty(p.getValue().getParent().getResidence()));
        parentReligionCol.setCellValueFactory((TableColumn.CellDataFeatures<StudentsParent, String> p)
                -> new SimpleStringProperty(p.getValue().getParent().getReligion()));
         
        //editParentButton, deleteParentButton
        editParentButton.disableProperty().bind(Bindings.isEmpty(parentsTableview.getSelectionModel().getSelectedItems()));
        deleteParentButton.disableProperty().bind(Bindings.isEmpty(parentsTableview.getSelectionModel().getSelectedItems()));
        
        sectionCombobox.valueProperty().addListener((ObservableValue<? extends Subsystem> observable, Subsystem oldValue, Subsystem newValue) -> {
            if(newValue == null){
                cycleCombobox.getItems().clear();
                levelCombobox.getItems().clear();
                classeCombobox.getItems().clear();
            }
            else{
                cycleCombobox.setItems(cycleModel.getCyclesBySectionId(newValue.getId()));
                levelCombobox.getItems().clear();
                classeCombobox.getItems().clear();
            }
        });
        cycleCombobox.valueProperty().addListener((ObservableValue<? extends Cycle> observable, Cycle oldValue, Cycle newValue) -> {
            if(newValue == null){
                levelCombobox.getItems().clear();
                classeCombobox.getItems().clear();
            }
            else{
                levelCombobox.setItems(levelModel.getLevelsByCycleId(newValue.getId()));
                classeCombobox.getItems().clear();
            }
        });
        levelCombobox.valueProperty().addListener((ObservableValue<? extends Level> observable, Level oldValue, Level newValue) -> {
            if(newValue == null){
                classeCombobox.getItems().clear();
            }
            else{
                classeCombobox.setItems(classeModel.getClasseByLevelId(newValue.getId()));
            }
        });
        
        sexCombo.getItems().addAll(FEMININ, MASCULIN);
        sectionCombobox.setItems(subsystemModel.getAll());
    }
    
    /**
     * Pour créer un level, on passe en paramètre la section
     * @param obj Pair of Subsystem and Level object
     */
    @Override
    public void setData(AbstractEntity obj){
        
        super.setData(obj);
        
        formHeaderTitle.setText(entity == null? "Nouvelle inscription" : "Modification Inscription");
        
        if(entity != null){
            Registration registration = (Registration)entity;
            Student student = registration.getStudent();
            Classe classe = registration.getClasse();
            
            firstnameField.setAutocomplete(false);
            lastnameField.setAutocomplete(false);
            
            setFields(classe, student);
        }
    }
    
    private void setImageView(String title, ImageView imview, ActionEvent event){
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle(title);
        
        Preferences prefs =  Preferences.userRoot();
        String prevPath = prefs.get("bambinos.last_imported_logo", null);
        if(prevPath!=null){
            if(!File.separator.equals("/"))
                prevPath = prevPath.replace("/", "\\");
            File prevFolder = new File(prevPath);
            if(prevFolder.exists())
                fileChooser.setInitialDirectory(prevFolder);
        }
        Window stage = ((Node)event.getSource()).getScene().getWindow();
        
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile == null )
            return;
        
        String selectedFileParent = selectedFile.getParent();
        if(!File.separator.equals("/"))
            selectedFileParent = selectedFileParent.replace("\\", "/");
        prefs.put("bambinos.last_imported_logo", selectedFileParent);
        
        imview.setImage(new Image(selectedFile.toURI().toString()));
    }
    
    @FXML
    public void loadPictureAction(ActionEvent event){
        setImageView("Selectionner la photo de l'élève", pictureImageView, event);
    }
    @FXML
    public void addParentAction(ActionEvent event){
        AbstractEntity e = FormController.showForm("registration/ParentForm.fxml", null);
         
        if(e!=null){
            StudentsParent stdP = (StudentsParent)e;
            parentsTableview.getItems().add(stdP);
        }
    }
    @FXML
    public void editParentAction(ActionEvent event){
        AbstractEntity entity = (AbstractEntity)parentsTableview.getSelectionModel().getSelectedItem();
        FormController.showForm("registration/ParentForm.fxml", entity);
        //TODO, make sure that parent table is updated well
        parentsTableview.refresh();
    }
    @FXML
    public void deleteParentAction(ActionEvent event){
        StudentsParent selected = parentsTableview.getSelectionModel().getSelectedItem();
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Suppression du parent "+selected.getParent().getFullname());
        alert.setContentText("Etes vous sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            parentsTableview.getItems().remove(selected);
            parentsTableview.getSelectionModel().clearSelection();
        }
    }
    
    @Override
    protected boolean validateInput() {

        String errorMessage = "";

        if (AppUtils.isEmptyOrNullString(firstnameField.getText())) {
            errorMessage += "Le Champ nom ne doit pas être vide!\n";
        }
        if (AppUtils.isEmptyOrNullString(pobField.getText())) {
            errorMessage += "Le Champ lieu de naisssnce ne doit pas être vide!\n";
        }
        if (AppUtils.isEmptyOrNullString(healthTextArea1.getText())) {
            errorMessage += "Le Champ santé 1 ne doit pas être vide!\n";
        }
        if (AppUtils.isEmptyOrNullString(healthTextArea2.getText())) {
            errorMessage += "Le Champ santé 2 ne doit pas être vide!\n";
        }
        
        if (dobDatePicker.getValue() == null) {
            errorMessage += "Le Champ Date de naisssnce ne doit pas être vide!\n";
        }
        if (sexCombo.getValue() == null) {
            errorMessage += "Le Champ Sexe ne doit pas être vide!\n";
        }
        
        if (classeCombobox.getValue() == null) {
            errorMessage += "Le Champ Classe ne doit pas être vide!\n";
        }
        if (parentsTableview.getItems().isEmpty()) {
            errorMessage += "La liste des parents ne doit pas être vide!\n";
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
        
        Student student = new Student(
                registrationNumberField.getText(),
                firstnameField.getText(),
                lastnameField.getText(),
                sexCombo.getValue().equals(MASCULIN),
                Date.valueOf(dobDatePicker.getValue()),
                pobField.getText(),
                healthTextArea1.getText(),
                healthTextArea2.getText(),
                AppUtils.imageView2ByteArray(pictureImageView)
                
        );
        
        Classe classe = classeCombobox.getValue();
        Registration reg = new Registration(new java.util.Date(), classe, student);
        
        
        try{
            if(entity != null){
                Registration oldReg = (Registration)entity;
                reg.setId(oldReg.getId());
                
                reg.setRegistrationDate(oldReg.getRegistrationDate());
                registrationModel.update(reg);
            }
            else{
                if(existingStudent == null)
                    studentModel.saveStudent(student, classe.getLevel().getCycle().getSubsystem());
                else{
                    student.setId(existingStudent.getId());
                    studentModel.update(student);
                }
                registrationModel.save(reg);
            }
            
            for(StudentsParent stdP : parentsTableview.getItems()){
                if(stdP.getParent().getId() == null){
                    parentModel.save(stdP.getParent());
                }
                else{
                    parentModel.update(stdP.getParent());
                }
                if(stdP.getStudent() ==null){
                    stdP.setStudent(student);
                    studentsParentModel.save(stdP);
                }
                else{
                    studentsParentModel.update(stdP);
                }
            }
            
            formStatus = STATUS_SUCCESS;
        }
        catch(Exception ex){
        	ex.printStackTrace();
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
        alert.setHeaderText("Inscription enregistrée");
        alert.setContentText("Informations enregistrées avec succès");
        return alert;
    }

    @Override
    protected Alert getFailAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur");
        alert.setHeaderText("L'inscription n'a pas été enregistrée");
        alert.setContentText("Une erreur est survenue lors de l'opération\n"+err_msg);
        return alert;
    }

    @Override
    public void handleAutocompletion(Student element) {
        Registration reg = registrationModel.getRegistrationByStudent(existingStudent).get(0);
        existingStudent = element;
        setFields(reg.getClasse(), existingStudent);
    }
    
    private void setFields(Classe classe, Student student){
        
        registrationNumberField.setText(student.getRegistrationNumber());
            
        firstnameField.setText(student.getNames());
        lastnameField.setText(student.getSurnames());
        pobField.setText(student.getPlaceOfBirth());
        healthTextArea1.setText(student.getHealth1());
        healthTextArea2.setText(student.getHealth2());
        dobDatePicker.setValue(student.getDateOfBirth().toLocalDate());

        sexCombo.setValue(student.getSex()?MASCULIN : FEMININ);

        classeCombobox.setValue(classe);
        levelCombobox.setValue(classe.getLevel());
        cycleCombobox.setValue(classe.getLevel().getCycle());
        sectionCombobox.setValue(classe.getLevel().getCycle().getSubsystem());

        if(student.getPicture() != null){
            pictureImageView.setImage(new Image(new ByteArrayInputStream(student.getPicture())));
        }
        parentsTableview.setItems(studentsParentModel.getParentsByStudent(student));
    }

}
