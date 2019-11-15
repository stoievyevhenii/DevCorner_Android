package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.stoiev.devcorner.model.HomeListItem;

public class Home extends AppCompatActivity {

    // var for firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exerciseRef = db.collection("exercises");
    private ExercisesAdapter adapter;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

        // --- Set layout --- //
        setUpRecyclerView();
    }


    private void setUpRecyclerView() {
        Query query = exerciseRef.orderBy("title", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<HomeListItem> options = new FirestoreRecyclerOptions.Builder<HomeListItem>()
                .setQuery(query, HomeListItem.class)
                .build();

        adapter = new ExercisesAdapter(options);
        RecyclerView recycleView = findViewById(R.id.cardsLayout);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAccount) {
            openAccountPage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Pages
    public void openPageForNewExercise(View view) {
        Intent addNewExercise = new Intent(this, NewExercise.class);
        startActivity(addNewExercise);
    }

    public void openAccountPage() {
        Toast.makeText
                (getApplicationContext(), "Account page in work, thank's for you patience", Toast.LENGTH_SHORT)
                .show();
    }

    public void openExercisePage(View view) {
        TextView cardTitle = view.findViewById(R.id.card_title);
        String cardTitleContent = cardTitle.getText().toString();

        Intent openExercisePage = new Intent(this, ExercisePage.class);

        Bundle b = new Bundle();
        b.putString("newTitle", cardTitleContent);
        openExercisePage.putExtras(b);

        startActivity(openExercisePage);


    }

}

