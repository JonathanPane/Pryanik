package com.example.pryanik.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class PrintPreviewController {

    @FXML
    private ToggleGroup orientation;

    @FXML
    private Spinner<?> quantity;

    @FXML
    private ListView<?> text_field;

    @FXML
    void print(MouseEvent event) {

    }

    @FXML
    void toggle_landscape(ActionEvent event) {

    }

    @FXML
    void toggle_portrait(ActionEvent event) {

    }

}
