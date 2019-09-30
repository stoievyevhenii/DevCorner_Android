package com.stoiev.devcorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ExercisePage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayoutManager;
    private LinearLayoutManager horizontalLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_page);

        // Set title text
        Bundle b = getIntent().getExtras();
        String newTitle = "Title";
        if (b != null) {
            newTitle = b.getString("newTitle");
            TextView pageTitle = findViewById(R.id.exercisePageTitle);
            pageTitle.setText(newTitle);
        }

        // Set new layout
        recyclerView = findViewById(R.id.exercise_lines);
        verticalLinearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(verticalLinearLayoutManager);
        RecyclerAdapter exercise_adapter = new RecyclerAdapter();
        recyclerView.setAdapter(exercise_adapter);
        exercise_adapter.addAll(ExerciseLine.getExerciseLines());
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

    private class RecyclerAdapter extends RecyclerView.Adapter<ExercisePage.RecyclerViewHolder> {
        private ArrayList<ExerciseLine> exercise_items = new ArrayList<>();

        void addAll(List<ExerciseLine> items) {
            int pos = getItemCount();
            this.exercise_items.addAll(items);
            notifyItemRangeInserted(pos, this.exercise_items.size());
        }

        @NonNull
        @Override
        public ExercisePage.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_line, parent, false);
            return new ExercisePage.RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExercisePage.RecyclerViewHolder holder, int position) {
            holder.bind(exercise_items.get(position));
        }

        @Override
        public int getItemCount() {
            return exercise_items.size();
        }

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView number;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.exercise_card_title);
            number = itemView.findViewById(R.id.exercise_card_number);
        }

        void bind(ExerciseLine linesItem) {
            title.setText(linesItem.getLine());
            number.setText(Integer.toString(linesItem.getNumber()));
        }
    }

}


