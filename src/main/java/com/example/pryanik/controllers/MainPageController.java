package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.UI.ReceiptItemView;
import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.project.library.ProjectFoundation;
import com.example.pryanik.project.library.StageConfiguration;
import com.example.pryanik.services.FileIOService;
import com.example.pryanik.services.ThemeSavingService;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Scanner;

public class MainPageController {
    private static FileChooser fileChooser;

    @FXML
    private AnchorPane anchor_pane;

    @FXML
    private ScrollPane scroll_pane;
    @FXML
    private ToggleGroup metric;
    @FXML
    private MenuItem guide;

    @FXML
    private FlowPane receipt_output_content_pane;

    @FXML
    private MenuBar menu_bar;

    private ObservableList<Node> receiptItemViews;

    private ObservableList<ReceiptItem> items;
    @FXML
    private RadioMenuItem metric_kg;
    @FXML
    private RadioMenuItem metric_tonn;
    private boolean selected = false;
    @FXML
    private Label pryanik_name;

    @FXML
    void initialize(){
        items = FXCollections.observableArrayList();
        receiptItemViews = receipt_output_content_pane.getChildren();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл рецепта", "*.receipt"));
        setup_bindings();
        BeanContext.register_bean("items_list", items);
        BeanContext.register_bean("metric kg", metric_kg);
        pryanik_name.setText(new File(BeanContext.<String>get_bean("path to file")).getName().replace(".receipt",
                ""));
    }

    private void setup_bindings(){
        menu_bar.prefWidthProperty().bind(anchor_pane.widthProperty());
        pryanik_name.setAlignment(Pos.TOP_CENTER);
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
        FileIOService.open_file();
        pryanik_name.setText(new File(BeanContext.<String>get_bean("path to file")).getName().replace(".receipt",
                ""));
    }

    @FXML
    void close_application() {
        BeanContext.<Stage>get_bean("Main Page").close();
    }


    @FXML
    void print_file() throws IOException {
        ProjectFoundation.create_new_window_from_fxml(StageConfiguration.builder()
                .path_to_fxml("PrintPreview.fxml")
                .make_non_resizable()
                .set_modality()
                .title("Предпросмотр печати")
                .show_and_wait()
                .build(),
                "/com/example/pryanik/Main.css",
                BeanContext.<ThemeEnum>get_bean("Theme").equals(ThemeEnum.DARK)?"/com/example/pryanik/DarkTheme.css":"",
                BeanContext.get_bean("Theme").equals(ThemeEnum.DEFAULT)?"/com/example/pryanik/Main.css":""
        );
    }


    @FXML
    void save_file() throws IOException {
        FileIOService.save_file(items);
    }

    @FXML
    void metric_kg() {
        if(selected)
            for (ReceiptItem item : items) {
                item.toggle_kg();
            }
        selected = false;
    }


    @FXML
    void metric_tonn() {
        if(!selected)
            for (ReceiptItem item : items) {
                item.toggle_t();
            }
        selected = true;
    }

    @FXML
    void pick_dark_theme() throws IOException {
        BeanContext.<Stage>get_bean("Main Page").getScene().getStylesheets().clear();
        BeanContext.<Stage>get_bean("Main Page")
                .getScene()
                .getStylesheets()
                .add(
                        Objects.requireNonNull(HelloApplication.class.getResource(ThemeEnum.DARK.path_to_css)).toExternalForm()
                );
        BeanContext.set_value_in_bean("Theme", ThemeEnum.DARK);
        ThemeSavingService.save_theme();
    }



    @FXML
    void pick_light_theme() throws IOException {
        BeanContext.<Stage>get_bean("Main Page").getScene().getStylesheets().clear();
        BeanContext.<Stage>get_bean("Main Page")
                .getScene()
                .getStylesheets()
                .add(
                        Objects.requireNonNull(HelloApplication.class.getResource(ThemeEnum.DEFAULT.path_to_css)).toExternalForm()
                );
        BeanContext.set_value_in_bean("Theme", ThemeEnum.DEFAULT);
        ThemeSavingService.save_theme();
    }

    @FXML
    void guide() throws IOException, URISyntaxException {
        String Mydocs = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        File file1 = new File(Mydocs + "/Pryanik");
        if(!file1.exists())
            file1.mkdirs();
        File file = new File(Mydocs +"/Pryanik/Guide.rtf");
        if(!file.exists()){
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(file),true);
            Scanner scanner = new Scanner(HelloApplication.class.getResourceAsStream("/Guide/Guide.rtf"));
            while (scanner.hasNextLine())
                printWriter.println(scanner.nextLine());
            scanner.close();
            printWriter.close();
        }
        Desktop.getDesktop().open(file);
    }
}
