package com.example.pryanik.UI;

import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.HelloApplication;
import com.example.pryanik.enums.IngredientsEnum;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.text.DecimalFormat;
import java.util.Objects;

public class ReceiptItemView extends VBox {
    private IngredientsEnum ingredients_type;
    private ReceiptItem receiptItem;

    private ReceiptItemView(ReceiptItem item) {
        this.receiptItem = item;
        define_ingredient_type_by_name(item.getName());

        Label title_label = new Label();
        title_label.textProperty().bind(item.nameProperty());
        title_label.setFont(new Font("Comic Sans", 15));
        title_label.setAlignment(Pos.CENTER);

        Label metric_label = new Label();
        metric_label.setText(item.getCount() + " " + item.getMetrics());
        item.metricsProperty().addListener((o, old_value, new_value) -> metric_label.textProperty().set(
                new DecimalFormat("0.#########").format(item.getCount()) + " " + new_value
        ));
        item.countProperty().addListener((o, old_value, new_value) -> metric_label.textProperty().set(
                new DecimalFormat("0.#########").format(new_value) + " " + item.getMetrics()
        ));
        metric_label.setFont(new Font("Comic Sans", 20));
        super.setAlignment(Pos.CENTER);


        Image image = new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream(ingredients_type.url_to_image)));
        ImageView image_view = new ImageView(image);
        image_view.setFitHeight(100);
        image_view.setFitWidth(100);


        getChildren().add(title_label);
        getChildren().add(image_view);
        getChildren().add(metric_label);


        getStyleClass().add("receipt-item-view");
    }

    public static ReceiptItemView view_for(ReceiptItem item){
        return new ReceiptItemView(item);
    }

    public IngredientsEnum getIngredients_type() {
        return ingredients_type;
    }

    public void setIngredients_type(IngredientsEnum ingredients_type) {
        this.ingredients_type = ingredients_type;
    }

    public ReceiptItem getReceiptItem() {
        return receiptItem;
    }

    public void setReceiptItem(ReceiptItem receiptItem) {
        this.receiptItem = receiptItem;
    }

    private void define_ingredient_type_by_name(String name) {
        if (contains_ignore_case(name, "Мука"))
            ingredients_type = IngredientsEnum.МУКА;
        else if (contains_ignore_case(name, "Сахар"))
            ingredients_type = IngredientsEnum.САХАР;
        else if (contains_ignore_case(name, "Меланж"))
            ingredients_type = IngredientsEnum.МЕЛАНЖ;
        else if (contains_ignore_case(name, "Аммоний"))
            ingredients_type = IngredientsEnum.АММОНИЙ;
        else if (contains_ignore_case(name, "Духи"))
            ingredients_type = IngredientsEnum.ДУХИ;
        else if (contains_ignore_case(name, "Мед натуральный"))
            ingredients_type = IngredientsEnum.МЕД_НАТУРАЛЬНЫЙ;
        else if (contains_ignore_case(name, "Мед искусственный"))
            ingredients_type = IngredientsEnum.МЕД_ИСКУССТВЕННЫЙ;
        else if (contains_ignore_case(name, "Маргарин"))
            ingredients_type = IngredientsEnum.МАРГАРИН;
        else if (contains_ignore_case(name, "Жженка"))
            ingredients_type = IngredientsEnum.ЖЖЕНКА;
        else if (contains_ignore_case(name, "Масло растительное"))
            ingredients_type = IngredientsEnum.МАСЛО_РАСТИТЕЛЬНОЕ;
        else if (contains_ignore_case(name, "Молоко"))
            ingredients_type = IngredientsEnum.МОЛОКО;
        else if (contains_ignore_case(name, "Натрий"))
            ingredients_type = IngredientsEnum.НАТРИЙ;
        else if (contains_ignore_case(name, "Патока"))
            ingredients_type = IngredientsEnum.ПАТОКА;
        else if (contains_ignore_case(name, "Сода"))
            ingredients_type = IngredientsEnum.НАТРИЙ;
        else if (contains_ignore_case(name, "Соль"))
            ingredients_type = IngredientsEnum.СОЛЬ;
        else if (contains_ignore_case(name, "Масло сливочное"))
            ingredients_type = IngredientsEnum.МАСЛО_СЛИВОЧНОЕ;
        else if (contains_ignore_case(name, "Майонез"))
            ingredients_type = IngredientsEnum.МАЙОНЕЗ;
        else if (contains_ignore_case(name, "Крахмал"))
            ingredients_type = IngredientsEnum.КРАХМАЛ;
        else if (contains_ignore_case(name, "Сироп"))
            ingredients_type = IngredientsEnum.ПАТОКА;
        else if (contains_ignore_case(name, "Ванилин"))
            ingredients_type = IngredientsEnum.ВАНИЛИН;
        else if (contains_ignore_case(name, "Углеаммонийная"))
            ingredients_type = IngredientsEnum.АММОНИЙ;
        else if (contains_ignore_case(name,"Творог"))
            ingredients_type = IngredientsEnum.ТВОРОГ;
        else ingredients_type = IngredientsEnum.DEFAULT;
    }

    private boolean contains_ignore_case(String name, String name1) {
        return name.toLowerCase().contains(name1.toLowerCase());
    }
}
