/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import com.smartstart.services.ContractServiceImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author diabl
 */
public class AutoContractCellController extends ListCell<Contract> {

    @FXML
    private AnchorPane gridPane;
    @FXML
    private Label username;
    @FXML
    private Label description;
    @FXML
    private Button display;
    @FXML
    private Label titles;
    private FXMLLoader mLLoader;
    final Tooltip tooltip = new Tooltip();

@Override
    protected void updateItem(Contract student, boolean empty) {
        super.updateItem(student, empty);

        if (empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/AutoContractCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            description.setText(student.getApplication().getOpportunity().getJob_title());
            username.setText(student.getUser().getUsername());
            tooltip.setText("Click Display to Complete your Contract");
                titles.setTooltip(tooltip);
            

            display.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {

                    AutoContractGuiController.lc.removeAll(AutoContractGuiController.lc);
                    AutoContractGuiController.lc.add(student);
                        Parent tableViewContract;
                        try {
                            tableViewContract = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AutoContractGui.fxml"));
                            Scene tableViewContractScene = new Scene(tableViewContract);
                            Stage window = (Stage) ((Node) t.getSource()).getScene().getWindow();
                            window.setScene(tableViewContractScene);
                        } catch (IOException ex) {
                            Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                }

            });
        };

        setText(null);
        setGraphic(gridPane);
    }

    
}
