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
            if (!isNumeric(remove_metrics(str.split("-")[1])))
                continue;
            receipt.put(str.split("-")[0].strip(), Double.parseDouble(remove_metrics(str.split("-")[1]).strip()));
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
        HashMap<String, Double> res = new HashMap<>();

        for (Map.Entry<String, Double> entry : receipt.entrySet()) {
            res.put(entry.getKey(), format(entry.getValue() * mass / 1000));
        }

        return res;
    }
    public static double format(double mass){
        return Double.parseDouble(String.format("%.3f", (double) Math.round(mass * 1000) / 1000));
    }
    private static String remove_metrics(String text){
        return text.replace("кг", "").replace("т", "");
    }
}

