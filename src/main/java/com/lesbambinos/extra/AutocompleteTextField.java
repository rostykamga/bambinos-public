/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.extra;

import com.lesbambinos.controller.AutocompleteProvider;
import com.lesbambinos.entity.AutocompletedEntity;
import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import com.lesbambinos.controller.AutocompletableForm;

public class AutocompleteTextField extends TextField {

    //popup GUI
    private final ContextMenu entriesPopup;
    private final AutocompletableForm autocompleHandler;
    private final AutocompleteProvider provider;
    
    private boolean autocomplete = true;


    public AutocompleteTextField(AutocompletableForm completable, AutocompleteProvider prov) {
        super();
        autocompleHandler = completable;
        provider = prov;
        this.entriesPopup = new ContextMenu();

        setListner();
    }  

    public boolean isAutocomplete() {
        return autocomplete;
    }

    public void setAutocomplete(boolean autocomple) {
        this.autocomplete = autocomple;
    }
    
    

    /**
     * "Suggestion" specific listners
     */
    private void setListner() {     
        //Add "suggestions" by changing text
        textProperty().addListener((observable, oldValue, newValue) -> {
            if(isAutocomplete()){
                String enteredText = getText();
                //always hide suggestion if nothing has been entered (only "spacebars" are dissalowed in TextFieldWithLengthLimit)
                if (enteredText == null || enteredText.isEmpty() || enteredText.length() < provider.getMinSearcheableLength()) {
                    entriesPopup.hide();
                } else {
                    //filter all possible suggestions depends on "Text", case insensitive
                    List<AutocompletedEntity> filteredEntries = provider.search(enteredText);
                    //some suggestions are found
                    if (!filteredEntries.isEmpty()) {
                        //build popup - list of "CustomMenuItem"
                        populatePopup(filteredEntries, enteredText);
                        if (!entriesPopup.isShowing()) { //optional
                            entriesPopup.show(AutocompleteTextField.this, Side.BOTTOM, 0, 0); //position of popup
                        }
                    }
                    else {//no suggestions -> hide
                        entriesPopup.hide();
                    }
                }
            }
        });

        //Hide always by focus-in (optional) and out
        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            entriesPopup.hide();
        });
    }             


    /**
    * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
    * 
    * @param searchResult The set of matching strings.
    */
    private void populatePopup(List<AutocompletedEntity> searchResult, String searchReauest) {
        //List of "suggestions"
        List<CustomMenuItem> menuItems = new LinkedList<>();
        //Build list as set of labels
        
        for (int i = 0; i < searchResult.size(); i++) {
          final AutocompletedEntity entity = searchResult.get(i);
          final String result = entity.getAutocompleteValue();
          //label with graphic (text flow) to highlight founded subtext in suggestions
          Label entryLabel = new Label();
          entryLabel.setGraphic(buildTextFlow(result, searchReauest));  
          entryLabel.setPrefHeight(10);  //don't sure why it's changed with "graphic"
          CustomMenuItem item = new CustomMenuItem(entryLabel, true);
          menuItems.add(item);

          //if any suggestion is select set it into text and close popup
          item.setOnAction(actionEvent -> {
              autocompleHandler.handleAutocompletion(entity);
              positionCaret(getText().length());
              entriesPopup.hide();
          });
        }

        //"Refresh" context menu
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    
    /**
 * Build TextFlow with selected text. Return "case" dependent.
 * 
 * @param text - string with text
 * @param filter - string to select in text
 * @return - TextFlow
 */
    public static TextFlow buildTextFlow(String text, String filter) {        
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
        textFilter.setFill(Color.ORANGE);
        textFilter.setFont(Font.font(textFilter.getFont().getFamily(), FontWeight.BOLD, textFilter.getFont().getSize()));
        return new TextFlow(textBefore, textFilter, textAfter);
    }   
}
