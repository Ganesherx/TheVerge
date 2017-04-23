package com.example.ganesh.theverge;


public class TheVergePojo {

    private String author;
    private String title;
    private String urlToimage;
    private String description;
    private String url;

    public TheVergePojo(String urlToimage, String author, String title, String description, String url) {
        this.urlToimage = urlToimage;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrlToimage() {
        return urlToimage;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }
}
