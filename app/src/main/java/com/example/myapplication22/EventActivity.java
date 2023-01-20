package com.example.myapplication22;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class EventActivity extends AppCompatActivity {
    int cyear, cmonth, cday, shour, sminutes, ehour, eminutes;
    EditText name, location, des, level;
    Button date, stime, etime, submit;
    TextView datetext, stimetext, etimetext;
    DatabaseHelper databaseHelper;
    ImageView image;
    Uri filepath;
    Bitmap bitmap;
    Date date1;
    Stime stime1;
    Etime etime1;
//    private static final int PICK_IMAGE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        databaseHelper = new DatabaseHelper(this);
        date = findViewById(R.id.date);
        stime = findViewById(R.id.stime);
        etime = findViewById(R.id.etime);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        des = findViewById(R.id.des);
        level = findViewById(R.id.level);
        image = findViewById(R.id.image);
        date1 = new Date();
        stime1 = new Stime();
        etime1 = new Etime();


        datetext = findViewById(R.id.datetext);
        stimetext = findViewById(R.id.stimetext);
        etimetext = findViewById(R.id.etimetext);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "time" + stime1.getStime().toString(), Toast.LENGTH_LONG).show();
                getEventValues();
                Intent intent = new Intent(EventActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                cyear = calendar.get(Calendar.YEAR);
                cmonth = calendar.get(Calendar.MONTH);
                cday = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date1.setDate(pad(i2) + "/" + pad((i1 + 1)) + "/" + i);
                        datetext.setText(date1.getDate().toString());

                    }
                }, cyear, cmonth, cday);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                datePickerDialog.show();
            }
        });

        stime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                shour = calendar.get(Calendar.HOUR_OF_DAY);
                sminutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        stime1.setStime(pad(hourOfDay) + ":" + pad(minute));
                        stimetext.setText(stime1.getStime().toString());
                    }
                }, shour, sminutes, true);
                timePickerDialog.show();
            }
        });
        etime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                ehour = calendar.get(Calendar.HOUR_OF_DAY);
                eminutes = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etime1.setEtime(pad(hourOfDay) + ":" + pad(minute));
                        etimetext.setText(etime1.getEtime().toString());
                    }
                }, ehour, eminutes, true);
                timePickerDialog.show();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select"), 1);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), filepath);
                image.setImageBitmap(bitmap);

            } catch (Exception e) {
                Toast.makeText(this, "message", Toast.LENGTH_SHORT);
            }
        }
    }

    public void getEventValues() {
        String nameValue = name.getText().toString();
        String locationValue = location.getText().toString();
        String dateValue = date1.getDate().toString();
        String stimeValue = stime1.getStime().toString();
        String etimeValue = etime1.getEtime().toString();
        String desValue = des.getText().toString();
        String levelValue = level.getText().toString();


        ContentValues contentValues = new ContentValues();
        contentValues.put("name", nameValue);
        contentValues.put("location", locationValue);
        contentValues.put("date", dateValue);
        contentValues.put("stime", stimeValue);
        contentValues.put("etime", etimeValue);
        contentValues.put("des", desValue);
        contentValues.put("level", levelValue);
        if (bitmap != null) {
            contentValues.put("image", getBlob(bitmap));
        }
        databaseHelper.insertEvent(contentValues);
        Toast.makeText(this, "Event registered", Toast.LENGTH_SHORT).show();

    }

    public static byte[] getBlob(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bArray = bos.toByteArray();
        StringBuilder sb = new StringBuilder();
        sb.append("bytearray:");
        sb.append(bArray);
        Log.i("bytearray", sb.toString());
        return bArray;
    }

    public static Bitmap getBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    private String pad(int value){

        if(value<10){
            return "0"+value;
        }


        return ""+value;
    }
}
