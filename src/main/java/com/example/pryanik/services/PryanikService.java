package com.example.pryanik.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PryanikService {

    public static Map<String, Double> getReceipt(String user_receipt) throws FileNotFoundException {
        File file = new File(user_receipt);
        Scanner scanner = new Scanner(new FileInputStream(file));
        HashMap<String, Double> receipt = new HashMap<>();
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.split("-").length != 2)
                continue;
            if (!isNumeric(str.split("-")[1]))
                continue;
            receipt.put(str.split("-")[0].strip(), Double.parseDouble(str.split("-")[1].strip()));
        }

        return receipt;
    }

    public static boolean isNumeric(String str) {
        if (str == null)
            return false;
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Map<String, Double> mass_counter(Map<String, Double> receipt, double mass) {
        double tonn = 1000.0;
        HashMap<String, Double> res = new HashMap<>();

        for (Map.Entry<String, Double> entry : receipt.entrySet()) {
            res.put(entry.getKey(), (entry.getValue() * mass) / tonn);
        }

        return res;
    }

    public static String map_to_string(Map<String, Double> edited_receipt) {
        String str = "";
        for (var entry : edited_receipt.entrySet())
            if (entry.getValue() != 0)
                str += String.format("%s - %.3f кг\n", entry.getKey(), entry.getValue());
        return str;
    }

    public static String map_to_string_tonn(Map<String, Double> edited_receipt) {
        String str = "";
        for (var entry : edited_receipt.entrySet())
            if (entry.getValue() != 0)
                str += String.format("%s - %.5f т\n", entry.getKey(), entry.getValue() / 1000);
        return str;
    }
}

