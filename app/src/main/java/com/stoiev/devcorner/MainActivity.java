package com.stoiev.devcorner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.stoiev.devcorner.DB.AppDatabase;
import com.stoiev.devcorner.entity.User;
import com.stoiev.devcorner.helpers.Message;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init DB
        fragmentManager = getSupportFragmentManager();
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "user_system_status").allowMainThreadQueries().build();

    }

    ///////////
    // Login //
    ///////////

    public void checkData(final View view) {

        // Login data
        EditText loginField = findViewById(R.id.login);
        final String fieldLoginData = loginField.getText().toString().replace(" ", "");

        // Passwd data
        EditText passwdField = findViewById(R.id.passwd);
        final String fieldPasswdData = passwdField.getText().toString().replace(" ", "");

        // Check data
        if (fieldPasswdData.isEmpty() || fieldLoginData.isEmpty()) {
            Message successMessage = new Message("Oooooops!", "One of the fields is empty!");
            successMessage.show(getSupportFragmentManager(), "dialog");

        } else {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(fieldLoginData);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Check user login and password
                            db.collection("users")
                                    .whereEqualTo("login", fieldLoginData)
//                                    .whereEqualTo("password", fieldPasswdData)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(Task<QuerySnapshot> task) {
                                            Log.d("CheckResult", "1");
                                            if (task.isSuccessful()) {
                                                Log.d("CheckResult", "2");
                                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                                    Log.d("CheckResult", "3");
                                                    setUserStatus(view, fieldLoginData);
                                                    openHomePage();
                                                }
                                            } else {
                                                Log.d("CheckResult", "2");
                                            }

                                        }

                                    });

                        } else {
                            Message successMessage = new Message("Oooooops!", "Our AI find error in your login!");
                            successMessage.show(getSupportFragmentManager(), "dialog");
                        }
                    } else {
                        Log.d("CheckResult", "get failed with ", task.getException());
                    }
                }
            });
        }

    }

    public void openRegisterPage(View view) {
        Intent registerForm = new Intent(this, RegisterActivity.class);
        startActivity(registerForm);
    }

    public void openHomePage() {
        Intent openHomePage = new Intent(this, HomeActivity.class);
        startActivity(openHomePage);
        finish();
    }

    public void setUserStatus(View view, String login) {
        // Create local user
        User user = new User();
        user.login = login;
        user.user_status = 1;

        // Insert user in Room db
        appDatabase.userDao().insert(user);
    }
}