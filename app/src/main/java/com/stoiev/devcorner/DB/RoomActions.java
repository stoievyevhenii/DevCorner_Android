package com.stoiev.devcorner.DB;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.stoiev.devcorner.entity.User;

public class RoomActions extends AppCompatActivity {
    public static AppDatabase appDatabase;

    public static void initializeRoomDB(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class,
                "user_system_status").allowMainThreadQueries().build();
    }

    public static void setUserStatus(String login) {
        // Create local user
        User user = new User();
        user.login = login;
        user.user_status = 1;

        // Insert user in Room db
        appDatabase.userDao().insert(user);
    }

}

