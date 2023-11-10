package com.example.pryanik.project.library;

import com.example.pryanik.BeanContext;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.enums.ThemeEnum;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ProjectFoundation {
    /**
     * create new window and register this in bean by name of title
     */
    public static void create_new_window_from_fxml(StageConfiguration configuration, String... path_to_stylesheets) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(configuration.getTitle());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(configuration.getPath_to_fxml()));
        Scene scene = new Scene(fxmlLoader.load());
        for(String path_to_stylesheet: path_to_stylesheets)
            scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource(path_to_stylesheet)).toExternalForm());
        stage.setScene(scene);
        stage.setResizable(configuration.isResizable());
        BeanContext.register_bean(configuration.getBean_name(), stage);
        if(configuration.isWait_termination())
            stage.showAndWait();
        else
            stage.show();
    }

    public static void maximizeStageWindow(Stage stage){
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setHeight(primaryScreenBounds.getHeight());
        stage.setWidth(primaryScreenBounds.getWidth());
    }

    public static void async(Runnable task){
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public static void show_modal_window_for_inputting_mass() throws IOException {
        ProjectFoundation.create_new_window_from_fxml(
                StageConfiguration.builder()
                        .title("Модальное окно")
                        .path_to_fxml("ModalWindow.fxml")
                        .bean_name("Modal Window Stage")
                        .show_and_wait()
                        .make_non_resizable()
                        .build(),
                "/com/example/pryanik/Main.css", BeanContext.<ThemeEnum>get_bean("Theme").path_to_css
        );
    }
}