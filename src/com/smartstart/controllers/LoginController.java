/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.services.fos_userService;
import com.smartstart.util.ConnectionDb;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.smartstart.entities.fos_user;
import com.smartstart.entities.UserSession;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class LoginController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView imgProgress;
    @FXML
    public TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Label isconnected;
    @FXML
    private Hyperlink Newaccount;
    
    public fos_user selected_user = new fos_user();
    @FXML
    private Hyperlink pass_forgot;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

     @FXML
    private void login(ActionEvent event) throws SQLException, IOException, InterruptedException, ClassNotFoundException  {
          ConnectionDb db = ConnectionDb.getInstance();
                Connection cn = db.getCnx();
                PreparedStatement ps=null;
             
          fos_userService fss = new fos_userService();
         if (fss.Authentification(username.getText(), password.getText()))
         {
             System.out.println("EY");
             isconnected.setText("connecté");
         
         
       
            if (UserSession.getInstance(fss.get_user_by_username(username.getText())).getUser().getRoles().equals("entreprise")){
                  Parent Opp=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
            Scene oppScene=new Scene (Opp);
            Stage window1=(Stage)((Node)event.getSource()).getScene().getWindow();
            window1.setScene(oppScene);
            }
            
            if (UserSession.getInstance(fss.get_user_by_username(username.getText())).getUser().getRoles().equals("freelancer")){
                  Parent Opp=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilAllOpportunities.fxml"));
            Scene oppScene=new Scene (Opp);
            Stage window1=(Stage)((Node)event.getSource()).getScene().getWindow();
            window1.setScene(oppScene);
            }
             
         }
         else isconnected.setText("introuvable");
    }

    
   
    @FXML
    private void newaccount(ActionEvent event) throws IOException {
                 Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Freelancer_RegisterGUI.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    }

    @FXML
    private void forgot(ActionEvent event) throws IOException {
        Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/forgot.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    }
    
}
