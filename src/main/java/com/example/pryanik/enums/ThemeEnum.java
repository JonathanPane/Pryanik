package com.example.pryanik.enums;

public enum ThemeEnum {
    DARK("/com/example/pryanik/DarkTheme.css"),
    DEFAULT("/com/example/pryanik/Main.css")
    ;
    public final String path_to_css;
    private ThemeEnum(String path_to_css){
        this.path_to_css = path_to_css;
    }
}
