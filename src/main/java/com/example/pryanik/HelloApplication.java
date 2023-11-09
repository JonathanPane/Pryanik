package com.example.pryanik;

import com.example.pryanik.controllers.MainPageController;
import com.example.pryanik.controllers.ModalGreetingWindowController;
import com.example.pryanik.enums.ThemeEnum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ModalGreetingWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BeanContext.register_bean("Theme", ThemeEnum.DEFAULT);
        scene.getStylesheets().add(HelloApplication.class.getResource("/com/example/pryanik/Main.css").toExternalForm());
        stage.setTitle("Пряникиии");
        stage.setResizable(false);
        stage.setScene(scene);
        BeanContext.register_bean("Modal Greeting Window", stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}