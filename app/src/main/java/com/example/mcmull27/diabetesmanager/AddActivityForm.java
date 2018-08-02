package com.example.mcmull27.diabetesmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class AddActivityForm extends AppCompatActivity {
    ArrayList<Act> acts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity_form);

        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.activity_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //recyclerView
        RecyclerView rvActs = (RecyclerView) findViewById(R.id.rvActs);

        //initialize activities
        acts = getSampleArrayList();
        ActivityAdapter adapter1 = new ActivityAdapter(acts);
        rvActs.setAdapter(adapter1);
        rvActs.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<Act> getSampleArrayList(){
        ArrayList<Act> items = new ArrayList<>();
        items.add(new Act(Act.BGL));
        items.add(new Act(Act.BGL));
        items.add(new Act(Act.MEDICATION));
        items.add(new Act(Act.DIET));
        items.add(new Act(Act.MEDICATION));
        items.add(new Act(Act.EXERCISE));
        items.add(new Act(Act.DIET));
        items.add(new Act(Act.EXERCISE));
        items.add(new Act(Act.BGL));
        items.add(new Act(Act.BGL));
        items.add(new Act(Act.EXERCISE));
        items.add(new Act(Act.BGL));
        return items;
    }
}
