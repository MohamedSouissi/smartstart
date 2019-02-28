/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import com.smartstart.entities.Opportunity;
import com.smartstart.services.OpportunityService;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author acmou
 */
public class OpportunityController {
     public static void main(String[] args) throws ParseException {
         OpportunityService p1=new OpportunityService();
       p1.Display_Opportunity();
       LocalDate today = LocalDate.now();
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
       Date d=new Date(0);
       
       
       java.util.Date utilDate = new java.util.Date();
 java.sql.Date date = new java.sql.Date(utilDate.getTime());

    
      // Opportunity O=new Opportunity(1,"AddedJob","Mobile","android studio",400,0,"44",d,d,1);
      // Opportunity O1=new Opportunity(1,"fathi","larou","larou",400,0,"44",date,date,1);
       //p1.create_Opportunity(O,1);
       //p1.delete_opporunity(5);
       //p1.update_opportunity(O1,2);
        
    }
     
    

    

        }
    
