package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.UI.PrintReceiptItemView;
import com.example.pryanik.UI.ReceiptItemView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class PrintPreviewController {
    @FXML
    private AnchorPane anchor_pane_print;
    @FXML
    private TableColumn<ReceiptItem, String> mass;

    @FXML
    private TableColumn<ReceiptItem, String> name;


    @FXML
    private ToggleGroup orientation;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private TableView<PrintReceiptItemView> text_field;
    private boolean is_portrait;
    private final int i1 = 315, i2 = 445;

    @FXML
    void initialize(){
        mass.setCellValueFactory(new PropertyValueFactory<>("amount"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        text_field.setItems(BeanContext.get_bean("items_list"));
        is_portrait = true;
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        quantity.setValueFactory(factory);
    }
    @FXML
    void print() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            for (var i = 1; i <= quantity.getValue(); i++) {
                PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4,
                        is_portrait ? PageOrientation.PORTRAIT : PageOrientation.LANDSCAPE,
                        0, 0, 0, 0);
                boolean success = printerJob.printPage(pageLayout, text_field);
                if (success) {
                    printerJob.endJob();
                }
            }
        }
}

    @FXML
    void toggle_landscape() {
        is_portrait = false;
        text_field.setPrefWidth(i2);
        text_field.setPrefHeight(i1);
    }

    @FXML
    void toggle_portrait() {
        is_portrait = true;
        text_field.setPrefWidth(i1);
        text_field.setPrefHeight(i2);
    }

}
