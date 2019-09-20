package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recycleView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private LinearLayoutManager horizontalLinearLayoutManager;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);

        // --- Set layout --- //

        recycleView = findViewById(R.id.cardsLayout);
        verticalLinearLayoutManager = new LinearLayoutManager(this);
        horizontalLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycleView.setLayoutManager(verticalLinearLayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter();
        recycleView.setAdapter(adapter);
        adapter.addAll(ModeItem.getFakeItems());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAccount:
                openAccountPage();
                return true;

            case R.id.menuAbout:
                openAboutPage();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Layout
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycleView.setLayoutManager(horizontalLinearLayoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recycleView.setLayoutManager(verticalLinearLayoutManager);
        }

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<Home.RecyclerViewHolder> {
        private ArrayList<ModeItem> items = new ArrayList<>();

        void addAll(List<ModeItem> fakeItems) {
            int pos = getItemCount();
            this.items.addAll(fakeItems);
            notifyItemRangeInserted(pos, this.items.size());
        }

        @NonNull
        @Override
        public Home.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new Home.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Home.RecyclerViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView group;
        private TextView author;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            group = itemView.findViewById(R.id.group);
            author = itemView.findViewById(R.id.author);
        }

        public void bind(ModeItem modeItem) {
            title.setText(modeItem.getTitle());
            group.setText(modeItem.getGroup());
            author.setText(modeItem.getAuthor());
        }
    }

    // Pages
    public void openPageForNewExercise(View view) {
        Intent addNewExercise = new Intent(this, NewExercise.class);
        startActivity(addNewExercise);
    }

    public void openAboutPage() {
        Intent openAbout = new Intent(this, About.class);
        startActivity(openAbout);
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

