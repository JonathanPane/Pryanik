package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.enums.ThemeEnum;
import com.example.pryanik.project.library.ProjectFoundation;
import com.example.pryanik.project.library.StageConfiguration;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class ModalGreetingWindowController {
    private static FileChooser fileChooser;

    @FXML
    private TextField text_field;
    @FXML
    private AnchorPane anchor_pane_greeting;
    @FXML
    private Button cancel;
    @FXML
    private Label label_greeting;
    @FXML
    private Button ok;
    @FXML
    private Label file_label;
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
        if(!exists_file_on_path(text_field.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Неверно задан путь к файлу");
            alert.showAndWait();
            return;
        }
        BeanContext.register_bean("path to file", text_field.getText());
        ProjectFoundation.create_new_window_from_fxml(
                StageConfiguration.builder()
                        .title("Пряникиии")
                        .bean_name("Main Page")
                        .path_to_fxml("MainPageView.fxml")
                        .make_resize_min()
                        .min_dimension(640,480)
                        .build(),
                "/com/example/pryanik/Main.css",
                BeanContext.<ThemeEnum>get_bean("Theme").equals(ThemeEnum.DARK)?"/com/example/pryanik/DarkTheme.css":"",
                BeanContext.get_bean("Theme").equals(ThemeEnum.DEFAULT)?"/com/example/pryanik/Main.css":""
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
    boolean exists_file_on_path(String path){
        return new File(path).exists();
    }
}
