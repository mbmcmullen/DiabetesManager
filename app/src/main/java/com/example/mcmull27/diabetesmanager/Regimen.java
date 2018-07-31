package com.example.mcmull27.diabetesmanager;

import java.util.ArrayList;
import java.util.Iterator;

public class Regimen {
    private ArrayList<Act> activities;

    public Regimen(){
        activities = new ArrayList<>();
    }

    public ArrayList<Act> getActivities(){
        return activities;
    }

    public void addActivity(Act act){
        activities.add(act);
    }

    public void deleteActivityId(int id){
        Iterator<Act> it = activities.iterator();
        for(Act e = it.next();it.hasNext();e = it.next()){
            if(e.getId() == id){
                activities.remove(e);
                break;
            }
        }
    }
}
