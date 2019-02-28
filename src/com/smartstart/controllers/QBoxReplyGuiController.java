package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.smartstart.entities.Reply;
import com.smartstart.entities.UserSession;
import de.jensd.fx.glyphs.control.GlyphCheckBox;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class QBoxReplyGuiController extends ListCell<Reply> {

    @FXML
    private HBox boxligne;
    @FXML
    private AnchorPane plan;
    @FXML
    private Label vote;
    @FXML
    private Label reply;
    @FXML
    private JFXButton replier;
    @FXML
    private Label date;
    @FXML
    private JFXButton up;
    @FXML
    private JFXButton down;

    private FXMLLoader mLLoader;
    // final Tooltip tooltip = new Tooltip();

    private Reply reponse;
    @FXML
    private GlyphCheckBox theAnswer;

    @Override
    protected void updateItem(Reply r, boolean empty) {
        super.updateItem(r, empty);

        if (empty || r == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/QBoxReplyGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            reply.setText(r.getContent());
            replier.setText(r.getUser().getUsername());
            date.setText(r.getDate().toString());
            vote.setText(String.valueOf(r.getLikes() - r.getDislikes()));
            reponse = r;
            setText(null);
            setGraphic(plan);
            
            up.setOnAction(e->{ 
        UserSession.getReplyService().SetLike(reponse);
         //this.getListView().refresh();
        vote.setText(UserSession.getReplyService().countVotes(reponse));
            });
            
            down.setOnAction(e->{ 
        UserSession.getReplyService().SetDisLike(reponse);
       // this.getListView().refresh();
        vote.setText(UserSession.getReplyService().countVotes(reponse));
            });
            
            theAnswer.setOnAction(e-> {
                if(reponse.getQuestion().getAnswered()==null)
            UserSession.getQuestionService().anwserQuestion(reponse.getQuestion());
            });
        }

    }

}
