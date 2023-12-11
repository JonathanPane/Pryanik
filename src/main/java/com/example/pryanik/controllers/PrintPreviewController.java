package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.UI.PrintReceiptItemView;
import com.example.pryanik.UI.ReceiptItemView;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

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
    private TableColumn<PrintReceiptItemView, String> name_pryanik;

    @FXML
    private Spinner<Integer> quantity;
    @FXML
    private Button print_button;

    @FXML
    private TableView<PrintReceiptItemView> text_field_tableview;
    private boolean is_portrait;
    private final int i1 = 315, i2 = 445;
    private final double i3 = 30;

    @FXML
    void initialize(){
        name_pryanik.setText(new File(BeanContext.<String>get_bean("path to file")).getName().replace(".receipt",
                ""));
        mass.setCellValueFactory(new PropertyValueFactory<>("amount"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TextFormatter<Integer> priceFormatter = getIntegerTextFormatter();
        quantity.getEditor().setTextFormatter(priceFormatter);
        text_field_tableview.setItems(BeanContext.get_bean("items_list"));
        is_portrait = true;
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1);
        quantity.setValueFactory(factory);
    }


    private static TextFormatter<Integer> getIntegerTextFormatter() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> formatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 0, filter);
        return formatter;
    }

    @FXML
    void print() throws IOException {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4,
                is_portrait ? PageOrientation.PORTRAIT : PageOrientation.LANDSCAPE,
                Printer.MarginType.HARDWARE_MINIMUM);
        text_field_tableview.setPrefHeight(i3 * text_field_tableview.getItems().size());
        printerJob.getJobSettings().setPageLayout(pageLayout);
        if (printerJob != null) {
            for (var i = 1; i <= quantity.getValue(); i++) {
                printerJob.printPage(pageLayout, text_field_tableview);
            }
            printerJob.endJob();
            text_field_tableview.getTransforms().clear();
        }
        if (is_portrait) {
            toggle_portrait();
        } else {
            toggle_landscape();
        }
    }


    @FXML
    void toggle_landscape() {
        is_portrait = false;
        text_field_tableview.setPrefWidth(i2);
        text_field_tableview.setPrefHeight(i1);
    }

    @FXML
    void toggle_portrait() {
        is_portrait = true;
        text_field_tableview.setPrefWidth(i1);
        text_field_tableview.setPrefHeight(i2);
    }
}
