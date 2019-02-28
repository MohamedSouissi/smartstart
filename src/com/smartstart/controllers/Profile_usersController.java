/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Opportunity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import com.smartstart.services.fos_userService;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.smartstart.util.ConnectionDb;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import com.smartstart.controllers.FreelancerConnectedController;
/**
 * FXML Controller class
 *
 * @author HP
 */
public class Profile_usersController implements Initializable {

    
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField txtField;
    public TableView<fos_user> table= new TableView<>();
   // public TableView<fos_user> tableuser= new TableView<>();
    @FXML
    private TableColumn<fos_user, Integer> colid_user;
    @FXML
    private TableColumn<fos_user, String> colUsername_username;
    @FXML
    private TableColumn<fos_user, String> colEmail_email;
    @FXML
    private TableColumn<fos_user, String> colrole_role;
    
    private ObservableList<fos_user> data;
    @FXML
    private Button deleteuser;
    @FXML
    private Button Add_Opportunity;
    @FXML
    private Button Show_My_Draft;
    @FXML
    private Button reload;
    @FXML
    private Button reload1;
    @FXML
    private Button change;
    
    public fos_userService fs ;
    @FXML
    private Label Nombre_users;
    @FXML
    private Button show_users;
    @FXML
    private TableView<fos_user> tableuser;
    @FXML
    private Button displayuser;
    @FXML
    private TableColumn<fos_user, String> colName_user1;

    /**
     * Initializes the controller class.
     */
        private void alert1(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Champ Vide");
        a1.setContentText(Message);
        a1.showAndWait();
    }
           private boolean alert1Confirmation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION DIALOG");
        a1.setHeaderText("SUPPRESSION CONFIRMATION");
        a1.setContentText("ARE YOU SURE THAT YOU WANT TO DELETE THIS USER?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
           
           fos_userService fs = new fos_userService();
           int i = fs.CountUsers();
           Nombre_users.setText(""+i+"");
                colid_load();
            
        } catch (SQLException ex) {
            Logger.getLogger(Profile_usersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void colid_load() throws SQLException
    {
        fos_userService fs = new fos_userService();
        int i = fs.CountUsers();
        Nombre_users.setText(""+i+"");
        data = fs.read_all_users();
        
        colid_user.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        colrole_role.setCellValueFactory(new PropertyValueFactory<>("roles"));
        colName_user1.setCellValueFactory(new PropertyValueFactory<>("name"));
System.out.println(data);
 tableuser.setItems(data);
         tableuser.setEditable(true);
        
    }

  
//detail a revoir 
    
    @FXML
    public void displayDetails(ActionEvent event) {
         if (tableuser.getSelectionModel().getSelectedItem() == null) {
            alert1("PLEASE SELECT THE USER THAT YOU WANT TO DISPLAY");
            return;
        } else {
        try {
            
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/User_details.fxml"));
            Parent root1 = (Parent) detail.load();
            Stage stage = new Stage();
            User_detailsController p = detail.getController();

            System.out.println(tableuser.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root1));
            stage.show();
            p.AfficherDetails(tableuser.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }}

    
    @FXML
    private void Show_Draft(ActionEvent event) {
    }

    @FXML
    private void ExportToExcel(ActionEvent event) {
    }

    @FXML
    private void reload(ActionEvent event) {
    }

    @FXML
    private void updateOpportunity(ActionEvent event) {
    }

   

    @FXML
    private void delete_user(ActionEvent event) {
        
          if (tableuser.getSelectionModel().getSelectedItem() == null) {
            alert1("PLEASE SELECT THE OPPORTUNITY THAT YOU WANT TO DELETE");
            return;
        } else {
            if (alert1Confirmation() == true) {
                int id_opp = 0;
                ObservableList<fos_user> Allus, Singleus;
                Allus = tableuser.getItems();
                Singleus = tableuser.getSelectionModel().getSelectedItems();
                fos_userService fs = new fos_userService();
                tableuser.getSelectionModel().getSelectedItem();
                System.out.println("ROW HEDHI" + tableuser.getSelectionModel().getSelectedItem().getId());
                fs.delete_user(tableuser.getSelectionModel().getSelectedItem().getId());
                Singleus.forEach(Allus::remove);
            } else {
                return;
            }

        }
    }
        private void clickItem(MouseEvent event)
    {
    if (event.getClickCount() == 2) //Checking double click
    {
        System.out.println(table.getSelectionModel().getSelectedItem().getId());
       
    }
    }

public void display_usr_details()
{
    
}

    @FXML
    private void ShowApplications(ActionEvent event) {
    }

    @FXML
    private void Add_an_opportunity(ActionEvent event) {
    }







}
