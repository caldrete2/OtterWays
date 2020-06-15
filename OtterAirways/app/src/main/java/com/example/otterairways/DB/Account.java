package com.example.otterairways.DB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "Accounts")
public class Account {
    @PrimaryKey
    @NonNull
    private String user;
    private String password;


    public Account(@NonNull String user, String password) {
        this.user = user;
        this.password = password;
    }


    @NonNull
    public String getUser() {
        return user;
    }

    public void setUser(@NonNull String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
