package com.example.myapplication22;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class BookingsFragment extends Fragment {
    @Nullable
    DatabaseHelper databaseHelper;
    SharedPreferences preferences;
    String uid;
    LinearLayout container1;
    EventInfo info;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databaseHelper = new DatabaseHelper(getActivity());
        preferences = getActivity().getSharedPreferences("UserIn", 0);
        uid = preferences.getString("id", null);
        View view = inflater.inflate(R.layout.bookings_layout, null);
        container1 = view.findViewById(R.id.container1);
        showList();
        return view;
    }

    public void showList() {
        ArrayList<String> elist = new ArrayList<>();
        container1.removeAllViews();
        elist = databaseHelper.getBEventList(uid);
        info = new EventInfo();
        for (int i = 0; i < elist.size(); i++) {
            info = databaseHelper.getEventInfo(elist.get(i));
            View views = LayoutInflater.from(getActivity()).inflate(R.layout.blist_layout, null);
            ImageView image = views.findViewById(R.id.image);
            TextView name = views.findViewById(R.id.name);
            TextView date = views.findViewById(R.id.date);
            name.setText(info.name);
            date.setText(info.date);
            if (info.image != null) {
                image.setImageBitmap(RegisterActivity.getBitmap(info.image));
            }
            views.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BookedDetailActivity.class);
                    intent.putExtra("id", info.id);
                    getActivity().startActivity(intent);
                }
            });
            container1.addView(views);
        }
    }

    //
    public void onResume() {
        super.onResume();
        showList();
    }

}
