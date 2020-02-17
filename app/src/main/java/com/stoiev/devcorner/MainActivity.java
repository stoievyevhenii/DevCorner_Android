package com.stoiev.devcorner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.stoiev.devcorner.DB.RoomActions;
import com.stoiev.devcorner.helpers.Message;
import com.stoiev.devcorner.helpers.ThemeChanger;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        final View activityRootView = findViewById(R.id.loginPage);
        ThemeChanger.setTheme(activityRootView, "BOTH");

        constraintLayout = findViewById(R.id.loginPage);
        constraintLayout.post(() -> {
            int height = constraintLayout.getHeight();
            constraintLayout.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
                if ((bottom - top) == height) {
                    hideElementsWheKeyboardIsActive("visible");
                } else {
                    hideElementsWheKeyboardIsActive("gone");
                }
            });
        });

    }

    public void checkData(final View view) {
        // Login data
        EditText loginField = findViewById(R.id.login);
        final String fieldLoginData = loginField.getText().toString();

        // Passwd data
        EditText passwdField = findViewById(R.id.passwd);
        final String fieldPasswdData = passwdField.getText().toString();

        // Check data
        if (fieldPasswdData.isEmpty() || fieldLoginData.isEmpty()) {
            Message successMessage = new Message("Oooooops!", "One of the fields is empty!");
            successMessage.show(getSupportFragmentManager(), "dialog");

        } else {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(fieldLoginData);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        // Check user login and password
                        db.collection("users")
                                .whereEqualTo("login", fieldLoginData)
                                .whereEqualTo("password", fieldPasswdData)
                                .get()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        for (QueryDocumentSnapshot document1 : Objects.requireNonNull(task1.getResult())) {
                                            Context context = getApplicationContext();
                                            CharSequence text = "Welcome back, " +
                                                    fieldLoginData + " !";
                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                            setUserStatus(fieldLoginData);
                                        }
                                    } else {
                                        Log.d("CheckResult", "2");
                                    }

                                });

                    } else {
                        Message successMessage = new Message("Oooooops!", "Our AI find error in your login!");
                        successMessage.show(getSupportFragmentManager(), "dialog");
                    }
                } else {
                    Log.d("CheckResult", "get failed with ", task.getException());
                }
            });
        }

    }

    public void openRegisterPage(View view) {
        Intent registerForm = new Intent(this, RegisterActivity.class);
        startActivity(registerForm);
    }

    public void setUserStatus(String login) {
        RoomActions.setUserStatus(login);
        Intent openHomePage = new Intent(this, HomeActivity.class);
        startActivity(openHomePage);
        finish();
    }

    private void hideElementsWheKeyboardIsActive(String visibility){
        ImageView logInIcon = findViewById(R.id.loginIcon);
        Button createNewAccountButton = findViewById(R.id.createAccount);

        switch(visibility){
            case "gone":
                logInIcon.setVisibility(View.GONE);
                createNewAccountButton.setVisibility(View.GONE);
                break;
            case "visible":
                logInIcon.setVisibility(View.VISIBLE);
                createNewAccountButton.setVisibility(View.VISIBLE);
                break;
        }

    }

}