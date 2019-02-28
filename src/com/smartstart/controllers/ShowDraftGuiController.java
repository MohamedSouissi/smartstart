/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class ShowDraftGuiController implements Initializable {
    @FXML
    private Label type;
    @FXML
    private Button CancelButton;
    @FXML
    private TableView<Opportunity> table;
    @FXML
    private TableColumn<Opportunity, Integer> colid_opportunity;
    @FXML
    private TableColumn<Opportunity, String> coljob_title;
    @FXML
    private TableColumn<Opportunity, String> coljob_category;
    @FXML
    private TableColumn<Opportunity, String> coljob_description;
    @FXML
    private TableColumn<Opportunity, Float> colbudget;
    @FXML
    private TableColumn<Opportunity, String> colDuration;
    @FXML
    private TableColumn<Opportunity, Date> colExpiry_Date;
    @FXML
    private Label Nombre_Opp;
    
    @FXML
    private Button Delete;
    @FXML
    private Button Add;
    private ObservableList<Opportunity> data;
     OpportunityService as = new OpportunityService();
     fos_user u=new fos_user();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }

        data = as.DisplayMy_OpportunitiesDrafts(u.getId());

       colid_opportunity.setCellValueFactory(new PropertyValueFactory<>("id_Opp"));
        colid_opportunity.setVisible(false);
        coljob_title.setCellValueFactory(new PropertyValueFactory<>("job_title"));

        coljob_category.setCellValueFactory(new PropertyValueFactory<>("job_category"));

        coljob_description.setCellValueFactory(new PropertyValueFactory<>("job_description"));

        colbudget.setCellValueFactory(new PropertyValueFactory<>("Budget"));

        colDuration.setCellValueFactory(new PropertyValueFactory<>("job_Duration"));

        colExpiry_Date.setCellValueFactory(new PropertyValueFactory<>("Expiry_date"));

      

        coljob_title.setSortType(TableColumn.SortType.DESCENDING);
        coljob_category.setSortType(TableColumn.SortType.DESCENDING);
        coljob_description.setSortType(TableColumn.SortType.DESCENDING);
        colbudget.setSortType(TableColumn.SortType.DESCENDING);
        colDuration.setSortType(TableColumn.SortType.DESCENDING);
        colExpiry_Date.setSortType(TableColumn.SortType.DESCENDING);
       

        System.out.println(data);

        table.setItems(data);
        try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
      int draftcount=as.CountDraft(u.getId());
      Nombre_Opp.setText(""+draftcount);
    }    

    @FXML
    private void annuler() {
        
        Stage stage = (Stage) CancelButton.getScene().getWindow();
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
    private void DeleteDraft(ActionEvent event) {
        if(table.getSelectionModel().getSelectedItem()==null)
        {
            alert1("PLEASE SELECT THE DRAFT THAT YOU WANT TO DELETE");
            return;
        }
        else{
       if(alert1Confirmation())
       {
        int id_opp = 0;
        ObservableList<Opportunity> AllOp, SingleOp;
        AllOp = table.getItems();
        SingleOp = table.getSelectionModel().getSelectedItems();
        OpportunityService s = new OpportunityService();
        table.getSelectionModel().getSelectedItem();
        System.out.println("Value is in this row which is selected" + table.getSelectionModel().getSelectedItem().getId_Opp());
        s.delete_opporunity(table.getSelectionModel().getSelectedItem().getId_Opp());
        SingleOp.forEach(AllOp::remove);
        int draftcount=as.CountDraft(u.getId());
      Nombre_Opp.setText(""+draftcount);
       }
       else
           return;

    }}

    @FXML
    private void AddToOpp(ActionEvent event) {
       /* Date date = java.sql.Date.valueOf(table.getSelectionModel().getSelectedItem().getExpiry_date().toLocalDate());

        Opportunity O=new Opportunity(table.getSelectionModel().getSelectedItem().getJob_title(),table.getSelectionModel().getSelectedItem().getJob_category(),table.getSelectionModel().getSelectedItem().getJob_description(),table.getSelectionModel().getSelectedItem().getBudget(),0,date,1);
        OpportunityService Os=new OpportunityService();
        Os.create_Opportunity(O, 1);*/
         if(table.getSelectionModel().getSelectedItem()==null)
        {
            alert1("PLEASE SELECT THE DRAFT THAT YOU WANT TO ADD !");
            return;
        }
         else{try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        OpportunityService Os=new OpportunityService();
        Os.SetDraftTo_Opp(table.getSelectionModel().getSelectedItem());
        annuler();
         }
        
    }
     private boolean alert1Confirmation()
    {
        Alert a1=new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION DIALOG");
        a1.setHeaderText("SUPPRESSION CONFIRMATION");
        a1.setContentText("ARE YOU SURE THAT YOU WANT TO DELETE THIS DRAFT?");
        Optional<ButtonType> result=a1.showAndWait();
        if(result.get()==ButtonType.OK)
            return true;
        else
            return false;
                
    }
       @FXML
    private void alert1(String Message)
    {
        Alert a1=new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Champ Vide");
        a1.setContentText(Message);
        a1.showAndWait();
                
    }
    
}
