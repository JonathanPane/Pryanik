package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.services.PryanikService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModalWindowController {

    @FXML
    private TextField mass_field;

    @FXML
    void cancel() {
        Stage stage = BeanContext.get_and_remove_bean("Modal Window Stage");
        stage.close();
    }

    @FXML
    void ok() {
        Stage stage = BeanContext.get_bean("Modal Window Stage");
        double res;
        if(!validate(mass_field.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы ввели некорретные данные");
            alert.showAndWait();
            return;
        }
        res = Double.parseDouble(mass_field.getText());
        BeanContext.register_bean("Масса", res);
        BeanContext.remove_bean("Modal Window Stage");
        stage.close();
    }
    boolean validate(String str){
        return PryanikService.isNumeric(str) && Double.parseDouble(str) > 0;
    }
}
