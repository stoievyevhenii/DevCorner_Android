package com.stoiev.devcorner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
                regNewUser(newUserLogin, newUserPassword, view);
            }
        });

    }

    protected void regNewUser(String newUserLogin, String newUserPassword, final View view) {

        newUser.put("login", newUserLogin);
        newUser.put("password", newUserPassword);
        db.collection("users")
                .add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText
                        (getApplicationContext(), "Success", Toast.LENGTH_SHORT)
                        .show();
                backToLogin(view);
                finish();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText
                                (getApplicationContext(), "Oopps, something went wrong", Toast.LENGTH_SHORT)
                                .show();

                    }
                });
    }

    protected void backToLogin(View view) {
        Intent backToLogin = new Intent(this, MainActivity.class);
        startActivity(backToLogin);
    }

}
