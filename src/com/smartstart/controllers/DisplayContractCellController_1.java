/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class DisplayContractCellController_1 extends ListCell<Contract> {

    @FXML
    private AnchorPane gridPane;
    @FXML
    private Label description;
    @FXML
    private Label username;
    @FXML
    private Label titles;
    @FXML
    private Button update;
    @FXML
    private Button export;
    @FXML
    private TextField sum;
    @FXML
    private TextField start;
    @FXML
    private TextField finish;

  
    
}
