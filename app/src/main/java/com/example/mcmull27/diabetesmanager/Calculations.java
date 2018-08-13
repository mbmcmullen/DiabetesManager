package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static com.example.mcmull27.diabetesmanager.Table.tCONTAINS;
import static com.example.mcmull27.diabetesmanager.Table.tFROM_DATE;
import static com.example.mcmull27.diabetesmanager.Table.tTO_DATE;
import static com.example.mcmull27.diabetesmanager.Table.tTYPE;


public class Calculations extends AppCompatActivity {
    private String ty;
    private String fd;
    private String td;
    private String ct;
    private DatabaseManager db;
    List<Act> displayList;
    Date fromDateD, toDateD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);

        Intent i = getIntent();

        Log.e("JKERR", "calc.TYPE received " + i.getStringExtra(tTYPE));
        Log.e("JKERR", "calc.FROM_DATE received " + i.getStringExtra(tFROM_DATE));
        Log.e("JKERR", "calc.TO_DATE received " + i.getStringExtra(tTO_DATE));
        Log.e("JKERR", "calc.CONTAINS received " + i.getStringExtra(tCONTAINS));

        ty = i.getStringExtra(tTYPE);
        fd = i.getStringExtra(tFROM_DATE);
        td = i.getStringExtra(tTO_DATE);
        ct = i.getStringExtra(tCONTAINS);

        calculations();

        //button to go to graph screen
        Button graph = (Button) findViewById(R.id.graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open up the graph activity
                openGraphPage();
            }
        });

        //button to go to query screen
        Button query = (Button) findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open up the query activity
                openQueryPage();
            }
        });

        //button to go back to main stats screen
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
     }

     //calculates the mean and standard deviance
     public void calculations()
     {
         db = new DatabaseManager(this);

        List<Act> actList;


         int a1 =0, a2 = 0, a3 = 0;

         int count = 3;
         double sum = 0;
         double mean = 0;
         double stdDev = 0;

         try {
             Log.e("JKERR", "0");
             actList = db.selectAllActs();

             Log.e("JKERR", "1");
             displayList = new ArrayList<>();

             Log.e("JKERR", "actList size: " + actList.size());

             //filter types
             for(int i=0; i<actList.size();i++){
                 Act e = actList.get(i);

                 if(e.getType().equals(ty)){
                     displayList.add(e);
                 }
             }

             SimpleDateFormat format = new SimpleDateFormat(Act.DATE_FORMAT);

             try{
                 fromDateD = format.parse(fd);
                 toDateD = format.parse(td);
             }catch(Exception e){
                 fromDateD = null;
                 toDateD = null;
             }

             for(int i=0; i<displayList.size(); i++)
             {
                 if((fromDateD != null && displayList.get(i).getDateTime().before(fromDateD)) || (toDateD != null && displayList.get(i).getDateTime().after(toDateD)) || ((ct != null && !ct.isEmpty()) && !displayList.get(i).getDescription().contains(ct)))
                 {
                     displayList.remove(i);
                 }
             }

             //calculate mean
             for (int i = 0; i < displayList.size(); i++) {
                 count++;
                 sum += displayList.get(i).getAmount();
                 Log.e("JKERR", "inside_mean_loop_amount: " + displayList.get(i).getAmount() + " \n\t\t\tsum: " + sum);
             }

             mean = sum / count;
             Log.e("JKERR", "Mean: " + mean);

             //calculate standard deviance
             for (int i = 0; i < displayList.size(); i++) {
                 stdDev += Math.pow(displayList.get(i).getAmount() - mean, 2);
             }

             Log.e("JKERR", "STD: " + stdDev);

             //set text fields
             TextView stdDev_res = (TextView) findViewById(R.id.stdDev_res);
             stdDev_res.setText(Double.toString(stdDev));

             TextView mean_res = (TextView) findViewById(R.id.mean_res);
             mean_res.setText(Double.toString(mean));
         }
         //if error occurs, populate fields with ----
         catch (Exception E)
         {
             TextView stdDev_res = (TextView) findViewById(R.id.stdDev_res);
             stdDev_res.setText("----");

             TextView mean_res = (TextView) findViewById(R.id.mean_res);
             mean_res.setText("----");
         }




     }

     public void openGraphPage()
     {
         Intent toGraph = new Intent(this, Graph.class);

         toGraph.putExtra("TYPE",this.ty);
         toGraph.putExtra("FROM_DATE",this.fd);
         toGraph.putExtra("TO_DATE",this.td);

         this.startActivity(toGraph);
     }

     public void openQueryPage()
     {
         Intent toTable = new Intent(Calculations.this, Table.class);
         toTable.putExtra("TYPE",this.ty);
         toTable.putExtra("FROM_DATE",this.fd);
         toTable.putExtra("TO_DATE",this.td);

         this.startActivity(toTable);
     }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Calculations.this, StatisticsPage.class);
        this.startActivity(intent);
    }
}
