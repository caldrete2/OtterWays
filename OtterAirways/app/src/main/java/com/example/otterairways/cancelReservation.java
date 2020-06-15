package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Flight;
import com.example.otterairways.DB.FlightDAO;
import com.example.otterairways.DB.Reservation;
import com.example.otterairways.DB.ReservationsDAO;

import java.util.ArrayList;
import java.util.List;

public class cancelReservation extends AppCompatActivity {

    private List<Reservation> data;
    private ReservationsDAO reservationsDAO;
    private FlightDAO flightDAO;
    private Flight flight;
    private Intent intent;
    private ListView list;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);

        intent = getIntent();
        String user = intent.getStringExtra("user");

        reservationsDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getReservationDAO();

        flightDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getFlightDAO();

        data = reservationsDAO.getReservations(user);
        updateListView();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String res = (String) adapter.getItem(position);
                String[] r = res.split(":");
                r = r[2].split("\n");
                String[] temp = res.split(" ");

                String flightID = r[0];
                String res_id = temp[1];

                cancelRes(flightID, res_id);
            }
        });
    }

    public void cancelRes(String f_id, String r_id) {
        intent = new Intent(this, cancelConfirmation.class);
        intent.putExtra("flight_id", f_id);
        intent.putExtra("res_id", r_id);
        startActivity(intent);
    }

    public void updateListView() {
        ArrayList<String> temp = new ArrayList<>();
        String s;

        if(data.size() == 0) {
            s = "No reservations available";
            temp.add(s);
            final Intent  i = new Intent(this, MainActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    startActivity(i);
                }
            }, 5000);
        } else {
            for(Reservation r: data) {
                flight = flightDAO.getFlightByID(r.getFlight());
                s = "Reservation#: " + r.getResID() + " " + flight.toString() + " Tickets: " + r.getTickets();
                temp.add(s);
            }
        }

        list = (ListView)findViewById(R.id.cancelList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temp);
        list.setAdapter(adapter);
    }
}
