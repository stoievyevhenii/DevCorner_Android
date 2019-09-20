package com.stoiev.devcorner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;


public class ExercisePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);

        Bundle b = getIntent().getExtras();
        String newTitle = "Title";
        if (b != null) {
            newTitle = b.getString("newTitle");
            TextView pageTitle = findViewById(R.id.exercisePageTitle);
            pageTitle.setText(newTitle);
        }

    }

}


