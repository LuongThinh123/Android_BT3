package com.example.bt3;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bt3.adapter.CategoryAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Category> categoryList;
    CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryList = new ArrayList<Category>();
        listView = (ListView) findViewById(R.id.listView);

        categoryList.add(new Category("Proteins", "https://www.petfoodindustry.com/rss/topic/292-proteins"));
        categoryList.add(new Category("Amino Acids", "https://www.petfoodindustry.com/rss/topic/293-amino-acids"));
        categoryList.add(new Category("Grains and Starches", "https://www.petfoodindustry.com/rss/topic/294-grains-and-starches"));
        categoryList.add(new Category("Fibers and Legumes", "https://www.petfoodindustry.com/rss/topic/295-fibers-and-legumes"));
        categoryList.add(new Category("Vitamins", "https://www.petfoodindustry.com/rss/topic/296-vitamins"));
        categoryList.add(new Category("Minerals", "https://www.petfoodindustry.com/rss/topic/297-minerals"));
        categoryList.add(new Category("Nutraceuticals", "https://www.petfoodindustry.com/rss/topic/298-nutraceuticals"));
        categoryList.add(new Category("Processing functional ingredients", "https://www.petfoodindustry.com/rss/topic/299-processing-functional-ingredients"));
        categoryList.add(new Category("Fats and Oils", "https://www.petfoodindustry.com/rss/topic/300-fats-and-oils"));
        categoryList.add(new Category("Preservatives", "https://www.petfoodindustry.com/rss/topic/301-preservatives"));


        adapter = new CategoryAdapter(this, android.R.layout.simple_list_item_1, categoryList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent iCategoryLink = new Intent(getApplicationContext(), ViewItemActivity.class);
//                Bundle bundle = new Bundle();
                iCategoryLink.putExtra("link", categoryList.get(position).link);
//
                startActivity(iCategoryLink);
            }
        });
    }
}