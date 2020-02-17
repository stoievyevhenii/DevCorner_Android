package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stoiev.devcorner.model.Exercise;

import java.util.ArrayList;

public class ExerciseLine {

    private String line;
    public String id;
    public static String title;
    private static String exerciseCardID;

    private static Exercise exercise = new Exercise();

    private static ArrayList<ExerciseLine> linesDataList = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    ExerciseLine() {
    }

    private ExerciseLine(String line) {
        this.line = line;
    }

    String getLine() {
        return line;
    }

    static void initDocumentID(String cardID) {
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

    static ArrayList<ExerciseLine> getExerciseLines() {
        DocumentReference docRef = db.collection("exercises").document(exerciseCardID);
        linesDataList.clear();
        docRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    exercise = documentSnapshot.toObject(Exercise.class);

                    assert exercise != null;
                    String exerciseBody = exercise.getBody();
                    addItem(exerciseBody);
                })
                .addOnFailureListener(e -> Log.d("ErrorText", "Error"));

        linesDataList.add(new ExerciseLine("First_line"));
        linesDataList.add(new ExerciseLine("Second_line"));
        linesDataList.add(new ExerciseLine("Third_line"));
        linesDataList.add(new ExerciseLine("Fourth_line"));

        return linesDataList;
    }

    private static void addItem(String body) {
        Log.d("BodyText", "Body = " + exercise.getBodyList());
        linesDataList.add(new ExerciseLine(body));
    }

    void cleanData() {
        linesDataList.clear();
    }


}

