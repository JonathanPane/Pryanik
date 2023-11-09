package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.UI.ReceiptItemView;
import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.services.PryanikService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Map;

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
    private Map<String, Double> edited_receipt;
    @FXML
    private ToggleGroup metric;

    @FXML
    private RadioMenuItem metric_kg;

    @FXML
    private RadioMenuItem metric_tonn;

    @FXML
    void initialize() throws IOException {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл рецепта", "*.receipt"));
        menu_bar.prefWidthProperty().bind(anchor_pane.widthProperty());
        scroll_pane.prefWidthProperty().bind(anchor_pane.widthProperty());
        scroll_pane.prefHeightProperty().bind(anchor_pane.heightProperty());
        receipt_output_content_pane.prefWidthProperty().bind(scroll_pane.widthProperty());
        receipt_output_content_pane.prefHeightProperty().bind(anchor_pane.heightProperty());
    }

    void show_receipt(Map<String, Double> receipt) throws IOException {
        receipt_output_content_pane.getChildren().clear();
        for (var entry : receipt.entrySet()) {
            if (entry.getValue() != 0) {
                if (metric_tonn.isSelected()) {
                    receipt_output_content_pane.getChildren().add(
                            new ReceiptItemView(
                                    entry.getKey(),
                                    String.format("%.7f %s", entry.getValue() / 1000, "т")
                            )
                    );
                }
                else receipt_output_content_pane.getChildren().add(
                        new ReceiptItemView(
                                entry.getKey(),
                                String.format("%.4f %s", entry.getValue(), "кг")
                        )
                );

            }
        }
    }
    @FXML
    void choose_receipt() throws IOException {
        File file = fileChooser.showOpenDialog(HelloApplication.stage);
        if(file != null) {
            double mass = get_mass();
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
        if(receipt_output_content_pane.getChildren().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не выбрали рецепт");
            alert.show();
            return;
        }

        File file = fileChooser.showSaveDialog(HelloApplication.stage);
        if(file != null) {
            file.createNewFile();
            PrintStream ps = new PrintStream(new FileOutputStream(file), true);
            if(metric_kg.isSelected())
                ps.println(PryanikService.map_to_string(edited_receipt));
            if(metric_tonn.isSelected())
                ps.println(PryanikService.map_to_string_tonn(edited_receipt));
            ps.close();
        }
    }
    @FXML
    void metric_kg() throws IOException {
        metric_kg.setToggleGroup(metric);
        receipt_output_content_pane.getChildren().clear();
        if(edited_receipt != null)
            show_receipt(edited_receipt);
    }

    @FXML
    void metric_tonn() throws FileNotFoundException {
        metric_tonn.setToggleGroup(metric);
        receipt_output_content_pane.getChildren().clear();
        if(edited_receipt != null) {
            for (var entry : edited_receipt.entrySet()) {
                if (entry.getValue() != 0)
                    receipt_output_content_pane.getChildren().add(
                            new ReceiptItemView(
                                    entry.getKey(),
                                    String.format("%.6f %s",entry.getValue() / 1000 , "т")
                            )
                    );
            }
        }
    }
    @FXML
    void pick_dark_theme() {
        HelloApplication
                .stage
                .getScene()
                .getStylesheets()
                .add(
                        HelloApplication.class.getResource(ThemeEnum.DARK.path_to_css).toExternalForm()
                );
        BeanContext.set_value_in_bean("Theme", ThemeEnum.DARK);
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

    double get_mass(){
        if(BeanContext.contains_bean("Масса"))
           return BeanContext.get_and_remove_bean("Масса");
        else return -1;
    }
    void show_modal_window() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ModalWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(HelloApplication.class.getResource("/com/example/pryanik/Main.css").toExternalForm());
        switch (BeanContext.<ThemeEnum>get_bean("Theme")){
            case DARK ->  scene.getStylesheets().add(HelloApplication.class.getResource(ThemeEnum.DARK.path_to_css).toExternalForm());
        }
        stage.setScene(scene);
        stage.setResizable(false);
        BeanContext.register_bean("Modal Window Stage", stage);
        stage.showAndWait();
    }
}
