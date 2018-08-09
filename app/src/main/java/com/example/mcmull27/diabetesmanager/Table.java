package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mcmull27.diabetesmanager.StatisticsPage.FROM_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TO_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TYPE;

public class Table extends AppCompatActivity {
    public List<Act> actList;
    RecyclerView recyclerView;
    private DatabaseManager db;
    private String fd;
    private String td;
    private String ty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        try{
            //ready the recyclerview
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //get previous intent's data
            Intent i = getIntent();
            ty = i.getStringExtra(TYPE);
            fd = i.getStringExtra(FROM_DATE);
            td = i.getStringExtra(TO_DATE);

            //query the db
            db = new DatabaseManager(this);
            actList = db.queryActs(ty,td,fd);

            //creating recyclerview adapter
            QueryAdaptor adapter = new QueryAdaptor(this, actList);

            //setting adapter to recyclerview
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("ERR", "Query Failed to Populate RecyclerView");
        }


    }
}
