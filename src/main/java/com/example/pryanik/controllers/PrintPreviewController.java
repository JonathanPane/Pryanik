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
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class PrintPreviewController {

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
        text_field.getItems().add(new PrintReceiptItemView("Имя", "Количество"));
        is_portrait = true;
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        quantity.setValueFactory(factory);
        List<ReceiptItem> item_list = BeanContext.get_bean("items_list");
        for (ReceiptItem receiptItem : item_list) {
            text_field.getItems().add(new PrintReceiptItemView(receiptItem));
        }
    }

    @FXML
    void print() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4,
                    is_portrait? PageOrientation.PORTRAIT:PageOrientation.LANDSCAPE,
                    0, 0, 0, 0);
            boolean success = printerJob.printPage(pageLayout, text_field);
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
