package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

public class PrintPreviewController {

    @FXML
    private ToggleGroup orientation;

    @FXML
    private Spinner<Integer> quantity;

    @FXML
    private ListView<?> text_field;
    private boolean is_portrait;
    private final int i1 = 315, i2 = 445;

    @FXML
    void initialize(){
        is_portrait = true;
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        quantity.setValueFactory(factory);
    }

    @FXML
    void print() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4,
                    is_portrait? PageOrientation.PORTRAIT:PageOrientation.LANDSCAPE,
                    0, 0, 0, 0);
            boolean success = printerJob.printPage(pageLayout, BeanContext.get_bean("items_list"));
            if(success){
                printerJob.endJob();
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
