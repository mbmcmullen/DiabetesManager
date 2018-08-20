package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        final EditText userName = (EditText) findViewById(R.id.registerName_ET);
        final EditText emailAddress = (EditText) findViewById(R.id.registerEmail_ET);
        final EditText passWord = (EditText) findViewById(R.id.registerPassWord_ET);
        final Button registerButton = (Button) findViewById(R.id.registerLayout_button);

        //check to see if user is already in sharedPreferences: App will only support one user right now
        myPrefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        Boolean userAlreadyRegistered = myPrefs.getBoolean("userRegistered", false);

        if(userAlreadyRegistered){
            Intent login = new Intent(Register.this,SignIn.class);
            Register.this.startActivity(login);
            Toast.makeText(Register.this, "User already registered", Toast.LENGTH_SHORT ).show( );
            Register.this.finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                //check if all fields are filled out first
               if(!userName.getText().toString().trim().equals("") && !emailAddress.getText().toString().trim().equals("") && !passWord.getText().toString().trim().equals("") ){

                    String user = userName.getText().toString();
                    String email = emailAddress.getText().toString();
                    String pass = passWord.getText().toString();

                    //after getting data from our editText fields, open an editor to store it in our SharedPreferences
                    editor = myPrefs.edit();
                    editor.putString("username", user);
                    editor.putString("password", pass);
                    editor.putString("email", email);
                    editor.putBoolean("userRegistered",true);
                    editor.commit();
                    Toast.makeText(Register.this, "User successfully registered", Toast.LENGTH_SHORT ).show( );

                    //return to loginScreen
                    Intent login = new Intent(Register.this,SignIn.class);
                    Register.this.startActivity(login);
                    Register.this.finish();

                }
                else{
                    Toast.makeText(Register.this, "User unsuccessfully registered", Toast.LENGTH_LONG ).show( );
                }

            }


        });

    }

    @Override
    public void onBackPressed() {
        Intent login = new Intent(this,SignIn.class);
        startActivity(login);
        this.finish();
    }
}
