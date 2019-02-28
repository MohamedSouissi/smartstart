/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.smartstart.entities.Feedback;
import com.smartstart.services.FeedbackServiceImpl;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class FeedbackCellGuiController extends ListCell<Feedback>{

    @FXML
    private Label titles;
    @FXML
    private Label description;
    @FXML
    private Label username;
    private TextField sum;
    private Label application_id;
    @FXML
    private Label contract_id;
    private FXMLLoader mLLoader;
    final Tooltip tooltip=new Tooltip();
    @FXML
    private AnchorPane gridPane;
    @FXML
    private Button update;
    @FXML
    private Button remove;
    @FXML
    private Label date;
    @FXML
    private Rating rating;
    @FXML
    private Text application;
    @FXML
    private Text user;
    @FXML
    private TextArea comment;
    
          

    
   @Override
    protected void updateItem(Feedback f, boolean empty) {
        super.updateItem(f, empty);

        if(empty || f == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/FeedbackCellGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
           

            rating.setRating(f.getRating());
            date.setText(new SimpleDateFormat("EEEEEE-MM-yyyy").format(f.getAdded_date()).toString());
            
            comment.setText(f.getComment());
            
            application.setText(String.valueOf(f.getApplication().getOpportunity().getJob_title()));
            user.setText(String.valueOf(f.getUser().getUsername()));
            tooltip.setText(f.getApplication().getOpportunity().getJob_description());
            titles.setTooltip(tooltip);
            
            
            remove.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    
                    
                                        if( alertConfirmation2() == true){

                    FeedbackServiceImpl cs = new FeedbackServiceImpl();
                    try {
                        cs.removeFeedback(f.getId_feedback());
                    } catch (SQLException ex) {
                        Logger.getLogger(FeedbackCellGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Parent tableViewFeedback;
                    try {
                        tableViewFeedback = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/FeedbackListGui.fxml"));
                        Scene tableViewFeedbackScene=new Scene (tableViewFeedback);
                        Stage window=(Stage)((Node)t.getSource()).getScene().getWindow();
                        window.setScene(tableViewFeedbackScene);
                    } catch (IOException ex) {
                        Logger.getLogger(FeedbackCellGuiController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                     
           
                }}
            });
            
           update.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    if( alertConfirmation() == true)
                    {
                    FeedbackServiceImpl cs = new FeedbackServiceImpl();
                    try {
                        f.setRating((float)rating.getRating());
                        f.setComment(comment.getText());
                        cs.updateFeedback(f);

                    } catch (SQLException ex) {
                    }
                   
                }}
            }); 
            
      
       /*   extraire.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    
                    try {
                        
                   
                    Document document = new Document();                        
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Mehdi.pdf"));
                        document.open();
                        
                      
                        document.add(new Paragraph("DETAIL FEEDBACK"));
                        
                        Paragraph p1=new Paragraph(comment.getText());
                        document.add(p1);
                        
                     
                        document.close();
                    } catch (DocumentException ex) {
                    } catch (FileNotFoundException ex) {
                    } catch (IOException ex) {
                    }
                }
          

         
        });*/
        }
            setText(null);
            setGraphic(gridPane);
        
    }
    private boolean alertConfirmation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION D'UPDATION");
        a1.setHeaderText("CONFIRMATION DE MODIFICATION");
        a1.setContentText("VOUS ETES SUR POUR LA MODIFICATION DE CE FEEDBACK ?");
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
        a1.setContentText("VOUS ETES SUR POUR LA SUPPRESSION DE CE FEEDBACK ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    }

       
    

