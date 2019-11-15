package com.stoiev.devcorner.dao;

public interface FirebaseDAO {

    void addExercise(String exerciseTitle, String exerciseGroup, String exerciseBody, String author);
    void regNewUser(String newUserLogin, String newUserPassword);
    void getAllExercise();
}
