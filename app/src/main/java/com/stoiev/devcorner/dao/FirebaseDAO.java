package com.stoiev.devcorner.dao;
import android.view.View;

public interface FirebaseDAO {

    void registration_firebase(String newUserLogin, String newUserPassword, final View view);
    boolean[] checkUserData(String fieldLoginData, String fieldPasswdData);

}
