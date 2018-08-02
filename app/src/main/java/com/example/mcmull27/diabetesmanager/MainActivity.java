package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login();

        //navigation bar
        findViewById(R.id.regimen_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PrescribedRegimen.class);
                MainActivity.this.startActivity(intent);
            }
        });
        findViewById(R.id.activities_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivitiesPage.class);
                MainActivity.this.startActivity(intent);
            }
        });
        findViewById(R.id.stats_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatisticsPage.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }


    public void Login(){
        Intent login = new Intent(this, SignIn.class);
        this.startActivity(login);
    }

}
