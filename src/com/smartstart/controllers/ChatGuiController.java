/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import client.Client;
import com.smartstart.entities.Message;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ChatServiceImpl;
import com.smartstart.services.fos_userService;
import com.smartstart.util.GetConnectedUser;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class ChatGuiController{
         public static ArrayList<Thread> threads;

    @FXML
    private ListView<fos_user> AllFriendlyUsers;
    @FXML
    private ImageView Profile_pic;

    @FXML
    private ImageView pic1;
    @FXML
    private Label count;
    @FXML
    private TextField recherche;
    @FXML
    private ListView<Message> chatRoom;
    @FXML
    private TextField txtUserMsg;
    @FXML
    private Button btnSend;
    @FXML
    private Button exit;

    // Java FX Implementation
    private ListView<String> listUser;

    private ObservableList<String> users;

    fos_userService fs = new fos_userService();
    fos_user Connected;
    
  


   

    // Server Configuration
    private boolean connected;
    private String server;
    @FXML
    Label username;
    private int port;
    // for I/O
    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private Socket socket;

    ObservableList<fos_user> dataFriendlyUser;
    ObservableList<Message> dataMessage;
    ObservableList<fos_user> dataConnectedUser;
    @FXML
    private GridPane gridPaneChat;

    public void initialize() throws SQLException, IOException {
          try {
           Connected = UserSession.getInstance(new fos_user()).getUser();
    
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        threads = new ArrayList<Thread>();
       /* TextField portTextField = new TextField("8888");
        System.out.println(threads.size());
        server.Server server = new server.Server(Integer.parseInt(portTextField
							.getText()));
					Thread serverThread = (new Thread(server));
					serverThread.setName("Server Thread");
					serverThread.setDaemon(true);
                                        if(!serverThread.isAlive()){
					serverThread.start();
                                        }
					threads.add(serverThread);*/

	Client client;	
        TextField nameField = new TextField(Connected.getUsername());
		TextField hostNameField = new TextField("localhost");
		TextField portNumberField = new TextField("8888");
                hostNameField.setDisable(true);
                portNumberField.setDisable(true);
                nameField.setDisable(true);
                 client = new Client(hostNameField.getText(), Integer
                         .parseInt(portNumberField.getText()), nameField
                                 .getText());
					Thread clientThread = new Thread(client);
					clientThread.setDaemon(true);
					clientThread.start();
					threads.add(clientThread);
                                        /* Make the root pane and set properties */
		gridPaneChat.setPadding(new Insets(20));
		gridPaneChat.setAlignment(Pos.CENTER);
		gridPaneChat.setHgap(10);
		gridPaneChat.setVgap(10);

		/*
		 * Make the Chat's listView and set it's source to the Client's chatLog
		 * ArrayList
		 */
		ListView<String> chatListView = new ListView<String>();
		chatListView.setItems(client.chatLog);
                chatListView.setPrefHeight(420);
                chatListView.setPrefWidth(846.0);

		/*
		 * Make the chat text box and set it's action to send a message to the
		 * server
		 */
		TextField chatTextField = new TextField();
		chatTextField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				client.writeToServer(chatTextField.getText());
				chatTextField.clear();
			}
		});

		/* Add the components to the root pane */
		gridPaneChat.add(chatListView, 0, 0);
		gridPaneChat.add(chatTextField, 0, 1);

		/* Make and return the scene */
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("======================");
        ChatServiceImpl ms = new ChatServiceImpl();
        dataFriendlyUser = FXCollections.observableArrayList();
        dataConnectedUser = FXCollections.observableArrayList();
        ObservableList<fos_user> ls = ms.discussionList(Connected.getId());
        System.err.println(ls.size());
        users = FXCollections.observableArrayList();
        users.stream().forEach((j) -> {
            ls.stream().forEach((l) -> {
                if (l.getUsername().equals(j)) {
                    dataConnectedUser.add(l);
                }
            });
        });
        ls.stream().forEach((j) -> {
            dataFriendlyUser.add(j);
        });
        AllFriendlyUsers.setItems(dataFriendlyUser);
        AllFriendlyUsers.setCellFactory(new Callback<ListView<fos_user>, ListCell<fos_user>>() {
            @Override
            public ListCell<fos_user> call(ListView<fos_user> param) {
                return new ChatDiscCellController();
            }
        });
        AllFriendlyUsers.getSelectionModel().selectedItemProperty().addListener((a, b, c) -> {
            if (c != null) {
                GetMessagess(c);
            }

        });
        AllFriendlyUsers.getSelectionModel().selectFirst();
    }

    /**
     * Method used by btnLogin from Java FX
     */
    public void login() {
        System.out.println("*******************");
        port = 1500;
        server = "localhost";
        System.out.println(server);
        //username = GetConnectedUser.GetConnectedUser();
       //s username = Connected;
        // test if we can start the connection to the Server
        // if it failed nothing we can do
        if (!start()) {
            System.out.println("error2");
            return;
        }
        connected = true;

    }

    /**
     * Method used by btnLogin from Java FX
     */
    public void login(fos_user u) {
        port = 1500;
        server = "localhost";
      //  username = u;
        // test if we can start the connection to the Server
        // if it failed nothing we can do
        if (!start()) {
            System.out.println("error2");
            return;
        }
        connected = true;

    }

    /**
     * Method used by btnLogout from Java FX
     */
    public void logout() {
        System.out.println(connected);
        System.out.println("logout");
        Message msg = new Message();
        msg.setType(Message.LOGOUT);
        msg.setContent("");
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            System.out.println("Exception writing to server: ");
        }

    }

    /**
     * To send a message to the server
     */
    @FXML
    public void sendMessage() throws SQLException {
        if (connected) {
            ChatServiceImpl ms = new ChatServiceImpl();
            Message m = new Message();
            m.setMessage_from(GetConnectedUser.GetConnectedUser());
            fos_user u;
            u = AllFriendlyUsers.getSelectionModel().getSelectedItem();
            if (u != null) {
                System.out.println("usersss==========s");
                m.setMessage_to(u);
                m.setContent(txtUserMsg.getText());
                m.setType(Message.MESSAGE);
                m.setDate_message(new Date());
                System.out.println(m.toString());
                ms.addMsg(m);
                txtUserMsg.setText("");
                
                try {
                    System.out.println("msg sent");
                    sOutput.writeObject(m);
                    dataFriendlyUser.remove(m.getMessage_to());
                    dataFriendlyUser.add(m.getMessage_to());
                    chatRoom.getItems().add(m);
                } catch (IOException e) {
                    System.out.println("Exception writing to server: " + e);
                }
            } else {
                System.err.println("there is no connected user");
            }
        }
    }

    /**
     * Sends message to server Used by TextArea txtUserMsg to handle Enter key
     * event
     */
    public void handleEnterPressed(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
            event.consume();
        }
    }

    /**
     * To start the dialog
     */
    public boolean start() {
        // try to connect to the server
        try {
            socket = new Socket(server, port);
        } // if it failed not much I can so
        catch (Exception ec) {
            System.err.println("Error connectiong to server:" + ec);
            return false;
        }
        String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        System.out.println(msg);

        /* Creating both Data Stream */
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            System.out.println("Exception creating new Input/output Streams: " + eIO);
            return false;
        }
        // creates the Thread to listen from the server 
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try {
            Message m = new Message();
           // m.setMessage_from(username);
            m.setType(0);
            sOutput.writeObject(m);
        } catch (IOException eIO) {
            System.out.println("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // success we inform the caller that it worked
        return true;
    }

    /*
     * When something goes wrong
     * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
        try {
            if (sInput != null) {
                sInput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (sOutput != null) {
                sOutput.close();
            }
        } catch (Exception e) {
        } // not much else I can do
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        } // not much else I can do

        // inform the GUI
        connectionFailed();

    }

    public void connectionFailed() {
        // don't react to a <CR> after the username
        connected = false;
    }

    private void GetMessagess(fos_user c) {
        try {
            ChatServiceImpl ms = new ChatServiceImpl();
            dataMessage = FXCollections.observableArrayList();
            List<Message> ls = ms.getMessages(Connected.getId(), c.getId());
            ls.stream().forEach((j) -> {
                dataMessage.add(j);
            });
            chatRoom.getItems().clear();
            chatRoom.setItems(dataMessage);
            chatRoom.setCellFactory((ListView<Message> param) -> new MessageCellGuiController());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    /*
     * a class that waits for the message from the server and append them to the JTextArea
     * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread {

        //   ObservableList<Utilisateur> dataFriendlyUser;
        public void run() {
            System.err.println("i got message");
            //listUser.setItems(users);
            while (true) {
                try {
                    Message msg = (Message) sInput.readObject();
                    int split = msg.getType();
                    if (split == 0) {
                        Platform.runLater(() -> {
                            users.add(msg.getMessage_from().getUsername());
                        });;
                    } else if (split == 2) {
                        Platform.runLater(() -> {
                            users.remove(msg.getMessage_from().getUsername());
                        });
                    } else {
                        System.err.println(msg.toString());
                        if (msg.getMessage_to().getId() == GetConnectedUser.GetConnectedUser().getId()) {
                            System.err.println(msg.getContent() + " yeaaah");
                            dataMessage.add(msg);
                            String bip = System.getProperty("user.dir") + "/src/utils/message.mp3";
                            //Media hit = new Media(new File(bip).toURI().toString());
                            //MediaPlayer mediaPlayer = new MediaPlayer(hit);
                            //mediaPlayer.play();
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Server has close the connection");
                    e.printStackTrace();
                    connectionFailed();
                    Platform.runLater(() -> {
                        listUser.setItems(null);
                    });
                    break;
                } // can't happen with a String object but need the catch anyhow
                catch (ClassNotFoundException e2) {

                }
            }
        }
    }
      
     @FXML
      public void annuler(ActionEvent event) throws IOException
      {
          Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
      }
}
