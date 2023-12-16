package com.example.news_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.ListViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    //All News Api Links
    private static final String ALL_NEWS = "https://newsapi.org/v2/everything?q=all&sortBy=publishedAt&apiKey=65be133b6ebc49ec925c07f2c269933e";
    private static final String ALL_NEWS_FROM_INDIA ="https://newsapi.org/v2/everything?q=country=in&sortBy=publishedAt&apiKey=65be133b6ebc49ec925c07f2c269933e";
    private static final String GLOBAL_TOP_HEADLINES = "https://newsapi.org/v2/everything?q=all&top-headlines&sortBy=publishedAt&apiKey=65be133b6ebc49ec925c07f2c269933e";
    private static final String INDIAN_TOP_HEADLINES ="https://newsapi.org/v2/everything?q=all&top-headlines&sortBy=publishedAt&apiKey=65be133b6ebc49ec925c07f2c269933e";
    private static final String ALL_NEWS_TECHNOLOGY ="https://newsapi.org/v2/everything?q=technology&sortBy=time&apiKey=65be133b6ebc49ec925c07f2c269933e";
    private static final String ALL_SPORTS_NEWS = "https://newsapi.org/v2/everything?q=sports&sortBy=time&apiKey=65be133b6ebc49ec925c07f2c269933e";
    private static final String ALL_GAMING_NEWS ="https://newsapi.org/v2/everything?q=gaming&sortBy=time&apiKey=65be133b6ebc49ec925c07f2c269933e";
    private static final String BASE_URL = "https://newsapi.org/v2/top-headlines?country=in&sortBy=time&apiKey=65be133b6ebc49ec925c07f2c269933e";

    //Listview And It's Adapter
    ListView listView;
    CustomAdapter adapter;

    //Button Declaration
    AppCompatButton allNews,allIndianNews,globalHeadlines,IndianHeadlines,technology,gaming,sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        listView = findViewById(R.id.list_view);

        //Calling Fetch Data Fuction Instead Of Writing Code Here
        fetchData(BASE_URL);

        //Calling Fatch Id Method to fetch the id's of button
        fetchIds();


    }
    public void ButtonClickListener(View button){
        int id = button.getId();
        if(id == R.id.AllNews){
           fetchData(ALL_NEWS);
        }else if(id == R.id.india){
           fetchData(ALL_NEWS_FROM_INDIA);
        }else if(id == R.id.global_top_headlines){
           fetchData(GLOBAL_TOP_HEADLINES);
        }else if(id == R.id.indian_top_headlines){
           fetchData(INDIAN_TOP_HEADLINES);
        }else if(id == R.id.technology){
            fetchData(ALL_NEWS_TECHNOLOGY);
        }else if(id == R.id.gaming){
            fetchData(ALL_GAMING_NEWS);
        }else if(id == R.id.sports){
           fetchData(ALL_SPORTS_NEWS);
        }
    }
    public void fetchIds(){
        allNews = findViewById(R.id.AllNews);
        allIndianNews = findViewById(R.id.india);
        globalHeadlines = findViewById(R.id.global_top_headlines);
        IndianHeadlines = findViewById(R.id.indian_top_headlines);
        technology = findViewById(R.id.indian_top_headlines);
        gaming = findViewById(R.id.gaming);
        sports =findViewById(R.id.sports);
    }
    public void fetchData(String url){
        ArrayList<News> myList = new ArrayList<>();
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {


                    @SuppressLint("SuspiciousIndentation")

                    @Override
                    public void onResponse(JSONObject response) {
                        String urlToImage = "",urlToWebsite="",article_name = "",publishing_house ="",publishing_timing="",article_Content ="";
                        try {

                            JSONArray newsArray = response.getJSONArray("articles");
                            for(int i=0;i< newsArray.length();i++){
                                JSONObject jsonObject =newsArray.getJSONObject(i);
                                urlToImage = jsonObject.getString("urlToImage");
                                urlToWebsite = jsonObject.getString("url");
                                article_name = jsonObject.getString("title");
                                publishing_house = jsonObject.getString("author");
                                publishing_timing = jsonObject.getString("publishedAt");
                                article_Content = jsonObject.getString("content");

                                Log.d("TAG",urlToWebsite);
                                myList.add(new News(article_name,publishing_house,publishing_timing,urlToImage,urlToWebsite,article_Content));

                            }

                            CustomAdapter adapter = new CustomAdapter(MainActivity.this,myList);
                            listView.setAdapter(adapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                  News newsObj = myList.get(position);
                                    listViewListener(newsObj);
                                }
                            });

                            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                   News newsObj = myList.get(position);
                                   listViewLongListener(newsObj);
                                   return  true;
                                }
                            });

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }
     private void listViewListener(News newsObject){
        Intent i = new Intent(MainActivity.this, NewsActivity.class);
        i.putExtra("url",newsObject.getArticle_to_website());
        startActivity(i);
     }

     private void listViewLongListener(News newsObject){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,"Hey Did You Get The News \n"+newsObject.getArticle_content()+"\n\nYou Can Check Out The Full Article Here : \n"+newsObject.getArticle_to_website());
        Intent chooserTarget = Intent.createChooser(i,"You Can Share this News As : ...");
        startActivity(i);
     }
}