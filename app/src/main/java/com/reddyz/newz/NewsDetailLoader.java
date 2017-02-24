package com.reddyz.newz;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Reddyz on 24-02-2017.
 */

public class NewsDetailLoader extends AsyncTaskLoader<Bitmap> {

    //Url to fecth the image of corresponding news item
    String mImageUrl;

    public NewsDetailLoader(Context context, String imageUrl) {
        super(context);
        mImageUrl = imageUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Bitmap loadInBackground() {
        return QueryUtils.fetchNewsImage(mImageUrl);
    }
}
