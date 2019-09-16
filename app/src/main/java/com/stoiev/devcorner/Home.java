package com.stoiev.devcorner;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recycleView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private LinearLayoutManager horizontalLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.newExerciseToolbar);
        setSupportActionBar(toolbar);

        // --- Set layout --- //

        recycleView = findViewById(R.id.cardsLayout);
        verticalLinearLayoutManager = new LinearLayoutManager(this);
        horizontalLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycleView.setLayoutManager(verticalLinearLayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter();
        recycleView.setAdapter(adapter);
        adapter.addAll(ModeItem.getFakeItems());

        // FAB scroll effect
        RecyclerView mRecyclerView = findViewById(R.id.cardsLayout);
        final FloatingActionButton mFloatingActionButton = findViewById(R.id.fab);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && mFloatingActionButton.getVisibility() == View.VISIBLE) {
                    mFloatingActionButton.hide();
                } else if (dy < 0 && mFloatingActionButton.getVisibility() != View.VISIBLE) {
                    mFloatingActionButton.show();
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recycleView.setLayoutManager(horizontalLinearLayoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recycleView.setLayoutManager(verticalLinearLayoutManager);
        }

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<Home.RecyclerViewHolder> {
        private ArrayList<ModeItem> items = new ArrayList<>();

        public void addAll(List<ModeItem> fakeItems) {
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
            title = itemView.findViewById(R.id.title);
            group = itemView.findViewById(R.id.group);
            author = itemView.findViewById(R.id.author);
        }

        public void bind(ModeItem modeItem) {
            title.setText(modeItem.getTitle());
            group.setText(modeItem.getGroup());
            author.setText(modeItem.getAuthor());
        }
    }

    public void openPageForNewExercise(View view) {
        Intent addNewExercise = new Intent(this, NewExercise.class);
        startActivity(addNewExercise);
    }

}

