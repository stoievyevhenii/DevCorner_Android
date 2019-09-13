package com.stoiev.devcorner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ///////////
    // Login //
    ///////////

    public void checkData(View view) {
        LoginData getData = new LoginData();
        // Login data
        EditText loginField = findViewById(R.id.login);
        String fieldLoginData = loginField.getText().toString();
        String loginData = getData.getLogin();

        // Passwd data
        EditText passwdField = findViewById(R.id.passwd);
        String fieldPasswdData = passwdField.getText().toString();
        String passwdData = getData.getPasswd();

        // Check login data
        if (fieldLoginData.equals(loginData) && fieldPasswdData.equals(passwdData)) {
            Intent homeForm = new Intent(this, Home.class);
            startActivity(homeForm);
            finish();
        } else if (!fieldLoginData.equals(loginData)){
            showMessage("Incorrect login");
        } else if(!fieldPasswdData.equals(passwdData)){
            showMessage("Incorrect password");
        } else{
            showMessage("Incorrect login and password");
        }
    }

    private void showMessage(String messageContext){
        Context context = getApplicationContext();
        CharSequence messageText = messageContext;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, messageText, duration);
        toast.show();
    }
}

class LoginData {
    private String login = "yevheniich";
    private String password = "qwerty";

    public LoginData() {
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return password;
    }
}