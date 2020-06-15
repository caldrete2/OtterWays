package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Flight;
import com.example.otterairways.DB.FlightDAO;

import java.util.ArrayList;
import java.util.List;

public class ReserveSeat extends AppCompatActivity {

    private FlightDAO flightDAO;
    private List<Flight> data;
    private ListView list;
    private EditText depart;
    private EditText arrive;
    private EditText seats;
    private ArrayAdapter<String> adapter;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_seat);

        depart = (EditText)findViewById(R.id.depart);
        arrive = (EditText)findViewById(R.id.arrive);
        seats = (EditText)findViewById(R.id.cap);
        result = (TextView)findViewById(R.id.reserveResult);

        flightDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getFlightDAO();

        insertDB_flights();
        data = flightDAO.getFlights();
        updateListView();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View view, int position, long id) {
                String f = adapter.getItem(position);
                String[] s = f.split("\n");
                s = s[0].split(":");
                String n = seats.getText() == null? "0" : seats.getText().toString();

                if(Integer.valueOf(n) <= 7) {
                    selectFlight(s[1], n);
                } else { result.setText("System restriction. Ticket limit 7 MAX. "); }
            }
        });
    }

    private void selectFlight(String s, String n) {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("id", s);
        intent.putExtra("seats", n);

        startActivity(intent);
    }

    public void filterFlights(View v) {
        String d = depart.getText().toString();
        String a = arrive.getText().toString();
        int n = Integer.valueOf(seats.getText().toString());

        if(n <= 7) {
            result.setText("");
            data = flightDAO.getFlights(d, a, n);
            if(data.size() != 0) { updateListView(); }
            else  { result.setText("NO RESULTS."); }
        } else { result.setText("System restriction. Ticket limit 7 MAX. "); }
    }

    public void updateListView() {
        ArrayList<String> temp = new ArrayList<>();

        for(Flight f: data) {
            String s = f.toString()+" Capacity: "+f.getCapacity()+"  Price: $"+f.getPrice();
            temp.add(s);
        }

        list = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temp);
        list.setAdapter(adapter);
    }

    public void insertDB_flights() {
        int n = flightDAO.getFlightCount();

        if(n == 0) {
            flightDAO.insert(new Flight("Otter101","Monterey", "Los Angeles", "10:00(AM)", 10, "150.00"));
            flightDAO.insert(new Flight("Otter102","Los Angeles", "Monterey", "1:00(PM)", 10, "150.00"));
            flightDAO.insert(new Flight("Otter201", "Monterey", "Seattle", "11:00(AM)", 5, "200.50"));
            flightDAO.insert(new Flight("Otter205","Monterey", "Seattle", "3:00(PM)", 15, "150.00"));
            flightDAO.insert(new Flight("Otter202","Seattle", "Monterey", "2:00(PM)", 5, "200.50"));
        }
    }
}
