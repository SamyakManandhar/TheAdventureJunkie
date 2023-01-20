package com.example.myapplication22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        TimeConsumingTask task = new TimeConsumingTask();
        task.execute();
    }


    public class TimeConsumingTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < 5; i++)
            {
                Log.i("count", "count:" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            startActivity(new Intent(SplashScreenActivity.this,LoginActivity.class));
            finish();
        }
    }
}

