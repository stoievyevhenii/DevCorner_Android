package com.stoiev.devcorner;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // --- Set layout --- //

        recycleView = findViewById(R.id.cardsLayout);
        verticalLinearLayoutManager = new LinearLayoutManager(this);
        horizontalLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycleView.setLayoutManager(verticalLinearLayoutManager);
        adapter = new Home.RecyclerAdapter();
        recycleView.setAdapter(adapter);
        adapter.addAll(ModeItem.getFakeItems());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    protected void showMessage(View view) {
        Context context = getApplicationContext();
        CharSequence messageText = "Work in progress";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, messageText, duration);
        toast.show();
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

}
