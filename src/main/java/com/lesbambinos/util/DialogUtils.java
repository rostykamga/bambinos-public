/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.util;

import com.lesbambinos.entity.AbstractEntity;
import com.lesbambinos.model.AbstractEntityModel;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Rostand
 */
public class DialogUtils {
    
    public static void showError(String title, String header, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void showSuccess(String title, String header, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static String prompt(
            String title,
            String header,
            String fieldname,
            String defaultValue){
         // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);


        // Set the button types.
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField textField = new TextField();
        if(defaultValue != null)
            textField.setText(defaultValue);

        grid.add(new Label(fieldname+": "), 0, 0);
        grid.add(textField, 1, 0);

        dialog.getDialogPane().setContent(grid);


        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return textField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        return result.isPresent() ? result.get() : null;
    }
    
    public static void showAndDelete(String message, AbstractEntity selected, ObservableList list, AbstractEntityModel model){
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
                ex.printStackTrace();
                showError("erreur", "Une erreur est survenue lors de la suppression de "+selected, ex.getMessage());
            }
        }
    }
}
