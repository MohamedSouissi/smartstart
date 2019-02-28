/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.ListOpportunitiesCell.opp_complaint;
import com.smartstart.entities.Complaint;
import com.smartstart.entities.Feedback;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ComplaintsServiceImpl;
import com.smartstart.services.FeedbackServiceImpl;
import com.smartstart.services.OpportunityService;
import com.smartstart.services.fos_userService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author dytcha
 */
public class AddComplaintGuiController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private Label type;
    @FXML
    private Button add;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField content;
    @FXML
    private TextField mail;
    @FXML
    private ChoiceBox<String> subject;
    private fos_user u;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        subject.getItems().clear();
        subject.getItems().addAll("Incorrect descriptions of the product", "Faulty Opportunity", "VERBAL ABUSE", "FAKE ENTREPRISE");

        // TODO
    }

    @FXML
    private void AddComplaint(ActionEvent event) throws SQLException, IOException {
        try {
            u = UserSession.getInstance(new fos_user()).getUser();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Opportunity o = opp_complaint;

        Complaint f = new Complaint();
        ComplaintsServiceImpl fs = new ComplaintsServiceImpl();
        f.setOpportunity(o);

        f.setUser(u);
        
        
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
 
        Pattern pattern = Pattern.compile(regex);
 
        Matcher matcher = pattern.matcher(mail.getText());
        if(!matcher.matches()){
        alertConfirmation4();
        }
        
        else
        {f.setMail_user(mail.getText());}
        f.setContent(content.getText());
        f.setSubject(subject.getValue());

        fs.addComplaint(f);
        annuler(event);
    }

    @FXML
    private void annuler(ActionEvent event) throws IOException {
         Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    
    private boolean alertConfirmation4() {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle("ERREUR D'AJOUT");
        a1.setHeaderText("AJOUT IMPOSSIBLE");
        a1.setContentText("VEUILLER VERIFIER VOTRE EMAIL");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
}
