package com.example.poof_ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class CurrentEvent extends AnchorPane{

    public CurrentEvent() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/CurrentEvent.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            AnchorPane root = fxmlLoader.getRoot();
            this.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
