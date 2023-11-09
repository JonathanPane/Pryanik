package com.example.pryanik;

import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.project.library.ProjectFoundation;
import com.example.pryanik.project.library.StageConfiguration;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        BeanContext.register_bean("Theme", ThemeEnum.DEFAULT);
        ProjectFoundation.create_new_window_from_fxml(
                StageConfiguration.builder()
                    .title("Input the file!")
                    .path_to_fxml("ModalGreetingWindow.fxml")
                    .bean_name("Modal Greeting Window")
                    .make_non_resizable()
                    .build(),
                "/com/example/pryanik/Main.css"
        );
    }

    public static void main(String[] args) {
        launch();
    }
}