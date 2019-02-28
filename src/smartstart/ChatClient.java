/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartstart;

import com.smartstart.controllers.ChatGuiController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author diabl
 */
public class ChatClient extends Application {

    private Stage primaryStage;

  
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Client");
         try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            System.err.println(getClass().getResource("/com/smartstart/gui/ChatGui.fxml")+"==========");
            loader.setLocation(getClass().getResource("/com/smartstart/gui/ChatGui.fxml"));
            
            AnchorPane chatLayout = (AnchorPane) loader.load();
            ChatGuiController controller = loader.getController();
            controller.login();
            
            Scene scene = new Scene(chatLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initChatLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/smartstart/gui/ChatGui.fxml"));
            System.err.println(getClass().getResource("/com/smartstart/gui/ChatGui.fxml"));
            AnchorPane chatLayout = (AnchorPane) loader.load();
            ChatGuiController controller = loader.getController();
            controller.login();
            Scene scene = new Scene(chatLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void stop() {
        System.out.println("Stop");
        ChatGuiController controller = new ChatGuiController();
        controller.logout();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
