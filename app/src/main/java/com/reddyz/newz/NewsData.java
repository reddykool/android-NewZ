package com.reddyz.newz;

/**
 * Created by reddy on 20-Feb-17.
 */

public class NewsData {
    private String mAuthor;
    private String mTitle;
    private String mDateTime;
    private String mDescription;
    private String mUrl;
    private String mUrlToImage;

    public NewsData(String author, String title, String description, String url, String urlToImage, String dateTime) {
        this.mAuthor = author;
        this.mTitle = title;
        this.mDescription = description;
        this.mUrl = url;
        this.mUrlToImage = urlToImage;
        this.mDateTime = dateTime;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getUrlToImage() {
        return mUrlToImage;
    }
}
