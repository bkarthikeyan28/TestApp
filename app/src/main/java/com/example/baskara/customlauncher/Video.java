package com.example.baskara.customlauncher;

public class Video implements Data {

    private String title;
    private String imageUri;
    private String asin;

    public Video(String title, String imageUri, String asin) {
        this.title = title;
        this.imageUri = imageUri;
        this.asin = asin;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getAsin() {
        return asin;
    }

    @Override
    public int getType() {
        return 2;
    }
}
