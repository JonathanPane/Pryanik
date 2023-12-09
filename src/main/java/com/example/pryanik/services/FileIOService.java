package com.example.pryanik.services;

import com.example.pryanik.BeanContext;
import com.example.pryanik.DTO.ReceiptItem;
import com.example.pryanik.project.library.ProjectFoundation;

import java.io.File;
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
        if(saving_file !=  null){
            saving_file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(saving_file);
        fileOutputStream.write(text.toString().getBytes(StandardCharsets.UTF_8));
        fileOutputStream.flush();
        }
    }
    public static void open_file() throws IOException {
        File open_file = ProjectFoundation.select_file("*.receipt", "Файл рецепта", false);
        if(open_file != null){
            BeanContext.set_value_in_bean("path to file", open_file.getPath());
            ProjectFoundation.show_modal_window_for_inputting_mass();
        }
    }

}
