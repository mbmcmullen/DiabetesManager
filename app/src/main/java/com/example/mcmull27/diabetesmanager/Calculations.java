package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.mcmull27.diabetesmanager.StatisticsPage.AMOUNT_ONE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.AMOUNT_THREE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.AMOUNT_TWO;

public class Calculations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);

        calculations();


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
     }

     //calculates the mean and standard deviance
     public void calculations()
     {
         //get passed in values from intent
         Intent intent = getIntent();

         String a1_txt;
         String a2_txt;
         String a3_txt;

         int a1 =0, a2 = 0, a3 = 0;

         int count = 3;
         double sum = 0;
         double mean = 0;
         double stdDev = 0;


         if(intent.getStringExtra(AMOUNT_THREE) == null || intent.getStringExtra(AMOUNT_THREE).equalsIgnoreCase("") || intent.getStringExtra(AMOUNT_THREE).equalsIgnoreCase("amount"))
         {
             a3_txt = "";
             count--;
         }
         else
         {
             a3_txt = intent.getStringExtra(AMOUNT_THREE).toString();
             a3 = Integer.parseInt(a3_txt);
             sum += a3;
         }

         if(intent.getStringExtra(AMOUNT_TWO) == null || intent.getStringExtra(AMOUNT_TWO).equalsIgnoreCase("") || intent.getStringExtra(AMOUNT_TWO).equalsIgnoreCase("amount"))
         {
             a2_txt = "";
             count--;
         }
         else
         {
             a2_txt = intent.getStringExtra(AMOUNT_TWO).toString();
             a2 = Integer.parseInt(a2_txt);
             sum += a2;
         }

         if(intent.getStringExtra(AMOUNT_ONE) == null || intent.getStringExtra(AMOUNT_ONE).equalsIgnoreCase("") || intent.getStringExtra(AMOUNT_ONE).equalsIgnoreCase("amount"))
         {
             a1_txt = "";
             count--;
         }
         else
         {
             a1_txt = intent.getStringExtra(AMOUNT_ONE).toString();
             a1 = Integer.parseInt(a1_txt);
             sum += a1;
         }

         //calculate the mean and standard.dev
         if(count > 0)
         {
             mean = sum/count;
             if(a1 > 0)
             {
                 stdDev += Math.pow(a1 - mean, 2);
             }
             if(a2 >0)
             {
                 stdDev += Math.pow(a2 - mean, 2);
             }
             if(a3 >0)
             {
                 stdDev += Math.pow(a3 - mean, 2);
             }

             TextView stdDev_res = (TextView) findViewById(R.id.stdDev_res);
             stdDev_res.setText(Double.toString(stdDev));

             TextView mean_res = (TextView) findViewById(R.id.mean_res);
             mean_res.setText(Double.toString(mean));

         }
         else
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
         startActivity(toGraph);
     }
}
