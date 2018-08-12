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

    TextView fd, td, ct;
    String td_txt, fd_txt,c_txt;
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

        //handler for search button
        td = (TextView) findViewById(R.id.toText);
        td_txt = td.getText().toString();

        fd = (TextView) findViewById(R.id.fromText);
        fd_txt = fd.getText().toString();

        ct = (TextView) findViewById(R.id.keywordText);
        c_txt = ct.getText().toString();

        format = new SimpleDateFormat(Act.DATE_FORMAT);

        Button search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if( fd_txt.equals("") && td_txt.equals("") ){openTablePage();
                }else if( (!fd_txt.equals("")) && (!td_txt.equals("")) ){
                    try{
                        fd_txt = format.parse(fd_txt).toString();
                        td_txt = format.parse(td_txt).toString();
                        openTablePage();
                    }catch(Exception e){
                        Toast.makeText(StatisticsPage.this, "Invalid Date", Toast.LENGTH_SHORT).show();
                    }
                }else if( (fd_txt.equals("")) ){
                    try{
                        td_txt = format.parse(td_txt).toString();
                        openTablePage();
                    }catch(Exception e){
                        Toast.makeText(StatisticsPage.this, "Invalid To Date", Toast.LENGTH_SHORT).show();
                    }
                }else if( td_txt.equals("") ){
                    try{
                        fd_txt = format.parse(fd_txt).toString();
                        openTablePage();
                    }catch(Exception e){
                        Toast.makeText(StatisticsPage.this, "Invalid From Date", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });

    }

    public void openTablePage()
    {
        Intent toTable= new Intent(this, Table.class);

        toTable.putExtra(FROM_DATE, fd_txt);

        Spinner ty = (Spinner) findViewById(R.id.activity_type_spinner);
        String ty_txt = ty.getSelectedItem().toString().toUpperCase();
        toTable.putExtra(TYPE, ty_txt);

        toTable.putExtra(TO_DATE, td_txt);
        toTable.putExtra(CONTAINS, c_txt);

        startActivity(toTable);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatisticsPage.this, MainActivity.class);
        this.startActivity(intent);
    }

}