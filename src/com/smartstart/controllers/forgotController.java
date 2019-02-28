/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;
import com.smartstart.entities.fos_user;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import com.smartstart.services.fos_userService;
import java.sql.SQLException;
/**
 * FXML Controller class
 *
 * @author HP
 */
public class forgotController implements Initializable {

    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView imgProgress;
    @FXML
    private Label sent;
    @FXML
    private Hyperlink backtologin;
    @FXML
    private Hyperlink Newaccount;
    @FXML
    private TextField email;
    @FXML
    private Button sendmail;
    @FXML
    private TextField username;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    



    @FXML
    private void newaccount(ActionEvent event) {
    }

    @FXML
    private void sendmailpasswd(ActionEvent event) throws SQLException {
        fos_userService fss = new fos_userService();
        String newpass = fss.random_code(5);
        
         fos_user us = fss.get_user_by_username(username.getText()) ;
        
        
        fss.sendmail_password(email.getText(),newpass);
        fss.update_passwordviamail(email.getText(), newpass);
        System.out.println("password reset");
        System.out.println("mail s");
        
    }

    @FXML
    private void username(ActionEvent event) {
    }

    @FXML
    private void back(ActionEvent event) {
        
    }
    
}
