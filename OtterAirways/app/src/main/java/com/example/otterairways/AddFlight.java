package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Flight;
import com.example.otterairways.DB.FlightDAO;
import com.example.otterairways.DB.LogsDAO;

public class AddFlight extends AppCompatActivity {

    private TextView flightInfo;
    private TextView arriveInfo;
    private TextView departInfo;
    private TextView timeInfo;
    private TextView capInfo;
    private TextView costInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        flightInfo = (TextView)findViewById(R.id.flightInfo);
        arriveInfo = (TextView)findViewById(R.id.arriveInfo);
        departInfo = (TextView)findViewById(R.id.departInfo);
        timeInfo = (TextView)findViewById(R.id.timeInfo);
        costInfo = (TextView)findViewById(R.id.costInfo);
        capInfo = (TextView)findViewById(R.id.capInfo);
    }

    public void onAddFlight(View v) {
        String f = flightInfo.getText().toString();
        String a = arriveInfo.getText().toString();
        String d = departInfo.getText().toString();
        String t = timeInfo.getText().toString();
        String c = costInfo.getText().toString();
        String cap = capInfo.getText().toString();
        FlightDAO flightDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getFlightDAO();

        if(flightDAO.getFlightByID(f) == null) {
            if(validate(f) && validate(a) && validate(d) && validate(t) && validate(c) && validate(cap)) {
                flightDAO.insert(new Flight(f, d, a, t, Integer.valueOf(cap), c));
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            TextView result = (TextView)findViewById(R.id.addFlightResult);
            result.setText("Information entered is not valid");
        }
    }

    public boolean validate(String s) {
        return s != null;
    }
}
