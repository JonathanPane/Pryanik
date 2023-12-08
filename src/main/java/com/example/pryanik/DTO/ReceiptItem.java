package com.example.pryanik.DTO;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DecimalFormat;

public class ReceiptItem {
    private StringProperty name;
    private DoubleProperty count;
    private StringProperty metrics;
    private StringProperty amount;

    public ReceiptItem(String name, double count, String metrics) {
        this.name = new SimpleStringProperty(name);
        this.count = new SimpleDoubleProperty(count);
        this.metrics = new SimpleStringProperty(metrics);
        this.amount = new SimpleStringProperty(count+" "+metrics);
        this.count.addListener((obs, oldV, newV) ->
        {
            this.metrics.addListener((obs1, oldV1, newV1) ->
                    amount.set(new DecimalFormat("0.#############").format(newV) + " " + newV1));
        });

    }

    public void toggle_kg(){
        count.set(count.doubleValue() * 1000);
        metrics.set("кг");
    }

    public void toggle_t(){
        count.set(count.doubleValue() / 1000);
        metrics.set("т");
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getCount() {
        return count.get();
    }

    public String getAmount(){
        return amount.get();
    }

    public void setCount(double count) {
        this.count.set(count);
    }

    public String getMetrics() {
        return metrics.get();
    }

    public void setMetrics(String metrics) {
        this.metrics.set(metrics);
    }

    public StringProperty nameProperty(){
        return name;
    }

    public DoubleProperty countProperty(){
        return count;
    }

    public StringProperty metricsProperty(){
        return metrics;
    }
}
