/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.AcceuilOpportunitiesController.PlayNotification;
import static com.smartstart.controllers.HomeOppController.mute;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.Date;
import java.net.URL;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class Profile_OpportunitiesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Opportunity> table = new TableView<>();
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
    private TableColumn<Opportunity, Date> colAdded_date;

    private ObservableList<Opportunity> data;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField txtField;
    @FXML
    private Button DeleteOpp;
    @FXML
    private Button Add_Opportunity;
    @FXML
    private Button displayopp;
    @FXML
    private Button Show_My_Draft;
    @FXML
    private Button reload;
    @FXML
    private Label applicationnbr;
    int f;
    @FXML
    private Label Nombre_Opp;
    final Tooltip tooltip=new Tooltip();
    @FXML
    private ImageView muteicon;
    fos_user u=new fos_user();
    OpportunityService as = new OpportunityService();

    @FXML
    public void displayDetails(ActionEvent event) {
         if (table.getSelectionModel().getSelectedItem() == null) {
            alert1("PLEASE SELECT THE OPPORTUNITY THAT YOU WANT TO DISPLAY");
            return;
        } else {
        try {if(mute==0)
                {
                
            PlayNotification ("detail.mp3");}
            
            
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/DetailOpportunityGui.fxml"));
            Parent root1 = (Parent) detail.load();
            Stage stage = new Stage();
            DetailOpportunityGuiController p = detail.getController();

            System.out.println(table.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root1));
            stage.show();
            p.AfficherDetails(table.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         if (mute == 0) {
            System.out.println("bij");
            System.out.println(mute);

            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\Unmute.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
           
        }
        if (mute == 1) {

            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\mutepng.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
           
        }

       
        OpportunityService s = new OpportunityService();
        int i = s.CountOpportunities(u.getId());

        Nombre_Opp.setText("" + i + "");

        reload();
        initFilter();
        
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

                ObservableList<Opportunity> tableItems = FXCollections.observableArrayList();

                ObservableList<TableColumn<Opportunity, ?>> cols = table.getColumns();

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

    @FXML
    public void RemoveOpportunity(ActionEvent event) {
        if (table.getSelectionModel().getSelectedItem() == null) {
            alert1("PLEASE SELECT THE OPPORTUNITY THAT YOU WANT TO DELETE");
            return;
        } else {
            if (alert1Confirmation() == true) {
                int id_opp = 0;
                ObservableList<Opportunity> AllOp, SingleOp;
                AllOp = table.getItems();
                SingleOp = table.getSelectionModel().getSelectedItems();
                OpportunityService s = new OpportunityService();
                table.getSelectionModel().getSelectedItem();
                System.out.println("Value is in this row which" + table.getSelectionModel().getSelectedItem().getId_Opp());

                s.delete_opporunity(table.getSelectionModel().getSelectedItem().getId_Opp());
                SingleOp.forEach(AllOp::remove);
                       int i = as.CountOpportunities(u.getId());
        Nombre_Opp.setText("" + i + "");

                if(mute==0)
                {
                PlayNotification ("deleted.mp3");}
               

        }
    }}

    @FXML
    public void Add_an_opportunity(ActionEvent event) throws IOException {
      Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Add_OpportunityGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
         if(mute==0)
                {
         PlayNotification ("add-an-opportunity.mp3");}
         
    }

    @FXML
    public void Show_Draft(ActionEvent event) {
        try {
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ShowDraftGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
         if(mute==0)
                {
         PlayNotification ("show-your-drafts.mp3");}
         

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }

    private boolean alert1Confirmation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION DIALOG");
        a1.setHeaderText("SUPPRESSION CONFIRMATION");
        a1.setContentText("ARE YOU SURE THAT YOU WANT TO DELETE THIS OPPORTUNITY?");
        if(mute==0)
                {
        PlayNotification ("sure.mp3");}
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    @FXML
    public void reload() {
        try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        
         f=as.CountAppliedApp(u.getId());
         applicationnbr.setText(""+f);
int i = as.CountOpportunities(u.getId());
        Nombre_Opp.setText("" + i + "");

        data = as.DisplayMy_Opportunities(u.getId());
        

        colid_opportunity.setCellValueFactory(new PropertyValueFactory<>("id_Opp"));
        colid_opportunity.setVisible(false);
        coljob_title.setCellValueFactory(new PropertyValueFactory<>("job_title"));

        coljob_category.setCellValueFactory(new PropertyValueFactory<>("job_category"));

        coljob_description.setCellValueFactory(new PropertyValueFactory<>("job_description"));

        colbudget.setCellValueFactory(new PropertyValueFactory<>("Budget"));

        colDuration.setCellValueFactory(new PropertyValueFactory<>("job_Duration"));

        colExpiry_Date.setCellValueFactory(new PropertyValueFactory<>("Expiry_date"));

        colAdded_date.setCellValueFactory(new PropertyValueFactory<>("added_date"));
        if(coljob_category.getText().compareTo("Symfony")==0)
        {
            System.out.println("");
        }

        coljob_title.setSortType(TableColumn.SortType.DESCENDING);
        coljob_category.setSortType(TableColumn.SortType.DESCENDING);
        coljob_description.setSortType(TableColumn.SortType.DESCENDING);
        colbudget.setSortType(TableColumn.SortType.DESCENDING);
        colDuration.setSortType(TableColumn.SortType.DESCENDING);
        colExpiry_Date.setSortType(TableColumn.SortType.DESCENDING);
        colAdded_date.setSortType(TableColumn.SortType.DESCENDING);

        System.out.println(data);

        table.setItems(data);
         table.setEditable(true);
    
        tooltip.setText("Bijor");
        table.setTooltip(tooltip);
        
         

    }

    private void alert1(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Champ Vide");
        a1.setContentText(Message);
        a1.showAndWait();
    }
    @FXML
    private void ExportToExcel() throws FileNotFoundException, IOException
    {
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");

        org.apache.poi.ss.usermodel.Row row = spreadsheet.createRow(0);

        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if(table.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }

        FileOutputStream fileOut = new FileOutputStream("smartstart.xls");
        workbook.write(fileOut);
        fileOut.close();

        

        System.out.println("Data is wrtten Successfully");
        if(mute==0)
                {
        PlayNotification ("excel.mp3");}
    }
     @FXML
    public void ShowApplications(ActionEvent event) {
        try {
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ListAppOpportunities.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
            stage1.show();
            if(mute==0)
                {
            PlayNotification ("show-applications.mp3");}

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
   

    @FXML
    public void updateOpportunity(ActionEvent event) {
         if (table.getSelectionModel().getSelectedItem() == null) {
            alert1("PLEASE SELECT THE OPPORTUNITY THAT YOU WANT TO Update");
            return;
        } else {
        try {
            
            FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/UpdateOpportunityGui.fxml"));
            Parent root1 = (Parent) detail.load();
            Stage stage = new Stage();
            UpdateOpportunityGuiController p = detail.getController();

            System.out.println(table.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root1));
            stage.show();
            p.AfficherDetailsUpdate(table.getSelectionModel().getSelectedItem());
           
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }}
      @FXML
    public void ShowHome(ActionEvent event) throws IOException {
        Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/AcceuilOpportunities.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          if(mute==0)
                {
                       
                
          PlayNotification("list.mp3");}
        
    }
    @FXML
    public void Show_Home(ActionEvent event) {
        try {
            Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/HomeOpportunities.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          if(mute==0)
                {
                      PlayNotification ("home.mp3");
                }
        

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
    
    @FXML
    public void mute() {
        System.out.println("bij");
        System.out.println(mute);
        if (mute == 0) {
            System.out.println("bij");
            System.out.println(mute);
            mute = 1;
            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\mutepng.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
            return;
        }
        if (mute == 1) {
            mute = 0;
            File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\Unmute.png");
            Image image1 = new Image(file.toURI().toString());

            muteicon.setImage(image1);
            return;
        }

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
      public void GotoComplaints(ActionEvent event) throws IOException
      {
          
           FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ComplaintsGui.fxml"));
            Parent root2 = (Parent) detail.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root2));
             stage1.show();
          
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
        
 
    
    
   
   

    

}
