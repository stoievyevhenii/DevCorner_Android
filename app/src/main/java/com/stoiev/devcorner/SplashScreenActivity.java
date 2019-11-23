package com.stoiev.devcorner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.stoiev.devcorner.DB.RoomActions;
import com.stoiev.devcorner.entity.User;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        RoomActions.initializeRoomDB(getApplicationContext());

        // Check user status in system
        checkUserStatus();

    }

    private void checkUserStatus() {
        List<User> users = RoomActions.appDatabase.userDao().getAll();
        int status = 0;
        int statusInSystem = 0;

        // If Room does not have users
        try {
            for (User usr : users) {
                statusInSystem = usr.user_status;
            }
        } catch (Exception e) {
            openNextPage("MainActivity");
        }

        // Open next page
        if (statusInSystem > status) {
            openNextPage("HomeActivity");
        } else {
            openNextPage("MainActivity");
        }

    }

    private void openNextPage(String nextPage) {
        switch (nextPage) {
            case "HomeActivity":
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case "MainActivity":
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                finish();
                break;
        }
    }


}
