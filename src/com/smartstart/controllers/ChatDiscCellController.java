/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Contract;
import com.smartstart.entities.fos_user;
import com.smartstart.gui.ChatDiscCellItemController;
import com.smartstart.services.ChatServiceImpl;
import com.smartstart.services.ContractServiceImpl;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class ChatDiscCellController extends ListCell<fos_user> {

    @FXML
    private AnchorPane gridPane;
    @FXML
    private Button user;
    private FXMLLoader mLLoader;
    
    
    @Override
    public void updateItem(fos_user c, boolean empty)
    {
        super.updateItem(c,empty);
        if(c != null)
        {
            ChatDiscCellItemController data = new ChatDiscCellItemController();
            data.setCandidature(c);
            setGraphic(data.getGridPane());
        }
    } 

    
}

