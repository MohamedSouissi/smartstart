package com.smartstart.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author mohamed
 */
public class DisplayQuestionController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private BorderPane bp;
    @FXML
    private Label type;
    @FXML
    private TabPane crud;
    @FXML
    private Tab myQuestions;
    @FXML
    private AnchorPane all;
    @FXML
    private JFXCheckBox answered;
    @FXML
    private JFXCheckBox notAnswered;
    @FXML
    private JFXComboBox<String> fbs;
    @FXML
    private AnchorPane QPane;
    @FXML
    private JFXToggleButton done;
    @FXML
    private Tab add;
    @FXML
    private TextArea postQuestion;
    @FXML
    private ComboBox<?> sug;
    @FXML
    private Button bAdd;
    @FXML
    private Button bCancel1;
    @FXML
    private Button imageChooser;
    @FXML
    private JFXTextField sub;
    @FXML
    private JFXTextField date;
    @FXML
    private Tab modify;
    @FXML
    private TableView<?> tabDelete;
    @FXML
    private TableColumn<?, ?> mt;
    @FXML
    private TableColumn<?, ?> mt1;
    @FXML
    private TableColumn<?, ?> mt2;
    @FXML
    private TableColumn<?, ?> mt3;
    @FXML
    private TableColumn<?, ?> mt4;
    @FXML
    private TableColumn<?, ?> mt5;
    @FXML
    private TableColumn<?, ?> mt6;
    @FXML
    private TableColumn<?, ?> mt7;
    @FXML
    private Button bModify;
    @FXML
    private Button bDelete;
    @FXML
    private Button bCancel2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      myQuestions.onSelectionChangedProperty().addListener(l->{ System.out.println("tab my question selected"); });
    }    
    
}
