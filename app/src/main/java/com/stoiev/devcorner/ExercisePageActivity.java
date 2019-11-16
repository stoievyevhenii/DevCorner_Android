package com.stoiev.devcorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class ExercisePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager verticalLinearLayoutManager;
    private LinearLayoutManager horizontalLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);

        // Set title text
        Bundle b = getIntent().getExtras();
        String newTitle;
        if (b != null) {
            newTitle = b.getString("newTitle");
            TextView pageTitle = findViewById(R.id.exercisePageTitle);
            pageTitle.setText(newTitle);
        }

        // Set new layout
        recyclerView = findViewById(R.id.exercise_lines);
        verticalLinearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(verticalLinearLayoutManager);
        final RecyclerAdapter exercise_adapter = new RecyclerAdapter();
        recyclerView.setAdapter(exercise_adapter);
        exercise_adapter.addAll(ExerciseLine.getExerciseLines());


        /////////////////
        //Drag and drop//
        /////////////////
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {

                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();
                Collections.swap(ExerciseLine.getExerciseLines(), position_dragged, position_target);
                Objects.requireNonNull(recyclerView.getAdapter()).notifyItemMoved(position_dragged, position_target);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(horizontalLinearLayoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(verticalLinearLayoutManager);
        }

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ExercisePageActivity.RecyclerViewHolder> {
        private ArrayList<ExerciseLine> exercise_items = new ArrayList<>();

        void addAll(List<ExerciseLine> items) {
            int pos = getItemCount();
            this.exercise_items.addAll(items);
            notifyItemRangeInserted(pos, this.exercise_items.size());
        }

        @NonNull
        @Override
        public ExercisePageActivity.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_line, parent, false);
            return new ExercisePageActivity.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExercisePageActivity.RecyclerViewHolder holder, int position) {
            holder.bind(exercise_items.get(position));
        }

        @Override
        public int getItemCount() {
            return exercise_items.size();
        }

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.exercise_card_title);
        }

        void bind(ExerciseLine linesItem) {
            title.setText(linesItem.getLine());
        }
    }


}


