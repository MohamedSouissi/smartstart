/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Opportunity;
import com.smartstart.services.OpportunityService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class ListOpportunitiesCell extends ListCell<Opportunity>{

 @FXML
    private Label titles;
    @FXML
    private Label category;
    @FXML
    private Label description;
    @FXML
    private Label Expiry;
    private FXMLLoader mLLoader;
    @FXML
    AnchorPane gridPane;
    @FXML
    private Label budget;
    @FXML
    private Label Expiry1;
     final Tooltip tooltip=new Tooltip();

    @Override
    protected void updateItem(Opportunity student, boolean empty) {
        super.updateItem(student, empty);

        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ListOpportunitiesCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
           

            titles.setText(String.valueOf(student.getJob_title()));
            category.setText(student.getJob_category());
            description.setText(student.getJob_description());
            Expiry.setText(String.valueOf(student.getExpiry_date()));
            budget.setText(""+student.getBudget());
            Expiry1.setText(String.valueOf(student.getAdded_date()));
            tooltip.setText("Duration :"+student.getJob_Duration());
        titles.setTooltip(tooltip);

         

            setText(null);
            setGraphic(gridPane);
        }

    }
}

