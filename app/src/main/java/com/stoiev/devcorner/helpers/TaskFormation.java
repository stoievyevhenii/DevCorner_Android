package com.stoiev.devcorner.helpers;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.stoiev.devcorner.ExerciseLine;

import java.util.Arrays;
import java.util.regex.Pattern;

public class TaskFormation {

    public TaskFormation(){
        Log.d("TaskForm","TaskFormation");
    }

    //
    // Download exercise
    //
    public void getExerciseData(String exerciseAuthor, String exerciseTitle){

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("exercises");

        // Sort data
        Query getExerciseBody = collRef.whereEqualTo("author", exerciseAuthor)
                .whereEqualTo("title", exerciseTitle);

        //Get data
        getExerciseBody.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot ds:queryDocumentSnapshots){
                    String originalText;
                    originalText = ds.getString("body");
                    assert originalText != null;
                    taskFormatting(originalText);
                }
            }
        });

    }

    //
    // Formatting exercise
    //
    private void taskFormatting(String bodyData){
        String[] codeLines = bodyData.split(Pattern.quote("\n"));
        Log.d("taskFormatting", "bodyData = " + Arrays.toString(codeLines));

        for (String codeLine: codeLines){
            ExerciseLine.addLine();
        }

    }

    //
    // Check user exercise
    // TODO: Complete the user exercise verification method
    private void checkUserExercise(String userExercise){

    }
}
