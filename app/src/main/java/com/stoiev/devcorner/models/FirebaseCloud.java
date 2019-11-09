package com.stoiev.devcorner.models;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.stoiev.devcorner.dao.FirebaseDAO;

import java.util.Objects;

public class FirebaseCloud implements FirebaseDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean[] result = new boolean[2];

    public FirebaseCloud() {
    }

    public boolean[] checkUserData(String fieldLoginData, String fieldPasswdData) {
        result[0] = false;
        db.collection("users")
                .whereEqualTo("login", fieldLoginData).whereEqualTo("password", fieldPasswdData)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                result[1] = true;
                            }

                        }
                    }
                });
        return new boolean[]{result[result.length - 1]};
    }

    public void registration_firebase(String newUserLogin, String newUserPassword, View view) {

    }
}
