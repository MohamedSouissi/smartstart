package com.smartstart.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.smartstart.entities.Question;
import com.smartstart.entities.Reply;
import com.smartstart.entities.fos_user;
import com.smartstart.entities.UserSession;
import com.teknikindustries.bulksms.SMS;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mohamed
 */
public class QuestionCellsController implements Initializable {

    @FXML
    private AnchorPane parent;
    @FXML
    private BorderPane bp;
    @FXML
    private TabPane crud;
    @FXML
    private Tab myQuestions;
    @FXML
    private Tab add;
    @FXML
    private AnchorPane all;
    @FXML
    private JFXCheckBox answered;
    @FXML
    private JFXCheckBox notAnswered;
    @FXML
    private JFXTextField date;
    @FXML
    private Tab modify;
    @FXML
    private ScrollPane scroll1;
    @FXML
    private JFXComboBox<String> notif;
    @FXML
    private JFXButton bModify;
    @FXML
    private JFXButton bDelete;
    @FXML
    private StackPane pane;
    @FXML
    private JFXTextField search;
    @FXML
    private JFXButton accueil;
    @FXML
    private JFXListView<Question> listQ;
    @FXML
    private JFXComboBox<String> filter;
    @FXML
    private TextArea description;
    @FXML
    private JFXTextField question;
    @FXML
    private JFXComboBox<String> subject;
    @FXML
    private JFXComboBox<String> suggest;
    @FXML
    private JFXButton post;
    @FXML
    private JFXButton Cancel1;
    @FXML
    private JFXButton CancelButton;
    @FXML
    private JFXListView<Reply> listR;
    
     private  ObservableList<Question> obss;
     private  ObservableList<Reply> rob;
              
    @FXML
    private Label rows;
    @FXML
    private JFXButton qDelete;
    @FXML
    private JFXButton qModify;
    @FXML
    private Label rrows;
    public static Reply rep;
    @FXML
    private JFXCheckBox qsms;
    @FXML
    private JFXCheckBox qmail;
    fos_user u;
     

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // instance d'un user:
       try {
    u = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        date.setText(LocalDate.now().toString());
        
        filter.setItems(FXCollections.observableArrayList(UserSession.getQuestionService().getsubjects()));
        subject.setItems(FXCollections.observableArrayList(UserSession.getQuestionService().getsubjects()));
        
     UserSession.getNotifSuggestService().getNotViewedNotifications(u).stream().map(m-> m.toString()).forEach(System.out::println);
        List<String> l =  UserSession.getNotifSuggestService().getNotViewedNotifications(u).stream().map(m-> m.toString()).collect(Collectors.toList());
        notif.setItems(FXCollections.observableArrayList(l));
     
        obss = FXCollections.observableArrayList(UserSession.getQuestionService().getMyQuestions(UserSession.getFosUser()));
        listQ.setItems(obss);
        listQ.setCellFactory((ListView<Question> list1) -> new QBoxQuestionGuiController());

        listQ.setExpanded(true);
        listQ.depthProperty().set(1);
        rows.setText("rows count"+listQ.getItems().stream().count());

        rob = FXCollections.observableArrayList(UserSession.getReplyService().getMyResponses(UserSession.getFosUser()));
        listR.setItems(rob);
        listR.setCellFactory((ListView<Reply> list1) -> new MyResponsesGuiController());
        listR.setExpanded(true);
        listR.depthProperty().set(1);
        rrows.setText("rows count"+listR.getItems().stream().count());

        
        
         //================= needed for sms integration ====================    
        Map<String, String> suggestMap = new HashMap<String, String>();
        suggestMap.put("mounir", "+216 58978667");
        suggestMap.put("metwi", "+216 99574161");
        suggestMap.put("marwen", "+216 22209052");
        suggestMap.put("mehdi", "+216 92156121");
        suggestMap.put("amine", "+216 21986562");
        suggestMap.put("laroussi", "+216 23383700");
        if (suggestMap.containsKey(UserSession.getFosUser().getUsername_canonical())) {
            suggestMap.remove(UserSession.getFosUser().getUsername_canonical());
        }

        suggest.setItems(FXCollections.observableArrayList(suggestMap.keySet()));
        //=================end of needs for sms integration ===============  
        
        post.setOnAction(e -> {
            if (!question.getText().equals("")) {
                if (!UserSession.getQuestionService().isFoundQuestion(question.getText(), description.getText(), subject.getValue())) {
                    UserSession.getQuestionService().askAQuestion(new Question(UserSession.getFosUser(), question.getText(), description.getText(), subject.getValue().toString()));
                    Stage currentStage = (Stage) pane.getScene().getWindow();
                    currentStage.close();
                    //=====================    sms AND mail integration   =========================        

                       if (!suggest.getSelectionModel().getSelectedItem().isEmpty()) {
                        if (qsms.isSelected()) {
                   UserSession.getQApiServices().sendSMS(question.getText(),suggest.getValue());
                        }
                       if (qmail.isSelected()) {
                            try{
                    UserSession.getQApiServices().sendQuestionToUser(suggest.getValue(),question.getText());
                            }catch(Exception ex){
                              AlertBox.displayError("QApiServices", "email not sent");
                            }
                        }
                    }

                    //=====================    fin sms AND mail integration =================================  
                
                
                
                }
            } else {
                question.setStyle("-fx-background-color: red");
            }

        });
        accueil.setOnAction(e->{
         Stage currentStage = (Stage) pane.getScene().getWindow();
            currentStage.close();
        });
        modify.setOnSelectionChanged(e->{
        rob = FXCollections.observableArrayList(UserSession.getReplyService().getMyResponses(UserSession.getFosUser()));
        listR.setItems(rob);
        listR.setCellFactory((ListView<Reply> list1) -> new MyResponsesGuiController());
        rrows.setText("rows count"+listR.getItems().stream().count());

        });
        myQuestions.setOnSelectionChanged(e->{
        obss = FXCollections.observableArrayList(UserSession.getQuestionService().getMyQuestions(UserSession.getFosUser()));
        listQ.setItems(obss);
        listQ.setCellFactory((ListView<Question> list1) -> new QBoxQuestionGuiController());
rows.setText("rows count"+listQ.getItems().stream().count());
        });

    }

    private void VerifyAndAddQuestion(ActionEvent event) {
        if (!question.getText().equals("")) {
            if (!UserSession.getQuestionService().isFoundQuestion(question.getText(), description.getText(),subject.getValue())) {
               UserSession.getQuestionService().askAQuestion(new Question(UserSession.getFosUser(), question.getText(), description.getText(), subject.getValue().toString()));
            }
        } else {
            question.setFocusColor(Color.RED);
        }
    }

    @FXML
    private void VerifyAndDelete(ActionEvent event) {
        
            if(listR.getSelectionModel().getSelectedIndex()>0){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure, delete this reply");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            UserSession.getReplyService().deleteReply(listR.getSelectionModel().getSelectedItem());
        //listR.refresh();
        rrows.setText("rows count"+listR.getItems().stream().count());
        }
        }
    }
     @FXML
    private void onActionDelete(ActionEvent event) {
        
            if(listQ.getSelectionModel().getSelectedIndex()>0){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure, delete this reply");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            UserSession.getQuestionService().deleteQuetion(listQ.getSelectionModel().getSelectedItem());
        }
        //listQ.refresh();
        rows.setText("rows count"+listQ.getItems().stream().count());
            }
    }
    @FXML
    private void onActionModify(){
        
            if(listQ.getSelectionModel().getSelectedIndex()>0){
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/smartstart/gui/QModify.fxml"));
                    try {
                        loader.load();
                    } catch (IOException io) {
                        AlertBox.displayError("QuestionCellsControllers", "can't load thi file: /com/smartstart/gui/QModify.fxml");
                    }
                    QModifyController qr = loader.getController();
                    qr.displayQuestion(listQ.getSelectionModel().getSelectedItem());
                    Parent p = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.show();
            }
            }
    
      @FXML
    private void VerifyAndModify(ActionEvent event) throws IOException {
        rep=listR.getSelectionModel().getSelectedItem();
        
        
        Parent tableViewOpportunity=FXMLLoader.load(getClass().getResource("/com/smartstart/gui/RModify.fxml"));
         Scene tableViewOpportunityScene=new Scene (tableViewOpportunity);
         Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(tableViewOpportunityScene);
        
        
        
        
        
        
        
        /* try {
        if(listR.getSelectionModel().getSelectedIndex()>0){
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/RModify.fxml"));
        Parent root2 = (Parent) loader.load();
        
        RModifyController qm = new RModifyController();
        qm.display(listR.getSelectionModel().getSelectedItem());
        
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(root2));
        stage1.show();
        }
        
        } catch (IOException e) {
        AlertBox.displayError("QuestionCellsControllers", "can't load this file: /com/smartstart/gui/RModify.fxml");
        }
        listR.refresh();*/
    }
    public void displayAllQuestion() {
        this.displayContainer(UserSession.getQuestionService().getMyQuestions(UserSession.getFosUser()), listQ);
    }

    @FXML
    public void displayNotAnsweredQuestion() {
        if (notAnswered.isSelected()) {
            answered.setSelected(false);
            this.displayContainer(UserSession.getQuestionService().getNotAnseweredQuestions(UserSession.getFosUser()), listQ);
        }else{
            this.displayAllQuestion();
        }
    }

    @FXML
    public void displayAnsweredQuestion() {
        if (answered.isSelected()) {
            notAnswered.setSelected(false);
            this.displayContainer(UserSession.getQuestionService().getAnseweredQuestions(UserSession.getFosUser()), listQ);
        }else{
            this.displayAllQuestion();
        }
    }

    @FXML
    public void displayBySubject() {
        if (answered.isSelected()) {
            this.displayContainer(UserSession.getQuestionService().getAnseweredQuestionsBySubject(UserSession.getFosUser(),filter.getValue()), listQ);
            return;
        }
        if ( notAnswered.isSelected()) {
            this.displayContainer(UserSession.getQuestionService().getNotAnsweredQuestionsBySubject(UserSession.getFosUser(),filter.getValue()), listQ);
            return;
        }
        this.displayContainer(UserSession.getQuestionService().getQuestionsBySubject(UserSession.getFosUser(),filter.getValue()), listQ);
    }

    private void displayContainer(List<Question> l, JFXListView t) {
        ObservableList obs2 = FXCollections.observableArrayList(l);
        t.setItems(obs2);
        //t.refresh();
        rows.setText("rows count"+listQ.getItems().stream().count());

    }
  @FXML
    private void annuler(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

}
