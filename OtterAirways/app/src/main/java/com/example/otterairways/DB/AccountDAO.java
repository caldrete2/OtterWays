package com.example.otterairways.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface AccountDAO {
    @Insert
    void insert(Account... a);

    @Update
    void update(Account... a);

    @Delete
    void delete(Account a);

    @Query("SELECT * FROM Accounts")
    List<Account> getAccs();

    @Query("SELECT * FROM Accounts WHERE user=:u")
    Account getUser(String u);

    @Query("SELECT COUNT(*) FROM Accounts")
    Integer getUserCount();

    @Query("SELECT * FROM Accounts WHERE user=:u AND password=:p")
    Account validateUser(String u, String p);
}
