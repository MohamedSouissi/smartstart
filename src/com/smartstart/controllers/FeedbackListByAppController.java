/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Feedback;
import com.smartstart.services.FeedbackServiceImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author dytcha
 */
public class FeedbackListByAppController implements Initializable {

    @FXML
    private AnchorPane recherche;
    @FXML
    private ListView<Feedback> List;
    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private ImageView pic1;
    @FXML
    private TextField rechercheF;
    @FXML
    private Text count;
    

        ObservableList<Feedback> data;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        FeedbackServiceImpl S =new FeedbackServiceImpl();
        try {
            
            data=S.listerFeedbackByApp(1);
             List.setItems(data);
        List.setCellFactory(FeedbackListView -> new FeedbackCellGuiController());
        
       // initFilter();
        int i = S.CountFeedbacks(1);
        count.setText(String.valueOf(i));
        rechercheF.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                ObservableList<Feedback> tmp = FXCollections.observableArrayList();

                
                for (Feedback feedback : data) {
                    if(feedback.getComment().toLowerCase().contains(newValue.toLowerCase())){
                        tmp.add(feedback);

                    }
                }
                
                         
                                                       List.setItems(tmp);
                                                       

                count.setText(String.valueOf(tmp.stream().count()));
    
            }});
        
        } catch (SQLException ex) {
        }
        
    }    
    
}
