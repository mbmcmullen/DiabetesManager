package com.example.mcmull27.diabetesmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check to see if user is logged in
        SharedPreferences myprefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        loggedIn = myprefs.getBoolean("loggedIn", false);

        if(!loggedIn){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Login or Register")
                    .setCancelable(false)
                    .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent loginIntent
                                    = new Intent( MainActivity.this, SignIn.class );
                            MainActivity.this.startActivity( loginIntent );
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Register", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent registerIntent
                                    = new Intent( MainActivity.this, Register.class );
                            MainActivity.this.startActivity( registerIntent );
                            MainActivity.this.finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        }

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

    //pressing this returns home to signIn class even on initial bootup
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, SignIn.class);
        MainActivity.this.startActivity(intent);
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
}
