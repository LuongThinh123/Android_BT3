package com.example.bt3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bt3.Item;
import com.example.bt3.R;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context, int resource, List<Item> itemList) {
        super(context, resource, itemList);
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
//        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        // Lookup view for data population
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        // Populate the data into the template view using the data object
        tvItem.setText(item.title);
        // Return the completed view to render on screen
        return convertView;
    }
}
