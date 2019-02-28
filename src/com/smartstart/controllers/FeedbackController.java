/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import com.smartstart.entities.Feedback;
import com.smartstart.services.ContractServiceImpl;
import com.smartstart.services.FeedbackServiceImpl;
import com.smartstart.services.OpportunityService;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dytcha
 */
public class FeedbackController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private TextField txtField;
    @FXML
    private TableView<Feedback> table;
    @FXML
    private TableColumn<?,String> id_user;
    @FXML
    private TableColumn<Feedback, Integer> id_rating;
    @FXML
    private TableColumn<Feedback, String> id_comment;
    @FXML
    private TableColumn<?,String> id_app;
    @FXML
    private TableColumn<Feedback,Date> id_Added_date;
    @FXML
    private Button DeleteFeedback;
    @FXML
    private Button Add_Feedback;
    @FXML
    private Button displayFeedback;
    @FXML
    private Button reload;
    @FXML
    private Button reload1;
    
    private ObservableList<Feedback> data;
    @FXML
    private Label Nombre_Feedback;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //OpportunityService s = new OpportunityService();
        //int i = s.CountOpportunities(1);

        //Nombre_Opp.setText("" + i + "");

        try {
            reload();
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        initFilter();
        // TODO
    }  
    @FXML
    public void reload() throws SQLException {
        FeedbackServiceImpl f = new FeedbackServiceImpl();

        //int i = cs.CountContracts(1);

        //Nombre_Opp.setText("" + i + "");

        data = f.listerFeedback(1);

        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        
        id_rating.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        id_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        id_Added_date.setCellValueFactory(new PropertyValueFactory<>("added_date"));

        id_app.setCellValueFactory(new PropertyValueFactory<>("id_application"));


        System.out.println(data);

        table.setItems(data);

    }
    private void initFilter() {

        txtField.setPromptText("Filter");

        txtField.textProperty().addListener(new InvalidationListener() {

            @Override

            public void invalidated(Observable o) {

                if (txtField.textProperty().get().isEmpty()) {

                    table.setItems(data);

                    return;

                }

                ObservableList<Feedback> tableItems = FXCollections.observableArrayList();

                ObservableList<TableColumn<Feedback, ?>> cols = table.getColumns();

                for (int i = 0; i < data.size(); i++) {

                    for (int j = 0; j < cols.size(); j++) {

                        TableColumn col = cols.get(j);

                        String cellValue = col.getCellData(data.get(i)).toString();

                        cellValue = cellValue.toLowerCase();

                        if (cellValue.contains(txtField.textProperty().get().toLowerCase())) {

                            tableItems.add(data.get(i));

                            break;

                        }

                    }

                }

                table.setItems(tableItems);

            }

        });

    }

    private void alert1(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Champ Vide");
        a1.setContentText(Message);
        a1.showAndWait();
    }

   
    @FXML
    private void RemoveFeedback(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            alert1("PLEASE SELECT THE FEEDBACK THAT YOU WANT TO DELETE");
            return;
        } else {
            if (alert1Confirmation() == true) {
                int idCon = 0;
                ObservableList<Feedback> AllCon = table.getItems();
                ObservableList<Feedback> SingleCon = table.getSelectionModel().getSelectedItems();
                OpportunityService s = new OpportunityService();
                table.getSelectionModel().getSelectedItem();
                System.out.println("Value is in this row which" + table.getSelectionModel().getSelectedItem().getId_feedback());

                s.delete_opporunity(table.getSelectionModel().getSelectedItem().getId_feedback());
                SingleCon.forEach(AllCon::remove);
            } else {
                return;
            }

        }
    }

    @FXML
    private void AddFeedback(ActionEvent event) {
        try {
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/AddFeedbackGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    

    @FXML
    private void displayDetails(ActionEvent event) {
        try {
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/DetailFeedbackGui.fxml"));
            Parent root1 = (Parent) detail.load();
            Stage stage = new Stage();
            
            DetailFeedbackGuiController f = detail.getController();

            System.out.println(table.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root1));
            stage.show();
        //   f.AfficherDetails(table.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    @FXML
    private void Show_Draft(ActionEvent event) {
    }

    @FXML
    private void ExportToExcel(ActionEvent event) {
    }
    private boolean alert1Confirmation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION DIALOG");
        a1.setHeaderText("SUPPRESSION CONFIRMATION");
        a1.setContentText("ARE YOU SURE THAT YOU WANT TO DELETE THIS Feedback ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }
    
}
                