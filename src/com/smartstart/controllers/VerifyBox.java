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
public class VerifyBox {
    
    public VerifyBox(){}
    
    public static void displayVerification(String title, String msg) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(title);
        dialog.setMinWidth(400);

        Label l = new Label(msg);
        Button o = new Button("oui");
        o.setOnAction(a -> dialog.close());
        Button n = new Button("non");
        n.setOnAction(a -> dialog.close());

        VBox v = new VBox(20);
        v.getChildren().addAll(l, o,n);
        v.setAlignment(Pos.CENTER);

        Scene s = new Scene(v);
        dialog.setScene(s);
        dialog.showAndWait();
    }
}
