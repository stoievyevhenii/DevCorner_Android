package com.stoiev.devcorner.dao;

public interface FirebaseDAO {

    void addExercise(String id,
                     String exerciseTitle,
                     String exerciseGroup,
                     String exerciseBody,
                     String author,
                     String exerciseLanguage);

    void regNewUser(String newUserLogin, String newUserPassword);

    void addDividedExerciseBody(String id, String body);
}
