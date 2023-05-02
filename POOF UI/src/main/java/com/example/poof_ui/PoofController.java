package com.example.poof_ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PoofController implements Initializable {
    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private ScrollPane chartScroll;

    private XYChart.Series series1;

    private Timeline timeline;

    private List<Double> lastTwoValues = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a new XYChart series
        series1 = new XYChart.Series();
        // Add the new series to the line chart
        lineChart.getData().addAll(series1);


        // Create a new timeline with a KeyFrame that adds a new data point to the chart every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            double randomNumber = Math.random() * 2;
            series1.getData().add(new XYChart.Data<>(String.valueOf(series1.getData().size() + 1), randomNumber));

            // Add the new value to the list of last two values and remove the oldest one if the list size exceeds 2
            lastTwoValues.add(randomNumber);
            if (lastTwoValues.size() > 2) {
                lastTwoValues.remove(0);
            }

            // Change the color of the line according to if it's more or less
            String redColor = "-fx-stroke: #FF0000;";
            String greenColor = "-fx-stroke: #73B902;";
            if (lastTwoValues.size() == 2) {
                if (lastTwoValues.get(1) > lastTwoValues.get(0)) {
                    series1.getNode().setStyle(greenColor);
                } else {
                    series1.getNode().setStyle(redColor);
                }
            }


            // Increase the preferred width of the chart by 30 pixels
            double currentPrefWidth = lineChart.getPrefWidth();
            lineChart.setPrefWidth(currentPrefWidth + 30);

            // Scroll the chart to the right to show the latest data point
            chartScroll.setHvalue(1);

        }));

        // Set the timeline to repeat 100 times
        timeline.setCycleCount(500);
        // Start the timeline
        timeline.play();
    }

    public void setStage(Stage stage) {
        // ...
    }

    @FXML
    void closeProgram(ActionEvent event) {
        // Stop the timeline when the program is closed
        timeline.stop();
    }
}
