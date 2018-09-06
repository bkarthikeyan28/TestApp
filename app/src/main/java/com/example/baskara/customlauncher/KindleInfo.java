package com.example.baskara.customlauncher;

public class KindleInfo implements Data {

    private String bookCover;
    private int progress;
    private String asin;

    public KindleInfo(String bookCover, int progress, String asin) {
        this.bookCover = bookCover;
        this.progress = progress;
        this.asin = asin;
    }

    public String getBookCover() {
        return bookCover;
    }

    public int getProgress() {
        return progress;
    }

    public String getAsin() {
        return asin;
    }

    @Override
    public int getType() {
        return 1;
    }
}
