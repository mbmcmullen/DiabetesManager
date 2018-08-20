package com.example.mcmull27.diabetesmanager;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    @Override
    public void onDestroy() {
        Act a = reg.getActivities().get(0);
        int day = a.getDateTime().getDate(),
            month = a.getDateTime().getMonth(),
            year = a.getDateTime().getYear(),
            hour = a.getDateTime().getHours(),
            min = a.getDateTime().getMinutes();

        Scheduler.setReminder(this, NotificationReceiver.class, day, month, year, hour, min);

        super.onDestroy();
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
                            AddActDialog dialog = new AddActDialog(PrescribedRegimen.this, reg.getById((int) view.getTag()));
                            dialog.show();
                            Window window = dialog.getWindow();
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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


    public class AddActDialog extends Dialog implements android.view.View.OnClickListener {

        public PrescribedRegimen ctx;
        public Dialog d;
        public Button addBtn, cancelBtn;
        public Act act;
        public EditText desc, amount, date, time;

        public AddActDialog(PrescribedRegimen ctx, Act act) {
            super(ctx);
            this.ctx = ctx;
            this.act = act;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (act.getType().equals(Act.BGL)) {
                setContentView(R.layout.item_bgl);
            } else {
                setContentView(R.layout.item_act);
            }

            LinearLayout ll = (LinearLayout)findViewById(R.id.container);

            addBtn = new Button(PrescribedRegimen.this);
            addBtn.setText("Add");

            cancelBtn = new Button(PrescribedRegimen.this);
            cancelBtn.setText("Add");
            ll.addView(cancelBtn);


            addBtn.setOnClickListener(this);
            cancelBtn.setOnClickListener(this);

            date = (EditText) findViewById(R.id.item_date);
            time = (EditText) findViewById(R.id.item_time);
            desc = (EditText) findViewById(R.id.item_description);
            amount = (EditText) findViewById(R.id.item_amount);

            date.setText(act.getDate());
            time.setText(act.getTime());
            if (desc != null) {
                desc.setText(act.getDescription());
            }
            amount.setText("" + act.getAmount());

        }

        @Override
        public void onClick(View v) {
            if (v.equals(cancelBtn)) {
                dismiss();
            }
            Act a = new Act(act.getType());
            a.setAmount(Double.parseDouble(amount.getText().toString()));
            a.setDate(date.getText().toString());
            a.setTime(time.getText().toString());
            if (desc != null) {
                a.setDescription(desc.getText().toString());
            }

            if (valid(a)) {
                reg.insertAct(a);
                reg.removeItem(act.getId());
                Toast.makeText(PrescribedRegimen.this, "Activity Added", Toast.LENGTH_SHORT).show();
                regAdapter.notifyDataSetChanged();
                dismiss();
            } else {
                Toast.makeText(PrescribedRegimen.this, "Incomplete or invalid activity.", Toast.LENGTH_SHORT).show();
            }


        }

        private boolean valid(Act a){
            if(a.getType().equals(Act.BGL)&&
                    (a.getAmount()<70||a.getAmount()>350))return false;
            else if(a.getAmount()==0) return false;

            if(a.getDescription()=="") return false;

            String timestamp = a.getTimestamp();
            SimpleDateFormat format = new SimpleDateFormat(Act.DATE_FORMAT);
            try{
                a.setDateTime(format.parse(timestamp));
            }catch(Exception e){
                return false;
            }
            return true;
        }
    }
}
