package com.argina.touristapp;

public class User {

    private int id;
    private String name,mail,pass;

    public User (int id, String name, String mail, String pass) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.pass = pass;
    }

    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getMail () {
        return mail;
    }

    public String getPass () {
        return pass;
    }
}

