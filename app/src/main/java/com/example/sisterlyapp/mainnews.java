package com.example.sisterlyapp;

import java.util.ArrayList;

public class mainnews {
    private String status;
    private String totalResult;
    private ArrayList<newscreator> articles;

    public mainnews(String status, String totalResult, ArrayList<newscreator> articles) {
        this.status = status;
        this.totalResult = totalResult;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }

    public ArrayList<newscreator> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<newscreator> articles) {
        this.articles = articles;
    }
}
