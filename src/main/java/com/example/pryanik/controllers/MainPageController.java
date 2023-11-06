package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.services.PryanikService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public class MainPageController {
    private static FileChooser fileChooser;
    @FXML
    private AnchorPane anchor_pane;
    @FXML
    private FlowPane receipt_output_content_pane;
    @FXML
    private MenuBar menu_bar;
    private Map<String, Double> edited_receipt;
    @FXML
    private ToggleGroup metric;

    @FXML
    private RadioMenuItem metric_kg;

    @FXML
    private RadioMenuItem metric_tonn;

    @FXML
    void initialize(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл рецепта", "*.receipt"));
        menu_bar.prefWidthProperty().bind(anchor_pane.widthProperty());
    }
    void show_receipt(Map<String, Double> receipt){
        receipt_output_content_pane.getChildren().clear();
        for (var entry : receipt.entrySet()) {
            if (entry.getValue() != 0)
                receipt_output_content_pane.getChildren().add(
                            new Button(
                                    String.format("%s - %.3f кг", entry.getKey(), entry.getValue())
                            )
                    );
        }
    }
    @FXML
    void choose_receipt() throws IOException {
        File file = fileChooser.showOpenDialog(HelloApplication.stage);
        if(file != null) {
            double mass = show_modal_window_and_get_mass();
            if(mass < 0)
                return;
            var receipt = PryanikService.getReceipt(file.getPath());
            receipt = PryanikService.mass_counter(receipt, mass);
            show_receipt(receipt);
            this.edited_receipt = receipt;
        }
    }
    @FXML
    void close_application() {
        HelloApplication.stage.close();
    }
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

    @FXML
    void save_file() throws IOException {
        File file = fileChooser.showSaveDialog(HelloApplication.stage);
        if(file != null) {
            file.createNewFile();
            PrintStream ps = new PrintStream(new FileOutputStream(file), true);
            ps.println(PryanikService.map_to_string(edited_receipt));
            ps.close();
        }
    }
    @FXML
    void metric_kg() {
//        metric_kg.setToggleGroup(metric);
//        if(edited_receipt != null)
//            show_receipt(edited_receipt);
    }

    @FXML
    void metric_tonn() {
//        metric_tonn.setToggleGroup(metric);
//        receipt_output_content_pane.getChildren().clear();
//        if(edited_receipt != null) {
//            for (var entry : edited_receipt.entrySet()){
//                if(entry.getValue() != 0)
//                    receipt_output_content_pane.getChildren().add(
//                        new Button(
//                                String.format("%s %.5f т", entry.getKey(), entry.getValue() / 1000)
//                        )
//                );
//            }
//        }
    }
    @FXML
    void pick_dark_theme() {
        HelloApplication
                .stage
                .getScene()
                .getStylesheets()
                .add(
                        HelloApplication.class.getResource("/com/example/pryanik/DarkTheme.css").toExternalForm()
                );
    }

    @FXML
    void pick_lavander_theme() {

    }

    @FXML
    void pick_light_theme() {

    }

    @FXML
    void pick_spring_theme() {

    }

    double show_modal_window_and_get_mass() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ModalWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        BeanContext.register_bean("Modal Window Stage", stage);
        stage.showAndWait();
        if(BeanContext.contains_bean("Масса"))
           return BeanContext.get_and_remove_bean("Масса");
        else return -1;
    }

}
