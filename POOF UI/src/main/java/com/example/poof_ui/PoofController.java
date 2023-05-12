package com.example.poof_ui;

// Imports

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    //List of profile pictures
    List<String> profilePictures = Arrays.asList(
            "ProfileImages/DaliMask.png"
            ,"ProfileImages/Mario.png"
            ,"ProfileImages/NinjaTurtle.png"
            ,"ProfileImages/PeterParker.png"
            ,"ProfileImages/RickSanchez.png"
            ,"ProfileImages/Sonic.png"
            ,"ProfileImages/Stitch.png"
            ,"ProfileImages/StormTrooper.png"
            ,"ProfileImages/Tom.png"
            ,"ProfileImages/WalterWhite.png"
    );
    // create an object of Random class
    Random random = new Random();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        // Initialize chart data
        series1 = new XYChart.Series();
        lineChart.getData().addAll(series1);
        // Initialize chart animation
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
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

            // Add a CurrentEvent
            CurrentEvent currentEvent = new CurrentEvent();
            currentEvents.getChildren().add(currentEvent);

            // Add traders
            TraderGUI traderGUI = new TraderGUI();
            tradersTile.getChildren().add(traderGUI);
            // Add trader's Profile Picture
            int randomTraderProfilePicture = random.nextInt(10);
            String currentTraderProfilePic = profilePictures.get(randomTraderProfilePicture);
            Image traderProfilePicture = new Image(getClass().getResourceAsStream((String) currentTraderProfilePic));
            traderGUI.setProfilePicture(traderProfilePicture);

            // Add miners
            MinerGUI minerGUI = new MinerGUI();
            minersTile.getChildren().add(minerGUI);

            // Add miner's Profile Picture
            int randomMinerProfilePicture = random.nextInt(10);
            String currentMinerProfilePic = profilePictures.get(randomMinerProfilePicture);
            Image minerProfilePicture = new Image(getClass().getResourceAsStream((String) currentMinerProfilePic));
            minerGUI.setProfilePicture(minerProfilePicture);

        }));

        // Set the timeline to repeat 500 times (10 minutes)
        timeline.setCycleCount(500);

        // Initialize UI elements
        graphViewScroll.setHvalue(0.5);
        graphViewScroll.setVvalue(0.5);



    }
    // Change image on the play button
    @FXML
    void startTimeline(ActionEvent event) {
        if (isPlaying) {
            // Change the image back to the play button
            Image playButton = new Image(getClass().getResourceAsStream("Icons/play_button.png"));
            play_image.setImage(playButton);
            // Stop the timeline
            timeline.stop();
        } else {
            // Change the image to the pause button
            Image pauseButton = new Image(getClass().getResourceAsStream("Icons/pause_button.png"));
            play_image.setImage(pauseButton);
            // Start the timeline
            timeline.play();
        }
        // Toggle the state of the button
        isPlaying = !isPlaying;
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
    }
}
