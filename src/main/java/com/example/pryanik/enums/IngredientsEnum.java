package com.example.pryanik.enums;

public enum IngredientsEnum {
    МУКА("/images/wheat.png"),
    САХАР("/images/sugar.png"),
    МЕЛАНЖ("/images/dough.png"),
    АММОНИЙ("/images/chemical.png"),
    ДУХИ("/images/perfume.png"),
    МЕД_НАТУРАЛЬНЫЙ("/images/apitherapy.png"),
    МЕД_ИСКУССТВЕННЫЙ("/images/honey-jar.png"),
    МАРГАРИН("/images/butter.png"),
    ЖЖЕНКА("/images/punch-bowl.png"),
    МАСЛО_РАСТИТЕЛЬНОЕ("/images/vegetable-oil.png"),
    МОЛОКО("/images/milk-box.png"),
    НАТРИЙ("/images/low-sodium.png"),
    ПАТОКА("/images/maple-syrup.png"),
    МАСЛО_СЛИВОЧНОЕ("/images/butter.png"),
    МАЙОНЕЗ("/images/mayonnaise.png"),
    КРАХМАЛ("/images/potato.png"),
    СОЛЬ("/images/salt.png"),
    ВАНИЛИН("/images/vanilla.png"),
    ТВОРОГ("/images/cottagecheese.png"),
    DEFAULT("/images/dairy-products.png"),
    ;
    public String url_to_image;
    private IngredientsEnum(String url_to_image){
        this.url_to_image = url_to_image;
    }
}
