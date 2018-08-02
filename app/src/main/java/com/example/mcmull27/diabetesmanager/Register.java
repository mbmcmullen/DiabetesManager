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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        final EditText userName = (EditText) findViewById(R.id.registerName_ET);
        final EditText emailAddress = (EditText) findViewById(R.id.registerEmail_ET);
        final EditText passWord = (EditText) findViewById(R.id.registerPassWord_ET);
        final Button registerButton = (Button) findViewById(R.id.registerLayout_button);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                if(userName.getText()!= null && emailAddress.getText() != null && passWord.getText() != null){

                    SharedPreferences myPrefs = getSharedPreferences("Preferences",MODE_PRIVATE);
                    String user = userName.getText().toString();
                    String email = emailAddress.getText().toString();
                    String pass = passWord.getText().toString();

                    SharedPreferences.Editor editor = myPrefs.edit();

                    editor.putString(user + pass, user + "\n" + email);
                    editor.commit();
                    Toast.makeText(Register.this, "User successfully registered", Toast.LENGTH_SHORT ).show( );

                    Intent login = new Intent(Register.this, SignIn.class);
                    startActivity(login);

                }
                else{
                    Toast.makeText(Register.this, "User unsuccessfully registered", Toast.LENGTH_LONG ).show( );
                }

            }


        });

    }

    public void goBack(View v){
        this.finish();
    }
}
