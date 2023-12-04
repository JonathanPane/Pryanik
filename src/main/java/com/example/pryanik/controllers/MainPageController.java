package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.UI.ReceiptItemView;
import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.project.library.ProjectFoundation;
import com.example.pryanik.project.library.StageConfiguration;
import com.example.pryanik.services.FileIOService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.Map;
import java.util.Objects;

public class MainPageController {
    private static FileChooser fileChooser;

    @FXML
    private AnchorPane anchor_pane;

    @FXML
    private ScrollPane scroll_pane;

    @FXML
    private FlowPane receipt_output_content_pane;

    @FXML
    private MenuBar menu_bar;

    private ObservableList<Node> receiptItemViews;

    private ObservableList<ReceiptItem> items;

    @FXML
    void initialize(){
        items = FXCollections.observableArrayList();
        receiptItemViews = receipt_output_content_pane.getChildren();

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл рецепта", "*.receipt"));
        setup_bindings();
        BeanContext.register_bean("items_list", items);
    }

    private void setup_bindings(){
        menu_bar.prefWidthProperty().bind(anchor_pane.widthProperty());
        scroll_pane.prefWidthProperty().bind(anchor_pane.widthProperty());
        scroll_pane.prefHeightProperty().bind(anchor_pane.heightProperty());
        receipt_output_content_pane.prefWidthProperty().bind(scroll_pane.widthProperty());
        receipt_output_content_pane.prefHeightProperty().bind(anchor_pane.heightProperty());
        binding_items_list_with_views_list();
    }

    private void binding_items_list_with_views_list(){
        items.addListener((ListChangeListener<? super ReceiptItem>) change -> {
            while (change.next()) {
                handle_adding_change(change);
                handle_removing_change(change);
            }
        });
    }

    private void handle_adding_change(ListChangeListener.Change<? extends ReceiptItem> change){
        if(!change.wasAdded())
            return;
        change.getAddedSubList().forEach(item -> receiptItemViews.add(ReceiptItemView.view_for(item)));
    }

    private void handle_removing_change(ListChangeListener.Change<? extends ReceiptItem> change){
        if(!change.wasRemoved())
            return;
        for (ReceiptItem receiptItem : change.getRemoved()) {
            receiptItemViews.removeIf(view -> ((ReceiptItemView) view).getReceiptItem() == receiptItem);
        }
    }


    @FXML
    void choose_receipt() throws IOException {
        File file = fileChooser.showOpenDialog(HelloApplication.stage);
        if(file != null) {
            BeanContext.set_value_in_bean("path to file", file.getPath());
            ProjectFoundation.show_modal_window_for_inputting_mass();
        }
    }

    @FXML
    void close_application() {
        HelloApplication.stage.close();
    }


    // TODO: не работает!
    @FXML
    void print_file() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            PageLayout pageLayout = printerJob.getPrinter().createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, 0, 0, 0, 0);
            boolean success = printerJob.printPage(pageLayout,receipt_output_content_pane);
            if(success){
                printerJob.endJob();
            }
        }
    }


    // TODO: рефакторить! много кода
    @FXML
    void save_file() throws IOException {
//        if(receipt_output_content_pane.getChildren().isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не выбрали рецепт");
//            alert.show();
//            return;
//        }
//
//        File file = fileChooser.showSaveDialog(HelloApplication.stage);
//        if(file != null) {
//            file.createNewFile();
//            PrintStream ps = new PrintStream(new FileOutputStream(file), true);
//            if(metric_kg.isSelected())
//                ps.println(PryanikService.map_to_string(edited_receipt));
//            if(metric_tonn.isSelected())
//                ps.println(PryanikService.map_to_string_tonn(edited_receipt));
//            ps.close();
//        }
        FileIOService.save_file(items);
    }

    @FXML
    void metric_kg() {
        for (ReceiptItem item : items) {
            item.toggle_kg();
        }
    }


    @FXML
    void metric_tonn() {
        for (ReceiptItem item : items) {
            item.toggle_t();
        }
    }

    @FXML
    void pick_dark_theme() {
        HelloApplication
                .stage
                .getScene()
                .getStylesheets()
                .add(
                        Objects.requireNonNull(HelloApplication.class.getResource(ThemeEnum.DARK.path_to_css)).toExternalForm()
                );
        BeanContext.set_value_in_bean("Theme", ThemeEnum.DARK);
    }

    @FXML
    void pick_lavender_theme() {

    }

    @FXML
    void pick_light_theme() {

    }

    @FXML
    void pick_spring_theme() {

    }
}
