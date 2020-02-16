package com.stoiev.devcorner.model;

import java.util.ArrayList;

public class Exercise {

    private String author;
    private String body;
    private String group;
    private String id;
    private String language;
    private String title;

    private ArrayList<String> bodyList;

    public Exercise() {
    }


    public Exercise(String author, String body, String group, String id,
                    String language, String title) {
        this.author = author;
        this.body = body;
        this.group = group;
        this.id = id;
        this.language = language;
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBodyArray(String bodyContent){
        this.bodyList.add(bodyContent);
    }

    public ArrayList<String> getBodyList(){
        return bodyList;
    }
}
