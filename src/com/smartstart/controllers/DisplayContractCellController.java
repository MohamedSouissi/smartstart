/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import com.smartstart.services.ContractServiceImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class DisplayContractCellController extends ListCell<Contract> {

    @FXML
    private AnchorPane gridPane;
    @FXML
    private Label description;
    @FXML
    private Label username;
    @FXML
    private Label titles;
    @FXML
    private Button update;
    @FXML
    private Button export;
    @FXML
    private TextField sum;
    @FXML
    private TextField start;
    @FXML
    private TextField finish;
     @FXML
    private ChoiceBox<String> payment;
    private FXMLLoader mLLoader;
    final Tooltip tooltip = new Tooltip();

    
    @Override
    protected void updateItem(Contract student, boolean empty) {
        super.updateItem(student, empty);

        if (empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/DisplayContractCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            description.setText(student.getApplication().getOpportunity().getJob_title());
            username.setText(student.getUser().getUsername());
            tooltip.setText("PLS NOTE THAT ALL THE FIELDS ARE REQUIRED");
            titles.setTooltip(tooltip);
            payment.getItems().addAll("en ligne","cheque","cash");
            payment.setValue("en ligne");
            

            /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date firstDate;
            try {
                firstDate = sdf.parse(student.getStart_date().toString());
                Date secondDate = sdf.parse(student.getFinish_date().toString());
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                
            } catch (ParseException ex) {
                Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            update.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    
            try {
                payment.getSelectionModel().getSelectedItem().isEmpty();
                Date firstDate = sdf.parse(start.getText());
                Date secondDate = sdf.parse(finish.getText());
                if(sum.getText().isEmpty()){alert("Sum is required & must be valid");}
                    else if(secondDate.before(firstDate)){alert("Start Date must be before Finish Date");}
                    else{
                        if (alertConfirmation() == true) {
                        try {
                            Contract c = new Contract();
                            c.setId_contract(student.getId_contract());
                            c.setFinish_date(secondDate);
                            c.setStart_date(firstDate);
                            c.setPayment_method(payment.getValue());
                            c.getApplication().setId(student.getApplication().getId());
                            c.setPrio(1);
                            c.setSum(Float.valueOf(sum.getText()));
                            ContractServiceImpl cs = new ContractServiceImpl();
                            cs.updateContract(c);

                        } catch (SQLException ex) {
                            Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Parent tableViewContract;
                        try {
                            tableViewContract = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
                            Scene tableViewContractScene = new Scene(tableViewContract);
                            Stage window = (Stage) ((Node) t.getSource()).getScene().getWindow();
                            window.setScene(tableViewContractScene);
                        } catch (IOException ex) {
                            Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                    }
                    
                    }
                
            } catch (ParseException ex) {
                alert("PLS Check That the format of Start Date & Finish Date is like yyyy-MM-dd");
            }
                }

            });
            
        };

        setText(null);
        setGraphic(gridPane);
    }

    private void alert(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Not Valid");
        a1.setContentText(Message);
        a1.showAndWait();

    }

    private boolean alertConfirmation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION DIALOG");
        a1.setHeaderText("UPDATE CONFIRMATION");
        a1.setContentText("ARE YOU SURE THAT YOU WANT TO DO THAT ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
    
}
