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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mcmull27.diabetesmanager.StatisticsPage.FROM_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TO_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TYPE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.CONTAINS;

public class Table extends AppCompatActivity {
    public List<Act> actList;
    RecyclerView recyclerView;
    private DatabaseManager db;
    private String fd;
    private String td;
    private String ty;
    private String ct;

    public static final String tFROM_DATE = "com.example.mcmull27.diabetesmanager.tFROM_DATE";
    public static final String tTO_DATE = "com.example.mcmull27.diabetesmanager.tTO_DATE";;
    public static final String tTYPE = "com.example.mcmull27.diabetesmanager.tTYPE";
    public static final String tCONTAINS = "com.example.mcmull27.diabetesmanager.tCONTAINS";

    List<Act> displayList;
    QueryAdaptor adapter;

    Date toDate, fromDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //get previous intent's data
        Intent intent = getIntent();
        ty = intent.getStringExtra(TYPE);

        fd = intent.getStringExtra(FROM_DATE);
        td = intent.getStringExtra(TO_DATE);
        ct = intent.getStringExtra(CONTAINS);

        //query the db
        db = new DatabaseManager(this);
        actList = db.selectAllActs();
                //db.queryActs(ty,td,fd);

        displayList = new ArrayList<>();

        //filter types
        for(int i=0; i<actList.size();i++){
            Act e = actList.get(i);
            if(e.getType().equals(ty)){
                displayList.add(e);
            }
        }
        SimpleDateFormat format = new SimpleDateFormat(Act.DATE_FORMAT);

        try{
            fromDate = format.parse(fd);
            toDate = format.parse(td);
        }catch(Exception e){
            fromDate = null;
            toDate = null;
        }


        for(int i=0; i<displayList.size(); i++)
        {
            if((fromDate != null && displayList.get(i).getDateTime().before(fromDate)) || (toDate != null && displayList.get(i).getDateTime().after(toDate)) || ((ct != null && !ct.isEmpty()) && !displayList.get(i).getDescription().contains(ct)))
            {
                displayList.remove(i);
            }
        }


        //creating recyclerview adapter and delete listener
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

        toGraph.putExtra("tTYPE",this.ty);
        toGraph.putExtra("tFROM_DATE",this.fd);
        toGraph.putExtra("tTO_DATE",this.td);

        startActivity(toGraph);
    }

    public void openCalculationsPage(){
        Intent toStats = new Intent(getApplicationContext(), Calculations.class);


        Log.e("JKERR", "putting TYPE as " + ty);
        Log.e("JKERR", "putting FROM_DATE as " + fd);
        Log.e("JKERR", "putting TO_DATE as " + td);
        toStats.putExtra("tTYPE",ty);
        toStats.putExtra("tFROM_DATE",fd);
        toStats.putExtra("tTO_DATE",td);

        startActivity(toStats);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Table.this, StatisticsPage.class);
        this.startActivity(intent);
    }
}
