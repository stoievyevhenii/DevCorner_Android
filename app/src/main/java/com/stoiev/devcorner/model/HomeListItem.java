package com.stoiev.devcorner.model;

public class HomeListItem{
    private String title;
    private String author;
    private String group;

    public HomeListItem(){
        // empty constructor for Firebase UI
    }
    public HomeListItem(String title, String group, String author){
        this.title = title;
        this.group = group;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGroup() {
        return group;
    }
}
