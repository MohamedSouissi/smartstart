/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartstart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author diabl
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
       
         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
      @FXML
    private void ChangeScene(ActionEvent event) throws IOException {
        
        
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
            Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
            Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
        

            
        
         


    
         
    }
    @FXML
    private void ChangeScene1(ActionEvent event) throws IOException {
         Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    }
    @FXML
     private void ChangeScene2(ActionEvent event) throws IOException {
         Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/DisplayAllOpportunitiesGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    }
     @FXML
     private void ChangeScenemansouri(ActionEvent event) throws IOException {
         Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/FeedbackGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    }

    @FXML
    private void user(ActionEvent event) throws IOException {
         Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Login.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    
    }
     @FXML
    private void gestionuser(ActionEvent event) throws IOException {
         Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/DisplayUsers.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
    
    }
    
}
