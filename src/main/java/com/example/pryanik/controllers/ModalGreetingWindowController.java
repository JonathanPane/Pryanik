package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.project.library.ProjectFoundation;
import com.example.pryanik.project.library.StageConfiguration;
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
import java.util.Objects;


public class ModalGreetingWindowController {
    private static FileChooser fileChooser;

    @FXML
    private TextField text_field;
    @FXML
    void initialize(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Файл рецепта","*.receipt"));

    }

    @FXML
    void cancel() {
        Stage stage = BeanContext.get_and_remove_bean("Modal Greeting Window");
        stage.close();
    }

    @FXML
    void ok() throws IOException {
        BeanContext.register_bean("path to file", text_field.getText());

        ProjectFoundation.create_new_window_from_fxml(
                StageConfiguration.builder()
                        .title("Пряникиии")
                        .bean_name("Main Page")
                        .path_to_fxml("MainPageView.fxml")
                        .build(),
                "/com/example/pryanik/Main.css"
        );

        ProjectFoundation.show_modal_window_for_inputting_mass();

        //ProjectFoundation.maximizeStageWindow(BeanContext.get_bean("Main Page"));

        BeanContext.<Stage>get_and_remove_bean("Modal Greeting Window").close();
    }

    @FXML
    void view_path() {
        File file = fileChooser.showOpenDialog(null);
        if(file != null)
            text_field.setText(file.getPath());
    }
}
