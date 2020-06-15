package com.example.otterairways;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.otterairways.DB.Account;
import com.example.otterairways.DB.AccountDAO;
import com.example.otterairways.DB.AppDB;
import com.example.otterairways.DB.Logs;
import com.example.otterairways.DB.LogsDAO;

import java.util.Calendar;
import java.util.Date;


public class createAcc extends AppCompatActivity {

    private EditText usr;
    private EditText pswrd;
    private TextView results;
    private AccountDAO accountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        accountDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getAccountDAO();
    }

    public void checkInput(View v) {
        usr = (EditText)findViewById(R.id.loginName);
        pswrd = (EditText)findViewById(R.id.loginPswrd);
        results = (TextView) findViewById(R.id.accResults);

        String user = usr.getText().toString();
        String password = pswrd.getText().toString();
        Account userAcc = accountDAO.getUser(user);

        if(checkString(user) && checkString(password)) {
            if(userAcc == null) {
                logAction(user);
                accountDAO.insert(new Account(user, password));
                results.setText("Account succesfully created!");
                final Intent intent = new Intent(this, MainActivity.class);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        startActivity(intent);
                    }
                }, 5000);
            } else { results.setText("Username already exist"); }
        }
        else
            results.setText("Error username/password do not meet criteria");
    }

    private void logAction(String u) {
        Date currentTime = Calendar.getInstance().getTime();

        LogsDAO logsDAO = Room.databaseBuilder(this, AppDB.class, AppDB.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getLogsDAO();

        String logID = String.valueOf(logsDAO.getLogCount() + 1);
        logsDAO.insert(new Logs(logID, "new account", u,null,null,null,null,null,null, currentTime.toString()));
    }

    public boolean checkString(String s) {
        int count = 0;

        if(!s.equals("admin2")) {
            for(int i=0;i<s.length();i++) {
                if((s.charAt(i) >= 'a' && s.charAt(i) <= 'z') || (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z'))
                    count++;
            }
        }

        if(count >= 3 && s.matches("(.*)[0-9](.*)") )
            return true;
        else
            return false;
    }
}