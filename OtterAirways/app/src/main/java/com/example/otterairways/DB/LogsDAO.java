package com.example.otterairways.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface LogsDAO {
    @Insert
    void insert(Logs... l);

    @Update
    void update(Logs... l);

    @Delete
    void delete(Logs l);

    @Query("SELECT COUNT(*) FROM Logs")
    Integer getLogCount();

    @Query("SELECT * FROM Logs")
    List<Logs> getLogs();

}
