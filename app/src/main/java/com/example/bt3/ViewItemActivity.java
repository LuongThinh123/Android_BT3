package com.example.bt3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bt3.adapter.ItemAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
    TextView tvItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_layout);

        titles = new ArrayList<String>();
        itemList = new ArrayList<Item>();
        lvItem = (ListView) findViewById(R.id.lvItem);

//        tvItem = (TextView) findViewById(R.id.tvItem);
//
        Intent intent = getIntent();
        String categoryLink = intent.getStringExtra("link");
//        tvItem.setText(categoryLink);
////

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute();
            }
        });


//        new ReadData().execute();
//
//        itemAdapter = new ItemAdapter(this, android.R.layout.simple_list_item_1, itemList);
//        lvItem.setAdapter(itemAdapter);
    }


    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public class ReadData extends AsyncTask<String, Void, List> {
//        ProcessDialog processDialog = new ProcessDialog(this);
//        Exception exception = null;
//        @Override
//        protected void onPreExecute() {
//
//        }

        @Override
        protected List doInBackground(String... strings) {
            try {
//                title.add("aloha");
//                title.add("abc");
//                title.add("xyz");
                URL url = new URL("https://www.petfoodindustry.com/rss/topic/292-proteins");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF-8");
                boolean insideItem = false;

                int eventType = xpp.getEventType();
                String title = "";
                String description = "";
                String pubDate = "";
                String link = "";
                String image = "";

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item") == true) {
                            insideItem = true;
                        }

                        if (insideItem == true) {
                            if (xpp.getName().equalsIgnoreCase("title")) {
//
                                    title = xpp.nextText();
                                    titles.add("aloha");
//
                            } else if (xpp.getName().equalsIgnoreCase("description")) {
//
                                    description = xpp.nextText();
                                    titles.add("zfwaf");
//
                            } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
//
                                    pubDate = xpp.nextText();
                                    titles.add("an my cay");
//
                            } else if (xpp.getName().equalsIgnoreCase("link")) {
//
                                    link = xpp.nextText();
                                    titles.add("superrrrrr");

                            } else if (xpp.getName().equalsIgnoreCase("media:content")) {
//
                                image = xpp.nextText();
                                titles.add("em khong an mung");
//
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
//                        titles.add("em an mung");
                        if (xpp.getName().equalsIgnoreCase("item") == true) {
                            insideItem = false;
                            titles.add("em an mung");
                            itemList.add(new Item(title, description, pubDate, image, link));

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

            return titles;
        }

        @Override
        protected void onPostExecute(List s) {
            super.onPostExecute(s);

//            itemList = (ArrayList<Item>) itemList;
//            String abc = titles.toString();
//            Toast.makeText(ViewItemActivity.this, abc, Toast.LENGTH_SHORT).show();
//            itemAdapter = new ItemAdapter(ViewItemActivity.this, android.R.layout.simple_list_item_1, itemList);
//            lvItem.setAdapter(itemAdapter);
//
//            String abc = title.toString();
//            Toast.makeText(ViewItemActivity.this, abc, Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewItemActivity.this, android.R.layout.simple_list_item_1, titles);
            lvItem.setAdapter(adapter);
        }
    }
}
