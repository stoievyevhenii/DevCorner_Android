package com.stoiev.devcorner.helpers;

import android.util.Log;

import java.util.Arrays;
import java.util.regex.Pattern;

public class TaskFormation {

    public TaskFormation() {}

    public String[] taskFormatting(String bodyData) {
        String[] codeLines = bodyData.split(Pattern.quote("\n"));
        Log.d("taskFormatting", "bodyData = " + Arrays.toString(codeLines));

        return codeLines;
    }

    //
    // Check user exercise
    // TODO: Complete the user exercise verification method
    private void checkUserExercise(String userExercise) {

    }
}
