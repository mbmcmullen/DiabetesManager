package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.os.Parcelable;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mcmull27.diabetesmanager.StatisticsPage.FROM_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TO_DATE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.TYPE;
import static com.example.mcmull27.diabetesmanager.StatisticsPage.CONTAINS;

public class Table extends AppCompatActivity {
    public List<Act> actList;
    RecyclerView recyclerView;
    private DatabaseManager db;
    private String fd;
    private String td;
    private String ty;
    private String ct;

    public static final String tFROM_DATE = "com.example.mcmull27.diabetesmanager.tFROM_DATE";
    public static final String tTO_DATE = "com.example.mcmull27.diabetesmanager.tTO_DATE";;
    public static final String tTYPE = "com.example.mcmull27.diabetesmanager.tTYPE";
    public static final String tCONTAINS = "com.example.mcmull27.diabetesmanager.tCONTAINS";
    public static final String tARRAY = "com.example.mcmull27.diabetesmanager.tARRAY";

    List<Act> displayList;
    QueryAdaptor adapter;

    Date toDate, fromDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        //load libraries for sqlcipher
        SQLiteDatabase.loadLibs(this);

        //get previous intent's data
        Intent intent = getIntent();
        ty = intent.getStringExtra(TYPE);

        fd = intent.getStringExtra(FROM_DATE);
        td = intent.getStringExtra(TO_DATE);
        ct = intent.getStringExtra(CONTAINS);

        //query the db
        db = DatabaseManager.getInstance(this);
        actList = db.selectAllActs();

        displayList = new ArrayList<>();

        //filter types
        for(int i=0; i<actList.size();i++){
            Act e = actList.get(i);
            if(e.getType().equals(ty)){
                displayList.add(e);
            }
        }
        SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");

        try{
            fromDate = format.parse(fd);

        }catch(Exception e){
            fromDate = null;
            Log.e("JKERR", "in table, error for date format: " + e.getLocalizedMessage());
        }

        try{
            toDate = format.parse(td);
        }catch(Exception e){

        }
        try{
            toDate = format.parse(td);
        }catch(Exception e){
            toDate = null;
            Log.e("JKERR", "in table, error for date format: " + e.getLocalizedMessage());
        }


Log.e("JKERR", "fromDate in Table: " + fromDate);
        Log.e("JKERR", "toDate in table: " + toDate);        for(int  i=(displayList.size()-1); i>=0; i--)
        {
            Act a = displayList.get(i);
            if((fromDate != null && a.getDateTime().before(fromDate))
                    || (toDate != null && a.getDateTime().after(toDate))
                    || ((ct != null && !ct.equals("")) && !a.getDescription().contains(ct)))
            {
                displayList.remove(i);
            }
        }


        //creating recyclerview adapter and delete listener
        adapter = new QueryAdaptor(displayList);
        adapter.setListener(new QueryAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Act e = displayList.get(position);
                db.deleteActByID(e.getId());
                displayList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        //setting adapter to recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        findViewById(R.id.graph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGraphPage();
            }
        });

        findViewById(R.id.stats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalculationsPage();
            }
        });
    }

    public void openGraphPage()
    {
        ArrayList<Act> x = new ArrayList<Act>();

        Intent toGraph = new Intent(this, Graph.class);

        x.addAll(displayList);
        Intent intent = getIntent();
        String _ty = intent.getStringExtra(TYPE);

        String _fd = intent.getStringExtra(FROM_DATE);
        String _td = intent.getStringExtra(TO_DATE);
        String _ct = intent.getStringExtra(CONTAINS);

        toGraph.putExtra(tTYPE,_ty);
        toGraph.putExtra(tFROM_DATE,_fd);
        toGraph.putExtra(tTO_DATE,_td);
        toGraph.putExtra(tCONTAINS, _ct);
        toGraph.putExtra(tARRAY, x);

        startActivity(toGraph);
    }

    public void openCalculationsPage(){
        Intent toCalc = new Intent(this, Calculations.class);

        Intent intent = getIntent();
        String _ty = intent.getStringExtra(TYPE);

        String _fd = intent.getStringExtra(FROM_DATE);
        String _td = intent.getStringExtra(TO_DATE);
        String _ct = intent.getStringExtra(CONTAINS);

        this.startActivity(toStats);
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
        Log.e("JKERR", "putting TYPE as " + _ty);
        Log.e("JKERR", "putting FROM_DATE as " + _fd);
        Log.e("JKERR", "putting TO_DATE as " + _td);
        toCalc.putExtra(tTYPE,_ty);
        toCalc.putExtra(tFROM_DATE,_fd);
        toCalc.putExtra(tTO_DATE,_td);
        toCalc.putExtra(tCONTAINS, _ct);

        startActivity(toCalc);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Table.this, StatisticsPage.class);
        this.startActivity(intent);
    }
}
