package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

import static com.example.mcmull27.diabetesmanager.StatisticsPage.FROM_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TO_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TYPE;

public class Graph extends AppCompatActivity {
    private String ty_txt;
    private String fd_txt;
    private String td_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ArrayList<Act> actList;
        Intent i = getIntent();

        ty_txt = i.getStringExtra(TYPE);
        fd_txt = i.getStringExtra(FROM_DATE);
        td_txt = i.getStringExtra(TO_DATE);
        actList = (ArrayList<Act>) i.getParcelableArrayListExtra("ARRAYLIST");


        if(ty_txt.equalsIgnoreCase(Act.BGL) || ty_txt.equalsIgnoreCase(Act.MEDICATION))
        {
            //Display bar Graphs for data
            ArrayList<BarEntry> barEntries = new ArrayList<>();
            ArrayList<String> dateEntries = new ArrayList<>();

            BarChart barChart = (BarChart) findViewById(R.id.displayGraph);

            for(int j = 0; j<actList.size(); j++)
            {
                barEntries.add(new BarEntry((float)actList.get(j).getAmount(),j));
                dateEntries.add(actList.get(j).printDate());

                Log.e("JKERR", "added value: " + actList.get(j).getAmount());
            }


            BarDataSet barDataSet = new BarDataSet(barEntries,"Dates");
            BarData barData = new BarData(barDataSet);
            barChart.setData(barData);


        }
        else if(ty_txt.equalsIgnoreCase(Act.DIET))
        {

        }
        else
        {

        }

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
        toCalc.putExtra(TYPE,this.ty_txt);
        toCalc.putExtra(FROM_DATE,this.fd_txt);
        toCalc.putExtra(TO_DATE,this.td_txt);
        startActivity(toCalc);
    }

    public void openTablePage(){
        Intent toTable = new Intent(this, Table.class);
        toTable.putExtra(TYPE,this.ty_txt);
        toTable.putExtra(FROM_DATE,this.fd_txt);
        toTable.putExtra(TO_DATE,this.td_txt);
        startActivity(toTable);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Graph.this, StatisticsPage.class);
        this.startActivity(intent);
    }
}
