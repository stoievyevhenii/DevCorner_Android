package com.stoiev.devcorner;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HomeList {
    //    private static DocumentReference db;
    public String title;
    public String group;
    public String author;

    static ArrayList<HomeList> itemsList = new ArrayList<>();

    // var for data from firebase
    public static ArrayList<HomeListItem> dataFromDB = new ArrayList<>();

    public HomeList() {
    }

    public HomeList(String title, String group, String author) {
        this.title = title;
        this.group = group;
        this.author = author;
    }

    String getTitle() {
        return title;
    }

    String getGroup() {
        return group;
    }

    String getAuthor() {
        return author;
    }

    static List<HomeList> getExerciseItems() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Install new data
        final ArrayList<HomeList> itemsList = new ArrayList<>();
//        itemsList.add(new HomeList("Exception", "Error", "Yevhenii Stoiev"));

//        db.collection("exercises")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
//                                dataFromDB = document.getData();
//
//                                // Insert data in ArrayList
//
//                                Log.d("Firebase", "itemsList = " + itemsList);
//                            }
//                        } else {
//                            Log.d("Firebase", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
        return itemsList;
    }

    // TODO: download data from server

    public static void getData() {

    }


}


