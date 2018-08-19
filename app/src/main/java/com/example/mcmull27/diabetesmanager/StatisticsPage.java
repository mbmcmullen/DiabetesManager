package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class StatisticsPage extends AppCompatActivity {

    private DatabaseManager db;
    public static final String FROM_DATE = "com.example.mcmull27.diabetesmanager.FROM_DATE";
    public static final String TO_DATE = "com.example.mcmull27.diabetesmanager.TO_DATE";;
    public static final String TYPE = "com.example.mcmull27.diabetesmanager.TYPE";
    public static final String CONTAINS = "com.example.mcmull27.diabetesmanager.CONTAINS";

    public EditText fd, td, ct;
    SimpleDateFormat format;

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


        Button search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openTablePage();
            }

        });

    }

    public void openTablePage()
    {
        format = new SimpleDateFormat(Act.DATE_FORMAT);
        Intent toTable= new Intent(this, Table.class);



        Spinner ty = (Spinner) findViewById(R.id.activity_type_spinner);
        String ty_txt = ty.getSelectedItem().toString().toUpperCase();


        fd = (EditText) findViewById(R.id.fromText);
        String _fd_txt = fd.getText().toString();

        ct = (EditText) findViewById(R.id.keywordText);
        String _c_txt = ct.getText().toString();

        //handler for search button

        td = (EditText) findViewById(R.id.toText);
        String _td_txt = td.getText().toString();


        toTable.putExtra(TYPE, ty_txt);
        toTable.putExtra(FROM_DATE, _fd_txt);
        toTable.putExtra(CONTAINS, _c_txt);
        toTable.putExtra(TO_DATE, _td_txt);

        Log.e("JKERR", "type from stats: " + _td_txt);
        Log.e("JKERR", "fromDate from stats: " + _fd_txt);
        Log.e("JKERR", "toDate from stats: " + _td_txt);
        Log.e("JKERR", "contains from stats: " + _c_txt);

        startActivity(toTable);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatisticsPage.this, MainActivity.class);
        this.startActivity(intent);
    }



}