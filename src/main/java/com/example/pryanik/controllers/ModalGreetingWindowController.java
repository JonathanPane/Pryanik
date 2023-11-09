package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.enums.ThemeEnum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class ModalGreetingWindowController {
    private static FileChooser fileChooser;

    @FXML
    private TextField text_field;
    @FXML
    void initialize(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл рецетпа","*.receipt"));

    }

    @FXML
    void cancel() {
        Stage stage = BeanContext.get_and_remove_bean("Modal Greeting Window");
        stage.close();
    }

    @FXML
    void ok() throws IOException {
        BeanContext.register_bean("path to file", text_field.getText());
        Stage stage = new Stage();
        HelloApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainPageView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BeanContext.register_bean("Theme", ThemeEnum.DEFAULT);
        scene.getStylesheets().add(HelloApplication.class.getResource("/com/example/pryanik/Main.css").toExternalForm());
        stage.setTitle("Пряникиии");
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setScene(scene);
        stage.show();
        BeanContext.<Stage>get_and_remove_bean("Modal Greeting Window").close();
    }

    @FXML
    void view_path() {
        File file = fileChooser.showOpenDialog(null);
        if(file != null)
            text_field.setText(file.getPath());
    }
}
