package com.stoiev.devcorner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);

        // Set action result title
        Bundle b = getIntent().getExtras();
        String actionResultText;
        if(b != null){
            actionResultText = b.getString("newTitle");
            TextView resultText = findViewById(R.id.resultText);
            resultText.setText(actionResultText);
        }
    }

    public void backToHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
