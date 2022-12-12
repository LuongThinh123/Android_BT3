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

        Item item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);

        tvItem.setText(item.title);

        return convertView;
    }
}
