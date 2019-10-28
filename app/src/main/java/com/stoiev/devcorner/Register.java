package com.stoiev.devcorner;

import android.os.Bundle;
import android.util.Log;
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
import com.stoiev.devcorner.models.RegUser;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText newUserLogin;
    EditText newUserPassword;
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

        ///////////////////////
        // --- Variables --- //
        ///////////////////////
        newUserLogin = findViewById(R.id.registerLogin);
        newUserPassword = findViewById(R.id.registerPasswd);
        regBtn = findViewById(R.id.reg_btn);
        ///////////////////////

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser.put("name", "yevheniich");
                newUser.put("password", "banana_yes");
                db.collection("users")
                        .add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText
                                (getApplicationContext(), "Success", Toast.LENGTH_SHORT)
                                .show();
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
        });

    }


}
