package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.smartstart.entities.Reply;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class MyResponsesGuiController extends ListCell<Reply> {

    @FXML
    private JFXButton question;
    @FXML
    private Label subject;
    @FXML
    private Label qdate;
    @FXML
    private Label reply;
    @FXML
    private Label votes;
    @FXML
    private Label views;
    @FXML
    private Label rdate;

    private FXMLLoader mLLoader;
    // final Tooltip tooltip = new Tooltip();

    private Reply reponse;
    @FXML
    private AnchorPane plan;
    
    @Override
    protected void updateItem(Reply r, boolean empty) {
        super.updateItem(r, empty);

        if (empty || r == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/MyResponsesGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            question.setText(r.getQuestion().getQuestion());
            subject.setText(r.getQuestion().getSubject());
            reply.setText(r.getContent());
           rdate.setText(r.getDate().toString());
            votes.setText(String.valueOf(r.getLikes() - r.getDislikes()));
            reponse = r;
            qdate.setText(r.getQuestion().getDate().toString());
            rdate.setText(r.getDate().toString());
            setText(null);
            setGraphic(plan); 
        
         question.setOnAction(e -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/smartstart/gui/QuestionsRepliesGui.fxml"));
                try {
                    loader.load();
                } catch (IOException io) {
                    AlertBox.displayError("QBoxQuestion", "can't load thi file: /com/smartstart/gui/QuestionsRepliesGui.fxml");
                }
                QuestionsRepliesController qr = loader.getController();
                qr.displayQuestion(r.getQuestion());
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.showAndWait();
            });
    }
    }
}
