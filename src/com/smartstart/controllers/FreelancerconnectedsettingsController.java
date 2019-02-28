/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.fos_userService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class FreelancerconnectedsettingsController implements Initializable {

    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label USERNAM;
    @FXML
    private Label username;
    @FXML
    private Button btnOverview;
    @FXML
    private ImageView pic1;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlsettings;
    @FXML
    private TextField confirmationcode;
    @FXML
    private Label checkverified;
   // fos_user u = new fos_user();
    @FXML
    private Button confirm;
    @FXML
    private Label ctrlsais;
    @FXML
    private TextField newusername;
    @FXML
    private Label type;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         
      
        System.out.println("connectedINTERFACE");
       Stage window = new Stage();
       fos_user u = new fos_user();
       fos_userService fss = new fos_userService();
       
        try {
            u =UserSession.getInstance(new fos_user()).getUser();
            System.out.println(currentuser().getUsername());
            username.setText(currentuser().getUsername());            
           
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FreelancerConnectedController.class.getName()).log(Level.SEVERE, null, ex);
        }
        USERNAM.setText(u.getEmail());
        if (fss.checkverif(u) == false)
        {
            checkverified.setText("Not verified");
            checkverified.setTextFill(Color.web("#FF0000"));
        }
            if (fss.checkverif(u) == true)
        {
            checkverified.setText("verified");
            checkverified.setTextFill(Color.web("#00FF00"));
            confirmationcode.setVisible(false);
            confirm.setVisible(false);
            u.setEnabled(1);
                    
        }
    }    

    public fos_user currentuser() throws ClassNotFoundException
    {
        fos_user u = new fos_user();
         u =UserSession.getInstance(new fos_user()).getUser();
         return u ;
        
    }
    @FXML
    private void handleClicks(ActionEvent event) throws IOException, ClassNotFoundException {
        if (event.getSource() == btnSignout)
        {
              if (event.getSource() == btnSignout)
                {
         Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Login.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          fos_userService fss = new fos_userService();
          fos_user u=new fos_user();
         UserSession.SetInstance();
          
       
         
                }
        }
    }
   

    @FXML
    private void confirmation(ActionEvent event) {
        
    }
private void alert2(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Champ Vide");
        a1.setContentText(Message);
        a1.showAndWait();
    }
    @FXML
    private void confirm(ActionEvent event) throws ClassNotFoundException, SQLException {
        fos_userService fss = new fos_userService();
        fos_user u = new fos_user();
        u = UserSession.getInstance(new fos_user()).getUser();
         Alert a ;
        if (confirmationcode.getText().isEmpty())
        {
            alert2("Veuillez ecrire votre code de confirmation");
            ctrlsais.setText("Ecrire votre code");
        }
        if (confirmationcode.getText().isEmpty() == false)
        {
            
              
            fss.confirmAccount(u, u.getConfirmation_token());
            ctrlsais.setText("account verified");
             System.out.println("confirmed");
             alert2("c'est bon");
        }
        
      
        
       
        
                
    }

    @FXML
    private void allOpps(ActionEvent event) throws IOException {
        Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilAllOpportunities.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    
    }

    @FXML
    private void myApps(ActionEvent event) throws IOException {
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
    private void suggestions(ActionEvent event) throws IOException {
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ShowSuggestedOpps.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    
    
    }
    
}
