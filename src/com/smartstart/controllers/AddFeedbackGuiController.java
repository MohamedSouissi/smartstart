/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.Add_OpportunitiesController.showExceptionDialog;
import com.smartstart.entities.Application;
import com.smartstart.entities.Feedback;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ApplicationService;
import com.smartstart.services.FeedbackServiceImpl;
import com.smartstart.services.OpportunityService;
import com.smartstart.services.fos_userService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author dytcha
 */
public class AddFeedbackGuiController implements Initializable {

    private Rating rating;
    private TextField comment;
    @FXML
    private Button add;
    @FXML
    private Button CancelButton;
    private fos_user u;
    public static Application a;
    @FXML
    private TextField code;
    @FXML
    private TextField card;
    @FXML
    private Label sum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void AddFeedback(ActionEvent event) throws SQLException, ClassNotFoundException {

        u = UserSession.getInstance(new fos_user()).getUser();

        Feedback f = new Feedback();
        FeedbackServiceImpl fs = new FeedbackServiceImpl();

        f.setApplication(a);
        f.setUser(u);
        f.setComment(comment.getText());
        f.setRating((float) rating.getRating());
        if (fs.Test(u.getId(), a.getId())) {
            alertConfirmation3();
        } else {
            fs.addFeedback(f);
            Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
        }

    }

    @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean alertConfirmation3() {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle("ERREUR D'AJOUT ");
        a1.setHeaderText("AJOUT FEEDBACK IMPOSSIBLE");
        a1.setContentText("VOUS AVEZ DEJA FAIT UN FEEDBACK SUR CETTE APPLICATION");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    @FXML
    private void payer(ActionEvent event) {
    }

}
