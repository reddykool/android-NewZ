package com.reddyz.newz;

/**
 * Created by Reddyz on 23-02-2017.
 */

public class NewsCategoryData {
    String mUrl;
    String mTitle;

    public NewsCategoryData(String url, String title) {
        this.mUrl = url;
        this.mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getTitle() {
        return mTitle;
    }
}
