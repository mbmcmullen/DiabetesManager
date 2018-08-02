package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        final EditText userName = (EditText) findViewById(R.id.userName_ET);
        final EditText  passWord = (EditText) findViewById(R.id.passWord_ET);
        Button loginBtn = (Button) findViewById(R.id.login_button);
        Button registerBtn = (Button) findViewById(R.id.register_Button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText()!= null && passWord.getText() != null){
                    String user = userName.getText().toString();
                    String pass = passWord.getText().toString();

                    SharedPreferences myPrefs = getSharedPreferences("Preferences" , MODE_PRIVATE);
                    String userDetails = myPrefs.getString(user + pass, "");

                    if(userDetails.equals(user + pass)){
                        Intent mainActivity = new Intent(SignIn.this, MainActivity.class);
                        startActivity(mainActivity);
                    }
                    else{
                        Toast.makeText(SignIn.this, "Incorrect password or username.", Toast.LENGTH_SHORT).show();
                        passWord.setText("");

                    }
                }
                else{
                    Toast.makeText(SignIn.this, "Incomplete password or username.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerScreen = new Intent(SignIn.this, Register.class);
                startActivity(registerScreen);
            }
        });
    }
}
