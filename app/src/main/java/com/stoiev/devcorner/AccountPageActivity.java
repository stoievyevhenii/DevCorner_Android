package com.stoiev.devcorner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.stoiev.devcorner.DB.AppDatabase;
import com.stoiev.devcorner.entity.User;
import com.stoiev.devcorner.model.HomeListItem;

import java.util.List;

public class AccountPageActivity extends AppCompatActivity {
    private static AppDatabase appDatabase;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exerciseRef = db.collection("exercises");
    private ExercisesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);

        // Init Room database
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "user_system_status").allowMainThreadQueries().build();

        // Init toolbar
        setUpToolbar();

        setUpRecyclerView();
        setUsername();
    }


    private void setUpRecyclerView() {
        Query query = exerciseRef.orderBy("title", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<HomeListItem> options = new FirestoreRecyclerOptions.Builder<HomeListItem>()
                .setQuery(query, HomeListItem.class)
                .build();

        adapter = new ExercisesAdapter(options);
        RecyclerView recycleView = findViewById(R.id.userExercises);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.accountToolbar);
        setSupportActionBar(toolbar);

        // Toolbar back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //  -- Firebase rules
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    // -- end firebase rules

    public void logout(View view) {
        List<User> users = appDatabase.userDao().getAll();

        // Delete all users from Room
        for (User usr : users) {
            appDatabase.userDao().delete(usr);
        }

        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
    }

    public void setUsername() {
        String username = null;
        List<User> users = appDatabase.userDao().getAll();
        for (User usr : users) {
            username = usr.login;
        }

        TextView usernameField = findViewById(R.id.username);
        usernameField.setText(username);
    }
}
