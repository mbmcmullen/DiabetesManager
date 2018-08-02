package com.example.mcmull27.diabetesmanager;

import java.util.Date;

public class Act{

    public static final String DIET = "DIET";
    public static final String EXERCISE = "EXERCISE";
    public static final String MEDICATION = "MEDICATION";
    public static final String BGL = "BGL";

    private int id;
    private String type;
    private String description;
    private double amount;
    private String timestamp;

    public Act(String type){
        this.type = type;
    }
    public Act(int id, String type, String description, double amount, String timestamp){
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getId() { return id; }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String t){
        type=t;
    }
}
