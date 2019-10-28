package com.stoiev.devcorner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    public static SplashScreen instance;
    private AppDatabase database;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load data (SQLite)
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "user-role").build();
        //
        // Load data ( --- FIREBASE ---)
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Hello beautiful World");
        myRef.setValue("Hello, World!");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static SplashScreen getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
