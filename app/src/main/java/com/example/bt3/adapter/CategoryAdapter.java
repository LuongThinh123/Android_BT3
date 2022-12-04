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
        // Get the data item for this position
        Category category = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category, parent, false);
        }
        // Lookup view for data population
        TextView tvCategory = (TextView) convertView.findViewById(R.id.categoryTitle);
        // Populate the data into the template view using the data object
        tvCategory.setText(category.title);
        // Return the completed view to render on screen
        return convertView;
    }
}
