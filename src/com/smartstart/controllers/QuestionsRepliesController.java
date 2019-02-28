package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.smartstart.entities.Question;
import com.smartstart.entities.Reply;
import com.smartstart.entities.UserSession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class QuestionsRepliesController implements Initializable {

    @FXML
    private JFXTextArea content;
    @FXML
    private JFXListView<Reply> listreply;
    @FXML
    private Label rows;

    private Question q;
    @FXML
    private AnchorPane replyAnchor;
    @FXML
    private Label description;
    @FXML
    private Label question;
    @FXML
    private Label views;
    @FXML
    private JFXButton reply;
    @FXML
    private JFXButton cancel;
    
    ObservableList<Reply> obss;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       }    
    
    
    @FXML
    public void makeReply(){
        if(UserSession.getReplyService().isFound(content.getText())){
            AlertBox.displayError("Reply Found", "this reply is copied please make a new reply, thanks");
        }
        else{
            Reply r = new Reply(content.getText(), q, UserSession.getFosUser());
            content.setText("");
           UserSession.getReplyService().replyQuestion(r);
        }
        reload();
    }
    public void cancelAction(){
        
    }

    void displayQuestion(Question quest) {
        q=quest;
        reload();
        listreply.setCellFactory(l -> new QBoxReplyGuiController());
        rows.setText("replies count: "+listreply.getItems().stream().count());
        question.setText(q.getQuestion());
        description.setText(q.getDescription());
        UserSession.getViewService().addViewQuestion(UserSession.getFosUser(), q);      
        views.setText(String.valueOf(UserSession.getViewService().CountViewedQuestions(q)));
       
        cancel.setOnAction(e-> {
        Stage currentStage = (Stage) replyAnchor.getScene().getWindow();
           currentStage.close();
        });
    }
    
private void reload(){
    obss = FXCollections.observableArrayList(UserSession.getReplyService()
            .getRepliesQuestion(q));
        listreply.setItems(obss);
        rows.setText(String.valueOf(listreply.getItems().stream().count()));
        }
    
}
