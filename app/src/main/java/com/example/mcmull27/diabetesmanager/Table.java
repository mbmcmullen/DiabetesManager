package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    List<Act> displayList;
    QueryAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //get previous intent's data
        Intent intent = getIntent();
        ty = intent.getStringExtra(TYPE);
        fd = intent.getStringExtra(FROM_DATE);
        td = intent.getStringExtra(TO_DATE);

        //query the db
        db = new DatabaseManager(this);
        actList = db.selectAllActs();
                //db.queryActs(ty,td,fd);

        displayList = new ArrayList<>();

        for(int i=0; i<actList.size();i++){
            Act e = actList.get(i);
            if(e.getType().equals(ty)){
                displayList.add(e);
            }
        }

        //creating recyclerview adapter
        adapter = new QueryAdaptor(displayList);
        adapter.setListener(new QueryAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Act e = displayList.get(position);
                db.deleteActByID(e.getId());
                displayList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        //setting adapter to recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.graph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGraphPage();
            }
        });

        findViewById(R.id.stats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openCalculationsPage();
            }
        });
    }

    public void openGraphPage()
    {
        Intent toGraph = new Intent(this, Graph.class);

        toGraph.putExtra("TYPE",this.ty);
        toGraph.putExtra("FROM_DATE",this.fd);
        toGraph.putExtra("TO_DATE",this.td);

        startActivity(toGraph);
    }

    public void openCalculationsPage(){
        Intent toStats = new Intent(Table.this, Calculations.class);

        toStats.putExtra("TYPE",ty);
        toStats.putExtra("FROM_DATE",fd);
        toStats.putExtra("TO_DATE",td);

        startActivity(toStats);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Table.this, StatisticsPage.class);
        this.startActivity(intent);
    }
}
