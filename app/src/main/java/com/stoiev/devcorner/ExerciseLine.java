package com.stoiev.devcorner;

import android.util.Log;

import com.stoiev.devcorner.helpers.TaskFormation;

import java.util.ArrayList;

public class ExerciseLine {

    private String line;

    public String author;
    public String title;

    public static ArrayList<ExerciseLine> linesDataList = new ArrayList<>();

    private ExerciseLine(String line) {
        this.line = line;
    }

    String getLine() {
        return line;
    }


    static ArrayList<ExerciseLine> getExerciseLines() {
        return linesDataList;
    }

    public static void addLine() {
        linesDataList.add(new ExerciseLine("First_line"));
        linesDataList.add(new ExerciseLine("Second_line"));
        linesDataList.add(new ExerciseLine("Third_line"));
        linesDataList.add(new ExerciseLine("Fourth_line"));
        linesDataList.add(new ExerciseLine("5"));
    }
}
