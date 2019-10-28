package com.stoiev.devcorner;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.stoiev.devcorner.dao.UserDAO;
import com.stoiev.devcorner.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
}