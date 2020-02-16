package com.stoiev.devcorner.model;

public class HomeListItem {
    private String title;
    private String author;
    private String group;
    private String language;
    private String id;


    public HomeListItem() {
        // empty constructor for Firebase UI
    }

    public HomeListItem(String title, String group, String author, String language, String id) {
        this.title = title;
        this.group = group;
        this.author = author;
        this.language = language;
        this.id = id;
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

    public String getId() {
        return id;
    }

}
