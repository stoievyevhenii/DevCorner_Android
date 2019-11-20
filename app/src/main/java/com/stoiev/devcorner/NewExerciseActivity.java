package com.stoiev.devcorner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.stoiev.devcorner.DB.AppDatabase;
import com.stoiev.devcorner.DB.FirebaseActions;
import com.stoiev.devcorner.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NewExerciseActivity extends AppCompatActivity {
    private static AppDatabase appDatabase;
    public static String exerciseLanguage;
    List<String> tagList = new ArrayList<>();
    private int selectedChipText = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_exercise);
        Toolbar toolbar = findViewById(R.id.newExerciseToolbar);
        setSupportActionBar(toolbar);

        // Init Room DB
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "user_system_status").allowMainThreadQueries().build();

        // Back button action
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize spinner data
        initSpinner();

        // Initialize chip group
        setTag();

    }


    protected void initSpinner() {
        // Spinner Data
        final Spinner groupSpinner = findViewById(R.id.newExerciseGroup);

        String[] groups = new String[]{
                "Select exercise group...",
                "Exception",
                "Other",
                "Array",
                "Sort"
        };

        final List<String> groupsList = new ArrayList<>(Arrays.asList(groups));

        // Init group spinner
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, groupsList) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                } else
                    tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        groupSpinner.setAdapter(spinnerArrayAdapter);

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // Init chip group
    private void setTag() {
        final ChipGroup chipGroup = findViewById(R.id.languages_chip_group);
        tagList.add("Java");
        tagList.add("Kotlin");
        tagList.add("C++");
        tagList.add("Python");
        tagList.add("JavaScript");

        for (int index = 0; index < tagList.size(); index++) {
            final String tagName = tagList.get(index);
            final Chip chip = new Chip(this);
            chip.setText(tagName);
            chip.setCheckable(true);

            //Added click listener on close icon to remove tag from ChipGroup
//            chip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    exerciseLanguage = chip.getText().toString();
//                }
//            });

            chip.setOnCheckedChangeListener(new Chip.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.d("Chip", "onCheckedChanged");
                    exerciseLanguage = "";
                }
            });

            Log.d("Chip", "Chip vale = " + exerciseLanguage);
            chipGroup.addView(chip);
        }
    }


    @SuppressLint("PrivateResource")
    public void uploadNewExercise(View view) {
        FirebaseActions uploadData = new FirebaseActions();
        String exerciseTitleText, exerciseGroupText, exerciseBodyText, selectableExerciseLanguage;

        // Get all field
        TextInputEditText exerciseTitle = findViewById(R.id.newExerciseTitle);
        Spinner exerciseGroup = findViewById(R.id.newExerciseGroup);
        TextInputEditText exerciseContent = findViewById(R.id.newExerciseContent);

        // Convert data from fields in String
        exerciseTitleText = Objects.requireNonNull(exerciseTitle.getText()).toString();
        exerciseGroupText = Objects.requireNonNull(exerciseGroup.getSelectedItem()).toString();
        exerciseBodyText = Objects.requireNonNull(exerciseContent.getText()).toString();
        selectableExerciseLanguage = exerciseLanguage;

        // Find selected chip
        ChipGroup chipGroup = findViewById(R.id.languages_chip_group);
        selectedChipText = chipGroup.getCheckedChipId();

        // Field blank check
        if (!exerciseTitleText.isEmpty()
                && !exerciseGroupText.equals("Select exercise group...")
                && !exerciseBodyText.isEmpty()
                && selectedChipText > 0) {
            //Get author
            List<User> users = appDatabase.userDao().getAll();
            String author = null;

            // If Room does not have users
            try {
                for (User usr : users) {
                    author = usr.login;
                }
            } catch (Exception ignored) {
            }


            // Send data in DB
            try {
                uploadData.addExercise(exerciseTitleText, exerciseGroupText, exerciseBodyText, author, selectableExerciseLanguage);
                Bundle actionResult = new Bundle();
                actionResult.putString("newTitle", "Thank you for your exercise!");

                Intent openResultPage = new Intent(this, ResultActivity.class);
                openResultPage.putExtras(actionResult);
                startActivity(openResultPage);
                finish();

            } catch (Exception e) {
                new MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Ooops!")
                        .setMessage("Our servers are resting")
                        .setPositiveButton("Ok!", /* listener = */ null);
            }

        } else {
            Toast.makeText
                    (getApplicationContext(), "Our AI find error in your data! Try to find this error", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onBackPressed(){
        Intent opeHomePage = new Intent(this, HomeActivity.class);
        startActivity(opeHomePage);
        finish();
    }
}
