package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.stoiev.devcorner.DB.AppDatabase;
import com.stoiev.devcorner.DB.FirebaseActions;
import com.stoiev.devcorner.DB.RoomActions;
import com.stoiev.devcorner.entity.User;
import com.stoiev.devcorner.helpers.Message;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

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

    @SuppressLint("PrivateResource")
    protected void regNewUser(final String newUserLogin, final String newUserPassword) {

        if (newUserLogin.isEmpty() || newUserPassword.isEmpty()) {
            Message successMessage = new Message("Oooooops!", "One of the fields is empty!");
            successMessage.show(getSupportFragmentManager(), "success dialog");
        } else {
            CollectionReference docRef = db.collection("users");
            Query checkDataExists = docRef.whereEqualTo("login", newUserLogin);

            checkDataExists.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    boolean isExisting = false;

                    // Check user in system
                    for (DocumentSnapshot ds : queryDocumentSnapshots) {
                        String login;
                        login = ds.getString("login");
                        if (login.equals(newUserLogin)) {
                            Log.d("RegResult", "User already exists");
                            isExisting = true;
                            Message successMessage = new Message("Oooooops!", "User already exist!");
                            successMessage.show(getSupportFragmentManager(), "success dialog");
                        }

                    }

                    // If user isn`t in system => register new user
                    if (!isExisting) {
                        newUser.put("login", newUserLogin);
                        newUser.put("password", newUserPassword);

                        db.collection("users").document(newUserLogin)
                                .set(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Context context = getApplicationContext();
                                        CharSequence text = "Welcome in our friendly family, " +
                                                newUserLogin + " !";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();

                                        // Open home page
                                        setUserStatus(newUserLogin);
//                                        openHomePage();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("DB", "Error writing document", e);
                                    }
                                });
                    }
                }
            });
        }
    }

    public void setUserStatus(String login){
        RoomActions.setUserStatus(login);
        Intent openHomePage = new Intent(this, HomeActivity.class);
        startActivity(openHomePage);
        finish();
    }

}
