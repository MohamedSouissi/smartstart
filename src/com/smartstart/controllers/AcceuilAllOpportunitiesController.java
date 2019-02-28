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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.management.Notification;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class AcceuilAllOpportunitiesController implements Initializable {

    @FXML
    private ListView<Opportunity> List;
    private ObservableList<Opportunity> data = FXCollections.observableArrayList();
    final Tooltip tooltip = new Tooltip();

    /**
     * Initializes the controller class.
     */
    Date d = new Date(0);
    fos_user u = new fos_user();
    @FXML
    private Label type;
    @FXML
    private Label username;
    @FXML
    private Button add;
    @FXML
    private ImageView pic1;
    @FXML
    private Button myApplications;
    @FXML
    private Button suggestions;
    @FXML
    private Button apply;
    @FXML
    private Button feedback;
    @FXML
    private TextField searchedInput;
    @FXML
    private Button search;
    @FXML
    private Button resetFilter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            OpportunityService S = new OpportunityService();
            data = S.Display_Opportunity();
            List.setItems(data);
            
            try {
                username.setText(currentuser().getUsername());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AcceuilAllOpportunitiesController.class.getName()).log(Level.SEVERE, null, ex);
            }
            List.setCellFactory(OpportunityListView -> new ListOpportunitiesCell());
            
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();
            
            // TODO
        } catch (IOException ex) {
            Logger.getLogger(AcceuilAllOpportunitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void apply(ActionEvent event) throws SQLException {
        try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (List.getSelectionModel().getSelectedItem() == null) {
            notifSelect();
            return;
        } else {
            //HERE
       
    
    
            ApplicationService p1 = new ApplicationService();
            if (p1.HasApplied(u.getId(), List.getSelectionModel().getSelectedItem().getId_Opp()) == false) {
                if (p1.isApt(u.getId(), List.getSelectionModel().getSelectedItem().getId_Opp())) {
                    Application app = new Application(List.getSelectionModel().getSelectedItem(),u);
                    p1.create_application(app);

                    p1.sendAppliedToUser(u.getEmail(), List.getSelectionModel().getSelectedItem().getJob_title());
                    notifSent();

                } else {
                    notifNotApt();
                }

            } else {
                notifAlreadyApplied();
            }

        }
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
    private void suggestionns(ActionEvent event) throws IOException {
        Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ShowSuggestedOpps.fxml"));
        Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewOpportunityScene);
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
      public void logoutbutton(ActionEvent event) throws ClassNotFoundException, IOException
      { fos_user u=new fos_user();
      u=UserSession.getInstance(new fos_user()).getUser();
            System.out.println(u.getName());
                 u=UserSession.getInstance(new fos_user()).getUser();
            System.out.println(u.getName());
            UserSession.SetInstance();
           
                
                
                 Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Login.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
                
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
      public void chatroom(ActionEvent event) throws IOException
      {
          
         
           FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ChatGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();
          
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
         @FXML
      public void showcontract(ActionEvent event) throws IOException
      {
          
        
         FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ContractForFreelancerGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
          
      }
        
         @FXML
    private void launchSearch(ActionEvent event) {
      String searched =searchedInput.getText();
      
     
     ApplicationService x = new ApplicationService();
        data = x.searchOpps(searched);
        List.setItems(data);

        try {
            username.setText(currentuser().getUsername());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AcceuilAllOpportunitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List.setCellFactory(OpportunityListView -> new ListOpportunitiesCell());
      
    }

    @FXML
    private void backtoDefault(ActionEvent event) {
     OpportunityService S = new OpportunityService();
        data = S.Display_Opportunity();
        List.setItems(data);

        try {
            username.setText(currentuser().getUsername());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AcceuilAllOpportunitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List.setCellFactory(OpportunityListView -> new ListOpportunitiesCell());
    
    }
    
        
}
