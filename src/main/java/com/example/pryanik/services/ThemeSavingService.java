package com.example.pryanik.services;

import com.example.pryanik.BeanContext;
import com.example.pryanik.enums.ThemeEnum;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class ThemeSavingService {
    public static ThemeEnum saved_theme() throws IOException {
        String Mydocs = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        File file1 = new File(Mydocs + "/Pryanik");
        if(!file1.exists())
            file1.mkdirs();
        File file = new File(Mydocs + "/Pryanik/SavedTheme.txt");
        if(!file.exists()) {
            file.createNewFile();
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(file), true);
            printWriter.println("Main");
        }
        Scanner scanner = new Scanner(new FileInputStream(file));
        String str = scanner.nextLine();
        if(str.equals("DarkTheme"))
            return ThemeEnum.DARK;
        return ThemeEnum.DEFAULT;
    }
    public static void save_theme() throws IOException {
        String Mydocs = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        File file = new File(Mydocs + "/Pryanik/SavedTheme.txt");
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(file), true);
        if( BeanContext.<ThemeEnum>get_bean("Theme").equals(ThemeEnum.DARK))
            printWriter.println("DarkTheme");
        printWriter.println("Main");
    }
}
