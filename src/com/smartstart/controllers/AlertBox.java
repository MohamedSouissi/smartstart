package com.smartstart.controllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author mohamed
 */
public class AlertBox {
    
    public AlertBox(){}

    public static void displayError(String title, String msg) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(title);
        dialog.setMinWidth(400);

        Label l = new Label(msg);
        Button b = new Button("close");
        b.setOnAction(a -> dialog.close());

        VBox v = new VBox(20);
        v.getChildren().addAll(l, b);
        v.setAlignment(Pos.CENTER);

        Scene s = new Scene(v);
        dialog.setScene(s);
        dialog.showAndWait();
    }
}
