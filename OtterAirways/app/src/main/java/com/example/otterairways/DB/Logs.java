package com.example.otterairways.DB;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Logs")
public class Logs {

    @PrimaryKey
    @NonNull
    private String logID;
    private String type;
    private String logUSER;
    private String logFLIGHT;
    private String depart;
    private String arrive;
    private String seats;
    private String logRESID;
    private String totalCost;
    private String date;

    public Logs(@NonNull String logID, String type, String logUSER, String logFLIGHT, String depart, String arrive, String seats, String logRESID, String totalCost, String date) {
        this.logID = logID;
        this.type = type;
        this.logUSER = logUSER;
        this.logFLIGHT = logFLIGHT;
        this.depart = depart;
        this.arrive = arrive;
        this.seats = seats;
        this.logRESID = logRESID;
        this.totalCost = totalCost;
        this.date = date;
    }


    public String toString() {
        return "Type: "+type+"\nUser: "+logUSER+"\nFlight: "+logFLIGHT+"\nDepart: "+depart+" Arrive: "+arrive+"\nTickets: "+seats+"\nReservation#: "+logRESID+"\nCost: $"+totalCost+"\nDate: "+date;
    }

    @NonNull
    public String getLogID() {
        return logID;
    }

    public void setLogID(@NonNull String logID) {
        this.logID = logID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogUSER() {
        return logUSER;
    }

    public void setLogUSER(String logUSER) {
        this.logUSER = logUSER;
    }

    public String getLogFLIGHT() {
        return logFLIGHT;
    }

    public void setLogFLIGHT(String logFLIGHT) {
        this.logFLIGHT = logFLIGHT;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getLogRESID() {
        return logRESID;
    }

    public void setLogRESID(String logRESID) {
        this.logRESID = logRESID;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
