package com.example.tomato.util;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tomato.model.Model;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "pomodoroDatabase";
    private static final String ID ="eid";

    private SQLiteDatabase db;

    private DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create tables - add local tables here
        Log.i("Database","-------------------------------Creating Tables--------------------------------");
        db.execSQL("CREATE TABLE IF NOT EXISTS events( eid INTEGER PRIMARY KEY AUTOINCREMENT, uid INTEGER, eventName TEXT, timeMinutes INTEGER ,timeSecond INTEGER ) ");
    }

    @Override
    //why do I need versions???
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Dropping tables -- delete all the tables that you've created to clear/ create new db
        Log.i("Database","-------------------------------Dropping Tables--------------------------------");
        db.execSQL("DROP TABLE IF EXISTS events");
        //create tables
        onCreate(db);

    }

    //open the database duhh
    public void openDatabase(){
        db = this.getWritableDatabase();
    }



    //Create Events specific functions

    public void insertEvent(Model evt){
        ContentValues cv = new ContentValues();
        cv.put("eventName", evt.getTask());
        cv.put("timeMinutes",evt.getTimeMinute());
        cv.put("timeSecond",evt.getTimeSec());
        long res = db.insertOrThrow("events",null, cv);
        if(res == -1){
            Log.i("Database", "FAILED TO INSERT FUNCTION BEGIN CRYING IN 3.. 2.. 1..");
        }else{
            Log.i("Database", "INSERT FUNCTION SUCCESSFUL");
        }


    }

    //Selecting values
    @SuppressLint("Range")
    public List<Model> getAllEvents(){
        List<Model> eventList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query("events",null,null,null,null,null,null,null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        Model event = new Model();
                        event.setId(cur.getInt(cur.getColumnIndex("eid")));
                        event.setTask(cur.getString(cur.getColumnIndex("eventName")));
                        event.setTimeMinute(cur.getInt(cur.getColumnIndex("timeMinutes")));
                        event.setTimeSec(cur.getInt(cur.getColumnIndex("timeSecond")));
                       //Log.i("Database",cur.getString(cur.getColumnIndex("eventName")) );
                       eventList.add(event);


                    }while(cur.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cur.close();
        }
        return eventList;

    }

    public void updateEvent(int id, String name, int min, int sec){
        ContentValues cv = new ContentValues();
        cv.put("eventName", name);
        cv.put("timeMinutes", min);
        cv.put("timeSecond", sec);
        Log.i("Testing---------------------------------------------------",String.valueOf(id));
        String where = "eid ="+id;
        db.update("events",cv,"eid=?", new String[] {String.valueOf(id)});
    }


    public void deleteTask(int id){
        db.delete("events","eid=?", new String[] {String.valueOf(id)});

    }



}
