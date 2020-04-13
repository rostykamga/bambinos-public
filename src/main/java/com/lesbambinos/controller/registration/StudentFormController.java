package com.lesbambinos.controller.registration;


import com.lesbambinos.auth.BambinosSecurityManager;
import com.lesbambinos.controller.AutocompletableForm;
import com.lesbambinos.controller.FormController;
import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Cycle;
import java.net.URL;
import java.util.ResourceBundle;

import com.lesbambinos.entity.Level;
import com.lesbambinos.entity.Student;
import com.lesbambinos.entity.Attachment;
import com.lesbambinos.entity.DiscountRequest;
import com.lesbambinos.entity.StudentsParent;
import com.lesbambinos.entity.Subsystem;
import com.lesbambinos.entity.Uniform;
import com.lesbambinos.entity.UniformOrder;
import com.lesbambinos.entity.UniformSize;
import com.lesbambinos.entity.UniformType;
import com.lesbambinos.extra.AutocompleteTextField;
import com.lesbambinos.model.AttachmentModel;
import com.lesbambinos.model.ClasseModel;
import com.lesbambinos.model.CycleModel;
import com.lesbambinos.model.DiscountRequestModel;
import com.lesbambinos.model.LevelModel;
import com.lesbambinos.model.ParentModel;
import com.lesbambinos.model.StudentModel;
import com.lesbambinos.model.StudentsParentModel;
import com.lesbambinos.model.SubsystemModel;
import com.lesbambinos.model.UniformModel;
import com.lesbambinos.model.UniformOrderModel;
import com.lesbambinos.model.UniformSizeModel;
import com.lesbambinos.model.UniformTypeModel;
import com.lesbambinos.util.AppUtils;
import com.lesbambinos.util.DialogUtils;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class StudentFormController extends FormController implements AutocompletableForm<Student>{
    
    private static final String MASCULIN = "Masculin";
    private static final String FEMININ = "Féminin";
    
    // Student info tab
    @FXML private ImageView pictureImageView;
    @FXML private TextField registrationNumberField,  pobField;
    @FXML private ComboBox<String> sexCombo;
    @FXML private DatePicker dobDatePicker;
    
    // health tab
    @FXML private TextArea healthTextArea1, healthTextArea2;
    @FXML private ListView<Attachment> attachmentsListview;
    @FXML private Button addDocumentButton,openAttachmentButton, editDocumentButton, deleteDocumentButton;
    
    // parcours tab
    @FXML private ComboBox<Subsystem> sectionCombobox;
    @FXML private ComboBox<Cycle> cycleCombobox;
    @FXML private ComboBox<Level> levelCombobox;
    @FXML private ComboBox<Classe> classeCombobox;
    
    // parents tab
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
    
    // other tab
    @FXML private CheckBox uniformCheckBox, discountCheckBox;
    @FXML private ComboBox<UniformSize> uniformSizeCombobox;
    @FXML private VBox uniformsVbox;
    @FXML private TextArea discountDescriptionTextArea;
    @FXML private GridPane uniformRequestGridpane, discountReguestGridpane;
    
    private final ClasseModel classeModel =  new ClasseModel();
    private final SubsystemModel subsystemModel = new SubsystemModel();
    private final LevelModel levelModel = new LevelModel();
    private final CycleModel cycleModel = new CycleModel();
    private final StudentModel studentModel = new StudentModel();
    private final ParentModel parentModel = new ParentModel();
    private final StudentsParentModel studentsParentModel = new StudentsParentModel();
    private final UniformSizeModel uniformSizeModel = new UniformSizeModel();
    private final UniformTypeModel uniformTypeModel = new UniformTypeModel();
    private final UniformOrderModel uniformOrderModel = new UniformOrderModel();
    private final UniformModel uniformModel = new UniformModel();
    private final DiscountRequestModel discountrequestModel = new DiscountRequestModel();
    private final AttachmentModel attchmentModel = new AttachmentModel();
    
    
    private final Map<UniformType, UniformOrder> PREVIOUS_ORDERS = new HashMap<>();
    private final Map<UniformType, Integer> UNIFORM_TYPES_QTY = new HashMap<>();
    private final List<UniformQuantitySelectorController> QUANTITIES_CONTROLLERS = new ArrayList<>();
    private final ObservableList<UniformType> UNIFORM_TYPES = FXCollections.observableArrayList();
    private final ObservableList<UniformSize> UNIFORM_SIZES = FXCollections.observableArrayList();
   
    private Student existingStudent;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
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
        
        // health tab
        //openAttachmentButton, editDocumentButton, deleteDocumentButton
        openAttachmentButton.disableProperty().bind(Bindings.isNull(attachmentsListview.getSelectionModel().selectedItemProperty()));
        editDocumentButton.disableProperty().bind(openAttachmentButton.disableProperty());
        deleteDocumentButton.disableProperty().bind(openAttachmentButton.disableProperty());
        
        
        // other tab
        uniformRequestGridpane.disableProperty().bind(uniformCheckBox.selectedProperty().not());
        discountReguestGridpane.disableProperty().bind(discountCheckBox.selectedProperty().not());
        
        UNIFORM_SIZES.addAll(uniformSizeModel.getAll());
        UNIFORM_TYPES.addAll(uniformTypeModel.getAll());
        
        UNIFORM_TYPES.forEach(type->{
            UNIFORM_TYPES_QTY.put(type, 0);
        });
        
        UNIFORM_TYPES_QTY.entrySet().forEach(entry->{
            uniformsVbox.getChildren().add(UniformQuantitySelectorController.createComponent(entry, QUANTITIES_CONTROLLERS));
        });
        
        uniformSizeCombobox.setItems(UNIFORM_SIZES);
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
            Student student = (Student)entity;
            Classe classe = student.getClassroom();
            
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
    
    @FXML
    public void addDocumentAction(ActionEvent event){
        
        AbstractEntity e = FormController.showForm("registration/AttachmentForm.fxml", null);
         
        if(e!=null){
            Attachment attachment = (Attachment)e;
            attachmentsListview.getItems().add(attachment);
        }
    }
    
    @FXML
    public void openAttachmentAction(ActionEvent event){
        Runnable r = () -> {
            try{
                Attachment attachment = attachmentsListview.getSelectionModel().getSelectedItem();
                String[]filenameParts = attachment.getFilename().split("\\.");
                File temp = File.createTempFile(filenameParts[0], "."+filenameParts[1]);
                try (FileOutputStream fos = new FileOutputStream(temp)) {
                    fos.write(attachment.getContent());
                }
                
                if(Desktop.isDesktopSupported()){
                    Desktop.getDesktop().open(temp);
                }
            }
            catch(Exception ex){
                Platform.runLater(()->DialogUtils.showError("erreur", "Erreur lors de l'ouverture du fichier", ex.getMessage()));
            }
        };
       new Thread(r).start();
    }
    
    @FXML
    public void editDocumentAction(ActionEvent event){
        Attachment att = attachmentsListview.getSelectionModel().getSelectedItem();
        FormController.showForm("registration/AttachmentForm.fxml", att);
        attachmentsListview.refresh();
    }
    @FXML
    public void deleteDocumentAction(ActionEvent event){
        Attachment selected = attachmentsListview.getSelectionModel().getSelectedItem();
       
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Suppression du document "+selected.getDescription());
        alert.setContentText("Etes vous sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            attachmentsListview.getItems().remove(selected);
            attachmentsListview.getSelectionModel().clearSelection();
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
        if(uniformCheckBox.isSelected()){
            
            if(uniformSizeCombobox.getValue() == null){
                errorMessage += "Choisir une taille d'uniforme !\n";
            }
            
            boolean anyUniformTypeQtySet = false;
            for(UniformQuantitySelectorController ctrl : QUANTITIES_CONTROLLERS){
                if(ctrl.getQuantity() > 0 && !ctrl.isDisable()){
                    anyUniformTypeQtySet = true;
                    break;
                }
            }
            
            if(!anyUniformTypeQtySet){
                errorMessage += "Choisir au moins un type d'uniforme !\n";
            }
        }
        
        if(discountCheckBox.isSelected()){
            if(AppUtils.isEmptyOrNullString(discountDescriptionTextArea.getText()))
                errorMessage += "Renseigner le motif de la demande de rabais !\n";
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
        
        Classe studentClassroom = classeCombobox.getValue();
        try{
            if(entity == null){
                entity = new Student(
                    registrationNumberField.getText(),
                    firstnameField.getText(),
                    lastnameField.getText(),
                    sexCombo.getValue().equals(MASCULIN),
                    Date.valueOf(dobDatePicker.getValue()),
                    pobField.getText(),
                    healthTextArea1.getText(),
                    healthTextArea2.getText(),
                    new java.util.Date(),
                    studentClassroom,
                    AppUtils.imageView2ByteArray(pictureImageView)
                );
                if(existingStudent == null)
                    studentModel.saveStudent((Student)entity, studentClassroom.getLevel().getCycle().getSubsystem());
                else{
                    entity.setId(existingStudent.getId());
                    studentModel.update((Student)entity);
                }
            }
            else{
                Student student = (Student)entity;
                student.setClassroom(studentClassroom);
                student.setDateOfBirth(Date.valueOf(dobDatePicker.getValue()));
                student.setHealth1( healthTextArea1.getText());
                student.setHealth2( healthTextArea2.getText());
                student.setNames(firstnameField.getText());
                student.setPicture(AppUtils.imageView2ByteArray(pictureImageView));
                student.setPlaceOfBirth(pobField.getText());
                student.setSex(sexCombo.getValue().equals(MASCULIN));
                student.setSurnames(lastnameField.getText());
        
                studentModel.update(student);
            }
            
            // save attachments
            List<Attachment> toSave = new ArrayList<>();
            List<Attachment> toUpdate = new ArrayList<>();
            List<Attachment> toDelete = new ArrayList<>();
            
            attachmentsListview.getItems().forEach(attachement->{
                if(attachement.getOwnerId() == null){
                    attachement.setOwnerId(entity.getId());
                    attachement.setOwnerType("Student");
                    toSave.add(attachement);
                }
                else{
                    toUpdate.add(attachement);
                }
            });
            
            List<Attachment> allAttachment = attchmentModel.getAttachmentByStudent((Student)entity);
            for(Attachment att : allAttachment){
                if(!attachmentsListview.getItems().contains(att))
                    toDelete.add(att);
            }
            
            toSave.forEach(attachment->attchmentModel.save(attachment));
            toUpdate.forEach(attachment->attchmentModel.update(attachment));
            toDelete.forEach(attachment->attchmentModel.delete(attachment));
            
            for(StudentsParent stdP : parentsTableview.getItems()){
                if(stdP.getParent().getId() == null){
                    parentModel.save(stdP.getParent());
                }
                else{
                    parentModel.update(stdP.getParent());
                }
                
                if(stdP.getStudent() == null){
                    stdP.setStudent((Student)entity);
                    studentsParentModel.save(stdP);
                }
                else{
                    studentsParentModel.update(stdP);
                }
            }
            
            if(discountCheckBox.isSelected() && !discountCheckBox.isDisable()){
                List<DiscountRequest> list = discountrequestModel.getDiscountRequestByStudent((Student)entity);
                if(!list.isEmpty()){
                    DiscountRequest req = list.get(0);
                    req.setDescription(discountDescriptionTextArea.getText());
                    //req.setRequestDate(new java.util.Date());
                    discountrequestModel.update(req);
                }
                else{
                    DiscountRequest req = new DiscountRequest();
                    req.setClassroom(studentClassroom);
                    req.setCreator(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity());
                    req.setDescription(discountDescriptionTextArea.getText());
                    req.setRequestDate(new java.util.Date());
                    req.setStatus(DiscountRequest.STATUS.PENDING);
                    req.setStudent((Student)entity);
                    discountrequestModel.save(req);
                }
            }
            
            if(uniformCheckBox.isSelected()){
                UniformSize size = uniformSizeCombobox.getValue();
                
                List<UniformOrder> newOrders = new ArrayList();
                List<UniformOrder> updatedOrders = new ArrayList();
                List<UniformOrder> deletedOrders = new ArrayList();
                
                for(UniformQuantitySelectorController ctrl : QUANTITIES_CONTROLLERS){
                    if(ctrl.isDisable())
                        continue;
                    
                    UniformType type = ctrl.getType();
                    if(PREVIOUS_ORDERS.containsKey(type)){
                        UniformOrder order = PREVIOUS_ORDERS.get(type);
                        // if the user sets the new quantity to zero, then it means to delete this order
                        if(ctrl.getQuantity() == 0)
                            deletedOrders.add(order);
                        else{
                            //order.getUniform().setSize(size);
                            order.setQuantity(ctrl.getQuantity());
                            order.setUniform(uniformModel.getUniformWith(size, type, ((Student)entity).getSex()));
                            updatedOrders.add(order);
                        }
                    }
                    else if(ctrl.getQuantity() > 0){
                        Uniform uniform = uniformModel.getUniformWith(size, type, ((Student)entity).getSex());
                        UniformOrder order = new UniformOrder();
                        
                        order.setCreator(BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity());
                        order.setOrderDate(new java.util.Date());
                        order.setOrderPrice(uniform.getUnitPrice());
                        order.setPaid(false);
                        order.setQuantity(ctrl.getQuantity());
                        order.setUniform(uniform);
                        order.setStudent((Student)entity);
                        
                        newOrders.add(order);
                    }
                }
                
                newOrders.forEach(order->uniformOrderModel.save(order));
                updatedOrders.forEach(order->uniformOrderModel.update(order));
                deletedOrders.forEach(order->uniformOrderModel.delete(order));
                
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
        existingStudent = element;
        setFields(existingStudent.getClassroom(), existingStudent);
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
              
        // on charge les piéces jointes
        attachmentsListview.getItems().setAll(attchmentModel.getAttachmentByStudent(student));
        
        // on charge les commandes d'uniformes passées par l'élève.
        List<UniformOrder> studentOrders = uniformOrderModel.getUniformOrdersByStudent(student);
        
        studentOrders.forEach(order->PREVIOUS_ORDERS.put(order.getUniform().getType(), order));
        
        if(!studentOrders.isEmpty()){
            // set the checkbox as well as uniform size
            uniformCheckBox.setSelected(true);
            uniformSizeCombobox.setValue(studentOrders.get(0).getUniform().getSize());
            
            studentOrders.forEach(order->{
                
                UniformType type = order.getUniform().getType();
                for(UniformQuantitySelectorController ctrl : QUANTITIES_CONTROLLERS){
                    if(type.equals(ctrl.getType())){
                        ctrl.setQuantity(order.getQuantity());
                        if(order.isDelivered() || order.isPaid())
                            ctrl.setDisable(true);
                        break;
                    }
                }
            });
        }
        
        
        // On charge la demande de rabais éventuelle
        List<DiscountRequest> discountRequests = discountrequestModel.getDiscountRequestByStudent(student);
        if(!discountRequests.isEmpty()){
            discountCheckBox.setSelected(true);
            
            DiscountRequest request = discountRequests.get(0);
            discountDescriptionTextArea.setText(request.getDescription());
            
            // Si la requete a déjà été étudiée, alors impossible de la modifier
            if(!request.getStatus().equals(DiscountRequest.STATUS.PENDING)){
                discountDescriptionTextArea.setDisable(true);
                discountCheckBox.setDisable(true);
            }
        }
    }

}
