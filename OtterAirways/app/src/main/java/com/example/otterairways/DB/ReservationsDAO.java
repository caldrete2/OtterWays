package com.example.otterairways.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReservationsDAO {
    @Insert
    void insert(Reservation... r);

    @Update
    void update(Reservation... r);

    @Delete
    void delete(Reservation r);

    @Query("SELECT * FROM Reservations WHERE user=:u")
    List<Reservation> getReservations(String u);

    @Query("SELECT COUNT(*) FROM Reservations;")
    Integer getResCount();

    @Query("SELECT * FROM Reservations WHERE resId=:id;")
    Reservation getReservationByID(String id);
}
