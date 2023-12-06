package com.example.pryanik.UI;

import com.example.pryanik.DTO.ReceiptItem;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;

public class PrintReceiptItemView extends HBox {
    private ReceiptItem receiptItem;

    public PrintReceiptItemView(ReceiptItem receiptItem) {
        this.receiptItem = receiptItem;
        Label name = new Label(receiptItem.getName());
        Label amount = new Label(receiptItem.getCount() + " " + receiptItem.getMetrics());
        this.getChildren().addAll(name, amount);
    }
    public PrintReceiptItemView(String name, String metrics){
        Label name2 = new Label(name);
        Label amount = new Label(metrics);
        this.getChildren().addAll(name2, amount);
    }
}
