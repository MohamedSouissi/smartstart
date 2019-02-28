/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.AcceuilOpportunitiesController.PlayNotification;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import static com.smartstart.entities.UserSession.logout;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class HomeOppController implements Initializable {
   
    @FXML
     private ListView<Opportunity> List;
    @FXML
    private ObservableList<Opportunity> data=FXCollections.observableArrayList();
     public static int mute=0;
     @FXML
     private Label applicationnbr;
    int f;
      @FXML
    private ImageView muteicon;
      fos_user u=new fos_user();
      

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      if (mute == 0) {
            System.out.println("bij");
            System.out.println(mute);

            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\Unmute.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
           
        }
        if (mute == 1) {

            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\mutepng.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
           
        }
        
       
load();  
        

       
    }    

    @FXML
    public void Add_an_opportunity(ActionEvent event) {
        try {
            
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Add_OpportunityGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
         if(mute==0)
                {
                     PlayNotification ("add-an-opportunity.mp3");
                }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    public void ShowApplications(ActionEvent event) {
        try {
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ListAppOpportunities.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();
            if(mute==0)
                {
                       PlayNotification ("show-applications.mp3");
                }
          

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    public void Show_Draft(ActionEvent event) {
        try {
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ShowDraftGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
         if(mute==0)
                {
                      PlayNotification ("show-your-drafts.mp3");
                }
        
         

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    
            @FXML
    public void ShowMy(ActionEvent event) {
        try {
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
          
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    
         window.setScene(tableViewOpportunityScene);
          //Pour faire disparaitre la barre de fermeture basique de Windows
          if(mute==0)
                {
                       PlayNotification ("myopp.mp3");
                }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    @FXML
    private void load()
    { 
         OpportunityService S=new OpportunityService();
        data=S.Display_Opportunity();
        System.out.println("bijor");
        List.setItems(data);
                System.out.println("bijor2");

        
        List.setCellFactory(OpportunityListView -> new HomeOppCell());
    }
    @FXML
    public void mute() {
        System.out.println("bij");
        System.out.println(mute);
        if (mute == 0) {
            System.out.println("bij");
            System.out.println(mute);
            mute = 1;
            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\mutepng.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
            return;
        }
        if (mute == 1) {
            mute = 0;
            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\Unmute.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
            return;
        }

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
      public void GotoComplaints(ActionEvent event) throws IOException
      {
          
           FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ComplaintsGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
         
          
      }
      @FXML
      public void showcontract(ActionEvent event) throws IOException
      {
           Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
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
      @FXML
      public void chatroom(ActionEvent event) throws IOException
      {
          
         
           FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ChatGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();
          
      }
     
        
   
    
}
