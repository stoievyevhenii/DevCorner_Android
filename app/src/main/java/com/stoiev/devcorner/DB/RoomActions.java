package com.stoiev.devcorner.DB;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.stoiev.devcorner.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomActions extends AppCompatActivity {
    static AppDatabase appDatabase;
    List<User> users;

    Map<String, Object> newUser = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_room_actions);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "user_system_status").allowMainThreadQueries().build();
        users = appDatabase.userDao().getAll();
    }

}
