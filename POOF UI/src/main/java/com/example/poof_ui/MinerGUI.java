package com.example.poof_ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MinerGUI extends AnchorPane {

    public MinerGUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/MinerGUI.fxml"));
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
    private ImageView minerPp;

    public void setProfilePicture(Image image) {
        minerPp.setImage(image);
    }
}
