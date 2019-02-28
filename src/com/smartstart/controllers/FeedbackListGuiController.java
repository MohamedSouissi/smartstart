/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Feedback;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.FeedbackServiceImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author dytcha
 */
public class FeedbackListGuiController implements Initializable {

    @FXML
    private ListView<Feedback> List;
    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private ImageView pic1;
    ObservableList<Feedback> data;
    @FXML
    private AnchorPane recherche;
    @FXML
    private TextField rechercheF;
    @FXML
    private Text count;
    @FXML
    private Button addF;
      @FXML
    private Button myApplications;
    @FXML
    private Button suggestions;
    
    @FXML
    private Button feedback;
    @FXML
    private Button logout;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FeedbackServiceImpl S = new FeedbackServiceImpl();
        try {
            data=S.listerFeedback(1);
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackListGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        List.setItems(data);
        List.setCellFactory(FeedbackListView -> new FeedbackCellGuiController());
        int i = S.CountFeedbacks(1);
        count.setText(String.valueOf(i));
        rechercheF.textProperty().addListener(new ChangeListener<String>() {
           
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                ObservableList<Feedback> tmp = FXCollections.observableArrayList();
                for (Feedback feedback : data) {
                    if (feedback.getComment().toLowerCase().contains(newValue.toLowerCase())) {
                        tmp.add(feedback);
                    }
                }

                count.setText(String.valueOf(tmp.stream().count()));
                List.setItems(tmp);

            }
        });

    }

    @FXML
    public void ShowAddF(ActionEvent event) throws IOException {

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
    private void browseAllOpps(ActionEvent event) throws IOException {
    
    Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilAllOpportunities.fxml"));
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
