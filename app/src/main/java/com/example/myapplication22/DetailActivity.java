package com.example.myapplication22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    String id;
    DatabaseHelper databaseHelper;
    TextView name, location, date, stime, etime, des, level;
    ImageView image;
    Button book, cancel;
    String uid;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        id = getIntent().getStringExtra("id");
        databaseHelper = new DatabaseHelper(this);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        stime = findViewById(R.id.stime);
        etime = findViewById(R.id.etime);
        des = findViewById(R.id.des);
        des = findViewById(R.id.des);
        level = findViewById(R.id.level);
        image = findViewById(R.id.image);
        book = findViewById(R.id.book);
        cancel = findViewById(R.id.cancel);
        preferences = this.getSharedPreferences("UserIn", 0);
        uid = preferences.getString("id", null);
        showEventDetails();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (databaseHelper.checkBookStatus(uid, id)) {
                    Toast.makeText(getApplicationContext(), "You are already booked!", Toast.LENGTH_SHORT).show();

                } else if (checkClash()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("userid", uid);
                    contentValues.put("eventid", id);
                    databaseHelper.insertBookedEvent(contentValues);
                    Toast.makeText(getApplicationContext(), "Event Booked", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "You have a booking clash", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showEventDetails() {
        EventInfo info = databaseHelper.getEventInfo(id);
        name.setText(info.name);
        location.setText(info.location);
        date.setText(info.date);
        stime.setText(info.stime);
        etime.setText(info.etime);
        des.setText(info.des);
        location.setText(info.location);
        level.setText(info.level);
        if (info.image != null) {
            image.setImageBitmap(EventActivity.getBitmap(info.image));
        }

    }

    public boolean checkClash() {
        ArrayList<String> elist = new ArrayList<>();
        elist = databaseHelper.getBEventList(uid);
        if (elist.isEmpty()) {
            return true;
        } else {
            for (int i = 0; i < elist.size(); i++)
            {
                String date1 = "";
                date1 = databaseHelper.getEvdate(elist.get(i));
                if (date1.equals(databaseHelper.getEvdate(id)))
                {
                    return false;
                }
            }
        }
        return true;
    }
}