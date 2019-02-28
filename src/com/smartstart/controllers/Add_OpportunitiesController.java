/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Opportunity;
import com.smartstart.entities.Skill;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import com.smartstart.services.SkillService;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;



/**
 * FXML Controller class
 *
 * @author acmou
 */
public class Add_OpportunitiesController implements Initializable {
    @FXML
    private AnchorPane parent;
    @FXML
    private Label type;
    @FXML
    private Button Save_and_exit;
    @FXML
    private Button Add_Opportunity;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField job_title;
    @FXML
    private TextField Job_category;
    @FXML
    private TextArea job_description;
    @FXML
    private DatePicker Expiry_Date;
    @FXML
    private TextField Budget;
    @FXML
    private TextField Needed_skills;
    @FXML
    private TextField Needed_skills1;
    @FXML
    private TextField Needed_skills2;
    fos_user u=new fos_user();
    
    
    String[] possibleSuggestions={"SYMFONY","JAVA","PHP","PHOTOSHOP","ILLUSTRATOR","HTML","CSS","JAVASCRIPT","ANGULAR","C","C++","C#"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        TextFields.bindAutoCompletion(Needed_skills, possibleSuggestions);
        TextFields.bindAutoCompletion(Needed_skills1, possibleSuggestions);
        TextFields.bindAutoCompletion(Needed_skills2, possibleSuggestions);
       
 
        
        // TODO
    }
    @FXML
     public void annuler()
    {  Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
        FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
            Parent root2;
        try {
            root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();
       
        } catch (IOException ex) {
            Logger.getLogger(Add_OpportunitiesController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    @FXML
    public void Add_OpportunityToDb() throws SQLException
    {
        int test=0;
        for (String possibleSuggestion : possibleSuggestions) {
            if (possibleSuggestion.equals(Needed_skills.getText())) {
                test=1;
                System.out.println(test);
            }
        }
    {
        
    }if(!Budget.getText().trim().matches("[0-9]+"))
            {
                alert1("Must be Flooat !!");
                
              
                return;
            }
        if(job_title.getText().isEmpty())
            {
                alert1("Job Title Is Empty !!");
                
              
                return;
            }
        if(Job_category.getText().isEmpty())
            {
                alert1("Job Category Is Empty !!");
                return;
            }
        if(job_description.getText().isEmpty())
            {
                alert1("Job Description Is Empty !!");
                return;
            }
        if(Budget.getText().isEmpty())
            {
                alert1("Budget Is Empty !!");
                return;
            }
         if(Expiry_Date.getValue().isBefore(LocalDate.now()))
            {
                alert1("La date d'expiration ne peut pas etre dans le passé !!");
                return;
            }
            if(Expiry_Date.getValue().equals(""))
            {
                alert1("La date d'expiration ne peut pas etre dans le passé !!");
                return;
            }
        Date date = java.sql.Date.valueOf(Expiry_Date.getValue());
       
  

        Opportunity O=new Opportunity(job_title.getText(),Job_category.getText().toString(),job_description.getText(),Float.parseFloat(Budget.getText()),0,date,1);
    
        
      
        if(test==1)
        {
              OpportunityService Os=new OpportunityService();
              try {
                   u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Os.create_Opportunity(O, u.getId());
        Opportunity O1=new Opportunity();
        O1=Os.LastOpportunity();
        System.out.println(O1);
        Skill s1=new Skill();
        Skill s2=new Skill();
        Skill s3=new Skill();
        SkillService s=new SkillService();
         if(Needed_skills.getText().equals(Needed_skills1.getText()))
            {
                alert1("Pas de meme skill deux fois!!");
                return;
            }
         if(Needed_skills.getText().equals(Needed_skills2.getText()))
            {
                alert1("Pas de meme skill deux fois!!");
                return;
            }
         if(Needed_skills.getText().equals(Needed_skills2.getText()))
            {
                alert1("Pas de meme skill deux fois !!");
                return;
            }
        if(Needed_skills.getText().isEmpty())
            {
                alert1("At least one skill is needed !!");
                return;
            }
        if(!Needed_skills1.getText().isEmpty())
            {
                 s2=s.findSkill(Needed_skills1.getText());
                 s.addNeededSkill(O1.getId_Opp(),s2.getId());
            }
        if(!Needed_skills2.getText().isEmpty())
            {
                s3=s.findSkill(Needed_skills2.getText());
                 s.addNeededSkill(O1.getId_Opp(),s3.getId());
            }
       s1=s.findSkill(Needed_skills.getText());
    
       
       s.addNeededSkill(O1.getId_Opp(),s1.getId());
        annuler();}
        else
        {
             alert1("le skill n'existe pas!!");
                return;
        }
        
    }
    @FXML
    public void SaveOpportunityAndExit()
    {try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(job_title.getText().isEmpty())
            {
                alert1("Job Title Is Empty !!");
                return;
            }
        if(Job_category.getText().isEmpty())
            {
                alert1("Job Category Is Empty !!");
                return;
            }
        if(job_description.getText().isEmpty())
            {
                alert1("Job Description Is Empty !!");
                return;
            }
        if(Budget.getText().isEmpty())
            {
                alert1("Budget Is Empty !!");
                return;
            }
         if(Expiry_Date.getValue().isBefore(LocalDate.now()))
            {
                alert1("La date d'expiration ne peut pas etre dans le passé !!");
                return;
            }
        Date date = java.sql.Date.valueOf(Expiry_Date.getValue());

        Opportunity O=new Opportunity(job_title.getText(),Job_category.getText().toString(),job_description.getText(),Float.parseFloat(Budget.getText()),0,date,1);
        OpportunityService Os=new OpportunityService();
        Opportunity O1=new Opportunity();
        Os.create_Opportunitydraft(O,u.getId());
        annuler();
    }
    private void alert1(String Message)
    {
        Alert a1=new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Champ Vide");
        a1.setContentText(Message);
        a1.showAndWait();
                
    }
     public static void showExceptionDialog(Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);

    alert.setTitle("Exception Dialog");
    alert.setHeaderText("An error occurred:");

    String content = "Error: ";
    if (null != e) {
        content += e.toString() + "\n\n";
    }

    alert.setContentText(content);

    Exception ex = new Exception(e);

    //Create expandable Exception.
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);

    String exceptionText = sw.toString();

    //Set up TextArea
    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);


    textArea.setPrefHeight(600);
    textArea.setPrefWidth(800);


    //Set expandable Exception into the dialog pane.
    alert.getDialogPane().setExpandableContent(textArea);


    alert.showAndWait();
}
     
   
    
    
}
