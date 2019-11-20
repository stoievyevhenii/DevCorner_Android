package com.stoiev.devcorner.model;

public class HomeListItem{
    private String title;
    private String author;
    private String group;
    private String language;

    public HomeListItem(){
        // empty constructor for Firebase UI
    }
    public HomeListItem(String title, String group, String author, String language){
        this.title = title;
        this.group = group;
        this.author = author;
        this.language = language;
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

    public String getLanguage() {
        return language;
    }
}
