/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ContractServiceImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class ContractGUIIntController implements Initializable {

    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private ImageView pic1;
    @FXML
    private Label count;
    ObservableList<Contract> data;
    @FXML
    private ListView<Contract> List;
    @FXML
    private TextField recherche;
    @FXML
    private Button drafts;
    @FXML
    private Button CancelButton;
    fos_user u;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContractServiceImpl S = new ContractServiceImpl();
        try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = S.CountContracts(u.getId());
        count.setText(String.valueOf(i));
        if(u.getRoles().equals("entreprise"))
        data = S.listContract(u.getId());
        else
        {
            try {
                data=S.listContractFreelancer(u.getId());
            } catch (SQLException ex) {
                Logger.getLogger(ContractGUIIntController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List.setItems(data);
        List.setCellFactory(ContractListView -> new ContractCellController());

        initFilter();

    }

    public void initFilter() {
        FilteredList<Contract> filteredData = new FilteredList<>(data, p -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cont -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if ((cont.getApplication().getOpportunity().getJob_title().toLowerCase().contains(lowerCaseFilter)) || (cont.getUser().getUsername().toLowerCase().contains(lowerCaseFilter))) {
                    return true; // Filter matches first name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Contract> sortedData = new SortedList<>(filteredData);
        List.setItems(sortedData);
        long s = filteredData.stream().count();
        count.setText(String.valueOf(s));

    }

    @FXML
    private void redirect(ActionEvent event) {
        AutoContractGuiController.lc.removeAll(AutoContractGuiController.lc);
        Parent tableViewContract;
        try {
            tableViewContract = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AutoContractGui.fxml"));
            Scene tableViewContractScene = new Scene(tableViewContract);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewContractScene);
        } catch (IOException ex) {
            Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     @FXML
    public void Add_an_opportunity(ActionEvent event) {
        try {

            Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Add_OpportunityGui.fxml"));
            Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
          

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    public void ShowApplications(ActionEvent event) {
        try {
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ListAppOpportunities.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.setOnCloseRequest(e->Platform.exit());
            
            stage1.show();
           
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    public void Show_Draft(ActionEvent event) {
        try {
            Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ShowDraftGui.fxml"));
            Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
           

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    @FXML
    public void Show_Home(ActionEvent event) {
        try {
            Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/HomeOpportunities.fxml"));
            Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
            window.setOnCloseRequest(e->Platform.exit());
            
            

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
     @FXML
      public void GotoComplaints(ActionEvent event) throws IOException
      {
          
        
         FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ComplaintsGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
          
      }
       @FXML
      public void showcontract(ActionEvent event) throws IOException
      {
           Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          
      }
       @FXML
      public void showq(ActionEvent event) throws IOException
      {
          
          
         FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/QuestionGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
          
      }
      @FXML
      public void logoutbutton(ActionEvent event) throws ClassNotFoundException, IOException
      { fos_user u=new fos_user();
      u=UserSession.getInstance(new fos_user()).getUser();
            System.out.println(u.getName());
                 u=UserSession.getInstance(new fos_user()).getUser();
            System.out.println(u.getName());
            UserSession.SetInstance();
           
                
                
                 Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Login.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
                
      }
      @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
