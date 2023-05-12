package com.example.poof_ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class TransactionBlock extends AnchorPane {

    public TransactionBlock() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/TransactionBlock.fxml"));
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
