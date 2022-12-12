package com.example.bt3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bt3.adapter.ItemAdapter;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ViewItemActivity extends Activity {
    ListView lvItem;
    ArrayList<Item> itemList;
    ItemAdapter itemAdapter;
    ArrayList<String> titles;

    public Bitmap decodeImageStream(String url) {
        Bitmap image = null;
        try {
            URL imageUrl = new URL(url);
            InputStream in = imageUrl.openConnection().getInputStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout);

        titles = new ArrayList<String>();
        itemList = new ArrayList<Item>();
        lvItem = (ListView) findViewById(R.id.lvItem);

        Intent intent = getIntent();
        String categoryLink = intent.getStringExtra("link");

        new ReadData().execute(categoryLink);

        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Dialog dialog = new Dialog(ViewItemActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_layout);

                Window window = dialog.getWindow();
                if(window == null) {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = Gravity.CENTER;
                window.setAttributes(windowAttributes);
                dialog.setCancelable(true);

                TextView tvDescription = dialog.findViewById(R.id.tvDescription);
                ImageView imageView = dialog.findViewById(R.id.imageView);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                TextView tvDate = dialog.findViewById(R.id.tvDate);
                Button moreBtn = dialog.findViewById(R.id.moreBtn);
                Button closeBtn = dialog.findViewById(R.id.closeBtn);

                tvDescription.setText(itemList.get(position).description);
                Picasso.get().load(itemList.get(position).image).into(imageView);
                tvTitle.setText(itemList.get(position).title);
                tvDate.setText(itemList.get(position).date);

                moreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemList.get(position).link));
                        startActivity(intent);
                    }
                });

                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public class ReadData extends AsyncTask<String, Void, List> {
        @Override
        protected List doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF-8");

                boolean done = false;
                Item item = null;
                boolean insideItem = false;
                int eventType = xpp.getEventType();
                String title = "";
                String description = "";
                String pubDate = "";
                String link = "";
                String imageURL = "";
//                Bitmap image = null;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String name = null;

                    if (eventType == XmlPullParser.START_TAG) {
                        name = xpp.getName();
                        if (name.equalsIgnoreCase("item") == true) {
                            insideItem = true;
                        }

                        if (insideItem == true) {
                            if (name.equalsIgnoreCase("title")) {
                                    title = xpp.nextText();
                            } else if (name.equalsIgnoreCase("description")) {
                                    description = xpp.nextText();
                                    if(description.contains("<p>")) {
                                        description = description.replace("<p>","")
                                                                 .replace("</p>", "");
                                    }
                            } else if (name.equalsIgnoreCase("pubDate")) {
                                    pubDate = xpp.nextText();
                            } else if (name.equalsIgnoreCase("link")) {
                                    link = xpp.nextText();
                            } else if (name.equalsIgnoreCase("media:content")) {
                                imageURL = xpp.getAttributeValue(null, "url");
                            }
                        }
                    }
                    else if (eventType == XmlPullParser.END_TAG) {
                        name = xpp.getName();
                        if(name.equalsIgnoreCase("item")) {
                            itemList.add(new Item(title, description, pubDate, link, imageURL));
                        } else if (name.equalsIgnoreCase("channel")) {
                            done = true;
                        }
                    }

                    eventType = xpp.next();
                }
            } catch (MalformedURLException e) {
                return null;
            }
            catch (XmlPullParserException e) {
                return null;
            } catch (IOException e) {
                return null;
            }

            return itemList;
        }

        @Override
        protected void onPostExecute(List s) {
            super.onPostExecute(s);
            itemAdapter = new ItemAdapter(ViewItemActivity.this, android.R.layout.simple_list_item_1, s);
            lvItem.setAdapter(itemAdapter);
        }
    }
}
