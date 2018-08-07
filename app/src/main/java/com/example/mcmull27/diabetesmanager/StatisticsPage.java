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
    public static final String AMOUNT_ONE = "com.example.mcmull27.diabetesmanager.AMOUNT_ONE";
    public static final String AMOUNT_TWO = "com.example.mcmull27.diabetesmanager.AMOUNT_TWO";;
    public static final String AMOUNT_THREE = "com.example.mcmull27.diabetesmanager.AMOUNT_THREE";




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
                ArrayList<Act> ret = new ArrayList<Act>();
                int retSize;

                //get each field's text
                Spinner type = (Spinner) findViewById(R.id.activity_type_spinner);
                String type_txt = type.getSelectedItem().toString();

                EditText frm = (EditText) findViewById(R.id.fromText);
                String frm_txt = frm.getText().toString();

                EditText to = (EditText) findViewById(R.id.toText);
                String to_txt = frm.getText().toString();

                //run query
                db = new DatabaseManager(StatisticsPage.this);
                ret = db.queryActs(type_txt, to_txt, frm_txt);



                if(ret != null && ret.size() >0)
                {
                    retSize = ret.size();

                    for(int i = 0; i<3; i++)
                    {
                        String amount;

                        //Activity Row 1
                        if(i == 0 && retSize > i)
                        {
                            TextView a1 = (TextView) findViewById(R.id.activity1);
                            a1.setText(ret.get(i).getDescription());
                            TextView t1 = (TextView) findViewById(R.id.time1);
                            t1.setText(ret.get(i).getTimestamp());
                            TextView am1 = (TextView) findViewById(R.id.amount1);
                            amount = Double.toString(ret.get(i).getAmount());
                            am1.setText(amount);

                        }
                        //Activity Row 2
                        else if( i == 1 && retSize > i)
                        {
                            TextView a2 = (TextView) findViewById(R.id.activity2);
                            a2.setText(ret.get(i).getDescription());
                            TextView t2 = (TextView) findViewById(R.id.time2);
                            t2.setText(ret.get(i).getTimestamp());
                            TextView am2 = (TextView) findViewById(R.id.amount2);
                            amount = Double.toString(ret.get(i).getAmount());
                            am2.setText(amount);
                        }
                        //Activity Row 3
                        else
                        {
                            if(i == 2 && retSize > i)
                            {
                                TextView a3 = (TextView) findViewById(R.id.activity3);
                                a3.setText(ret.get(i).getDescription());
                                TextView t3 = (TextView) findViewById(R.id.time3);
                                t3.setText(ret.get(i).getTimestamp());
                                TextView am3 = (TextView) findViewById(R.id.amount3);
                                amount = Double.toString(ret.get(i).getAmount());
                                am3.setText(amount);
                            }
                        }

                    }

                }

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
        Intent toCalc = new Intent(this, Calculations.class);

        TextView a1 = (TextView) findViewById(R.id.amount1);
        String a1_txt = a1.getText().toString();
        toCalc.putExtra(AMOUNT_ONE, a1_txt);

        TextView a2 = (TextView) findViewById(R.id.amount2);
        String a2_txt = a2.getText().toString();
        toCalc.putExtra(AMOUNT_TWO, a2_txt);

        TextView a3 = (TextView) findViewById(R.id.amount3);
        String a3_txt = a3.getText().toString();
        toCalc.putExtra(AMOUNT_THREE, a3_txt);


        startActivity(toCalc);
    }

    public void openGraphPage()
    {
        Intent toCalc = new Intent(this, Graph.class);

        TextView a1 = (TextView) findViewById(R.id.amount1);
        String a1_txt = a1.getText().toString();
        toCalc.putExtra(AMOUNT_ONE, a1_txt);

        TextView a2 = (TextView) findViewById(R.id.amount2);
        String a2_txt = a2.getText().toString();
        toCalc.putExtra(AMOUNT_TWO, a2_txt);

        TextView a3 = (TextView) findViewById(R.id.amount3);
        String a3_txt = a3.getText().toString();
        toCalc.putExtra(AMOUNT_THREE, a3_txt);

        startActivity(toCalc);
    }

}