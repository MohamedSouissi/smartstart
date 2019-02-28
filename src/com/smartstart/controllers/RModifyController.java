/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import static com.smartstart.controllers.QuestionCellsController.rep;
import com.smartstart.entities.Reply;
import com.smartstart.entities.UserSession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class RModifyController implements Initializable {

    @FXML
    private TextArea reply;
    @FXML
    private JFXButton modify;
    @FXML
    private AnchorPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         System.out.println(rep);
        
        reply.setText(rep.getContent());
        
       modify.setOnAction(e -> {
            if (!reply.getText().isEmpty()) {
                rep.setContent(reply.getText());
                UserSession.getReplyService().updateReply(rep);
                
            Stage currentStage = (Stage) pane.getScene().getWindow();
           currentStage.close();
        
            }else
            {
                AlertBox.displayError("impossible modification", "this reply is empty !");
            }
        }); 
    }    
   
    
}
