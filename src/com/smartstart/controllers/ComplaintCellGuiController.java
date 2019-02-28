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
import com.smartstart.entities.Complaint;
import com.smartstart.entities.Feedback;
import com.smartstart.services.ComplaintsServiceImpl;
import com.smartstart.services.FeedbackServiceImpl;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author dytcha
 */
public class ComplaintCellGuiController extends ListCell<Complaint> {

    @FXML
    private AnchorPane gridPane;
    @FXML
    private Label description;
    @FXML
    private Label username;
    @FXML
    private Label date;
    @FXML
    private Label contract_id;
    @FXML
    private Label titles;
    @FXML
    private Button update;
    @FXML
    private Button remove;
    @FXML
    private Text opportunity;
    @FXML
    private Text user;
    @FXML
    private TextArea content;
    @FXML
    private TextField mail;
    @FXML
    private ChoiceBox<String> subject;
    @FXML
    private Button extraire;
        private FXMLLoader mLLoader;


    /**
     * Initializes the controller class.
     */
    protected void updateItem(Complaint f, boolean empty) {
        super.updateItem(f, empty);

        if(empty || f == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ComplaintCellGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mail.setText(f.getMail_user());
            //*************************************************************************************************
            //yajouti subject koll matetrafrecha l page ,plusieurs fois **********************************
            subject.getItems().clear();
            subject.getItems().addAll("Incorrect descriptions of the product", "Faulty Opportunity", "VERBAL ABUSE","FAKE ENTREPRISE");
            subject.getSelectionModel().select(f.getSubject());
            date.setText(new SimpleDateFormat("EEEEEE-MM-yyyy").format(f.getAdded_date()).toString());
            content.setText(f.getContent());
            opportunity.setText(f.getOpportunity().getJob_title());
            user.setText(f.getUser().getUsername());
            
            //tooltip.setText("Duration of Feedback :"+duration);
            //titles.setTooltip(tooltip);
            
            
            remove.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    
                    
                                        if( alertConfirmation2() == true){

                    ComplaintsServiceImpl cs = new ComplaintsServiceImpl();
                    try {
                        cs.removeComplaint(f.getId_complaint());
                    } catch (SQLException ex) {
                        Logger.getLogger(FeedbackCellGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Parent tableViewComplaint;
                    try {
                        tableViewComplaint = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ComplaintsGui.fxml"));
                        Scene tableViewFeedbackScene=new Scene (tableViewComplaint);
                        Stage window=(Stage)((Node)t.getSource()).getScene().getWindow();
                        window.setScene(tableViewFeedbackScene);
                    } catch (IOException ex) {
                        Logger.getLogger(FeedbackCellGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     
     
                   
                }}
            });
            
            
            
            
            extraire.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                                                            if( alertConfirmation4() == true){

                    try {
                        
                   
                    Document document = new Document();                        
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("ComplaintDetailsPDF.pdf"));
                        document.open();
                        
                      
                        document.add(new Paragraph("********************************DETAILS COMPLAINT********************************"));
                        
                       Paragraph p11=new Paragraph("**********NOM D'UTILISATEUR :**********");
                        document.add(p11);
                        Paragraph p1=new Paragraph(f.getUser().getUsername());
                        document.add(p1);
                        
                        Paragraph p22=new Paragraph("**********NOM OPPORTUNITÉ:**********");
                        document.add(p22);
                        Paragraph p2=new Paragraph(f.getOpportunity().getJob_title());
                        document.add(p2);
                        
                        Paragraph p33=new Paragraph("**********MAIL UTILISATEUR :**********");
                        document.add(p33);
                        Paragraph p3=new Paragraph(f.getMail_user());
                        document.add(p3);
                        
                        Paragraph p44=new Paragraph("**********SUJET DE LA RECLAMATION :**********");
                        document.add(p44);
                        Paragraph p4=new Paragraph(f.getSubject());
                        document.add(p4);
                        
                        Paragraph p55=new Paragraph("**********CONTENU DE LA RECLAMATION :**********");
                        document.add(p55);
                        Paragraph p5=new Paragraph(f.getContent());
                        document.add(p5);
                        
                        Paragraph p66=new Paragraph("**********DATE DE L'AJOUT DE LA RECLAMATION :**********");
                        document.add(p66);
                        Paragraph p6=new Paragraph(f.getAdded_date().toString());
                        document.add(p6);       
                        document.close();
                    } catch (DocumentException ex) {
                    } catch (FileNotFoundException ex) {
                    } catch (IOException ex) {
                    }
                }
          

                }
        });
            
        
           update.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
        // if(ChronoUnit.DAYS.between(f.getAdded_date().toLocalDate(), LocalDate.now()).getDays){         
   if(f.getAdded_date().toLocalDate().isBefore(LocalDate.now())){
                            alertConfirmation3();
                    } 
                    
                   else if( alertConfirmation() == true)
                    {
                    ComplaintsServiceImpl cs = new ComplaintsServiceImpl();
                   
                    try {
                        f.setMail_user(mail.getText());
                        f.setSubject(subject.getValue());
                        f.setContent(content.getText());
                        cs.updateComplaint(f);
                        System.out.println(f.getId_complaint());
                    } catch (SQLException ex) {
                    }
                   
                }}
            }); 
      
        };
            setText(null);
            setGraphic(gridPane);
        }
    
    private boolean alertConfirmation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION D'UPDATION");
        a1.setHeaderText("CONFIRMATION DE MODIFICATION");
        a1.setContentText("VOUS ETES SUR POUR LA MODIFICATION DE CETTE RECLAMATION ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
    
     private boolean alertConfirmation2() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION DE SUPPRESSION");
        a1.setHeaderText("CONFIRMATION DE SUPPRESSION");
        a1.setContentText("VOUS ETES SUR POUR LA SUPPRESSION DE CETTE RECLAMATION?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
     private boolean alertConfirmation3() {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle("ERREUR DE MODIFICATION");
        a1.setHeaderText("MODIFICATION IMPOSSIBLE");
        a1.setContentText("VOUS AVEZ DEPASSER LE DELAI DE MODIFICATION -1 Day ago-)");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
    
     private boolean alertConfirmation4() {
        Alert a1 = new Alert(Alert.AlertType.INFORMATION);
        a1.setTitle("SUCCES DE CONVERSION TO PDF");
        a1.setHeaderText("SUCCES DE CONVERSION TO PDF");
        a1.setContentText("VEUILLEZ CONSULTEZ VOTRE FICHIER PDF QUI A ÉTÉ CRÉE DANS VOTRE DOSSIER");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
}
