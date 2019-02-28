/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Application;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import static com.smartstart.entities.UserSession.logout;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ApplicationService;
import com.smartstart.services.OpportunityService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class ShowSuggestionsController implements Initializable {
    @FXML
    private ListView<Opportunity> List;
    private ObservableList<Opportunity> data=FXCollections.observableArrayList();
     final Tooltip tooltip=new Tooltip();

    /**
     * Initializes the controller class.
     */
     
     
     Date d=new Date(0);
    fos_user f=new fos_user(1, "username", "username_canonical", "email", "email_canonical", 0, "salt", "password", d, "confirmation_token", d, "roles", "name", "last_name", d, "bio", "location", d, 0, 0, 0, "field_company");
    @FXML
    private Label type;
    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private Button add;
    @FXML
    private ImageView pic1;
    @FXML
    private Button myApplications;
    @FXML
    private Button Drafts;
    @FXML
    private Button apply;
    fos_user u=new fos_user();
     @FXML
    private Button feedback;
    
    
    
   @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          
        ApplicationService S=new ApplicationService();
        try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        data=S.showSuggestedOpps(u.getId());
        List.setItems(data);
        
        List.setCellFactory(OpportunityListView -> new ListOpportunitiesCell());
       
        
        // TODO
    }
   
        
   
    
    
    
    
    @FXML
    public void apply(ActionEvent event) throws SQLException {
        if (List.getSelectionModel().getSelectedItem() == null) {
            notifSelect();
            return;
        } else {
           //HERE
            ApplicationService p1=new ApplicationService();
        if (p1.HasApplied(1,List.getSelectionModel().getSelectedItem().getId_Opp())==false)
        {
        if (p1.isApt(1,List.getSelectionModel().getSelectedItem().getId_Opp()))
        { 
        Application app=new Application(List.getSelectionModel().getSelectedItem(),f); 
        p1.create_application(app);
        
       
         p1.sendAppliedToUser("marouenedakhlaoui@gmail.com",List.getSelectionModel().getSelectedItem().getJob_title());
          notifSent();
        }
        
        else 
         notifNotApt();
        
         
    
    
    } else notifAlreadyApplied();

        }
    }
 public void notifSent() {
       

        Notifications notificationBuilder = Notifications.create()
                .title("SUCESS!")
                .text("YOUR APPLICATION HAS BEEN SENT,CHECK YOUR E-MAIL")
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

    public void notifNotApt() {
    
        Notifications notificationBuilder = Notifications.create()
                .title("DENIED!")
                .text("You don't have the required skills to apply for this opportunity")
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

    public void notifAlreadyApplied() {
        Notifications notificationBuilder = Notifications.create()
                .title("DENIED!")
                .text("You have already applied for this opportunity")
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

  
   
    @FXML
    public void ShowApplications(ActionEvent event) {
        try {
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ListFreelancerApps.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    private void browseAllOpps(ActionEvent event) throws IOException {
    
    Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilAllOpportunities.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    
    
    }
    
      @FXML
      public void logoutbutton() throws ClassNotFoundException
      { fos_user u=new fos_user();
      u=UserSession.getInstance(new fos_user()).getUser();
            System.out.println(u.getName());
                 logout();
                 System.out.println(u.getName());
      }

    @FXML
    private void settings(ActionEvent event) throws IOException {
        Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Freelancerconnectedsettings.fxml"));
            System.out.println("Settings");
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         System.out.println("Settings");
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         System.out.println("Settings");
         window.setScene(tableViewOpportunityScene);
    }
    
        public fos_user currentuser() throws ClassNotFoundException
    {
        fos_user u = new fos_user();
         u =UserSession.getInstance(new fos_user()).getUser();
         return u ;
        
    }
  
     @FXML
      public void GotoComplaints(ActionEvent event) throws IOException
      {
          
          FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ComplaintsGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
          
      }
         @FXML
    private void feedback(ActionEvent event) throws IOException {
         Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/FeedbackListGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    }
     @FXML
      public void showq(ActionEvent event) throws IOException
      {
          
          
         FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/QuestionGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
          
      }
}
