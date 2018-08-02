package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class ActivitiesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activities_page);

        //navigation bar
        findViewById(R.id.regimen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitiesPage.this, PrescribedRegimen.class);
                ActivitiesPage.this.startActivity(intent);
            }
        });
        findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitiesPage.this, MainActivity.class);
                ActivitiesPage.this.startActivity(intent);
            }
        });
        findViewById(R.id.stats_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitiesPage.this, StatisticsPage.class);
                ActivitiesPage.this.startActivity(intent);
            }
        });

        //add activity button
        findViewById(R.id.add_activity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivitiesPage.this, AddActivityForm.class);
                ActivitiesPage.this.startActivity(intent);
            }
        });
    }


}