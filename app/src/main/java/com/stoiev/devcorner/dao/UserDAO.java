package com.stoiev.devcorner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.stoiev.devcorner.entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Insert
    void insert(User... users);

    @Delete
    void delete(User user);

}