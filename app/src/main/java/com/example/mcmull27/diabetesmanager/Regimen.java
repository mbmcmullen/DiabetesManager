package com.example.mcmull27.diabetesmanager;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Regimen {
    private List<Act> activities;
    private Stack<Act> all;
    private DatabaseManager dbMan;
    private static final int MAX_LIST_ITEMS = 5; // todo: probably higher than this

    public Regimen(Context ctx){
        // Getting regimen from the db

        //load libraries for sqlcipher
        SQLiteDatabase.loadLibs(ctx);

        dbMan = DatabaseManager.getInstance(ctx);
        update();
    }

    public void update() {
        List<Act> allActs = dbMan.selectAllRegItems();

        // test regimen
//        List<Act> allActs = _testList;

        Collections.sort(allActs, new Comparator<Act>() {
            public int compare(Act o1, Act o2) {
                if (o1.getDateTime() == null || o2.getDateTime() == null)
                    return 0;
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });

        all = new Stack<>();

        if (allActs.size() <= MAX_LIST_ITEMS) {
            activities = new ArrayList<>(allActs);
        } else {
            activities = allActs.subList(0, MAX_LIST_ITEMS);
            all.addAll(allActs.subList(MAX_LIST_ITEMS, allActs.size()));
        }
    }


    public List<Act> getActivities(){
        return activities;
    }

    public void addActivity(Act act){
        activities.add(act);
    }

    private void saveToActivity(Act a) {
        dbMan.insertAct(a);
        Log.d("REGIMEN", "Saved regimen item to completed activity: " + a);
    }

    public void insertAct(Act a) {
        dbMan.insertRegItem(a);
    }

    public Act getById(int i) {
        Act a = null;
        for (Act act: activities) {
            if (act.getId() == i) {
                return act;
            }
        }
        return null;
    }


    private Act deleteActivityId(int id){
        Act a = null;
        Iterator<Act> it = activities.iterator();
        while(it.hasNext()) {
            Act e = it.next();
            if(e.getId() == id){
                a = e;
                activities.remove(e);
                dbMan.deleteRegItemByID(id);
//                break;
            }
        }
        return a;
    }

    public void markCompleted(int id) {
        Act completed = deleteActivityId(id);
        saveToActivity(completed);
        if (!all.isEmpty()) {
            activities.add(all.pop());
        }
    }

    public void removeItem(int id) {
        deleteActivityId(id);
        if (!all.isEmpty()) {
            activities.add(all.pop());
        }
    }

    // TODO : remove the following test data
        // Act(int id, String type, String description, double amount, String timestamp)
//    private List<Act> _testList = new ArrayList<>(Arrays.asList(
//            new Act(0, Act.EXERCISE, "Morning run", 2.0, new Date(2018, 8, 4, 6, 0).toString()),
//            new Act(1, Act.BGL, "Morning reading", 0, new Date(2018, 8, 4, 6, 30).toString()),
//            new Act(2, Act.DIET, "Breakfast", 2.0, new Date(2018, 8, 4, 6, 30).toString()),
//            new Act(3, Act.BGL, "Mid Morning Reading", 0, new Date(2018, 8, 4, 10, 0).toString()),
//            new Act(4, Act.DIET, "Morning Snack", 2.0, new Date(2018, 8, 4, 10, 0).toString()),
//            new Act(5, Act.EXERCISE, "Walk to lunch", 2.0, new Date(2018, 8, 4, 12, 0).toString()),
//            new Act(6, Act.BGL, "Midday reading", 0, new Date(2018, 8, 4, 12, 30).toString()),
//            new Act(7, Act.DIET, "Lunch", 2.0, new Date(2018, 8, 4, 12, 30).toString()),
//            new Act(8, Act.BGL, "afternoon Reading", 0, new Date(2018, 8, 4, 15, 10).toString()),
//            new Act(9, Act.DIET, "Dinner", 2.0, new Date(2018, 8, 4, 18, 0).toString())
//    ));



}
