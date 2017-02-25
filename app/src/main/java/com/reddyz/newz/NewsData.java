package com.reddyz.newz;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by reddy on 20-Feb-17.
 */

public class NewsData implements Parcelable {
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


    // Implementing Parcelable interface

    protected NewsData(Parcel in) {
        mAuthor = in.readString();
        mTitle = in.readString();
        mDateTime = in.readString();
        mDescription = in.readString();
        mUrl = in.readString();
        mUrlToImage = in.readString();
    }

    public static final Creator<NewsData> CREATOR = new Creator<NewsData>() {
        @Override
        public NewsData createFromParcel(Parcel in) {
            return new NewsData(in);
        }

        @Override
        public NewsData[] newArray(int size) {
            return new NewsData[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mTitle);
        dest.writeString(mDateTime);
        dest.writeString(mDescription);
        dest.writeString(mUrl);
        dest.writeString(mUrlToImage);
    }
}
