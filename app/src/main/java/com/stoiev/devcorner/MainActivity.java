package com.stoiev.devcorner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.stoiev.devcorner.DB.AppDatabase;
import com.stoiev.devcorner.entity.User;
import com.stoiev.devcorner.helpers.Message;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        String fieldPasswdData = passwdField.getText().toString().replace(" ", "");

        // Check data
        if (fieldPasswdData.isEmpty() || fieldLoginData.isEmpty()) {
            Message successMessage = new Message("Oooooops!","One of the fields is empty!");
            successMessage.show(getSupportFragmentManager(), "success dialog");

        } else {
            final FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .whereEqualTo("login", fieldLoginData).whereEqualTo("password", fieldPasswdData)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    setUserStatus(view, fieldLoginData);
                                    openHomePage();
                                }
                            }
                        }
                    });
        }

    }

    public void openRegisterPage(View view) {
        Intent registerForm = new Intent(this, Register.class);
        startActivity(registerForm);
    }

    public void openHomePage() {
        Intent openHomePage = new Intent(this, Home.class);
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