package com.example.pryanik.controllers;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.services.PryanikService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы ввели некорректные данные");
            alert.showAndWait();
            return;
        }
        res = Double.parseDouble(mass_field.getText());
        fill_receipt_items_list(res);
        BeanContext.remove_bean("Modal Window Stage");
        stage.close();
    }

    boolean validate(String str){
        return PryanikService.isNumeric(str) && Double.parseDouble(str) > 0;
    }

    private void fill_receipt_items_list(double mass){
        List<ReceiptItem> items = BeanContext.get_bean("items_list");
        items.clear();
        String path_to_file = BeanContext.get_bean("path to file");
        try {
            var map = PryanikService.getReceipt(path_to_file);
            map = PryanikService.mass_counter(map, mass);
            for (var entry : map.entrySet()) {
                items.add(
                        new ReceiptItem(
                                entry.getKey(),
                                entry.getValue(),
                                "кг"
                        )
                );
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
