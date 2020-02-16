package com.stoiev.devcorner;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stoiev.devcorner.model.Exercise;

import java.util.ArrayList;

public class ExerciseLine {

    private String line;
    public String id;
    public static String title;
    private String exerciseCardID;

    private static Exercise exercise = new Exercise();

    private static ArrayList<ExerciseLine> linesDataList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    ExerciseLine() {
    }

    private ExerciseLine(String line) {
        this.line = line;
    }

    String getLine() {
        return line;
    }

    void getBodyFromDB(String cardID) {
//        db.collection("exercises")
//                .whereEqualTo("id", cardID)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                                document.toObject(Exercise.class);
//                                exercise.setBody(document.getString("body"));
//                                linesDataList.add(new ExerciseLine("First_line"));
//                                linesDataList.add(new ExerciseLine("Second_line"));
//                                linesDataList.add(new ExerciseLine("Third_line"));
//                                linesDataList.add(new ExerciseLine("Fourth_line"));
//                            }
//
//                        } else {
//                            Log.d("FirebaseActions", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//        linesDataList.add(new ExerciseLine(exercise.getBody()));
        exerciseCardID = cardID;
    }

    ArrayList<ExerciseLine> getExerciseLines() {
        DocumentReference docRef = db.collection("exercises").document(exerciseCardID);
        linesDataList.clear();
        docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        exercise = documentSnapshot.toObject(Exercise.class);
                        assert exercise != null;
                        String exerciseBody = exercise.getBody();
                        addItem(exerciseBody);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ErrorText", "Error");
                    }
                });
        return linesDataList;
    }

    private void addItem(String body) {
        Log.d("BodyText", "Body = " + exercise.getBodyList());
        linesDataList.add(new ExerciseLine(body));
    }

    public void cleanData() {
        linesDataList.clear();
    }


}

