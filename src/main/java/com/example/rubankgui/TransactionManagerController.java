package com.example.rubankgui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionManagerController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}