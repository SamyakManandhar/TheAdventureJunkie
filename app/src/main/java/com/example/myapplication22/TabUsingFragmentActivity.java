package com.example.myapplication22;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TabUsingFragmentActivity extends AppCompatActivity {

    TextView tab1, tab2, tab3, tab4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        tab1.setBackgroundColor(Color.WHITE);
    }

    public void tabClickListner(View view) {
        tab1.setBackgroundColor(Color.TRANSPARENT);
        tab2.setBackgroundColor(Color.TRANSPARENT);
        tab3.setBackgroundColor(Color.TRANSPARENT);
        tab4.setBackgroundColor(Color.TRANSPARENT);

        if (view.getId() == R.id.tab1) {
            tab1.setBackgroundColor(Color.WHITE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        } else if (view.getId() == R.id.tab2) {
            tab2.setBackgroundColor(Color.WHITE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).commit();
        } else if (view.getId() == R.id.tab3) {
            tab3.setBackgroundColor(Color.WHITE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new BookingsFragment()).commit();
        } else {
            tab4.setBackgroundColor(Color.WHITE);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
        }
    }
}
