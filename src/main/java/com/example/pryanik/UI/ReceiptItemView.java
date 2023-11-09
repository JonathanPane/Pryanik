package com.example.pryanik.UI;

import com.example.pryanik.HelloApplication;
import com.example.pryanik.enums.IngredientsEnum;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ReceiptItemView extends VBox {
    private ImageView image_view;
    private Label title;
    private Label metric;
    private IngredientsEnum ingredients_type;
    public ReceiptItemView(String title, String metric) throws FileNotFoundException {
        define_ingredient_type_by_name(title);
        this.title = new Label(title);
        this.title.setFont(new Font("Comic Sans", 15));
        this.title.setAlignment(Pos.CENTER);
        this.metric = new Label(metric);
        this.metric.setFont(new Font("Comic Sans", 20));
        super.setAlignment(Pos.CENTER);
        Image image = new Image(HelloApplication.class.getResourceAsStream(ingredients_type.url_to_image));
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
    private void define_ingredient_type_by_name(String name){
        if(contains_ignore_case(name, "Мука"))
            ingredients_type = IngredientsEnum.МУКА;
       else if(contains_ignore_case(name, "Сахар"))
            ingredients_type = IngredientsEnum.САХАР;
       else if(contains_ignore_case(name, "Меланж"))
            ingredients_type = IngredientsEnum.МЕЛАНЖ;
       else if(contains_ignore_case(name, "Аммоний"))
            ingredients_type = IngredientsEnum.АММОНИЙ;
        else if(contains_ignore_case(name, "Духи"))
            ingredients_type = IngredientsEnum.ДУХИ;
        else if(contains_ignore_case(name, "Мед натуральный"))
            ingredients_type = IngredientsEnum.МЕД_НАТУРАЛЬНЫЙ;
        else if(contains_ignore_case(name, "Мед искусственный"))
            ingredients_type = IngredientsEnum.МЕД_ИСКУССТВЕННЫЙ;
        else if(contains_ignore_case(name, "Маргарин"))
            ingredients_type = IngredientsEnum.МАРГАРИН;
        else if(contains_ignore_case(name, "Жженка"))
            ingredients_type = IngredientsEnum.ЖЖЕНКА;
        else if(contains_ignore_case(name, "Масло растительное"))
            ingredients_type = IngredientsEnum.МАСЛО_РАСТИТЕЛЬНОЕ;
        else if(contains_ignore_case(name, "Молоко"))
            ingredients_type = IngredientsEnum.МОЛОКО;
        else if(contains_ignore_case(name,"Натрий"))
            ingredients_type = IngredientsEnum.НАТРИЙ;
        else if(contains_ignore_case(name,"Патока"))
            ingredients_type = IngredientsEnum.ПАТОКА;
        else if(contains_ignore_case(name, "Сода"))
            ingredients_type = IngredientsEnum.НАТРИЙ;
        else if(contains_ignore_case(name, "Соль"))
            ingredients_type = IngredientsEnum.СОЛЬ;
        else if(contains_ignore_case(name, "Масло сливочное"))
            ingredients_type = IngredientsEnum.МАСЛО_СЛИВОЧНОЕ;
        else if(contains_ignore_case(name, "Майонез"))
            ingredients_type = IngredientsEnum.МАЙОНЕЗ;
        else if(contains_ignore_case(name, "Крахмал"))
            ingredients_type = IngredientsEnum.КРАХМАЛ;
        else if(contains_ignore_case(name, "Сироп"))
            ingredients_type = IngredientsEnum.ПАТОКА;
        else if(contains_ignore_case(name, "Ванилин"))
            ingredients_type = IngredientsEnum.ВАНИЛИН;
        else if(contains_ignore_case(name, "Углеаммонийная"))
            ingredients_type = IngredientsEnum.АММОНИЙ;
        else ingredients_type = IngredientsEnum.DEFAULT;
    }
    private boolean contains_ignore_case(String name, String name1){
        return name.toLowerCase().contains(name1.toLowerCase());
    }
}
