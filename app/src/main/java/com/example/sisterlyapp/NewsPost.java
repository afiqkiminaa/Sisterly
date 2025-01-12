package com.example.sisterlyapp;

public class NewsPost {
    private String publisher;
    private String title;
    private String newscontent;
    private int images;

    public NewsPost(String publisher, String title, String newscontent, int images) {
        this.publisher = publisher;
        this.title = title;
        this.newscontent = newscontent;
        this.images=images;

    }

    public NewsPost(String publisher, String title, int image) {
    }

    public static NewsPost get(int position) {
        return NewsPost.get(position);
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return newscontent;
    }

    public void setContent(String content) {
        this.newscontent = content;
    }
    public int getImages() {
        return images;
    }
}
