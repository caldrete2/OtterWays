package com.example.otterairways.DB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Reservations")
public class Reservation {
    @PrimaryKey
    @NonNull
    private String resID;
    private String user;
    private String flight;
    private String tickets;

    public Reservation(@NonNull String resID, String user, String flight, String tickets) {
        this.resID = resID;
        this.user = user;
        this.flight = flight;
        this.tickets = tickets;
    }

    @NonNull
    public String getResID() {
        return resID;
    }

    public void setResID(@NonNull String resID) {
        this.resID = resID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getTickets() {
        return tickets;
    }

    public void setTickets(String tickets) {
        this.tickets = tickets;
    }
}
