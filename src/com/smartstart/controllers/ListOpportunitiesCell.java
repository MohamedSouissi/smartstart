/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    @FXML
    private Button DeleteOpp;
    @FXML
    private Button complaint;
    @FXML
    private ImageView image;
     final Tooltip tooltip=new Tooltip();
     public static Opportunity opp_complaint;
     fos_user u;

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
           
try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
 if(u.getRoles().equals("freelancer"))
            {File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\atom.gif");
        Image image1 = new Image(file.toURI().toString());
                
                image.setImage(image1);
            }
            titles.setText(String.valueOf(student.getJob_title()));
            category.setText(student.getJob_category());
            description.setText(student.getJob_description());
            Expiry.setText(String.valueOf(student.getExpiry_date()));
            budget.setText(""+student.getBudget());
            Expiry1.setText(String.valueOf(student.getAdded_date()));
            tooltip.setText("Duration :"+student.getJob_Duration());
        titles.setTooltip(tooltip);
        complaint.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                   
                    opp_complaint=student;
                     FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/AddComplaintGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
                   
                } catch (IOException ex) {
                    Logger.getLogger(ListOpportunitiesCell.class.getName()).log(Level.SEVERE, null, ex);
                }
}
        });
        

         

            setGraphic(gridPane);
        }
        
        
    }

    }
    






