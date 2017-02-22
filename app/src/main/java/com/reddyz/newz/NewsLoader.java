package com.reddyz.newz;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by reddy on 20-Feb-17.
 */
public class NewsLoader extends AsyncTaskLoader<List<NewsData>> {

    String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsData> loadInBackground() {
        List<NewsData> newsList = QueryUtils.fetchNews(mUrl);
        return newsList;
    }
}
