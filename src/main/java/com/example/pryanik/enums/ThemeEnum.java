package com.example.pryanik.enums;

public enum ThemeEnum {
    DARK("/com/example/pryanik/DarkTheme.css"),
    LAVENDER(""),
    SPIRNG(""),
    DEFAULT("")
    ;
    public String path_to_css;
    private ThemeEnum(String path_to_css){
        this.path_to_css = path_to_css;
    }
}
