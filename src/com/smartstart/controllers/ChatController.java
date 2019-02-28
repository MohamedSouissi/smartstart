/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.smartstart.entities.Message;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ChatServiceImpl;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
//import javax.websocket.DeploymentException;
//import javax.websocket.Session;
//import org.glassfish.tyrus.client.ClientManager;

/**
 *
 * @author diabl
 */
public class ChatController implements Initializable {
    
    private ObservableList<fos_user> data;
    public static List<Message> dataMessage = new ArrayList<Message>();
    
    ChatServiceImpl cs = new ChatServiceImpl();
    @FXML
    private ListView<fos_user> List;
    @FXML
    private ImageView Profile_pic;
    @FXML
    private Label username;
    @FXML
    private ImageView pic1;
    @FXML
    private Label count;
    @FXML
    private TextField recherche;
    @FXML
    private TextField content;
    @FXML
    private Button send;
    @FXML
    private ListView<Message> messages;
    public static fos_user userfrom = new fos_user();
    public static fos_user userto = new fos_user();
 public static final String SERVER = "ws://localhost:8025/ws/chat"; 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
          
        String message;

        // connect to server
       // Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tiny Chat!");
        System.out.println("What's your name?");
      //  String user = scanner.nextLine();
       
      //  System.out.println("You are logged in as: " + user);
        try {
             
            data = cs.discussionList(1);
        } catch (SQLException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        count.setText(String.valueOf(data.size()));
        List.setItems(data);
        List.setCellFactory(ChatListView -> new ChatDiscCellController());
        ObservableList lmf = FXCollections.observableArrayList(dataMessage);
        messages.setItems(lmf);
        messages.setCellFactory(messagesListView -> new MessageCellGuiController());
//        send.setOnAction(new EventHandler<ActionEvent>() {
//
//                @Override
//                public void handle(ActionEvent t) {
//               ClientManager client = ClientManager.createClient();
//             Session session;
//                    try {
//                        session = client.connectToServer(ClientEndpoint.class, new URI(SERVER));
//                        do {
//            //message = content.getText();
//            session.getBasicRemote().sendText(formatMessage(content.getText(), userto.getUsername()));
//            ChatServiceImpl cs = new ChatServiceImpl();
//            Message m = new Message();
//            m.setMessage_to(userto);
//            m.getMessage_from().setId(1);
//            m.setContent(content.getText());
//            m.setDate_message(new Date());
//            m.setViewed(0);
//            m.setAttachment("");
//     try {
//         cs.addMsg(m);
//     } catch (SQLException ex) {
//         Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
//     }
//        } while (!content.getText().equalsIgnoreCase("quit"));
//                    } catch (DeploymentException ex) {
//                        Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (URISyntaxException ex) {
//                        Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//        
//                    
// 
//                       }
//
//            });

        
    }
    
}
