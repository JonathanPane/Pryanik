package com.example.pryanik.services;

import com.example.pryanik.BeanContext;
import com.example.pryanik.enums.ThemeEnum;

import java.io.*;
import java.util.Scanner;

public class ThemeSavingService {
    public static ThemeEnum saved_theme() throws FileNotFoundException {
        File file = new File("S:/Users/Administrator/Desktop/SavedTheme.txt");
        Scanner scanner = new Scanner(new FileInputStream(file));
        String str = scanner.nextLine();
        if(str.equals("DarkTheme"))
            return ThemeEnum.DARK;
        return ThemeEnum.DEFAULT;
    }
    public static void save_theme() throws FileNotFoundException {
        File file = new File("S:/Users/Administrator/Desktop/SavedTheme.txt");
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(file), true);
        if( BeanContext.<ThemeEnum>get_bean("Theme").equals(ThemeEnum.DARK))
            printWriter.println("DarkTheme");
        printWriter.println("Main");
    }
}
