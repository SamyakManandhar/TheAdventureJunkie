package com.example.myapplication22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPagerActivity extends AppCompatActivity {

    TextView tab1, tab2, tab3, tab4;
    ViewPager viewPager;
    SharedPreferences preferences;
//    public static final String MY_SHARED_PREFERENCES = "MySharedPrefs" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        final String id = getIntent().getStringExtra("id");
        preferences = getSharedPreferences("UserIn", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.apply();
        Toast.makeText(this, "id is" + id, Toast.LENGTH_LONG).show();
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        tab4 = findViewById(R.id.tab4);
        tab1.setBackgroundColor(Color.WHITE);
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(new TabPageAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tab1.setBackgroundColor(Color.TRANSPARENT);
                tab2.setBackgroundColor(Color.TRANSPARENT);
                tab3.setBackgroundColor(Color.TRANSPARENT);
                tab4.setBackgroundColor(Color.TRANSPARENT);
                if (position == 0) {
                    tab1.setBackgroundColor(Color.WHITE);
                } else if (position == 1) {
                    tab2.setBackgroundColor(Color.WHITE);
                } else if (position == 2) {
                    tab3.setBackgroundColor(Color.WHITE);
                } else {
                    tab4.setBackgroundColor(Color.WHITE);
                }
            }

            public String getId() {
                return id;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void tabClickListner(View view) {
        tab1.setBackgroundColor(Color.TRANSPARENT);
        tab2.setBackgroundColor(Color.TRANSPARENT);
        tab3.setBackgroundColor(Color.TRANSPARENT);
        tab4.setBackgroundColor(Color.TRANSPARENT);

        if (view.getId() == R.id.tab1) {
            tab1.setBackgroundColor(Color.WHITE);
            viewPager.setCurrentItem(0);
        } else if (view.getId() == R.id.tab2) {
            tab2.setBackgroundColor(Color.WHITE);
            viewPager.setCurrentItem(1);
        } else if (view.getId() == R.id.tab3) {
            tab3.setBackgroundColor(Color.WHITE);
            viewPager.setCurrentItem(2);
        } else {
            tab4.setBackgroundColor(Color.WHITE);
            viewPager.setCurrentItem(3);
        }
    }

    public class TabPageAdapter extends FragmentPagerAdapter {

        public TabPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeFragment();
            } else if (position == 1) {
                return new SearchFragment();
            } else if (position == 2) {
                return new BookingsFragment();
            }
            return new ProfileFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
