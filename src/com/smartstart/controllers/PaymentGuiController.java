/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.smartstart.entities.Contract;
import com.smartstart.services.PaymentServiceImpl;
import com.stripe.model.Order;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class PaymentGuiController implements Initializable {

    @FXML
    private Button add;
    @FXML
    private Button CancelButton;
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
        sum.setText(String.valueOf(ContractCellController.c.getSum()));
        
    }

    @FXML
    private void payer(ActionEvent event) {
        String regex = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(card.getText());
        if (!matcher.matches()) {
            alert("PLS Check Your Card Number");
        }
        String regex1 = "`^[0-9]{3}$`";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern.matcher(code.getText());
        if (/*!matcher1.matches()*/code.getText().isEmpty()) {
            alert("PLS Check Your Code");
        } 
else {
            Order order = new Order();
            long l = Long.valueOf((long) ContractCellController.c.getSum()*100);
            System.out.println(l);
            order.setAmount(l);
            PaymentServiceImpl ps = new PaymentServiceImpl();
            ps.chargeCreditCard(order);
            System.out.println("yes");
            try {
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ContractCellController.c.getApplication().getOpportunity().getJob_title() + ".pdf"));
                document.open();

                document.add(new Paragraph("You payed a sum of " + sum.getText() + "for " + ContractCellController.c.getApplication().getFreelancer().getName() + " " + ContractCellController.c.getApplication().getFreelancer().getLast_name()));
                document.close();
                alert1("You Will Find payment Receipt In a PDF File Which name is " + ContractCellController.c.getApplication().getOpportunity().getJob_title() + ".pdf");
                Parent tableViewContract;
                try {
                    tableViewContract = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
                    Scene tableViewContractScene = new Scene(tableViewContract);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(tableViewContractScene);
                } catch (IOException ex) {
                    Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (DocumentException ex) {
                Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    

    private void alert(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Not Valid");
        a1.setContentText(Message);
        a1.showAndWait();

    }

    private void alert1(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Validation");
        a1.setHeaderText("Make Sure That This Action Is Full Secure");
        a1.setContentText(Message);
        a1.showAndWait();

    }
    @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
