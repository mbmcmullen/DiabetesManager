package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.mcmull27.diabetesmanager.StatisticsPage.FROM_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TO_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TYPE;

public class Graph extends AppCompatActivity {
    private String ty;
    private String fd;
    private String td;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Intent i = getIntent();

        ty = i.getStringExtra(TYPE);
        fd = i.getStringExtra(FROM_DATE);
        td = i.getStringExtra(TO_DATE);


        Button stats = (Button) findViewById(R.id.stats);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalcPage();
            }
        });

        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTablePage();
            }
        });


    }

    public void openCalcPage()
    {
        Intent toCalc = new Intent(this, Calculations.class);
        toCalc.putExtra("TYPE",this.ty);
        toCalc.putExtra("FROM_DATE",this.fd);
        toCalc.putExtra("TO_DATE",this.td);
        startActivity(toCalc);
    }

    public void openTablePage(){
        Intent toTable = new Intent(this, Table.class);
        toTable.putExtra("TYPE",this.ty);
        toTable.putExtra("FROM_DATE",this.fd);
        toTable.putExtra("TO_DATE",this.td);
        startActivity(toTable);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Graph.this, StatisticsPage.class);
        this.startActivity(intent);
    }
}
