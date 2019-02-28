package com.smartstart.controllers;

import com.smartstart.controllers.ServerGUIController;
import com.smartstart.entities.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {
    // a unique ID for each connection

    private static int uniqueId;
    // an ArrayList to keep the list of the Client
    private ArrayList<ClientThread> clientsConnected;
    // if I am in a GUI
    private ServerGUIController serverGUIController;
    // to display time
    private SimpleDateFormat sdf;
    // the port number to listen for connection
    private int port;
    // the boolean that will be turned of to stop the server
    private boolean keepGoing;


    /*
	 *  server constructor that receive the port to listen to for connection as parameter
     */
    public Server(int port) {
        this(port, null);
    }

    public Server(int port, ServerGUIController serverController) {
        // GUI or not
        this.serverGUIController = serverController;
        // the port
        this.port = port;
        // to display hh:mm:ss
        sdf = new SimpleDateFormat("HH:mm:ss");
        // ArrayList for the Client list
        clientsConnected = new ArrayList<ClientThread>();
    }

    public void start() {
        keepGoing = true;
        /* create socket server and wait for connection requests */
        try {
            // the socket used by the server
            ServerSocket serverSocket = new ServerSocket(port);

            // infinite loop to wait for connections
            while (keepGoing) {
                // format message saying we are waiting
                display("Server waiting for Clients on port " + port + ".");

                Socket socket = serverSocket.accept();  // accept connection
                // if I was asked to stop
                if (!keepGoing) {
                    break;
                }
                ClientThread t = new ClientThread(socket);  // make a thread of it
                clientsConnected.add(t); // save it in the ArrayList
                t.start();
            }
            // I was asked to stop
            try {
                serverSocket.close();
                for (int i = 0; i < clientsConnected.size(); ++i) {
                    ClientThread tc = clientsConnected.get(i);
                    try {
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    } catch (IOException ioE) {
                        // not much I can do
                        System.out.println("Server Start catch");
                    }
                }
            } catch (Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        } // something went bad
        catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
            display(msg);
        }
    }

    /*
	 * For the GUI to stop the server
     */
    public void stop() {
        keepGoing = false;
        // connect to myself as Client to exit statement 
        // Socket socket = serverSocket.accept();
        try {
            new Socket("localhost", port);
        } catch (Exception e) {
            // nothing I can really do
        }
    }

    /*
	 * Display an event (not a message) to the GUI
     */
    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        serverGUIController.appendEvent(time + "\n");
    }

    /*
	 *  to broadcast a message to all Clients
     */
    private synchronized void broadcast(Message message) {
        // add HH:mm:ss and \n to the message
        Message messageLf = new Message();
        if (message.getType() == 1 || message.getType() == 2) {
            messageLf = message;
            System.out.println(message.toString());
            serverGUIController.appendRoom(message.getMessage_from().getUsername() + " => "
                    + message.getMessage_to().getUsername() + " :" + message.getContent());
        } else {
            //messageLf = time + " " + message + "\n";
            System.out.println(message.toString());
            serverGUIController.appendRoom(message.getMessage_from().getUsername() + " => "
                    + message.getMessage_to().getUsername() + " :" + message.getContent()); // append in the room window
        }

        // we loop in reverse order in case we would have to remove a Client
        // because it has disconnected
        for (int i = clientsConnected.size(); --i >= 0;) {
            ClientThread ct = clientsConnected.get(i);
            // try to write to the Client if it fails remove it from the list
            if (!ct.writeMsg(messageLf)) {
                clientsConnected.remove(i);
                serverGUIController.remove(ct.username);
                display("Disconnected Client " + ct.username + " removed from list.");
            }
        }
    }

    // for a client who logoff using the LOGOUT message
    synchronized void remove(int id) {
        // scan the array list until we found the Id
        for (int i = 0; i < clientsConnected.size(); ++i) {
            ClientThread ct = clientsConnected.get(i);
            // found it
            if (ct.id == id) {
                serverGUIController.remove(ct.username);
                Message m = new Message();
                m.getMessage_from().setUsername(ct.username);
                m.setType(2);
                ct.writeMsg(m);
                clientsConnected.remove(i);
                return;
            }
        }
    }

    /**
     * One instance of this thread will run for each client
     */
    class ClientThread extends Thread {
        // the socket where to listen/talk

        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        // my unique id (easier for deconnection)
        int id;
        // the Username of the Client
        String username;
        // the only type of message a will receive
        Message cm;
        // the date I connect
        String date;

        // Constructore
        ClientThread(Socket socket) {
            // a unique id
            id = ++uniqueId;
            this.socket = socket;
            /* Creating both Data Stream */
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                // create output first
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                // read the username
                Message m = (Message) sInput.readObject();
                username = m.getMessage_from().getUsername();
                m.setType(0);
                serverGUIController.addUser(username);
                broadcast(m); //Broadcast user who logged in
                Message mm = new Message();
                mm.getMessage_from().setUsername(username);
                mm.setType(2);
                writeMsg(mm);
                System.out.println(mm.toString());
                for (ClientThread client : clientsConnected) {
                    System.out.println(client.cm.toString());
                    writeMsg(client.cm);
                }
                display(username + " just connected.");
            } catch (IOException e) {
                display("Exception creating new Input/output Streams: " + e);
                return;
            } // have to catch ClassNotFoundException
            // but I read a String, I am sure it will work
            catch (ClassNotFoundException e) {
            }
            date = new Date().toString() + "\n";
        }
        // what will run forever

        public void run() {
            // to loop until LOGOUT
            boolean keepGoing = true;
            while (keepGoing) {
                // read a String (which is an object)
                try {
                    cm = (Message) sInput.readObject();
                } catch (IOException e) {
                    display(username + " Exception reading Streams: " + e);
                    break;
                } catch (ClassNotFoundException e2) {
                    System.out.println("ClassNotFoundException :" + e2);
                    break;
                }
                // the messaage part of the ChatMessage
                Message message = cm;
                System.out.println("Type " + cm.getType());
                // Switch on the type of message receive
                switch (message.getType()) {

                    case 1:
                        broadcast(cm);
                        System.out.println("broadcast msg");
                        break;
                    case 2:
                        display(username + " disconnected with a LOGOUT message.");
                        broadcast(cm);
                        keepGoing = false;
                        break;
                }
            }
            // remove myself from the arrayList containing the list of the
            // connected Clients
            remove(id);
            close();
        }

        // try to close everything
        private void close() {
            // try to close the connection
            try {
                if (sOutput != null) {
                    sOutput.close();
                }
            } catch (Exception e) {
            }
            try {
                if (sInput != null) {
                    sInput.close();
                }
            } catch (Exception e) {
            };
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
            }
        }

        /*
		 * Write a String to the Client output stream
         */
        private boolean writeMsg(Message msg) {
            // if Client is still connected send the message to it
            if (!socket.isConnected()) {
                close();
                return false;
            }
            // write the message to the stream
            try {
                sOutput.writeObject(msg);
            } // if an error occurs, do not abort just inform the user
            catch (IOException e) {
                display("Error sending message to " + username);
                display(e.toString());
            }
            return true;
        }
    }
}
