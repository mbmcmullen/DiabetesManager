package com.example.mcmull27.diabetesmanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Act implements Parcelable{
    public static final String DIET = "DIET";
    public static final String EXERCISE = "EXERCISE";
    public static final String MEDICATION = "MEDICATION";
    public static final String BGL = "BGL";
//    public static final String DATE_FORMAT = "MM/dd/yyyy hh:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
        dateParser = new SimpleDateFormat(DATE_FORMAT); // TODO: make sure this is the correct format
        prettyDatePrinter = new SimpleDateFormat(PRETTY_DATE);
        dateTime = new Date();
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

    protected Act(Parcel in) {
        id = in.readInt();
        type = in.readString();
        description = in.readString();
        amount = in.readDouble();
        timestamp = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeDouble(amount);
        dest.writeString(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Act> CREATOR = new Creator<Act>() {
        @Override
        public Act createFromParcel(Parcel in) {
            return new Act(in);
        }

        @Override
        public Act[] newArray(int size) {
            return new Act[size];
        }
    };

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

    public void setTime(String time) {
        SimpleDateFormat parser = new SimpleDateFormat("hh:mm");
        Date t;
        try {
            t = parser.parse(time);
        } catch(Exception e) {
            Log.e("ACT", "Failed to set time : " + time, e);
            timestamp = dateParser.format(dateTime);
            return;
        }
        dateTime.setHours(t.getHours());
        dateTime.setMinutes(t.getMinutes());
        timestamp = dateParser.format(dateTime);
    }

    public void setDate(String date) {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
        Date d;
        try {
            d = parser.parse(date);
        } catch(Exception e) {
            Log.e("ACT", "Failed to set date : " + date, e);
            return;
        }
        dateTime.setDate(d.getDate());
        dateTime.setMonth(d.getMonth());
        dateTime.setYear(d.getYear());
        timestamp = dateParser.format(dateTime);
    }

    public String getDate() {
        SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy");
        return parser.format(this.dateTime);
    }

    public String getTime() {
        SimpleDateFormat parser = new SimpleDateFormat("hh:mm");
        return parser.format(this.dateTime);
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
