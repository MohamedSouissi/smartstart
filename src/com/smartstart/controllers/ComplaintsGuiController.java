/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.AcceuilOpportunitiesController.PlayNotification;
import static com.smartstart.controllers.HomeOppController.mute;
import com.smartstart.entities.Complaint;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ComplaintsServiceImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dytcha
 */
public class ComplaintsGuiController implements Initializable {

    @FXML
    private AnchorPane recherche;
    @FXML
    private ListView<Complaint> List;
    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private ImageView pic1;
    @FXML
    private TextField rechercheF;
    @FXML
    private Text count;
    @FXML
    private Button CancelButton;
    ObservableList<Complaint> data;
    public fos_user u;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          ComplaintsServiceImpl S =new ComplaintsServiceImpl();
          try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            
            data=S.listerComplaint(u.getId());
             List.setItems(data);
        List.setCellFactory(complaintsbackListView -> new ComplaintCellGuiController());
        
       // initFilter();
        int i = S.CountComplaint(u.getId());
        count.setText(String.valueOf(i));
        
        
            rechercheF.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                ObservableList<Complaint> tmp = FXCollections.observableArrayList();
                for (Complaint comlaint : data) {
                    if(comlaint.getContent().toLowerCase().contains(newValue.toLowerCase())){
                        tmp.add(comlaint);
                    }
                }
                List.setItems(null);

                count.setText(String.valueOf(tmp.stream().count()));
                List.setItems(tmp);
                                    

                
            }});
        
        
        
        
        } catch (SQLException ex) {
        }
    }    

    @FXML
    private void ShowAddF(ActionEvent event) throws IOException {
         Parent tableViewComplaint=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
         Scene tableViewFeedbackScene=new Scene (tableViewComplaint);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewFeedbackScene);
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
    public void Add_an_opportunity(ActionEvent event) {
        try {

            Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Add_OpportunityGui.fxml"));
            Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
          

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
            stage1.setOnCloseRequest(e->Platform.exit());
            
            stage1.show();
           
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    public void Show_Draft(ActionEvent event) {
        try {
            Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ShowDraftGui.fxml"));
            Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
           

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    public void Show_Home(ActionEvent event) {
        try {
            Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/HomeOpportunities.fxml"));
            Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
            window.setOnCloseRequest(e->Platform.exit());
            
            

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
     @FXML
    private void annuler(ActionEvent event) throws IOException {
         Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    
}
