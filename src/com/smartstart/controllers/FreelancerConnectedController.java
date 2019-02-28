/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static java.awt.SystemColor.window;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.smartstart.entities.fos_user;
import com.smartstart.controllers.LoginController;
import com.smartstart.services.fos_userService;
import java.security.KeyStore;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.smartstart.entities.fos_user;

import com.smartstart.entities.UserSession;
import static com.smartstart.entities.UserSession.logout;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
/**
 * FXML Controller class
 *
 * @author HP
 */
public class FreelancerConnectedController implements Initializable {

    @FXML
    private Button btnOverview;
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
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private VBox pnItems;
    @FXML
    public Label username;
    @FXML
    private ImageView Profile_pic;
    @FXML
    private ImageView pic1;
    @FXML
    private Pane pnlSetting;
    
    private fos_user selected_user;
    @FXML
    private Label USERNAM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        // TODO
        
        System.out.println("connectedINTERFACE");
       Stage window = new Stage();
       fos_user u = new fos_user();
        try {
            u =UserSession.getInstance(new fos_user()).getUser();
            System.out.println(u.getUsername());
            username.setText(u.getUsername());
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FreelancerConnectedController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        
       
        
    }
public void inidata(fos_user u) 
{
    username.setText(u.getUsername());
    USERNAM.setText("AAAAAAAAA");
 
}

    public fos_user currentuser() throws SQLException
    {
        LoginController lc = null ;
        fos_userService fss = new fos_userService() ;
        String selected_username = lc.username.getText();
  
        return fss.get_user_by_username(selected_username);
    }
    
    @FXML
    private void handleClicks(ActionEvent event) throws IOException {
       if (event.getSource() == btnOverview)
       {
           pnlOverview.toFront(); 
           pnlOverview.setStyle("-fx-background-color : #02030A");
           
       }
        if (event.getSource() == btnSettings)
        {
            System.out.println("Settings");
           
           
             try {
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Freelancerconnectedsettings.fxml"));
            System.out.println("Settings");
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         System.out.println("Settings");
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         System.out.println("Settings");
         window.setScene(tableViewOpportunityScene);
          System.out.println(UserSession.getInstance(selected_user));
                 logout();
                 System.out.println(UserSession.getInstance(selected_user));
      //   PlayNotification ("home.mp3");

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
             
        
       //  window.setScene(tableViewOpportunityScene);
        
        }
                if (event.getSource() == btnCustomers)
        {
            pnlCustomer.toFront();
        }
                if (event.getSource() == btnSignout)
                {
                      Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Login.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
                }
    }

    private void Settings(ActionEvent event) throws IOException {
        System.out.println("Settings");
         
    }
    
}
