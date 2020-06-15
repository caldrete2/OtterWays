package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.otterairways.DB.Account;
import com.example.otterairways.DB.AccountDAO;
import com.example.otterairways.DB.AppDB;


public class Login extends AppCompatActivity {
    private EditText loginUser;
    private EditText loginPswrd;
    private TextView loginResults;
    private AccountDAO accountDAO;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intent = getIntent();

        accountDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    public void validateLogin(View v) {
        loginUser = (EditText)findViewById(R.id.loginName);
        loginPswrd = (EditText)findViewById(R.id.loginPswrd);
        loginResults = (TextView)findViewById(R.id.loginResults);

        String id = intent.getStringExtra("id");
        String seats = intent.getStringExtra("seats");

        String u = loginUser.getText().toString();
        String p = loginPswrd.getText().toString();

        Account a = accountDAO.validateUser(u, p);

        if(a != null) {
            String type = intent.getStringExtra("type");
            type = type == null? "null" : type;

            if(type.equals("cancel")) {
                intent = new Intent(this, cancelReservation.class);
                intent.putExtra("user", u);
            } else if(type.equals("admin")) {
                if(u.equals("admin2")) {
                    intent = new Intent(this, Admin.class);
                }
            } else {
                intent = new Intent(this, Confirmation.class);
                intent.putExtra("flight_id", id);
                intent.putExtra("user", u);
                intent.putExtra("seats", seats);
            }
            startActivity(intent);
        } else{
            loginResults.setText("Error, invalid username/password. Try again.");
        }
    }
}
