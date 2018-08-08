package com.example.mcmull27.diabetesmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;


public class AddActivityForm extends AppCompatActivity {
    ArrayList<Act> acts;
    ActivityAdapter adapter;
    Button bgl, diet, exercise, medication, add;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_form);

        //initialize database manager
        db = new DatabaseManager(this);


        //initialize buttons and handlers
        bgl = (Button) findViewById(R.id.button_bgl);
        diet = (Button) findViewById(R.id.button_diet);
        exercise = (Button) findViewById(R.id.button_exercise);
        medication = (Button) findViewById(R.id.button_medication);
        add = (Button) findViewById(R.id.btnAdd);

        bgl.setOnClickListener(new SelectionHandler());
        diet.setOnClickListener(new SelectionHandler());
        exercise.setOnClickListener(new SelectionHandler());
        medication.setOnClickListener(new SelectionHandler());
        add.setOnClickListener(new addListener());

        //recyclerView
        RecyclerView rvActs = (RecyclerView) findViewById(R.id.rvActs);

        //initialize activities
        acts = new ArrayList<>();
        adapter = new ActivityAdapter(acts);
        rvActs.setAdapter(adapter);
        rvActs.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        for(int i= acts.size()-1; i>=0;i--){
            acts.remove(i);
            adapter.notifyItemRemoved(i);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        db = new DatabaseManager(this);
    }

    @Override
    public void onBackPressed() {
        if(!acts.isEmpty()){
            int i = acts.size()-1;
            acts.remove(i);
            adapter.notifyItemRemoved(i);
        }else{
            super.onBackPressed();
        }

    }

    private boolean valid(Act a){
        return true;
    }

    private class addListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            for(int i= acts.size()-1; i>=0;i--){
                Act cur = acts.get(i);
                if(valid(cur)){
                    Log.d("adding", cur.getAmount()+" "+cur.getDescription()+" "+cur.getTimestamp()+" "+cur.getAmount());
                    db.insertAct(cur);
                    acts.remove(i);
                    adapter.notifyItemRemoved(i);
                }
            }
        }
    }

    private class SelectionHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button_bgl:
                    acts.add(new Act(Act.BGL));
                    adapter.notifyItemInserted(acts.size()-1);
                    break;
                case R.id.button_diet:
                    acts.add(new Act(Act.DIET));
                    adapter.notifyItemInserted(acts.size()-1);
                    break;
                case R.id.button_exercise:
                    acts.add(new Act(Act.EXERCISE));
                    adapter.notifyItemInserted(acts.size()-1);
                    break;
                case R.id.button_medication:
                    acts.add(new Act(Act.MEDICATION));
                    adapter.notifyItemInserted(acts.size()-1);
                    break;
            }
        }
    }

}


