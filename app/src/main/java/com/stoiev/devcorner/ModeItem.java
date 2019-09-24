package com.stoiev.devcorner;

import java.util.ArrayList;
import java.util.List;

class ModeItem {
    private String title;
    private String group;
    private String author;

    private ModeItem(String title, String group, String author) {
        this.title = title;
        this.group = group;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getGroup() {
        return group;
    }

    public String getAuthor() {
        return author;
    }

    public static List<ModeItem> getFakeItems() {
        ArrayList<ModeItem> itemsList = new ArrayList<>();
        itemsList.add(new ModeItem("Exception", "Error", "Yevhenii Stoiev"));
        itemsList.add(new ModeItem("Quick Sort", "Sort", "Pasichenko Maksim"));
        itemsList.add(new ModeItem("Bubble Sort", "Sort", "Kotigrobov Stas"));
        itemsList.add(new ModeItem("If ... else", "Condition", "Konishev Aleksandr"));
        itemsList.add(new ModeItem("Map", "Array", "Nezdoliy Yurii"));
        itemsList.add(new ModeItem("Pointer", "Other", "Borovleva Svetlana"));
        itemsList.add(new ModeItem("Array create", "Array", "Stoiev Yevhenii"));
        return itemsList;
    }
}


