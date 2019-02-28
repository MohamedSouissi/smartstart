package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.smartstart.entities.Question;
import com.smartstart.entities.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class QModifyController implements Initializable {

    @FXML
    private JFXTextField question;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton modify;
    @FXML
    private JFXComboBox<String> subject;
    @FXML
    private JFXTextField to;
    @FXML
    private JFXButton delete;
    @FXML
    private JFXButton post;
    @FXML
    private Label views;
    @FXML
    private JFXCheckBox answer;
    @FXML
    private Label date;

    private Question quest;
    @FXML
    private StackPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void displayQuestion(Question q) {
        quest = q;
        question.setText(q.getQuestion());
        description.setText(q.getDescription());
        date.setText("posted: "+quest.getDate().toString());
        if (q.getAnswered() != null) {
            answer.setSelected(true);
            answer.setDisable(false);
        }
        views.setText("viewed= " + UserSession.getViewService().CountViewedQuestions(quest));
        subject.setItems(FXCollections.observableArrayList(UserSession.getQuestionService().getsubjects()));
        subject.setValue(q.getSubject());

        // crud of question :
        delete.setOnAction(e -> {
            UserSession.getQuestionService().deleteQuetion(quest);
             Stage currentStage = (Stage) pane.getScene().getWindow();
           currentStage.close();
        });

        modify.setOnAction(e -> {
            if (q.getReponses().isEmpty()) {
                q.setQuestion(question.getText());
                q.setDescription(description.getText());
                q.setSubject(subject.getValue());
                UserSession.getQuestionService().updateQuestion(q);
            Stage currentStage = (Stage) pane.getScene().getWindow();
           currentStage.close();
        
            } else 
            if(quest.getAnswered()!=null){
  AlertBox.displayError("No need for modification", "this question has been answered !");
           
            }else
            {
                AlertBox.displayError("impossible modification", "this question has replies, you can't change it !");
            }
        });

        post.setOnAction(e -> {
          makeFadeOut();
        });
        
          cancel.setOnAction(e-> {
        Stage currentStage = (Stage) pane.getScene().getWindow();
           currentStage.close();
        });
    
        
    }

    public void modifingQuestion(Question q) {
        question.setText(q.getQuestion());
        description.setText(q.getDescription());
        subject.setItems(FXCollections.observableArrayList(UserSession.getQuestionService().getsubjects()));
        subject.setValue(q.getSubject());
    }
    
    private void makeFadeOut(){
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(700));
        fade.setNode(pane);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setOnFinished(e -> { try{
            Parent p=  FXMLLoader.load(getClass().getResource("/com/smartstart/gui/QAsk.fxml"));
           Scene newscene = new Scene(p);
            Stage currentStage = (Stage) pane.getScene().getWindow();
            currentStage.setScene(newscene);
           }catch(IOException io){ io.printStackTrace(); }
            
        });
        fade.play();
    }

}
