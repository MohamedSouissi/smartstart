/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Opportunity;
import com.smartstart.entities.fos_user;
import com.smartstart.services.OpportunityService;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author acmou
 */
public class HomeOppCell extends ListCell<Opportunity>{

 @FXML
    private Label titles;
    @FXML
    private Label category;
    @FXML
    private Label description;
    @FXML
    private Label Expiry;
    private FXMLLoader mLLoader;
    @FXML
    private AnchorPane gridPane;
    @FXML
    private Label budget;
    @FXML
    private Label Expiry1;
    @FXML
    private Label exp;
    @FXML
    private Label count;
    @FXML
    private Label company_name;
    @FXML 
    private ImageView image;
    final Tooltip tooltip = new Tooltip();
  
    

    @Override
    protected void updateItem(Opportunity student, boolean empty) {
        super.updateItem(student, empty);
        

        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else if(!empty || student != null) {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/HomeCellGui.fxml"));
               mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
            
            OpportunityService s=new OpportunityService();
            fos_user u=new fos_user();
            u=s.getCompanyNameById_opp(student.getId_Opp());
            System.out.println(u.getUsername());
             System.out.println(u);
            
            
           company_name.setText(u.getUsername());

            titles.setText(String.valueOf(student.getJob_title()));
            category.setText(student.getJob_category());
            description.setText(student.getJob_description());
     
            budget.setText(""+student.getBudget());
            Expiry1.setText(String.valueOf(student.getAdded_date()));
            exp.setText(String.valueOf(student.getExpiry_date()));
            count.setText(""+s.CountApplication(student.getId_Opp()));
            if(count.getText().equals("1"))
            {File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\1.png");
        Image image1 = new Image(file.toURI().toString());
                
                image.setImage(image1);
            }
             if(count.getText().equals("2"))
            {File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\two.png");
        Image image1 = new Image(file.toURI().toString());
                
                image.setImage(image1);
            }
             if(count.getText().equals("3"))
            {File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\3.png");
        Image image1 = new Image(file.toURI().toString());
                
                image.setImage(image1);
            }
             if(count.getText().equals("0"))
            {File file = new File("C:\\Users\\acmou\\Documents\\GitHub\\SmartStart\\src\\images\\0.png");
        Image image1 = new Image(file.toURI().toString());
                
                image.setImage(image1);
            }
            
            
            
        

  tooltip.setText("Duration :"+student.getJob_Duration());
        titles.setTooltip(tooltip);
         

            
            setGraphic(gridPane);
           
           
         


        }}
    
    
}

    


