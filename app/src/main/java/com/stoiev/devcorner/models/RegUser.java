package com.stoiev.devcorner.models;

public class RegUser {

    private String login;
    private String password;

    public RegUser() {
    }

    public RegUser(String login, String password) {
        // ...
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
