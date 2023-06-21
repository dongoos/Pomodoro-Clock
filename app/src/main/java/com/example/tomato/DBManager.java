package com.example.tomato;

import android.util.Log;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    public void run() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                String url = "jdbc:mysql://" + "8.130.17.190" + "/" + "server";
                String user_server = "root";
                String password_server = "TC619";
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = (Connection) DriverManager.getConnection(url, user_server, password_server);
                    Log.i("run","finished");

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    public void close(){
        Log.i("close","finished");
    }
}