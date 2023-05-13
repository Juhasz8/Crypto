package com.example.poof_ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CurrentEvent extends AnchorPane {
    private String eventName;
    private double probability;

    public CurrentEvent(String eventName, double probability) {
        this.eventName = eventName;
        this.probability = probability;

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

    @FXML
    private Label currentEventName;

    public void initialize() {
        currentEventName.setText(eventName);
    }

    public double getProbability() {
        return probability;
    }
}
