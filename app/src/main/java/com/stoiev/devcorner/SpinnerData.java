package com.stoiev.devcorner;

import java.util.ArrayList;
import java.util.List;

public class SpinnerData {
    private String author;
    private String group;
    private ArrayList<SpinnerData> authorItems = new ArrayList<>();

    public SpinnerData(){}

    public SpinnerData(String author, String group) {
        this.author = author;
        this.group = group;
    }

    public String getAuthor() {
        return author;
    }

    public String getGroup() {
        return group;
    }

    public static List<SpinnerData> getFakeItems() {
        ArrayList<SpinnerData> itemsList = new ArrayList<>();
        itemsList.add(new SpinnerData("Yevhenii Stoiev", "Error"));
        return itemsList;
    }
    }
