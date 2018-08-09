package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static com.example.mcmull27.diabetesmanager.StatisticsPage.FROM_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TO_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TYPE;

public class Calculations extends AppCompatActivity {
    private String ty;
    private String fd;
    private String td;
    private DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);

        Intent i = getIntent();

        ty = i.getStringExtra(TYPE);
        fd = i.getStringExtra(FROM_DATE);
        td = i.getStringExtra(TO_DATE);

        calculations(ty,fd,td);

        //button to go back to main stats screen
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open up the query activity
                openQueryPage();
            }
        });
     }

     //calculates the mean and standard deviance
     public void calculations(String type, String fromDate, String toDate)
     {
         db = new DatabaseManager(this);

        List<Act> actList;

         String a1_txt;
         String a2_txt;
         String a3_txt;

         int a1 =0, a2 = 0, a3 = 0;

         int count = 3;
         double sum = 0;
         double mean = 0;
         double stdDev = 0;

         try {
             actList = db.queryActs(type, fromDate, toDate);

             //calculate mean
             for (int i = 0; i < actList.size(); i++) {
                 count++;
                 sum += actList.get(i).getAmount();
             }

             mean = sum / count;

             //calculate standard deviance
             for (int i = 0; i < actList.size(); i++) {
                 stdDev += Math.pow(actList.get(i).getAmount() - mean, 2);
             }

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

         startActivity(toGraph);
     }

     public void openQueryPage()
     {
         Intent toTable = new Intent(Calculations.this, Table.class);
         toTable.putExtra("TYPE",this.ty);
         toTable.putExtra("FROM_DATE",this.fd);
         toTable.putExtra("TO_DATE",this.td);

         startActivity(toTable);
     }
}
