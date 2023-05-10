package com.example.poof_ui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class TransactionBlock extends AnchorPane {

    public TransactionBlock() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TransactionBlock.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            AnchorPane root = fxmlLoader.getRoot();
            this.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add any additional methods or properties for the component

}
