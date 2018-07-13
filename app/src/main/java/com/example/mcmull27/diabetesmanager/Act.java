package com.example.mcmull27.diabetesmanager;

import java.util.Date;

public class Act{
    public int id;
    public String type;
    public String description;
    public double amount;
    public Date timestamp;

    public Act(int id, String type, String description, double amount, Date timestamp){
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
