/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import com.smartstart.services.fos_userService;
import com.smartstart.entities.fos_user;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javax.xml.bind.DatatypeConverter;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class Freelancer_RegisterGUIController implements Initializable {

    @FXML
    private TextField password;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField name;
    @FXML
    private TextField last_name;
    @FXML
    private DatePicker birth_date;
    @FXML
    private TextField country;
    @FXML
    private TextField state;
    @FXML
    private TextField city;
    @FXML
    private ImageView file;
    private Label Nomremplir;
    @FXML
    private Label noname;
    @FXML
    private Label nolastname;
    @FXML
    private Label nopassword;
    @FXML
    private Label noemail;
    @FXML
    private Label nousername;
    @FXML
    private Label nobirthdate;
    @FXML
    private Label nocountry;
    

    /**
     * Initializes the controller class.
     */
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
    return java.sql.Date.valueOf(dateToConvert);
}
    @FXML
    private void add_user(ActionEvent event) {
      
        Date last = new Date(0);
        fos_userService fs = new fos_userService();
        //current date
         SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");  
Date datecurrent = new Date(System.currentTimeMillis());  
//System.out.println(formatter.format(date));  
//controle de saisie
       if(username.getText().isEmpty()  )
       {
            nousername.setVisible(true);
           nousername.setText("veuilleuz remplir le nom");
       }
         if(password.getText().isEmpty()  )
       {
           nopassword.setVisible(true);
           nopassword.setText("veuilleuz remplir le champ mot de passe");
       }
             if(email.getText().isEmpty() ||email.getText().contains("@") ==false )
       {
           noemail.setVisible(true);
           noemail.setText("veuilleuz remplir un email valide");
       }
             if(name.getText().isEmpty())
             {
                 noname.setVisible(true);
                 noname.setText("Entrer un nom");
             }
              if(last_name.getText().isEmpty())
             {
                 nolastname.setVisible(true);
                 nolastname.setText("Entrer un nom de famille");
             }
              /*    if(birth_date.getValue().isAfter(0))
             {
                 nolastname.setVisible(true);
                 nolastname.setText("Entrer un nom de famille");
             }*/
              if(country.getText().isEmpty())
              {
                  nocountry.setVisible(true);
                  nocountry.setText("enter a country");
              }
              
       //location
       else {
        String location =(""+country.getText()+","+state.getText().toString()+","+city.getText().toString()+"");
     //el canonical = crypted
     String encodedpass = DatatypeConverter.printBase64Binary(password.getText().getBytes());
     String encodedmail = DatatypeConverter.printBase64Binary(email.getText().getBytes());
        
      // LocalDate birthdate = birth_date.getValue(); 
        Date birthdate = new Date(birth_date.getValue().getYear(),birth_date.getValue().getMonthValue(),
        birth_date.getValue().getDayOfMonth());
        //confirmation 
        String code = fs.random_code(12);
        String encodedconfirmation = DatatypeConverter.printBase64Binary("ss".getBytes());
        
             //BIO A AJOUTER
        fos_user u = new fos_user(0,username.getText(),encodedpass,email.getText(),encodedmail,0,"salt",
        password.getText(),last,code,last,"freelancer",name.getText(),last_name.getText(),
        birthdate,"Bio",location,datecurrent,0,0,0,"");     
        fs.create_user(u);
        System.out.println("USER ADDED"); 
        fs.sendmail(email.getText(), code);
    }
    }
}
