package com.example.poof_ui;

// Imports

import java.io.IOException;
import java.net.URL;
import java.util.*;

import com.example.poof_ui.Blockchain_Side.SimulationManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PoofController implements Initializable {

    // UI elements
    @FXML
    private Label label_Years;
    @FXML
    private Label label_Weeks;
    @FXML
    private Label label_Months;
    @FXML
    private VBox currentEvents;
    @FXML
    private Tab graphView;
    @FXML
    private ScrollPane currentEventsScroll;
    @FXML
    private Button buttonBuy;
    @FXML
    private Button buttonSell;
    @FXML
    private Button restart_button;
    @FXML
    private ImageView play_image;
    @FXML
    private Button play_button;
    @FXML
    private ScrollPane graphViewScroll;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private ScrollPane chartScroll;

    @FXML
    private TilePane blockchain_Tile;
    @FXML
    private TilePane tradersTile;
    @FXML
    private TilePane minersTile;
    @FXML
    private Label marketPrice;

    // Chart data
    private XYChart.Series series1;
    private List<Double> lastTwoValues = new ArrayList<>();
    private boolean isPlaying = false;

    // Chart animation
    private Timeline timeline;

    // Timeline variables
    int yearsPassed = 0;
    int monthsPassed = 0;
    int weeksPassed = 0;

    // create an object of Random class
    Random random = new Random();

    public static PoofController instance;
    public CurrentEventManager eventManager = new CurrentEventManager();

    public int eventIndex = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        instance = this;


        // Initialize chart data
        series1 = new XYChart.Series();
        lineChart.getData().addAll(series1);
        // Initialize chart animation
        timeline = new Timeline(new KeyFrame(Duration.seconds(2.5), event -> {



            // Timeline Counter
            // Increment the years, months, and weeks passed
            weeksPassed ++;
            if (weeksPassed == 4){
                monthsPassed ++;
                weeksPassed = 0;
            }
            if (monthsPassed == 12){
                yearsPassed ++;
                monthsPassed = 0;
            }
            label_Years.setText(String.valueOf(yearsPassed));
            label_Months.setText(String.valueOf(monthsPassed));
            label_Weeks.setText(String.valueOf(weeksPassed));

            // Generate a new data point with a random value between 0 and 2
            double randomNumber = Math.random() * 2;
            series1.getData().add(new XYChart.Data<>(String.valueOf(series1.getData().size() + 1), randomNumber));

            // Keep track of the last two values in the chart
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

            // Add transaction blocks
            TransactionBlock transactionBlock = new TransactionBlock();
            blockchain_Tile.getChildren().add(transactionBlock);

            if(eventIndex == 1)
            {
                PotentiallyMakeEvent();
                eventIndex = 0;
            }
            else
                eventIndex++;

        }));

        // Set the timeline to repeat 500 times (10 minutes)
        timeline.setCycleCount(500);

        // Initialize UI elements
        graphViewScroll.setHvalue(0.5);
        graphViewScroll.setVvalue(0.5);

    }

    public void PotentiallyMakeEvent()
    {
        CurrentEvent event = eventManager.getEvent();
        if(event != null)
            currentEvents.getChildren().add(0, new CurrentEvent(event.GetName()));
    }

    private Thread simulationThread;

    // Change image on the play button
    @FXML
    void startTimeline(ActionEvent event) {
        if (isPlaying) {
            // Change the image back to the play button
            Image playButton = new Image(getClass().getResourceAsStream("Icons/play_button.png"));
            play_image.setImage(playButton);
            // Stop the timeline
            timeline.stop();

            SimulationManager.getInstance().SuspendSimulation();

        } else {
            // Change the image to the pause button
            Image pauseButton = new Image(getClass().getResourceAsStream("Icons/pause_button.png"));
            play_image.setImage(pauseButton);
            // Start the timeline
            timeline.play();

            if(simulationThread == null)
                StartSimulation();
            else
                SimulationManager.getInstance().ResumeSimulation();
        }
        // Toggle the state of the button
        isPlaying = !isPlaying;
    }

    public void AddMinerGUI()
    {
        // Add miners
        MinerGUI minerGUI = new MinerGUI();
        minersTile.getChildren().add(minerGUI);
    }

    public void AddTraderGUI()
    {
        // Add traders
        TraderGUI traderGUI = new TraderGUI();
        tradersTile.getChildren().add(traderGUI);
    }

    public void updateMarketPriceLabel(String newText) {
        Platform.runLater(() -> marketPrice.setText(newText));
    }

    public static PoofController getInstance()
    {
        return instance;
    }

    public void StartSimulation()
    {
        System.out.println("---------------------------------------------");
        simulationThread = new Thread(SimulationManager.getInstance());
        simulationThread.start();
        System.out.println("Simulation started!");
        System.out.println("---------------------------------------------");
    }

    @FXML
    void restartApplication(ActionEvent event) throws IOException {
        // Get the current stage
        Stage stage = (Stage) restart_button.getScene().getWindow();

        // Create a new instance of the application
        PoofInterface poofApp = new PoofInterface();

        //Set height and with of the stage
        stage.setHeight(1080);
        stage.setWidth(1920);

        // Call the start method of the new instance with the current stage
        poofApp.start(stage);
        //SimulationManager.getInstance().RestartSimulation();
    }
    @FXML
    void buyPoofs(ActionEvent event) throws IOException {
        // Opens the poof market modal in buy mode (tab)
    }
    @FXML
    void sellPoofs(ActionEvent event) throws IOException {
        // Opens the poof market modal in sell mode (tab)
    }
}
