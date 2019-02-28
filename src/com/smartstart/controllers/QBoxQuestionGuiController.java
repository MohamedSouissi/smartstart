package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.smartstart.entities.Question;
import com.smartstart.entities.UserSession;
import java.io.File;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class QBoxQuestionGuiController extends ListCell<Question> {

    @FXML
    private JFXButton question;
    @FXML
    private JFXButton modify;
    @FXML
    private HBox boxligne;
    @FXML
    private AnchorPane plan;
    @FXML
    private Label labelSubject;
    @FXML
    private Label username;
    @FXML
    private Label description;
    @FXML
    private Label views;
    @FXML
    private Label date;
    @FXML
    private ImageView img;

    private FXMLLoader mLLoader;
    // final Tooltip tooltip = new Tooltip();
    @FXML
    AnchorPane gridPane;
   
    private Question quest;

    /**
     * Initializes the controller class.
     *
     * @param q
     */
    @Override
    protected void updateItem(Question q, boolean empty) {
        super.updateItem(q, empty);

        if (empty || q == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/QBoxQuestionGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            quest = q;
            username.setText(q.getUser().getUsername());
            question.setText(q.getQuestion());
            labelSubject.setText(q.getSubject());
            description.setText(q.getDescription());
            views.setText(String.valueOf(UserSession.getViewService().CountViewedQuestions(q)));
            date.setText(q.getDate().toString());

            
            
           
            
            
            if (q.getAnswered() != null) {
                File file = new File("/Users/mohamed/NetBeansProjects10/dip_3A3/src/images/ok.jpeg");
        Image image1 = new Image(file.toURI().toString());
                
                img.setImage(image1);
            }

            question.setOnAction(e -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/smartstart/gui/QuestionsRepliesGui.fxml"));
                try {
                    loader.load();
                } catch (IOException io) {
                    AlertBox.displayError("QBoxQuestion", "can't load thi file: /com/smartstart/gui/QuestionsRepliesGui.fxml");
                }
                QuestionsRepliesController qr = loader.getController();
                qr.displayQuestion(quest);
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
            });

       
            setText(null);
            setGraphic(gridPane);
        }

    }

    /*  public void showQuestion(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/com/smartstart/gui/QuestionsRepliesGui.fxml"));
    loader.load();
    QuestionsRepliesController qr = loader.getController();
    qr.displayQuestion(quest);
    Parent p = loader.getRoot();
    Stage stage = new Stage();
    stage.setScene(new Scene(p));
    stage.showAndWait();
    }*/
   

    
}
