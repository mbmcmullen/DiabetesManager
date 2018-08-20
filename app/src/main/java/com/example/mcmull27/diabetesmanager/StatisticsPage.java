package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
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
    public static final String TO_DATE = "com.example.mcmull27.diabetesmanager.TO_DATE";
    public static final String TYPE = "com.example.mcmull27.diabetesmanager.TYPE";
    public static final String CONTAINS = "com.example.mcmull27.diabetesmanager.CONTAINS";

    public EditText fd, td, ct;
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
        td = (EditText) findViewById(R.id.toText);
        fd = (EditText) findViewById(R.id.fromText);
        ct = (EditText) findViewById(R.id.keywordText);


        format = new SimpleDateFormat("mm/dd/yyyy");

        Button search = (Button) findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String c_txt = ct.getText().toString();
                String fd_txt = fd.getText().toString();
                String td_txt = td.getText().toString();

                if( fd_txt.equals("") && td_txt.equals("") ){openTablePage();
                }else if( (!fd_txt.equals("")) && (!td_txt.equals("")) ){
                    try{
                        format.parse(fd_txt).toString();
                        format.parse(td_txt).toString();
                        openTablePage();
                    }catch(Exception e){
                        Toast.makeText(StatisticsPage.this, "Invalid Date", Toast.LENGTH_SHORT).show();
                    }
                }else if( (fd_txt.equals("")) ){
                    try{
                        format.parse(td_txt).toString();
                        openTablePage();
                    }catch(Exception e){
                        Toast.makeText(StatisticsPage.this, "Invalid To Date", Toast.LENGTH_SHORT).show();
                    }
                }else if( td_txt.equals("") ){
                    try{
                        format.parse(fd_txt).toString();
                        openTablePage();
                    }catch(Exception e){
                        Toast.makeText(StatisticsPage.this, "Invalid From Date", Toast.LENGTH_SHORT).show();
                    }
                }
                openTablePage();
            }

        });

    }

    public void openTablePage()
    {
        format = new SimpleDateFormat(Act.DATE_FORMAT);
        Intent toTable= new Intent(this, Table.class);



        Spinner ty = (Spinner) findViewById(R.id.activity_type_spinner);
        String ty_txt = ty.getSelectedItem().toString().toUpperCase();


        fd = (EditText) findViewById(R.id.fromText);
        String _fd_txt = fd.getText().toString();

        ct = (EditText) findViewById(R.id.keywordText);
        String _c_txt = ct.getText().toString();

        //handler for search button

        td = (EditText) findViewById(R.id.toText);
        String _td_txt = td.getText().toString();


        toTable.putExtra(TYPE, ty_txt);
        toTable.putExtra(FROM_DATE, _fd_txt);
        toTable.putExtra(CONTAINS, _c_txt);
        toTable.putExtra(TO_DATE, _td_txt);

        Log.e("JKERR", "type from stats: " + _td_txt);
        Log.e("JKERR", "fromDate from stats: " + _fd_txt);
        Log.e("JKERR", "toDate from stats: " + _td_txt);
        Log.e("JKERR", "contains from stats: " + _c_txt);

        startActivity(toTable);
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
        Intent intent = new Intent(StatisticsPage.this, MainActivity.class);
        this.startActivity(intent);
    }




}