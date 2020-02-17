package com.stoiev.devcorner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stoiev.devcorner.helpers.ThemeChanger;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_result);

        // Set action result title
        Bundle b = getIntent().getExtras();
        String actionResultText;
        if (b != null) {
            actionResultText = b.getString("newTitle");
            TextView resultText = findViewById(R.id.resultText);
            resultText.setText(actionResultText);
        }

        final View activityRootView = findViewById(R.id.resultPage);
        ThemeChanger.setTheme(activityRootView, "BOTH");
    }

    public void backToHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
