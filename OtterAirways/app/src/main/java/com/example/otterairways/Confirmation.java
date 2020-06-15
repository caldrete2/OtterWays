package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.otterairways.DB.Account;
import com.example.otterairways.DB.AccountDAO;
import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Flight;
import com.example.otterairways.DB.FlightDAO;
import com.example.otterairways.DB.Logs;
import com.example.otterairways.DB.LogsDAO;
import com.example.otterairways.DB.Reservation;
import com.example.otterairways.DB.ReservationsDAO;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class Confirmation extends AppCompatActivity {
    private TextView confirmRes;
    private FlightDAO flightDAO;
    private Flight f;
    private ReservationsDAO reservationsDAO;
    private Intent intent;
    private String flight;
    private String user;
    private String n;
    private Double total;
    private TextView cancelText;
    private TextView confirmationHead;
    private Button yes;
    private Button no;
    private Button cancel;
    private Button finish;
    private Boolean cancelled;
    private static int resID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        intent = getIntent();

        confirmationHead = (TextView)findViewById(R.id.confirmationHead);
        confirmRes = (TextView)findViewById(R.id.confirmRes);
        cancelText = (TextView)findViewById(R.id.cancelText);
        yes = (Button)findViewById(R.id.yesButton);
        no = (Button)findViewById(R.id.noButton);
        cancel = (Button)findViewById(R.id.cancelConfirm);
        finish = (Button)findViewById(R.id.confirmButton);

        cancelText.setText("");
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
        cancel.setClickable(true);
        cancelled = false;

        flightDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getFlightDAO();

        reservationsDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getReservationDAO();

        buildConfirmInfo();
    }

    private void buildConfirmInfo() {
        flight = intent.getStringExtra("flight_id");
        flight = flight.replace(" ", "");
        user = intent.getStringExtra("user");
        n = intent.getStringExtra("seats");
        f = flightDAO.getFlightByID(flight);
        total = Integer.valueOf(n) * Double.valueOf(f.getPrice());

        String s = "User: "+user+"\nFlight: "+f.getFlight_id()+"\nReservation#: "+resID+"\nDeparture: "+f.getDeparture()+"\nArrival: "+f.getArrival()+"\nTime: "+f.getTime()+"\nTickets: "+n+" Total: $"+total;

        confirmRes.setText(s);
    }

    public void finishConfirmation(View v){
        if(!cancelled) {
            updateFlight();
            insertReservation();
            logAction();
        }
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateFlight() {
        f.setCapacity(f.getCapacity() - Integer.valueOf(n));
        flightDAO.update(f);
    }

    private void logAction() {
        Date currentTime = Calendar.getInstance().getTime();

        LogsDAO logsDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getLogsDAO();

        Flight f = flightDAO.getFlightByID(flight);

       // public Logs(@NonNull String logID, String type, String logUSER, String logFLIGHT, String depart, String arrive, String seats, String logRESID, String totalCost)
        String id = String.valueOf(logsDAO.getLogCount()+1);
        logsDAO.insert(new Logs(id, "reserve seat", user, flight, f.getDeparture(), f.getArrival(), n, String.valueOf(resID), total.toString(), currentTime.toString()));
    }

    public void insertReservation() {
        reservationsDAO.insert(new Reservation(String.valueOf(resID), user, flight, n));
        resID += 1;
    }

    public void cancelConfirmation(View v) {
        cancelText.setText("Would you like to cancel your reservation?");
        yes.setVisibility(View.VISIBLE);
        no.setVisibility(View.VISIBLE);
    }

    public void optionNo(View v){
        cancelText.setText("");
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
    }

    public void optionYes(View v){
        cancelled = true;
        cancelText.setText("");
        yes.setVisibility(View.INVISIBLE);
        no.setVisibility(View.INVISIBLE);
        cancel.setClickable(false);
        confirmRes.setText("You cancelled your reservation.");
    }
}
