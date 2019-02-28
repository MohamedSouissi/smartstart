/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import static com.smartstart.controllers.HomeOppController.mute;
import com.smartstart.entities.Opportunity;
import com.smartstart.entities.UserSession;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import static com.smartstart.entities.UserSession.logout;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ApplicationService;
import com.smartstart.services.OpportunityService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javax.management.Query.gt;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class AcceuilOpportunitiesController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Pagination pagination;
    @FXML
    private ListView<Opportunity> List;
    @FXML
    private ObservableList<Opportunity> data = FXCollections.observableArrayList();
    @FXML
    private ImageView muteicon;
    @FXML
    private Button show_app;
    @FXML
    private Label applicationnbr;
    final Tooltip tooltip = new Tooltip();
    OpportunityService S = new OpportunityService();
    ApplicationService A = new ApplicationService();
    @FXML
    private Button ShowApplications;
    fos_user u=new fos_user();
    int f;

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
        data = S.DisplayMy_Opportunities(u.getId());
         f=S.CountAppliedApp(u.getId());
         applicationnbr.setText(""+f);
        
        List.setItems(data);
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

        List.setCellFactory(OpportunityListView -> new ListOpportunitiesCell());

        // TODO
    }

    @FXML
    public void RemoveOpportunity(ActionEvent event) {
        if (List.getSelectionModel().getSelectedItem() == null) {
            alert1("PLEASE SELECT THE OPPORTUNITY THAT YOU WANT TO DELETE");
            return;
        } else {
            if (alert1Confirmation() == true) {
                int id_opp = 0;
                ObservableList<Opportunity> AllOp, SingleOp;
                AllOp = List.getItems();
                SingleOp = List.getSelectionModel().getSelectedItems();

                List.getSelectionModel().getSelectedItem();
                System.out.println("Value is in this row which" + List.getSelectionModel().getSelectedItem().getId_Opp());

                S.delete_opporunity(List.getSelectionModel().getSelectedItem().getId_Opp());
                SingleOp.forEach(AllOp::remove);
                if (mute == 0) {
                    PlayNotification("deleted.mp3");
                }

            } else {
                return;
            }

        }
    }

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
        a1.setContentText("ARE YOU SURE THAT YOU WANT TO DELETE THIS OPPORTUNITY?");

        if (mute == 0) {
            PlayNotification("sure.mp3");
        }
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    public void showtab(ActionEvent event) throws IOException {
        Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/DisplayOpportunitiesGui.fxml"));
        Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewOpportunityScene);
        window.setOnCloseRequest(e->Platform.exit());
        if (mute == 0) {
            PlayNotification("table.mp3");
        }

    }

    @FXML
    public void Add_an_opportunity(ActionEvent event) {
        try {

            Parent tableViewOpportunity = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/Add_OpportunityGui.fxml"));
            Scene tableViewOpportunityScene = new Scene(tableViewOpportunity);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewOpportunityScene);
            if (mute == 0) {
                PlayNotification("add-an-opportunity.mp3");
            }

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
            if (mute == 0) {
                PlayNotification("show-applications.mp3");
            }

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
            if (mute == 0) {
                PlayNotification("show-your-drafts.mp3");
            }

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
             if (mute == 0) {
                PlayNotification("home.mp3");
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

    public static void PlayNotification(String fileNotif) {
        String musicFile = "\\c:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\sounds\\" + fileNotif;     // For example

        /*Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();*/
    
      
    

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
      public void gotohelp(ActionEvent event) throws IOException
      {
           Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/HelpCenterGui.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          
      }
      @FXML
      public void DisplayStatistics(ActionEvent event) throws IOException
      {
           Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/statisticsGui.fxml"));
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
        @FXML
      public void showcontract(ActionEvent event) throws IOException
      {
           Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
          
      }
}
