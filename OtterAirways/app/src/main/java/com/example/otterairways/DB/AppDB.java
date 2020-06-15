package com.example.otterairways.DB;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Account.class, Flight.class, Reservation.class, Logs.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public static final String DB_NAME = "OtterAirwaysDB";

    public abstract AccountDAO getAccountDAO();
    public abstract FlightDAO getFlightDAO();
    public abstract ReservationsDAO getReservationDAO();
    public abstract LogsDAO getLogsDAO();

}
