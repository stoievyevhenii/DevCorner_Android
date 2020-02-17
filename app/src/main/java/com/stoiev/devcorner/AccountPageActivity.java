package com.stoiev.devcorner;

import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.stoiev.devcorner.DB.RoomActions;
import com.stoiev.devcorner.entity.User;
import com.stoiev.devcorner.helpers.RecyclerViewSwipeDecorator;
import com.stoiev.devcorner.helpers.ThemeChanger;
import com.stoiev.devcorner.model.HomeListItem;

import java.util.List;

public class AccountPageActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference exerciseRef = db.collection("exercises");
    private CardExercisesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);

        // Init toolbar
        setUpToolbar();

        setUpRecyclerView("author");
        setUsername();

        final View activityRootView = findViewById(R.id.accountPage);
        ThemeChanger.setTheme(activityRootView, "NAVIGATION_BAR");
    }


    private void setUpRecyclerView(String sortBy) {
        Query query = exerciseRef.whereEqualTo(sortBy, setUsername());
        FirestoreRecyclerOptions<HomeListItem> options = new FirestoreRecyclerOptions.Builder<HomeListItem>()
                .setQuery(query, HomeListItem.class)
                .build();

        adapter = new CardExercisesAdapter(options);
        RecyclerView recycleView = findViewById(R.id.userExercises);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(AccountPageActivity.this, R.color.red))
                        .addActionIcon(R.drawable.delete_outline)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recycleView);
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.accountToolbar);
        setSupportActionBar(toolbar);

        // Toolbar back button
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
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
        List<User> users = RoomActions.appDatabase.userDao().getAll();

        // Delete all users from Room
        for (User usr : users) {
            RoomActions.appDatabase.userDao().delete(usr);
        }

        Intent openMain = new Intent(this, MainActivity.class);
        startActivity(openMain);
        // Remove all shortcuts
        removeShortcuts();
        finish();
    }

    public String setUsername() {
        String username = null;
        List<User> users = RoomActions.appDatabase.userDao().getAll();
        for (User usr : users) {
            username = usr.login;
        }

        TextView usernameField = findViewById(R.id.username);
        usernameField.setText(username);

        return username;
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

    private void removeShortcuts() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            if (shortcutManager != null) {
                shortcutManager.removeAllDynamicShortcuts();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent opeHomePage = new Intent(this, HomeActivity.class);
        startActivity(opeHomePage);
        finish();
    }

}
