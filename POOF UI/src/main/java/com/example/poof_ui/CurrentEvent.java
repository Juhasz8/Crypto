package com.example.poof_ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CurrentEvent extends AnchorPane {
    private String eventName;
    private double probability;

    // Constructor to create a CurrentEvent object
    public CurrentEvent(String eventName, double probability) {
        this.eventName = eventName;
        this.probability = probability;

        // Create a new FXMLLoader for loading the FXML file and setting the controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/CurrentEvent.fxml"));
        fxmlLoader.setController(this);

        try {
            // Load the FXML file and retrieve the root AnchorPane
            fxmlLoader.load();
            AnchorPane root = fxmlLoader.getRoot();

            // Add the root pane to the children of the CurrentEvent object
            this.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label currentEventName;

    // Method called when the FXML is initialized
    public void initialize() {
        // Set the text of the currentEventName label to the eventName
        currentEventName.setText(eventName);
    }

    // Getter method for probability
    public double getProbability() {
        return probability;
    }
}
