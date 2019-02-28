package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.smartstart.entities.Question;
import com.smartstart.entities.fos_user;
import com.smartstart.entities.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author mohamed
 */
public class QuestionController implements Initializable {

    //private QuestionServices service;
    @FXML
    private JFXCheckBox answered;
    @FXML
    private JFXCheckBox notAnswered;
    @FXML
    private JFXTextField search;
    @FXML
    private JFXComboBox<String> subject;
    @FXML
    private JFXTextField suggestTo;
    @FXML
    private Label rows;

    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private ImageView pic1;

    @FXML
    private JFXListView<Question> list;
    @FXML
    private JFXButton mySpace;
    @FXML
    private JFXButton CancelButton;

    // intelligent Textfield search
    Set<String> setSearch;
    AutoCompletionBinding<String> autoSearch;
    @FXML
    private JFXComboBox<String> notify;
    @FXML
    private static AnchorPane questionAnchor;

    private ObservableList<Question> obss;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
       
        ObservableList obs = FXCollections.observableArrayList(UserSession.getQuestionService().getsubjects());
        subject.getItems().setAll(obs);

        ObservableList<String> obl = FXCollections
                .observableArrayList(UserSession.getNotifSuggestService()
                        .getNotViewed(UserSession.getFosUser()).keySet()
                        .stream().collect(Collectors.toList()));
        //  UserSession.getNotifSuggestService().getNotifications(UserSession.getFosUser()).forEach(System.out::println);
        notify.getItems().setAll(obl);

        // set a list of question
        obss = FXCollections.observableArrayList(UserSession.getQuestionService().getNewestQuestions());
        list.setItems(obss);
        list.setCellFactory((ListView<Question> list1) -> new QBoxQuestionGuiController());
        list.setExpanded(true);
        list.depthProperty().set(1);

        rows.setText("rows count:" + list.getItems().stream().count());

        // intelligent search of questions
        setSearch = UserSession.getQuestionService().getQuestionsPosted();
        autoSearch = TextFields.bindAutoCompletion(search, setSearch);

        search.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case ENTER:
                    learnWord(search.getText());
                    break;
                default:
                    //  ObservableList<Question> obsss = FXCollections.observableArrayList(service.getNewestQuestions().stream()
                    //  .filter(p-> p.getQuestion().startsWith(search.getText())).collect(Collectors.toList()));
                    list.setItems(FXCollections.observableArrayList(obss.stream()
                            .filter(p -> p.getQuestion().startsWith(search.getText())).collect(Collectors.toList())));
                    list.setCellFactory((ListView<Question> list1) -> new QBoxQuestionGuiController());
                    //list.refresh();

                    rows.setText("rows count:" + list.getItems().stream().count());
                    break;
            }
        });
        mySpace.setOnAction(e -> {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/QuestionCellsGui.fxml"));
                loader.load();
                Parent p = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.show();
            } catch (IOException io) {
                io.printStackTrace();
            }
        });

    }

    private void handleButtonAction(ActionEvent event) {
        //not working for this moment !
        try {
            Parent p = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/QuestionCellsGui.fxml"));
            /* Scene sp=new Scene (pp);
          Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(sp);
          
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("/com/smartstart/gui/QuestionCellsGui.fxml"));
          loader.load();
          Parent p = loader.getRoot();*/
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void VerifyAndDelete(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete the question");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
//            service.deleteQuetion(list.selectionModelProperty().getValue().getSelectedItem());
        }
       // list.refresh();
    }

    @FXML
    public void displayNotAnsweredQuestion() {
        if (notAnswered.isSelected()) {
            answered.setSelected(false);
            this.displayContainer(UserSession.getQuestionService().getNotAnseweredQuestions(), list);
        } else {
            this.displayAllQuestion();
        }
    }

    @FXML
    public void displayAnsweredQuestion() {
        if (answered.isSelected()) {
            notAnswered.setSelected(false);
            this.displayContainer(UserSession.getQuestionService().getAnseweredQuestions(), list);
        } else {
            this.displayAllQuestion();
        }
    }

    @FXML
    public void displayBySubject() {
        if (answered.isSelected()) {
            this.displayContainer(UserSession.getQuestionService().getAnseweredQuestionsBySubject(subject.getValue()), list);
            return;
        }
        if (notAnswered.isSelected()) {
            this.displayContainer(UserSession.getQuestionService().getNotAnsweredQuestionsBySubject(subject.getValue()), list);
            return;
        }
        this.displayContainer(UserSession.getQuestionService().getQuestionsBySubject(subject.getValue()), list);
    }

    public void displayAllQuestion() {
        this.displayContainer(UserSession.getQuestionService().getNewestQuestions(), list);
    }

    private void displayContainer(List<Question> l, ListView t) {
        obss = FXCollections.observableArrayList(l);
        t.setItems(obss);
        rows.setText("rows count:" + t.getItems().stream().count());
       // t.refresh();
    }

    public void showNotification() {
        UserSession.getNotifSuggestService().getNotViewedNotifications(UserSession.getFosUser());

    }

    private void learnWord(String text) {
        if (!setSearch.contains(search.getText())) {
            setSearch.add(text);
            if (autoSearch != null) {
                autoSearch.dispose();
            }
        }
        autoSearch = TextFields.bindAutoCompletion(search, setSearch);
    }
    @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
