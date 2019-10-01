package com.stoiev.devcorner;

import java.util.ArrayList;

public class ExerciseLine {
    private int number;
    private String line;

    private ExerciseLine(int number, String line) {
        this.number = number;
        this.line = line;
    }

    public int getNumber() {
        return number;
    }

    public String getLine() {
        return line;
    }

    public static ArrayList<ExerciseLine> getExerciseLines() {
        ArrayList<ExerciseLine> linesDataList = new ArrayList<>();
        linesDataList.add(new ExerciseLine(1, "First_line"));
        linesDataList.add(new ExerciseLine(2, "Second_line"));
        linesDataList.add(new ExerciseLine(3, "Third_line"));
        linesDataList.add(new ExerciseLine(4, "Fourth_line"));
        linesDataList.add(new ExerciseLine(1, "First_line"));
        linesDataList.add(new ExerciseLine(2, "Second_line"));
        linesDataList.add(new ExerciseLine(3, "Third_line"));
        linesDataList.add(new ExerciseLine(4, "Fourth_line"));
        linesDataList.add(new ExerciseLine(2, "Second_line"));
        linesDataList.add(new ExerciseLine(3, "Third_line"));
        linesDataList.add(new ExerciseLine(4, "Fourth_line"));
        linesDataList.add(new ExerciseLine(1, "First_line"));
        linesDataList.add(new ExerciseLine(2, "Second_line"));
        linesDataList.add(new ExerciseLine(3, "Third_line"));
        linesDataList.add(new ExerciseLine(4, "Fourth_line"));
        linesDataList.add(new ExerciseLine(1, "First_line"));
        linesDataList.add(new ExerciseLine(2, "Second_line"));
        linesDataList.add(new ExerciseLine(3, "Third_line"));
        linesDataList.add(new ExerciseLine(4, "Fourth_line"));
        linesDataList.add(new ExerciseLine(1, "First_line"));
        linesDataList.add(new ExerciseLine(2, "Second_line"));
        linesDataList.add(new ExerciseLine(3, "Third_line"));
        linesDataList.add(new ExerciseLine(4, "Fourth_line"));


        return linesDataList;
    }
}
