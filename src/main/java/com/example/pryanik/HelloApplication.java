package com.example.pryanik;

import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.project.library.ProjectFoundation;
import com.example.pryanik.project.library.StageConfiguration;
import com.example.pryanik.services.ThemeSavingService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        BeanContext.register_bean("Theme", ThemeSavingService.saved_theme());
        ProjectFoundation.create_new_window_from_fxml(
                StageConfiguration.builder()
                    .title("Выберете файл!")
                    .path_to_fxml("ModalGreetingWindow.fxml")
                    .bean_name("Modal Greeting Window")
                    .make_non_resizable()
                    .build(),
                "/com/example/pryanik/Main.css",
                BeanContext.<ThemeEnum>get_bean("Theme").equals(ThemeEnum.DARK)?"/com/example/pryanik/DarkTheme.css":"",
                BeanContext.get_bean("Theme").equals(ThemeEnum.DEFAULT)?"/com/example/pryanik/Main.css":""
        );
    }

    public static void main(String[] args) {
        launch();
    }
}