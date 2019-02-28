/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.ListAppFreelancerCell.app_feedback;
import com.smartstart.entities.Application;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ApplicationService;
import com.smartstart.services.OpportunityService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class ListApplicationForFreelancer implements Initializable {
    @FXML
    private ListView<Application> list;
    private ObservableList<Application> data=FXCollections.observableArrayList();
    @FXML
    private Button CancelButton;
    fos_user u=new fos_user();
    @FXML
    private Label type;
    @FXML
    private Button withdraw;
    @FXML
    private Button delete;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ApplicationService as = new ApplicationService();
        OpportunityService os=new OpportunityService();
        try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        data = as.getApplicationsByFreelancerId(u.getId());
        list.setItems(data);
        
        list.setCellFactory(OpportunityListView -> new ListAppFreelancerCell());
    }
   
    public void reload()
    {
         ApplicationService as = new ApplicationService();
        OpportunityService os=new OpportunityService();
       
        data = as.getApplicationsByFreelancerId(1);
        list.setItems(data);
        
        list.setCellFactory(OpportunityListView -> new ListAppFreelancerCell());
       
        
    }
     @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

     @FXML
    private void withdraw(ActionEvent event) {

        if (list.getSelectionModel().getSelectedItem() == null) {
            notifSelect();
            return;
        } else {
        

            ApplicationService p1 = new ApplicationService();
            if (p1.CanWithdraw(list.getSelectionModel().getSelectedItem().getState()) == true) {
                p1.update_application(list.getSelectionModel().getSelectedItem().getId(), "WITHDRAWN");
                notifWithdrawn();

            } else {
                notiftooLate();
            }

        }

    }

    public void notiftooLate() {
        Notifications notificationBuilder = Notifications.create()
                .title("DENIED!")
                .text("There has already been a decision concerning your applicaiton")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Click on notifications");
                    }
                });

        notificationBuilder.showError();

    }

    public void notifSelect() {

        Notifications notificationBuilder = Notifications.create()
                .title("ERROR")
                .text("PLEASE SELECT AN OPPORTUNITY")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Click on notifications");
                    }
                });

        notificationBuilder.showConfirm();

    }

    public void notifWithdrawn() {

        Notifications notificationBuilder = Notifications.create()
                .title("SUCESS!")
                .text("You have withdrawn from this opportunity")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Click on notifications");
                    }
                });

        notificationBuilder.showConfirm();

    }
    
     public void notifDeleted() {

        Notifications notificationBuilder = Notifications.create()
                .title("SUCESS!")
                .text("You have deleted this application")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Click on notifications");
                    }
                });

        notificationBuilder.showConfirm();

    }

    @FXML
    private void delete(ActionEvent event) {
     if (list.getSelectionModel().getSelectedItem() == null) {
            notifSelect();
            return;
        } else {
        

            ApplicationService p1 = new ApplicationService();
            if (list.getSelectionModel().getSelectedItem().getState().equals("WITHDRAWN")) {
                p1.delete_Application(list.getSelectionModel().getSelectedItem().getId());
                notifDeleted();

            } else {
                notiftooLate();
            }

        }
    
    }
    
}
