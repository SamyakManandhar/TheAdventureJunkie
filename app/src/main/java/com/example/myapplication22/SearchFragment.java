package com.example.myapplication22;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    @Nullable
    ListView listView;
    DatabaseHelper databaseHelper;
    EventListAdapter eventListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databaseHelper=new DatabaseHelper(getActivity());
        View view = inflater.inflate(R.layout.activity_event_list,null);
        listView=view.findViewById(R.id.listview);
        eventListAdapter=new EventListAdapter(getActivity(), databaseHelper.getEventList());
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.search_bar, null);
        listView.addHeaderView(header);
        listView.setAdapter(eventListAdapter);
        return view;
    }
}