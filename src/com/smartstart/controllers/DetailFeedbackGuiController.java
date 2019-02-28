/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import com.smartstart.entities.Feedback;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dytcha
 */
public class DetailFeedbackGuiController implements Initializable {

    @FXML
    private Label type;
    @FXML
    private Button CancelButton;
    @FXML
    private Label id_rating;
    @FXML
    private Label id_app;
    @FXML
    private Label id_user;
    @FXML
    private Label id_added_date;
    @FXML
    private Label id_comment;

    /**
     * Initializes the controller class.
     */
   @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }
    /*  @FXML
  public void AfficherDetails(Feedback f )
    { 
                id_app.setText(""+f.getId_application());
                id_user.setText(""+f.getId_user());

        id_added_date.setText(f.getAdded_date().toString());
       
        id_comment.setText(f.getComment());
        id_rating.setText(""+f.getRating());
    }
*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
}
