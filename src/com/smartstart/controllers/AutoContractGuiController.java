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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
public class AutoContractGuiController implements Initializable {

    @FXML
    private ListView<Contract> List;
    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private ImageView pic1;
    @FXML
    private Label count;
    @FXML
    private TextField recherche;
    @FXML
    private Button contracts;
    @FXML
    private ListView<Contract> display_list;
    ObservableList<Contract> data;
    public static List<Contract> lc = new ArrayList<Contract>();
    fos_user u;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ContractServiceImpl S = new ContractServiceImpl();
        try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            data = S.lowPrioContracts(u.getId());
        } catch (SQLException ex) {
            Logger.getLogger(AutoContractGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = data.size();
        count.setText(String.valueOf(i));
        List.setItems(data);
        List.setCellFactory(ContractListView -> new AutoContractCellController());
        ObservableList lcf = FXCollections.observableArrayList(lc);
        display_list.setItems(lcf);
        display_list.setCellFactory(AutoContractListView -> new DisplayContractCellController());
        

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
        Parent tableViewContract;
        try {
            tableViewContract = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
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
          
           Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ComplaintsGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          
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
          
           Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/QuestionGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          
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

}
