package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.otterairways.DB.Account;
import com.example.otterairways.DB.AccountDAO;
import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Logs;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private AccountDAO accountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accountDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();

        insertDB_data();
    }

    public void createAcc(View v){
        intent = new Intent(this, createAcc.class);
        startActivity(intent);
    }

    public void reserveSeat(View v) {
        intent = new Intent(this, ReserveSeat.class);
        startActivity(intent);
    }

    public void cancelReservation(View v) {
        intent = new Intent(this, Login.class);
        intent.putExtra("type", "cancel");
        startActivity(intent);
    }

    public void manageSystem(View v) {
        intent = new Intent(this, Login.class);
        intent.putExtra("type", "admin");
        startActivity(intent);
    }

    public void insertDB_data() {
        int n = accountDAO.getUserCount();

        if(n == 0) {
            accountDAO.insert(new Account("alice5", "csumb100"));
            accountDAO.insert(new Account("brian77", "123ABC"));
            accountDAO.insert(new Account("chris21", "CHRIS21"));
            accountDAO.insert(new Account("admin2", "admin2"));
        }
    }
}
