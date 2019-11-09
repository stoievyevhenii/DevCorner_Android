package com.stoiev.devcorner.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "login")
    public String login;

    @ColumnInfo(name = "user_status")
    public int user_status;

}
