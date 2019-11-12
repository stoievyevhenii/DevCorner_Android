package com.stoiev.devcorner.DB;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stoiev.devcorner.dao.FirebaseDAO;

import java.util.HashMap;
import java.util.Map;

public class FirebaseActions implements FirebaseDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String, Object> newUser = new HashMap<>();
    private Map<String, Object> newExercise = new HashMap<>();


    @Override
    public void addExercise(String exerciseTitle, String exerciseGroup, String exerciseBody, String author) {
        newExercise.put("title", exerciseTitle);
        newExercise.put("group", exerciseGroup);
        newExercise.put("body", exerciseBody);
        newExercise.put("author", author);
        db.collection("exercises")
                .add(newExercise).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    public void regNewUser(String newUserLogin, String newUserPassword) {

        newUser.put("login", newUserLogin);
        newUser.put("password", newUserPassword);
        db.collection("users")
                .add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}
