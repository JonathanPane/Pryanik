package com.example.pryanik.services;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.project.library.ProjectFoundation;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileIOService{

    public static void save_file(List<ReceiptItem> receipt_items) throws IOException {
        StringBuilder text = new StringBuilder();
        for (ReceiptItem receiptItem : receipt_items) {
            text.append(receiptItem.getName()).append(" - ");
            text.append(receiptItem.getCount()).append(" ");
            text.append(receiptItem.getMetrics()).append("\n");
        }
        File saving_file = ProjectFoundation.select_file("*.receipt", "Файл рецепта", true);
        if(!saving_file.exists())
            saving_file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(saving_file);
        fileOutputStream.write(text.toString().getBytes(StandardCharsets.UTF_8));
        fileOutputStream.flush();
    }
}
