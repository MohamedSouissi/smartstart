/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.gui;

import com.smartstart.entities.fos_user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class ChatDiscCellItemController {

    @FXML
    private AnchorPane gridPane;
    @FXML
    private Button user;
    @FXML
    private Text nom;

    public AnchorPane getGridPane() {
        return gridPane;
    }

    public void setGridPane(AnchorPane gridPane) {
        this.gridPane = gridPane;
    }

    public ChatDiscCellItemController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ChatDiscCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
             throw new RuntimeException(e);
        }
    }

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        // TODO
    }

    fos_user c = new fos_user();

    public void setCandidature(fos_user user) {
        this.c = user;

        if (user != null) {
            nom.setText(user.getName() + " " + user.getLast_name());
            this.user.setText(user.getName() + " " + user.getLast_name());
        }
    }

}
