package com.stoiev.devcorner.DB;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.stoiev.devcorner.HomeActivity;
import com.stoiev.devcorner.R;
import com.stoiev.devcorner.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomActions extends AppCompatActivity {
    static AppDatabase appDatabase;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_actions);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "user_system_status").allowMainThreadQueries().build();
        users = appDatabase.userDao().getAll();

        // Get new user
        Bundle b = getIntent().getExtras();
        String usrLogin;
        if (b != null) {
            usrLogin = b.getString("user");
            setUserStatus(usrLogin);
        }
    }

    public void setUserStatus(String login) {
        // Create local user
        User user = new User();
        user.login = login;
        user.user_status = 1;

        // Insert user in Room db
        appDatabase.userDao().insert(user);
        goToHomePage();
        finish();
    }

    public void goToHomePage(){
        Intent openHomePage = new Intent(this, HomeActivity.class);
        startActivity(openHomePage);
        finish();
    }

}

