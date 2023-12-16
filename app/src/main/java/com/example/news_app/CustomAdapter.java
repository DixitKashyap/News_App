package com.example.news_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<News> list;
    public CustomAdapter(Context context,ArrayList<News>list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View result = LayoutInflater.from(context).inflate(R.layout.list_view_item,parent,false);
        ImageView article_image = result.findViewById(R.id.news_image);
        Glide.with(context).load(list.get(position).getPublishing_image()).into(article_image);

        TextView artilcle_name = result.findViewById(R.id.news_article_tile);
        artilcle_name.setText(list.get(position).getArticle_name());

        TextView publishing_house = result.findViewById(R.id.publishing_house);
        publishing_house.setText(list.get(position).getPublishing_house());;


        TextView publishing_time = result.findViewById(R.id.publishing_date);
        String time = list.get(position).getPublishing_time();
        String forMattedDate = time.substring(0,10);

        publishing_time.setText(forMattedDate);
        return result;
    }
}
