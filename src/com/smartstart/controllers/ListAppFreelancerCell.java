/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.ListOpportunitiesCell.opp_complaint;
import com.smartstart.entities.Application;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class ListAppFreelancerCell extends ListCell<Application>{

 @FXML
    private Label job_title;
    @FXML
    private Label job_category;
    @FXML
    private Label State;
    
    @FXML
    private Label startDate;  
    @FXML
    private Label endDate;  
    @FXML
    private Button feedback;
    @FXML
    AnchorPane gridPane;
    private FXMLLoader mLLoader;
    private ObservableList<fos_user> data=FXCollections.observableArrayList();
         public static Application app_feedback;

    
   
     final Tooltip tooltip=new Tooltip();

    @Override
    protected void updateItem(Application student, boolean empty) {
        super.updateItem(student, empty);

        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ListAppFreelancerCellGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            OpportunityService O=new OpportunityService();
            fos_user u=O.getUserByIdOpp(student.getFreelancer().getId());
            
           
            
            job_title.setText(student.getOpportunity().getJob_title());
            job_category.setText(student.getOpportunity().getJob_category());
            State.setText(student.getState());
            startDate.setText(String.valueOf(student.getOpportunity().getAdded_date()));
            endDate.setText(String.valueOf(student.getOpportunity().getExpiry_date()));;

          

          feedback.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    AddFeedbackGuiController.a = student;
                    Parent tableViewComplaint=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AddFeedbackGui.fxml"));
                    Scene tableViewFeedbackScene=new Scene (tableViewComplaint);
                    Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(tableViewFeedbackScene);
                } catch (IOException ex) {
                    Logger.getLogger(ListOpportunitiesCell.class.getName()).log(Level.SEVERE, null, ex);
                }
}
        });

            setText(null);
            setGraphic(gridPane);
        }
 
    }
    
  
}

