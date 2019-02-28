/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.smartstart.entities.Question;
import com.smartstart.entities.UserSession;
import com.teknikindustries.bulksms.SMS;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class QAskController implements Initializable {

    @FXML
    private JFXTextField question;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXComboBox<String> subject;
    @FXML
    private JFXComboBox<String> to;
    @FXML
    private Label date;
    @FXML
    private JFXButton post;
    @FXML
    private StackPane pane;
    @FXML
    private JFXCheckBox qsms;
    @FXML
    private JFXCheckBox qmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //================= needed for sms integration ==================== 
        
    Map<String, String> suggestMap = UserSession.getQApiServices().getMap();
        if (suggestMap.containsKey(UserSession.getFosUser().getUsername_canonical())) {
            suggestMap.remove(UserSession.getFosUser().getUsername_canonical());
        }

        to.setItems(FXCollections.observableArrayList(suggestMap.keySet()));
        //=================end of needs for sms integration ===============    

        pane.setOpacity(0);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(pane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        date.setText("posted: " + LocalDate.now().toString());
        subject.setItems(FXCollections.observableArrayList(UserSession.getQuestionService().getsubjects()));
       
        post.setOnAction(e -> {
            if (!question.getText().equals("")) {
                if (!UserSession.getQuestionService().isFoundQuestion(question.getText(), description.getText(), subject.getValue())) {
                    UserSession.getQuestionService().askAQuestion(new Question(UserSession.getFosUser(), question.getText(), description.getText(), subject.getValue()));
                    Stage currentStage = (Stage) pane.getScene().getWindow();
                    currentStage.close();
                    //=====================    sms AND mail integration   =========================        

                    if (!to.getSelectionModel().getSelectedItem().isEmpty()) {
                        if (qsms.isSelected()) {
                   UserSession.getQApiServices().sendSMS(question.getText(),suggestMap.get(to.getValue()));
                        }
                        if (qmail.isSelected()) {
                    UserSession.getQApiServices().sendQuestionToUser(to.getValue(),question.getText());
                        }
                    }

                    //=====================    fin sms AND mail integration =================================       
                }
            } else {
                question.setStyle("-fx-background-color: red");
            }

        });

        cancel.setOnAction(e -> {
            Stage currentStage = (Stage) pane.getScene().getWindow();
            currentStage.close();
        });

    }

   

}
