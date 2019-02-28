/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import com.smartstart.entities.Message;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class MessageCellGuiController extends ListCell<Message> {

    @FXML
    private AnchorPane gridPane;
    @FXML
    private Label username;
    @FXML
    private TextArea content;
    @FXML
    private Label vu;
    private FXMLLoader mLLoader;
    
    final Tooltip tooltip = new Tooltip();

@Override
    protected void updateItem(Message student, boolean empty) {
        super.updateItem(student, empty);

        if (empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/MessageCellGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
           
            username.setText(student.getMessage_from().getUsername()+": ");
            content.setText(student.getContent());
            content.setEditable(false);
            if(student.getViewed()==1){
            vu.setText("Vu");
            }
            tooltip.setText(student.getDate_message().toString());
            username.setTooltip(tooltip);
            

        };

        setText(null);
        setGraphic(gridPane);
    }


    
    
}
