package com.example.bt3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bt3.Category;
import com.example.bt3.R;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(Context context, int resource, List<Category> category) {
        super(context, resource, category);
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        Category category = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category, parent, false);
        }

        TextView tvCategory = (TextView) convertView.findViewById(R.id.categoryTitle);

        tvCategory.setText(category.title);

        return convertView;
    }
}
