package com.example.newenglishspeaking.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserData {
    public String name;
    public String password;

    public UserData(){
    }

    public UserData(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

