package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import static com.example.mcmull27.diabetesmanager.Table.tARRAY;
import static com.example.mcmull27.diabetesmanager.Table.tFROM_DATE;
import static com.example.mcmull27.diabetesmanager.Table.tTO_DATE;
import static com.example.mcmull27.diabetesmanager.Table.tTYPE;


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

        ty_txt = i.getStringExtra(tTYPE);
        fd_txt = i.getStringExtra(tFROM_DATE);
        td_txt = i.getStringExtra(tTO_DATE);

        Log.e("JKERR", "graph.ty_txt: " + ty_txt);
        Log.e("JKERR", "graph.fd_txt: " + fd_txt);
        Log.e("JKERR", "graph.td_txt: " + td_txt);

        actList = i.getParcelableArrayListExtra(tARRAY);



        if(ty_txt != null)
        {
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
                //Display bar Graphs for data
                ArrayList<Entry> lineEntries = new ArrayList<>();
                ArrayList<String> dateEntries = new ArrayList<>();

                LineChart lineChart = (LineChart) findViewById(R.id.lineGraph);

                for(int j = 0; j<actList.size(); j++)
                {
                    lineEntries.add(new Entry((float)actList.get(j).getAmount(),j));

                    Log.e("JKERR", "added value: " + actList.get(j).getAmount());
                }


                LineDataSet lineDataSet = new LineDataSet(lineEntries,"Dates");
                LineData lineData = new LineData(lineDataSet);
                lineChart.setData(lineData);
            }
            else
            {
                //Display bar Graphs for data
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                ArrayList<String> dateEntries = new ArrayList<>();

                double d,m,b,e = 0;

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
//        Intent toCalc = new Intent(this, Calculations.class);
//        toCalc.putExtra(TYPE,this.ty_txt);
//        toCalc.putExtra(FROM_DATE,this.fd_txt);
//        toCalc.putExtra(TO_DATE,this.td_txt);
//        startActivity(toCalc);
    }

    public void openTablePage(){
//        Intent toTable = new Intent(this, Table.class);
//        toTable.putExtra(TYPE,this.ty_txt);
//        toTable.putExtra(FROM_DATE,this.fd_txt);
//        toTable.putExtra(TO_DATE,this.td_txt);
//        startActivity(toTable);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater( ).inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId( );
        switch ( id ) {
            case R.id.menu_Login:
                Intent loginIntent
                        = new Intent( this, SignIn.class );
                this.startActivity( loginIntent );
                return true;
            case R.id.menu_Logout:              //currently does nothing
                Toast.makeText(this, "Successfully Logged out",Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                prefsEditor.putBoolean("loggedIn",false);
                prefsEditor.commit();
                Intent logoutIntent
                        = new Intent( this, MainActivity.class );
                this.startActivity( logoutIntent );
                return true;
            default:
                return super.onOptionsItemSelected( item );
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Graph.this, StatisticsPage.class);
        this.startActivity(intent);
    }
}
