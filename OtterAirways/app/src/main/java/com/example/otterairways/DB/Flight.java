package com.example.otterairways.DB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Flights")
public class Flight {
    @PrimaryKey
    @NonNull
    private String flight_id;
    private String departure;
    private String arrival;
    private String time;
    private int capacity;
    private String price;

    public Flight(@NonNull String flight_id, String departure, String arrival, String time, int capacity, String price) {
        this.flight_id = flight_id;
        this.departure = departure;
        this.arrival = arrival;
        this.time = time;
        this.capacity = capacity;
        this.price = price;
    }


    public String toString(){
        return "Flight: "+flight_id+"\nDeparture: "+departure+" Arrival: "+arrival+"\nTime: "+time;
    }

    @NonNull
    public String getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(@NonNull String flight_id) {
        this.flight_id = flight_id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
