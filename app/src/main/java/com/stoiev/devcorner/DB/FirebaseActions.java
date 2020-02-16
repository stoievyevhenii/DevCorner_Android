package com.stoiev.devcorner.DB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.stoiev.devcorner.dao.FirebaseDAO;
import com.stoiev.devcorner.helpers.TaskFormation;

import java.util.HashMap;
import java.util.Map;

public class FirebaseActions implements FirebaseDAO {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Map<String, Object> newUser = new HashMap<>();
    private Map<String, Object> newExercise = new HashMap<>();
    private Map<String, Object> exerciseLines = new HashMap<>();

    public FirebaseActions() {
    }

    @Override
    public void addExercise(String id, String exerciseTitle, String exerciseGroup, String exerciseBody, String author, String exerciseLanguage) {

        newExercise.put("id", id);
        newExercise.put("title", exerciseTitle);
        newExercise.put("group", exerciseGroup);
        newExercise.put("body", exerciseBody);
        newExercise.put("author", author);
        newExercise.put("language", exerciseLanguage);


        db.collection("exercises").document(id)
                .set(newExercise)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
        addDividedExerciseBody(id, exerciseBody);

    }

    @Override
    public void regNewUser(final String newUserLogin, final String newUserPassword) {
        CollectionReference docRef = db.collection("users");
        Query checkDataExists = docRef.whereEqualTo("login", newUserLogin);

        checkDataExists.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean isExisting = false;
                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String login;
                    login = ds.getString("login");
                    assert login != null;
                    if (login.equals(newUserLogin)) {
                        Log.d("RegResult", "User already exists");
                        isExisting = true;
                    }

                }
                if (!isExisting) {
                    Log.d("RegResult", "User isn't exists");
                    newUser.put("login", newUserLogin);
                    newUser.put("password", newUserPassword);

                    db.collection("users").document(newUserLogin)
                            .set(newUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("DB", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("DB", "Error writing document", e);
                                }
                            });
                }
            }
        });
    }

    @Override
    public void addDividedExerciseBody(String id, String body) {

        TaskFormation exercise = new TaskFormation();
        String[] codeLines = exercise.taskFormatting(body);

        exerciseLines.put("exerciseID", id);
        for (int i = 0; i < codeLines.length; i++) {
            exerciseLines.put("line " + i, codeLines[i]);
        }

        db.collection("dividedExercises").document(id)
                .set(exerciseLines)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
