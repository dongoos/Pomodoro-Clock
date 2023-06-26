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

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //create tables - add local tables here
        Log.i("Database","-------------------------------Creating Tables--------------------------------");
        db.execSQL("CREATE TABLE IF NOT EXISTS events( eid INTEGER PRIMARY KEY AUTOINCREMENT, uid INTEGER, eventName TEXT, timeMinutes INTEGER  ) ");
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
        //onUpgrade(db,1,2);
        onCreate(db);

    }



    //Create Events specific functions

    public void insertEvent(Model evt){
        ContentValues cv = new ContentValues();
        //cv.put("eid", evt.getId());
        cv.put("uid", 1);
        cv.put("eventName", evt.getTask());
        cv.put("timeMinutes",evt.getTimeMinute());
        long res = db.insertOrThrow("events",null, cv);
        if(res == -1){
            Log.i("Database", "FAILED TO INSERT FUNCTION BEGIN CRYING IN 3.. 2.. 1..");
        }else{
            Log.i("Database", "INSERT FUNCTION SUCCESSFUL");
        }


    }

    //Rearrange order for events for in case of deletion... i think
    @SuppressLint("Range")
    public List<Model> getAllEvents(){
        List<Model> eventList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{

            cur = db.query("events",null,null,null,null,null,null,null);
          // cur = db.rawQuery("SELECT SUM(eventName) from events",null);

            if(cur != null){
               // Log.i("Database",cur.getString(cur.getColumnIndex("eventName")) );
                if(cur.moveToFirst()){
//                   int amount = cur.getInt(0);
//                   String x = ""+amount;
//                   Log.i("database",x+": is this working like wtf huhhhhhhh");
                    //Log.i("Database",cur.getString(cur.getColumnIndex("eventName")) );
                    do{
                        Model event = new Model();
                        event.setId(cur.getInt(cur.getColumnIndex("eid")));
                        event.setTask(cur.getString(cur.getColumnIndex("eventName")));
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

    public void updateEventTitle(int id, String name){
        ContentValues cv = new ContentValues();
        cv.put("eventName", name);
        db.update("events",cv,"eid=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete("events","eid=?", new String[] {String.valueOf(id)});

    }

}
