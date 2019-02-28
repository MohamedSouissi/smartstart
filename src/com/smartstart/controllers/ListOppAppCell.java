/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Application;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class ListOppAppCell extends ListCell<Application> {

    @FXML
    private Label job_title;
    @FXML
    private Label job_category;
    @FXML
    private Label State;
    @FXML
    private Label username;
    @FXML
    private Label email;

    @FXML
    AnchorPane gridPane;
    private FXMLLoader mLLoader;
    private ObservableList<fos_user> data = FXCollections.observableArrayList();

    final Tooltip tooltip = new Tooltip();

    @Override
    protected void updateItem(Application student, boolean empty) {
        super.updateItem(student, empty);

        if (empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ListOppAppCellGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            OpportunityService O = new OpportunityService();
            fos_user u = O.getUserByIdOpp(student.getFreelancer().getId());
            username.setText(u.getUsername());
            email.setText(u.getEmail());

            job_title.setText(student.getOpportunity().getJob_title());
            job_category.setText(student.getOpportunity().getJob_category());
            State.setText(student.getState());

            setText(null);
            setGraphic(gridPane);
        }

    }
}
