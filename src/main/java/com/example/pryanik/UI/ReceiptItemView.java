package com.example.pryanik.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ReceiptItemView extends VBox {
    private ImageView image_view;
    private Label title;
    private Label metric;
    public ReceiptItemView(String title, String metric, String image_name) throws FileNotFoundException {
        this.title = new Label(title);
        this.title.setFont(new Font("Comic Sans", 15));
        this.title.setAlignment(Pos.CENTER);
        this.metric = new Label(metric);
        this.metric.setFont(new Font("Comic Sans", 20));
        super.setAlignment(Pos.CENTER);
        Image image = new Image(new FileInputStream(image_name));
        this.image_view = new ImageView(image);
        image_view.setFitHeight(100);
        image_view.setFitWidth(100);
        place_data_on_view();
        getStyleClass().add("receipt-item-view");
    }
     private void place_data_on_view(){
        super.getChildren().add(title);
        super.getChildren().add(image_view);
        super.getChildren().add(metric);
    }
}
