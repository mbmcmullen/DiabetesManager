package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PrescribedRegimen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regimen_layout);

        //navigation bar
        findViewById(R.id.home_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescribedRegimen.this, MainActivity.class);
                PrescribedRegimen.this.startActivity(intent);
            }
        });
        findViewById(R.id.activities_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescribedRegimen.this, ActivitiesPage.class);
                PrescribedRegimen.this.startActivity(intent);
            }
        });
        findViewById(R.id.stats_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrescribedRegimen.this, StatisticsPage.class);
                PrescribedRegimen.this.startActivity(intent);
            }
        });
    }
}
