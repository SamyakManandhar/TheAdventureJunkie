package com.example.myapplication22;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String name = "application22";
    static int version = 1;

    String createTableUser = "CREATE TABLE if not exists \"user\" (\n" +
            "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"username\"\tTEXT UNIQUE,\n" +
            "\t\"password\"\tTEXT,\n" +
            "\t\"email\"\tTEXT,\n" +
            "\t\"address\"\tTEXT,\n" +
            "\t\"phone\"\tTEXT,\n" +
            "\t\"gender\"\tTEXT,\n" +
            "\t\"image\"\tBLOB\n" +
            ")";

    String createTableEvents = "CREATE TABLE if not exists \"events\" (\n" +
            "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"name\"\tTEXT UNIQUE,\n" +
            "\t\"location\"\tTEXT,\n" +
            "\t\"date\"\tTEXT,\n" +
            "\t\"stime\"\tTEXT,\n" +
            "\t\"etime\"\tTEXT,\n" +
            "\t\"des\"\tTEXT,\n" +
            "\t\"level\"\tTEXT,\n" +
            "\t\"image\"\tBLOB\n" +
            ")";

    String createTableBookedEvents = "CREATE TABLE if not exists \"bevents\" (\n" +
            "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
            "\t\"userid\"\tTEXT,\n" +
            "\t\"eventid\"\tTEXT\n" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, name, null, version);
        getWritableDatabase().execSQL(createTableUser);
        getWritableDatabase().execSQL(createTableEvents);
        getWritableDatabase().execSQL(createTableBookedEvents);

    }

    public void insertEvent(ContentValues contentValues) {
        getWritableDatabase().insert("events", "", contentValues);

    }

    public void insertBookedEvent(ContentValues contentValues) {
        getWritableDatabase().insert("bevents", "", contentValues);

    }

    public ArrayList<EventInfo> getEventList() {
        String sql = "Select * from events";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        ArrayList<EventInfo> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            EventInfo info = new EventInfo();
            info.id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            info.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            info.location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            info.date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            info.stime = cursor.getString(cursor.getColumnIndexOrThrow("stime"));
            info.etime = cursor.getString(cursor.getColumnIndexOrThrow("etime"));
            info.des = cursor.getString(cursor.getColumnIndexOrThrow("des"));
            info.level = cursor.getString(cursor.getColumnIndexOrThrow("level"));
            info.image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            list.add(info);
        }
        cursor.close();
        return list;

    }

    public ArrayList<String> getBEventList(String uid) {
        String sql = "Select eventid from bevents where userid=" + uid;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        ArrayList<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String eventid="";
            eventid = cursor.getString(cursor.getColumnIndexOrThrow("eventid"));
            list.add(eventid);
        }
        cursor.close();
        return list;

    }

    public boolean checkBookStatus(String uid, String eid){
        String sql = "Select count(*) from bevents where userid='" + uid + "' and eventid='" + eid + "'";
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        long l = stm.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else
            return false;
    }



    public long insertUser(ContentValues contentValues) {
        long l = getWritableDatabase().insert("user", "", contentValues);
        return l;
    }

    public boolean isLoginSuccessful(String username, String password) {
        String sql = "Select count(*) from user where username='" + username + "' and password='" + password + "'";
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        long l = stm.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else
            return false;
    }

    public boolean isLoginSuccessful(String username) {
        String sql = "Select count(*) from user where username='" + username + "'";
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        long l = stm.simpleQueryForLong();
        if (l == 1) {
            return true;
        } else
            return false;
    }

    public String getUserID(String username) {
        String sql = "Select id from user where username='" + username + "'";
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        String s = stm.simpleQueryForString();
        return s;
    }

    public String getEvdate(String id) {
        String sql = "Select date from events where id=" + id;
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        String s = stm.simpleQueryForString();
        return s;
    }

    public String getBEventID(String uid, String eid) {
        String sql = "Select id from bevents where uid='" + uid + "' and eventid='" + eid + "'";
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        String s = stm.simpleQueryForString();
        return s;
    }


    public UserInfo getUserinfo(String id) {
        String sql = "Select * from user where id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        UserInfo info = new UserInfo();
        while (cursor.moveToNext()) {
            info.username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            info.address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            info.image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));

        }
        cursor.close();
        return info;
    }

    public EventInfo getEventInfo(String id) {
        String sql = "Select * from events where id=" + id;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        EventInfo info = new EventInfo();
        while (cursor.moveToNext()) {
            info.id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            info.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            info.location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            info.date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            info.stime = cursor.getString(cursor.getColumnIndexOrThrow("stime"));
            info.etime = cursor.getString(cursor.getColumnIndexOrThrow("etime"));
            info.des = cursor.getString(cursor.getColumnIndexOrThrow("des"));
            info.level = cursor.getString(cursor.getColumnIndexOrThrow("level"));
            info.image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));

        }
        cursor.close();
        return info;
    }

//    public void deleteBEvents(String id){
//        getWritableDatabase().delete("bevents","id="+id,null);
//    }

    public void deleteBEvents(String uid, String eid){
        String sql = "DELETE from bevents where userid='" + uid + "' and eventid='" + eid + "'";
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        stm.simpleQueryForLong();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTableUser);
        sqLiteDatabase.execSQL(createTableEvents);
        sqLiteDatabase.execSQL(createTableBookedEvents);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(createTableUser);
        sqLiteDatabase.execSQL(createTableEvents);
        sqLiteDatabase.execSQL(createTableBookedEvents);

    }
}
