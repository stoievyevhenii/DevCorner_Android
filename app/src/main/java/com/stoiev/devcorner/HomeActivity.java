package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Canvas;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.stoiev.devcorner.model.HomeListItem;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

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

        // Set layout
        setUpRecyclerView("title");

        // Shortcuts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            checkShortcuts();
        }
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
        List<User> users = RoomActions.appDatabase.userDao().getAll();
        for (User usr : users) {
            user = usr.login;
        }

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
        finish();
    }

    public void openAccountPage() {
        Intent accountPage = new Intent(this, AccountPageActivity.class);
        startActivity(accountPage);
        finish();
    }

    public void openExercisePage(View view) {

        Intent openExercisePage = new Intent(this, ExercisePageActivity.class);

        // Get exercise title
        TextView cardTitle = view.findViewById(R.id.card_title);
        String cardTitleContent = cardTitle.getText().toString();

        // Get exercise author
        TextView cardAuthor = view.findViewById(R.id.author);
        String cardAuthorContent = cardAuthor.getText().toString();

        // Send bundle data
        Bundle b = new Bundle();
        b.putString("newTitle", cardTitleContent);
        b.putString("exerciseAuthor", cardAuthorContent);

        openExercisePage.putExtras(b);
        startActivity(openExercisePage);
        finish();

    }

    // Shortcuts
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void checkShortcuts(){
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        assert shortcutManager != null;

        // Get shortcuts list
        List<ShortcutInfo> shortcutList = shortcutManager.getDynamicShortcuts();
        int shortcutSize = shortcutList.size();

        // Check shortcuts
        if (shortcutSize == 0){
            setShortcut();
        }
    }

    private void setShortcut() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
            Intent openNewExercisePage = new Intent(this, NewExerciseActivity.class);
            openNewExercisePage.setAction(Intent.ACTION_VIEW);

            ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "NewExercise")
                    .setShortLabel("Add exercise")
                    .setLongLabel("Add exercise")
                    .setIcon(Icon.createWithResource(getApplicationContext(), R.drawable.fab))
                    .setIntent(openNewExercisePage)
                    .build();
            shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
        }
    }

}

