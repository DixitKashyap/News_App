package com.example.news_app;

public class News {
    String article_name;
    String publishing_house;
    String publishing_time;
    String publishing_image;
    String article_to_website;
    String article_content;

    public News(String article_name, String publishing_house, String publishing_time, String publishing_image,String article_to_website,String article_content) {
        this.article_name = article_name;
        this.publishing_house = publishing_house;
        this.publishing_time = publishing_time;
        this.publishing_image = publishing_image;
        this.article_to_website = article_to_website;
        this.article_content = article_content;
    }

    public String getArticle_name() {
        return article_name;
    }

    public String getPublishing_house() {
        return publishing_house;
    }

    public String getPublishing_time() {
        return publishing_time;
    }

    public String getPublishing_image() {
        return publishing_image;
    }
    public String getArticle_to_website(){return article_to_website;}
    public String getArticle_content(){return  article_content;}
}
