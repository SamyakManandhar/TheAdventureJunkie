package com.example.myapplication22;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<EventInfo> {

    Context context;
    public EventListAdapter(@NonNull Context context, ArrayList<EventInfo> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_layout, null);
        final EventInfo info = getItem(position);
        ImageView imageView = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        TextView location = view.findViewById(R.id.location);
        name.setText(info.name);
        location.setText(info.location);
        if (info.image != null) {
            imageView.setImageBitmap(EventActivity.getBitmap(info.image));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", info.id);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
