package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.UI.PrintReceiptItemView;
import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.services.PryanikService;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
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
    private Label pryanik_name_printpreview;
    @FXML
    private ToggleGroup orientation;

    @FXML
    private Spinner<Integer> quantity;
    @FXML
    private Button print_button;

    @FXML
    private TableView<PrintReceiptItemView> text_field_tableview;
    private boolean is_portrait;
    private final int i1 = 315, i2 = 445;

    @FXML
    void initialize(){
        pryanik_name_printpreview.setText(new File(BeanContext.<String>get_bean("path to file")).getName().replace(".receipt",
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
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 0, filter);
        return priceFormatter;
    }

    @FXML
    void print() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
                for (var i = 1; i <= quantity.getValue(); i++) {
                PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4,
                        is_portrait ? PageOrientation.PORTRAIT : PageOrientation.LANDSCAPE,
                        0, 0, 0, 0);
                boolean success = printerJob.printPage(pageLayout, text_field_tableview);
                if (success) {
                    printerJob.endJob();
                }
            }
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
