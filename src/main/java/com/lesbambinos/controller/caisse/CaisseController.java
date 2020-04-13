package com.lesbambinos.controller.caisse;

import com.lesbambinos.controller.AbstractController;
import com.lesbambinos.entity.Classe;
import com.lesbambinos.entity.Installment;
import com.lesbambinos.entity.InstallmentPerLevel;
import com.lesbambinos.entity.InvoiceData;

import com.lesbambinos.entity.Student;
import com.lesbambinos.entity.Tuition;
import com.lesbambinos.extra.ExtendedCheckBoxTreeCellFactory;
import com.lesbambinos.extra.ExtendedCheckBoxTreeItem;
import com.lesbambinos.model.InstallmentPerLevelModel;
import com.lesbambinos.model.PaymentModel;
import com.lesbambinos.model.StudentModel;
import com.lesbambinos.util.AppUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.Stack;
import java.util.TreeMap;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CaisseController extends AbstractController {

    // =========== Left Pane ================
    @FXML private TextField searchField;
    @FXML private TableColumn<Student, String> studentColumn;
    @FXML private TableView<Student> studentTableview;
    
    //============== Middle pane ==========================
    @FXML private TextField matriculeField, studentName, classeField,  partialTotalField;
    @FXML private Button paymentButton, cancelButton;
    @FXML private TreeView  tuitionTree;
    
    private final ObjectProperty<Student> selectedStudent = new SimpleObjectProperty<>();
    private final ObservableList<Student> REGISTRATIONLIST = FXCollections.observableArrayList();
    private CheckBoxTreeItem root;
    
    private StudentModel studentModel;
    private InstallmentPerLevelModel instPerLevelModel;
    private PaymentModel paymentModel;
   
    @Override
    protected void initUI() {
        
        studentModel = new StudentModel();
        instPerLevelModel = new InstallmentPerLevelModel();
        paymentModel = new PaymentModel();
        
        root = new CheckBoxTreeItem<>("Tous les frais");
        tuitionTree.setRoot(root);
        tuitionTree.setCellFactory(new ExtendedCheckBoxTreeCellFactory());
        tuitionTree.setDisable(true);

        
        studentColumn.setCellValueFactory((TableColumn.CellDataFeatures<Student, String> p)
                -> new SimpleStringProperty(
                        p.getValue().getRegistrationNumber()+" "+
                        p.getValue().getNames()+" "+
                        p.getValue().getSurnames()));
        
        matriculeField.disableProperty().bind(Bindings.isNull(selectedStudent));
        studentName.disableProperty().bind(Bindings.isNull(selectedStudent));
        classeField.disableProperty().bind(Bindings.isNull(selectedStudent));
        partialTotalField.disableProperty().bind(Bindings.isNull(selectedStudent));
        
        paymentButton.disableProperty().bind(Bindings.isNull(selectedStudent));
        cancelButton.disableProperty().bind(Bindings.isNull(selectedStudent));
        
        studentTableview.setItems(REGISTRATIONLIST);
        selectedStudent.addListener((ObservableValue<? extends Student> observable, Student oldValue, Student newValue) -> {
            showStudentDetails(newValue);
        });
        
        studentTableview.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Student> observable, Student oldValue, Student newValue) -> {
            selectedStudent.set(newValue);
        });
        
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
           REGISTRATIONLIST.setAll(studentModel.find(newValue));
        });
    }
    
    
    /**
     * This method is called when a registration is selected or deselected.
     * It displays the student informations (name, registration number and classe) involved in
     * registration object, 
     * Then it finds all the tuition installement tied to the registration classe level, and display them
     * as checkboxtreeitem
     * @param student 
     */
    private void showStudentDetails(Student student){
      
        // Always need to clear the root
        clearNode(root);
        
        //If nothing is selected, then reset UI
        if(student == null){
            resetStudentFields();
            root.setSelected(false);
            tuitionTree.setDisable(true);
        }
        else{
            tuitionTree.setDisable(false);
            matriculeField.setText(student.getRegistrationNumber());
            studentName.setText(student.getNames() + " "+ student.getSurnames());
            classeField.setText(student.getClassroom().getShortname());
            partialTotalField.setText("0.00");
            buildTuitionPaymentTree(student.getClassroom(), student);
        }
    }
    
    private void buildTuitionPaymentTree(Classe classe, Student std){
        
        // get all installments for this classe's level
        ObservableList<InstallmentPerLevel> allStudentInstallements = instPerLevelModel
                .getInstallmentsByLevel(classe.getLevel());

        // Group installmentPerLevel by tuition
        Map<Tuition, List<InstallmentPerLevel>> studentTuitionMap = new HashMap<>();
        allStudentInstallements.forEach(instPL->{

            Tuition t = instPL.getInstPk().getInstallment().getTuition();
            List<InstallmentPerLevel> list = studentTuitionMap.get(t);
            if(list == null){
                list = new ArrayList<>();
                studentTuitionMap.put(t, list);
            }
            list.add(instPL);
        });
        //and sort them, by tuition name
        TreeMap<Tuition, List<InstallmentPerLevel>> sortedTuitionMap = new TreeMap<>(studentTuitionMap);

        //Build the treeView items
        //One node below the root for each tuition
        sortedTuitionMap.forEach((tuition, tuitionInstallmentList)->{
            
            if(!tuitionInstallmentList.isEmpty()){
            
                //The tuition only has one installment, then transform the parent tuition node to an installment node
                if(tuitionInstallmentList.size() == 1 && installmentNotPaid(tuitionInstallmentList.get(0), std)){
                    ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper> tuitionNode;
                    tuitionNode = new ExtendedCheckBoxTreeItem<>(
                            new InstallmentPerLevelWrapper(tuition, tuitionInstallmentList.get(0), tuitionInstallmentList));
                    addSelectionListener(tuitionNode);
                    root.getChildren().add(tuitionNode);
                }
                else{
                    ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper> currentItem, prevItem=null;
                    List<ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper>> children = new ArrayList<>();
                    for(InstallmentPerLevel instpl :  tuitionInstallmentList){
                        
                        if(installmentNotPaid(instpl, std)){
                            currentItem = new ExtendedCheckBoxTreeItem<>(
                                    new InstallmentPerLevelWrapper(tuition, instpl, tuitionInstallmentList));
                            addSelectionListener(currentItem);
                            children.add(currentItem);

                            if(prevItem != null)
                                addPrecedenceListener(currentItem.disabledProperty, prevItem.selectedProperty());

                            prevItem = currentItem;
                        }
                    }
                    if(!children.isEmpty()){
                       ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper> tuitionNode;
                       tuitionNode = new ExtendedCheckBoxTreeItem<>(new InstallmentPerLevelWrapper(tuition, null, null));
                       tuitionNode.getChildren().addAll(children);
                       root.getChildren().add(tuitionNode);
                       
                       children.forEach(item->{
                            ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper> firstItem =  (ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper>)item;
                            boolean oldValue = firstItem.isSelected();
                            firstItem.setSelected(!oldValue);
                            firstItem.setSelected(oldValue);
                        });
                    }
                    
                }
            }
        });

//        if(hasAllChildrenSelected(root)){
//            tuitionTree.setDisable(true);
//            root.setSelected(true);
//        }
    }
    
//    private boolean hasAllChildrenSelected(CheckBoxTreeItem node){
//        if(node.getChildren().isEmpty())
//            return false;
//        
//        for(Object child : node.getChildren()){
//            ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper> ch =
//                    (ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper>)child;
//            if(!ch.isDisabled()){
//                return false;
//            }
//        }
//        return true;
//    }
    
    private void addPrecedenceListener(BooleanProperty disableProperty, BooleanProperty selectedProperty){
        selectedProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean selected) {
                disableProperty.set(!selected);
            }
        });
    }
    
    private boolean installmentNotPaid(InstallmentPerLevel installement, Student std){
        return paymentModel.getPaymentByInstallementAndStudent(installement.getInstPk().getInstallment(), std) == null;
    }
    
    private void disableItemIfAlreadyPaid(ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper> node, Student std){
        
        Installment inst = node.getValue().getInstPL().getInstPk().getInstallment();
        if(paymentModel.getPaymentByInstallementAndStudent(inst, std) != null){
            // The student has already paid this installement, then disables it
            node.setIndependent(true); // stops listen to children and parent
            node.setDisabled(true);
            node.setSelected(true);
        }
        else{
             addSelectionListener(node);
        }
    }
    
    
    private void addSelectionListener(final CheckBoxTreeItem<InstallmentPerLevelWrapper> node){
        node.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean selected) -> {
            double amount = node.getValue().getAmount();
            double existing = Double.parseDouble(partialTotalField.getText());
            existing = selected ? existing + amount : existing - amount;
            partialTotalField.setText(String.valueOf(existing));
        });
    }
    
    private void resetStudentFields(){
        matriculeField.setText("");
        studentName.setText("");
        classeField.setText("");
        partialTotalField.setText("0.00");
    }
    
    private void clearNode(TreeItem item){
        if(!item.getChildren().isEmpty()){
            for(Object obj : item.getChildren()){
                TreeItem child = (TreeItem)obj;
                clearNode(child);
            }
            item.getChildren().clear();
        }
    }
    
    protected boolean validateInput() {

        String errorMessage = "";

        if (!AppUtils.isValidPositiveDouble(partialTotalField.getText())) {
            errorMessage += "Vous devez sélectionner au moins un type de frais à payer!\n";
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
    protected void dataLoaded(Object data) {
        if (!REGISTRATIONLIST.isEmpty()) {
            REGISTRATIONLIST.clear();
        }
        REGISTRATIONLIST.addAll((ObservableList<Student>)data);
    }

    @Override
    protected Object loadData() {
//        return null;
        return studentModel.getAll();
    }


    @FXML
    public void cancelAction(ActionEvent event) throws Exception {
        studentTableview.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void paymentAction(ActionEvent event) throws Exception {
        
        if(validateInput()){
            
            List<InstallmentPerLevel> selectedInstallments = new ArrayList<>();
            
//            Stack<ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper>> stack = new Stack<>();
            Stack<TreeItem> stack = new Stack<>();
            
            stack.addAll(root.getChildren());
            while(!stack.isEmpty()){
               
                TreeItem item = stack.pop();
                if(item.getChildren().isEmpty()){
                    ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper> extTreeItem = 
                            (ExtendedCheckBoxTreeItem<InstallmentPerLevelWrapper>)item;
                    if(extTreeItem.isSelected() && !extTreeItem.isDisabled()){
                        InstallmentPerLevel instPL = extTreeItem.getValue().getInstPL();
                        selectedInstallments.add(instPL);
                    }
                    
                }
                else
                    stack.addAll(item.getChildren());
            }
            
            
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/caisse/Invoice.fxml")));
            //loader.setController(controller);
            Parent root = loader.load();
            InvoiceController controller = loader.getController();
            double total = Double.parseDouble(partialTotalField.getText());
            double netPayable = Double.parseDouble(partialTotalField.getText());
            controller.setData(new InvoiceData(selectedStudent.get(), total, netPayable, selectedInstallments, null));
            Scene scene = new Scene(root, 450, 400);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            root.setOnMousePressed((MouseEvent e) -> {
                xOffset = e.getSceneX();
                yOffset = e.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent e) -> {
                stage.setX(e.getScreenX() - xOffset);
                stage.setY(e.getScreenY() - yOffset);
            });
            stage.setTitle("Confirmation");
            stage.getIcons().add(new Image("/images/logo.png"));
            stage.setScene(scene);
            stage.showAndWait();
            
            if(!controller.windowClosed()){
                studentTableview.getSelectionModel().clearSelection();
            }
            
        }
    }

    @Override
    protected String getPageName() {
        return "Caisse";
    }

}

class InstallmentPerLevelWrapper{
    private Tuition tuition;
    private InstallmentPerLevel instPL;
    private List<InstallmentPerLevel> siblings;

    public InstallmentPerLevelWrapper(Tuition tuition, InstallmentPerLevel instPL, List<InstallmentPerLevel> siblings) {
        this.tuition = tuition;
        this.instPL = instPL;
        this.siblings = siblings;
    }

    public Tuition getTuition() {
        return tuition;
    }

    public void setTuition(Tuition tuition) {
        this.tuition = tuition;
    }

    public InstallmentPerLevel getInstPL() {
        return instPL;
    }

    public void setInstPL(InstallmentPerLevel instPL) {
        this.instPL = instPL;
    }

    public List<InstallmentPerLevel> getSiblings() {
        return siblings;
    }

    public void setSiblings(List<InstallmentPerLevel> siblings) {
        this.siblings = siblings;
    }
    
    public double getAmount(){
        return instPL.getAmount();
    }
    
    @Override
    public String toString(){
        if(siblings == null)
            return tuition.getTitle();
        
        if(siblings.size() == 1){
            return tuition.getTitle() + " ("+String.valueOf(instPL.getAmount())+")";
        }
        else{
            int index = siblings.indexOf(instPL)+1;
            return "Tranche "+index+" ("+String.valueOf(instPL.getAmount())+")";
        }
    }
}