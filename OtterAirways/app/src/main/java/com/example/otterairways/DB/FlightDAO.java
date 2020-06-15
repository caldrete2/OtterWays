package com.example.otterairways.DB;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FlightDAO {
    @Insert
    void insert(Flight... f);

    @Update
    void update(Flight... f);

    @Delete
    void delete(Flight f);

    @Query("SELECT * FROM Flights WHERE capacity > 0")
    List<Flight> getFlights();

    @Query("SELECT COUNT(*) FROM Flights")
    Integer getFlightCount();

    @Query("SELECT * FROM Flights WHERE departure=:d AND arrival=:a AND capacity >:n")
    List<Flight> getFlights(String d, String a, int n);

    @Query("SELECT * FROM Flights WHERE flight_id=:id")
    Flight getFlightByID(String id);
}
