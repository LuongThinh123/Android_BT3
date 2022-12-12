package com.example.bt3;

import android.graphics.Bitmap;

public class Item {
    public String title, description, link, date, image;

    public Item(String title, String description, String date, String link, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.link = link;
        this.image = image;
    }
}
