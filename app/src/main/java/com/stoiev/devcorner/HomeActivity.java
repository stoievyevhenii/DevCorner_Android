package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.stoiev.devcorner.DB.AppDatabase;
import com.stoiev.devcorner.entity.User;
import com.stoiev.devcorner.helpers.RecyclerViewSwipeDecorator;
import com.stoiev.devcorner.model.HomeListItem;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static AppDatabase appDatabase;

    // var for firebase
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exerciseRef = db.collection("exercises");
    private CardExercisesAdapter adapter;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

        // Init Room
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "user_system_status").allowMainThreadQueries().build();

        // --- Set layout --- //
        setUpRecyclerView("title");
    }


    private void setUpRecyclerView(String sortBy) {
        String user = null;

        Query query = exerciseRef.orderBy(sortBy, Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<HomeListItem> options = new FirestoreRecyclerOptions.Builder<HomeListItem>()
                .setQuery(query, HomeListItem.class)
                .build();

        adapter = new CardExercisesAdapter(options);
        RecyclerView recycleView = findViewById(R.id.cardsLayout);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);

        // Delete item if user = admin
        List<User> users = appDatabase.userDao().getAll();
        for (User usr : users) {
            user = usr.login;
        }

        assert user != null;
        if (user.equals("admin")) {
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    adapter.deleteItem(viewHolder.getAdapterPosition());
                }

                @Override
                public void onChildDraw(Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.red))
                            .addActionIcon(R.drawable.delete_outline)
                            .create()
                            .decorate();

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }

            }).attachToRecyclerView(recycleView);

        }
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
        Intent addNewExercise = new Intent(this, NewExerciseActivity.class);
        startActivity(addNewExercise);
    }

    public void openAccountPage() {
        Intent accountPage = new Intent(this, AccountPageActivity.class);
        startActivity(accountPage);
    }

    public void openExercisePage(View view) {
        TextView cardTitle = view.findViewById(R.id.card_title);
        String cardTitleContent = cardTitle.getText().toString();

        Intent openExercisePage = new Intent(this, ExercisePageActivity.class);

        Bundle b = new Bundle();
        b.putString("newTitle", cardTitleContent);
        openExercisePage.putExtras(b);
        startActivity(openExercisePage);

    }

}

