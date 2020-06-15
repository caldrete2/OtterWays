package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Flight;
import com.example.otterairways.DB.Logs;
import com.example.otterairways.DB.LogsDAO;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    private List<Logs> data;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private LogsDAO logsDAO;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        logsDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getLogsDAO();

        data = logsDAO.getLogs();
        updateListView();
    }

    public void onFinish(View v) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void updateListView() {
        ArrayList<String> temp = new ArrayList<>();

        for(Logs l: data) {
            String s = l.toString();
            temp.add(s);
        }

        list = (ListView)findViewById(R.id.logsList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, temp);
        list.setAdapter(adapter);
    }

    public void addFlight(View v) {
        intent = new Intent(this, AddFlight.class);
        startActivity(intent);
    }
}
