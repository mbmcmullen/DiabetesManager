package com.example.mcmull27.diabetesmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    private EditText userName, passWord;
    private String user, pass;
    private Button loginBtn, registerBtn;
    private Boolean saveLogin;
    private CheckBox rememberMeCheckBox;
    private SharedPreferences loginPrefs;
    private SharedPreferences.Editor loginEditor;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        userName = (EditText) findViewById(R.id.userName_ET);
        passWord = (EditText) findViewById(R.id.passWord_ET);
        loginBtn = (Button) findViewById(R.id.login_button);
        registerBtn = (Button) findViewById(R.id.register_Button);
        rememberMeCheckBox = (CheckBox)findViewById(R.id.rememberMe_check);

        //setup sharedPreferences and get the editor
        loginPrefs = getSharedPreferences("Preferences", MODE_PRIVATE);
        loginEditor = loginPrefs.edit();

        //get boolean value from preferences, if no value matches the key: saveLogin is set to false
        saveLogin = loginPrefs.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            userName.setText(loginPrefs.getString("username", ""));
            passWord.setText(loginPrefs.getString("password", ""));
            rememberMeCheckBox.setChecked(true);
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if fields are not null get the text from editTexts and store in strings
                if(!userName.getText().toString().trim().equals("") && !passWord.getText().toString().trim().equals("")) {
                    user = userName.getText().toString();
                    pass = passWord.getText().toString();

                }

                String userDetails = loginPrefs.getString("username","") +
                    loginPrefs.getString("password","");

                //check to see if user + password is a match
                if(userDetails.equals(user + pass)){

                    //check to see if rememberMe is checked
                    if(rememberMeCheckBox.isChecked()) {
                        loginEditor.putBoolean("saveLogin", true);
                        loginEditor.commit();
                    }
                    else{
                        loginEditor.putBoolean("saveLogin", false);
                        loginEditor.commit();
                    }

                    //put in preferences that a user is currently logged in
                    loginEditor.putBoolean("loggedIn",true);
                    loginEditor.commit();

                    Intent mainActivity = new Intent(SignIn.this, MainActivity.class);
                    startActivity(mainActivity);
                    SignIn.this.finish();
                }
                else{
                        Toast.makeText(SignIn.this, "Incorrect password or username.", Toast.LENGTH_SHORT).show();
                        passWord.setText("");
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
