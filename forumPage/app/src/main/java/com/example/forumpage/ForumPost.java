package com.example.forumpage;
public class ForumPost {
    private String username;
    private String content;
    private int images;

    // Constructor
    public ForumPost(String username, String content, int images) {
        this.username = username;
        this.content = content;
        this.images = images;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public int getImages() {
        return images;
    }
}


