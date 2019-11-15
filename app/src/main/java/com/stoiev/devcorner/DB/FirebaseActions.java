package com.stoiev.devcorner.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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

    @Override
    public void getAllExercise() {
        db.collection("exercises")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Firebase", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("Firebase", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
