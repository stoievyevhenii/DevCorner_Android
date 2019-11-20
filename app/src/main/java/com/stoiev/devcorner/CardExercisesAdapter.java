package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.stoiev.devcorner.model.HomeListItem;



public class CardExercisesAdapter extends FirestoreRecyclerAdapter<HomeListItem, CardExercisesAdapter.ExerciseHolder> {


    public CardExercisesAdapter(@NonNull FirestoreRecyclerOptions<HomeListItem> options) {
        super(options);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull ExerciseHolder holder, int position, @NonNull HomeListItem model) {

        holder.title.setText(model.getTitle());
        holder.group.setText(model.getGroup());
        holder.author.setText(model.getAuthor());
        holder.language.setText(model.getLanguage());
    }


    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ExerciseHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ExerciseHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView group;
        TextView author;
        TextView language;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            group = itemView.findViewById(R.id.group);
            author = itemView.findViewById(R.id.author);
            language = itemView.findViewById(R.id.exercise_language);

        }
    }


}
