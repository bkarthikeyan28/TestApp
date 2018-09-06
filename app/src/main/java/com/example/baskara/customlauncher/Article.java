package com.example.baskara.customlauncher;

import android.graphics.drawable.Drawable;

public class Article implements Data {
    private String title;
    private String thumbnail;
    private String description;
    private String asin;

    public Article() {
    }

    public Article(String title, String thumbnail, String description, String asin) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAsin() {
        return asin;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    @Override
    public int getType() {
        return 0;
    }
}
