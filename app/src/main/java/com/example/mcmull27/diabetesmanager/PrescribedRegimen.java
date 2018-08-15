package com.example.mcmull27.diabetesmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PrescribedRegimen extends AppCompatActivity {
    private Regimen reg;
    private ListView lsView;
    private RegAdapter regAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regimen_layout);

        reg = new Regimen(this);
        lsView = (ListView) findViewById(R.id.regimen_ls);

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

        //list view
        regAdapter = new RegAdapter(
                this,
                android.R.layout.simple_list_item_1,
                reg.getActivities() );
        lsView.setAdapter(regAdapter);
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
        Intent intent = new Intent(PrescribedRegimen.this, MainActivity.class);
        PrescribedRegimen.this.startActivity(intent);
    }


    public class RegAdapter extends ArrayAdapter<Act> {
        private List<Act> objects;

        public RegAdapter(Context context, int textViewResourceId, List<Act> objects) {
            super(context, textViewResourceId, objects);
            this.objects = objects;
        }

        public View getView(int pos, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.reg_item, null);
            }

            Act a = objects.get(pos);
            v.setTag(a.getId());

            if (a != null) {
                switch(a.getType()) {
                    case(Act.BGL):
                        v.setBackgroundColor(getResources().getColor(R.color.colorBGLItem));
                        break;
                    case(Act.EXERCISE):
                        v.setBackgroundColor(getResources().getColor(R.color.colorExerciseItem));
                        break;
                    case(Act.DIET):
                        v.setBackgroundColor(getResources().getColor(R.color.colorDietItem));
                        break;
                    case(Act.MEDICATION):
                        v.setBackgroundColor(getResources().getColor(R.color.colorMedicationItem));
                        break;
                }
                ImageButton complete = (ImageButton) v.findViewById(R.id.btnComplete);
                ImageButton remove = (ImageButton) v.findViewById(R.id.btnRemove);

                TextView desc = (TextView) v.findViewById(R.id.txtDesc);
                TextView type = (TextView) v.findViewById(R.id.txtType);
                TextView amount = (TextView) v.findViewById(R.id.txtAmount);
                TextView date = (TextView) v.findViewById(R.id.txtDateTime);

                if (desc != null) {
                    desc.setText(a.getDescription());
                }
                if (type != null) {
                    type.setText(a.getType());
                }
                if (amount != null) {
                    amount.setText("" + a.getAmount());
                }
                if (date != null) {
                    date.setText(a.printDate());
                }

                if(complete != null) {
                    complete.setTag(a.getId());
                    complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("RegActivity", "Completed Click Listener called for id: " + (int)view.getTag());
                            reg.markCompleted((int) view.getTag());
                            regAdapter.notifyDataSetChanged();
                        }
                    });
                }
                if(remove != null) {
                    remove.setTag(a.getId());
                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("RegActivity", "Removed Click Listener called for id: " + (int)view.getTag());
                            reg.removeItem((int) view.getTag());
                            regAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            return v;
        }

    }
}
