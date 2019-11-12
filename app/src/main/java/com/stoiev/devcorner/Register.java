package com.stoiev.devcorner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.stoiev.devcorner.DB.FirebaseActions;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText newUserLoginField;
    EditText newUserPasswordField;
    String newUserLogin;
    String newUserPassword;
    Button regBtn;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    Map<String, Object> newUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /////////////////////
        // --- Actions --- //
        /////////////////////
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newUserLoginField = findViewById(R.id.registerLogin);
        newUserPasswordField = findViewById(R.id.registerPasswd);

        regBtn = findViewById(R.id.reg_btn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUserLogin = newUserLoginField.getText().toString();
                newUserPassword = newUserPasswordField.getText().toString();
                regNewUser(newUserLogin, newUserPassword);
            }
        });

    }

    protected void regNewUser(String newUserLogin, String newUserPassword) {
        FirebaseActions callFirebaseActions = new FirebaseActions();
        callFirebaseActions.regNewUser(newUserLogin, newUserPassword);
    }

    public void backToLogin() {
        Intent backToLogin = new Intent(this, MainActivity.class);
        startActivity(backToLogin);
    }

}
