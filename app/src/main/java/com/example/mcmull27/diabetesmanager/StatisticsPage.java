package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class StatisticsPage extends AppCompatActivity {

    private DatabaseManager db;
    public static final String FROM_DATE = "com.example.mcmull27.diabetesmanager.FROM_DATE";
    public static final String TO_DATE = "com.example.mcmull27.diabetesmanager.TO_DATE";;
    public static final String TYPE = "com.example.mcmull27.diabetesmanager.TYPE";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_page);

        Spinner spinner = (Spinner) findViewById(R.id.activity_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //navigation bar
        findViewById(R.id.regimen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsPage.this, PrescribedRegimen.class);
                StatisticsPage.this.startActivity(intent);
            }
        });
        findViewById(R.id.activities_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsPage.this, ActivitiesPage.class);
                StatisticsPage.this.startActivity(intent);
            }
        });
        findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsPage.this, MainActivity.class);
                StatisticsPage.this.startActivity(intent);
            }
        });

        //handler for search button
        Button search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openTablePage();

            }


        });

        Button stats = (Button) findViewById(R.id.display_stats_button);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open up new window to display calculation results
                openStatsPage();
            }
        });

        Button graph = (Button) findViewById(R.id.graph_button);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open up new window to display calculation results
                openGraphPage();
            }
        });

    }

    public void openStatsPage()
    {

        Intent toCalc= new Intent(this, Calculations.class);

        TextView fd = (TextView) findViewById(R.id.fromText);
        String fd_txt = fd.getText().toString();
        toCalc.putExtra(FROM_DATE, fd_txt);

        Spinner ty = (Spinner) findViewById(R.id.activity_type_spinner);
        String ty_txt = ty.getSelectedItem().toString();
        toCalc.putExtra(TYPE, ty_txt);

        TextView td = (TextView) findViewById(R.id.toText);
        String td_txt = td.getText().toString();
        toCalc.putExtra(TO_DATE, td_txt);

        startActivity(toCalc);
    }

    public void openGraphPage()
    {
        Intent toGraph= new Intent(this, Graph.class);

        TextView fd = (TextView) findViewById(R.id.fromText);
        String fd_txt = fd.getText().toString();
        toGraph.putExtra(FROM_DATE, fd_txt);

        Spinner ty = (Spinner) findViewById(R.id.activity_type_spinner);
        String ty_txt = ty.getSelectedItem().toString();
        toGraph.putExtra(TYPE, ty_txt);

        TextView td = (TextView) findViewById(R.id.toText);
        String td_txt = td.getText().toString();
        toGraph.putExtra(TO_DATE, td_txt);

        startActivity(toGraph);
    }

    public void openTablePage()
    {
        Intent toTabl= new Intent(this, Table.class);

        TextView fd = (TextView) findViewById(R.id.fromText);
        String fd_txt = fd.getText().toString();
        toTabl.putExtra(FROM_DATE, fd_txt);

        Spinner ty = (Spinner) findViewById(R.id.activity_type_spinner);
        String ty_txt = ty.getSelectedItem().toString();
        toTabl.putExtra(TYPE, ty_txt);

        TextView td = (TextView) findViewById(R.id.toText);
        String td_txt = td.getText().toString();
        toTabl.putExtra(TO_DATE, td_txt);

        startActivity(toTabl);
    }

}