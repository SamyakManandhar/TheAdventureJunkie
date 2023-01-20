package com.example.myapplication22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BookedDetailActivity extends AppCompatActivity {
    String id;
    DatabaseHelper databaseHelper;
    TextView name, location, date, stime, etime, des, level;
    ImageView image;
    Button ok, cbooking;
    String uid;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booked_detail_layout);
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
        ok = findViewById(R.id.okay);
        cbooking = findViewById(R.id.cbooking);
        preferences = this.getSharedPreferences("UserIn", 0);
        uid = preferences.getString("id", null);
        showEventDetails();
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(this,SearchFragment.class)
//            }
//        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cbooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteBEvents(uid,id);
//                databaseHelper.deleteBEvents(id1);
                Toast.makeText(BookedDetailActivity.this, "Unbooked!", Toast.LENGTH_SHORT).show();
                finish();
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

}