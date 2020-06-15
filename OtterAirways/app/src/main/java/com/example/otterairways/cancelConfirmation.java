package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Flight;
import com.example.otterairways.DB.FlightDAO;
import com.example.otterairways.DB.Logs;
import com.example.otterairways.DB.LogsDAO;
import com.example.otterairways.DB.Reservation;
import com.example.otterairways.DB.ReservationsDAO;

import java.util.Calendar;
import java.util.Date;

public class cancelConfirmation extends AppCompatActivity {

    private TextView cancelRes;
    private Intent intent;
    private Reservation reservation;
    private ReservationsDAO reservationsDAO;
    private Flight flight;
    private FlightDAO flightDAO;
    private Button cancel;
    private boolean cancelled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_confirmation);

        cancel = (Button) findViewById(R.id.cancelCancel);
        cancelRes = (TextView)findViewById(R.id.cancelRes);
        intent = getIntent();
        cancelled = true;
        String f_id = intent.getStringExtra("flight_id");
        String r_id = intent.getStringExtra("res_id");
        f_id = f_id.replaceAll(" ", "");
        cancel.setClickable(true);

        buildCancelConfirmation(f_id, r_id);
    }

    public void buildCancelConfirmation(String f_id, String r_id) {
        flightDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getFlightDAO();

        reservationsDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getReservationDAO();

        flight = flightDAO.getFlightByID(f_id);
        reservation = reservationsDAO.getReservationByID(r_id);
        String s = "Reservation: " + r_id + " " + flight.toString() + " Tickets: " + reservation.getTickets();
        cancelRes.setText(s);
    }

    public void onCancel(View v) {
        cancelRes.setText("Reservation is still active. Click Finish to continue.");
        cancel.setClickable(false);
        cancelled = false;
    }

    public void onFinish(View v) {
        Date currentTime = Calendar.getInstance().getTime();

        if(cancelled) {
            reservationsDAO.delete(reservation);
            LogsDAO logsDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                    .allowMainThreadQueries()
                    .build()
                    .getLogsDAO();

            String logID = String.valueOf(logsDAO.getLogCount() + 1);
            logsDAO.insert(new Logs(logID, "cancel reservation", reservation.getUser(), reservation.getFlight(), flight.getDeparture(), flight.getArrival(), reservation.getTickets(), reservation.getResID(), null, currentTime.toString()));
            flight.setCapacity(flight.getCapacity() + Integer.valueOf(reservation.getTickets()));
            flightDAO.update(flight);
        }

        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
