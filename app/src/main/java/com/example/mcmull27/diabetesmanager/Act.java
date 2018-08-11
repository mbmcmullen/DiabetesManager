package com.example.mcmull27.diabetesmanager;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Act{
    public static final String DIET = "DIET";
    public static final String EXERCISE = "EXERCISE";
    public static final String MEDICATION = "MEDICATION";
    public static final String BGL = "BGL";
    public static final String DATE_FORMAT = "MM/dd/yyyy hh:mm";

    private int id;
    private String type;
    private String description;
    private double amount;
    private String timestamp;
    private Date dateTime;
    private SimpleDateFormat dateParser;
    private static String TAG = "ActModel";
    private static String PRETTY_DATE = "EEE, d MMM yyyy hh:mm: aaa";
    private SimpleDateFormat prettyDatePrinter;

    public Act(String type){
        this.type = type;
        if(type.equals(BGL)){
            description = "BGL Reading";
        }
    }
    public Act(int id, String type, String description, double amount, String timestamp){
        this.id = id;
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.timestamp = timestamp;
        dateParser = new SimpleDateFormat(DATE_FORMAT); // TODO: make sure this is the correct format
        prettyDatePrinter = new SimpleDateFormat(PRETTY_DATE);
        try {
            dateTime = dateParser.parse(timestamp);
        } catch(ParseException e) {
            Log.w(TAG, "Act: Error parsing timestamp: " + timestamp + ", in format: " + DATE_FORMAT);
            dateTime = new Date();
        }

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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        // TODO: this could be set to return the form that is needed for SQL
        // which could simplify the database manager
        return "" + id + type + description + amount + timestamp;
    }

    public String printDate() {
        return prettyDatePrinter.format(dateTime);
    }
}
