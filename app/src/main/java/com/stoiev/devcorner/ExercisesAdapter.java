package com.stoiev.devcorner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ExercisesAdapter extends FirestoreRecyclerAdapter<HomeListItem, ExercisesAdapter.ExerciseHolder> {

    public ExercisesAdapter(@NonNull FirestoreRecyclerOptions<HomeListItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ExerciseHolder holder, int position, @NonNull HomeListItem model) {
        holder.title.setText(model.getTitle());
        holder.group.setText(model.getGroup());
        holder.author.setText(model.getAuthor());
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ExerciseHolder(view);
    }

    class ExerciseHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView group;
        TextView author;

        public ExerciseHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            group = itemView.findViewById(R.id.group);
            author = itemView.findViewById(R.id.author);
        }
    }
}
