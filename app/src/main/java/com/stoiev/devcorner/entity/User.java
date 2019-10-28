package com.stoiev.devcorner.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "login")
    public String login;

    @ColumnInfo(name = "system_role")
    public String system_role;

}
