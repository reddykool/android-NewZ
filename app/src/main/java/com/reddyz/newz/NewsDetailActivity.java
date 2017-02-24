package com.reddyz.newz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap>{

    public static final String NEWS_DETAIL_URL = "NewsDetailUrl";
    public static final String NEWS_IMAGE_URL = "NewsImageUrl";
    public static final String NEWS_DETAIL_TITLE = "NewsDetailTitle";
    public static final String NEWS_DETAIL_TEXT = "NewsDetailText";


    private static final int NEWS_DETAIL_LOADER_ID = 2;

    ImageView mDetailImage;
    TextView mDetailText;
    TextView mDetailTitle;
    String mImageFetchUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_activity);

        //Get data passed to this intent from News lIst activity.
        final String detailUrl = getIntent().getStringExtra(NEWS_DETAIL_URL);
        mImageFetchUrl = getIntent().getStringExtra(NEWS_IMAGE_URL);
        String detailText = getIntent().getStringExtra(NEWS_DETAIL_TEXT);
        String detailTitle = getIntent().getStringExtra(NEWS_DETAIL_TITLE);

        //Set the data into corresponding views
        mDetailImage = (ImageView) findViewById(R.id.news_detail_image);
        mDetailText = (TextView) findViewById(R.id.news_detail_text);
        mDetailTitle = (TextView) findViewById(R.id.news_detail_title);

        mDetailTitle.setText(detailTitle);
        mDetailText.setText(detailText);


        //Set click listener on "Ream More" view to open browser intent with full url.
        TextView readMore = (TextView) findViewById(R.id.news_read_more);
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(detailUrl);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                if(browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
            }
        });

        // Initiate loader to fetch image form the url(in Bagkground thread) and display the same once
        // it is fetched and notified through loader callback {@ link onLoadFinished}
        getSupportLoaderManager().initLoader(NEWS_DETAIL_LOADER_ID, null, this);
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called by initLoader() to start a loader {@link NewsLoader}  background method.
     */
    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new NewsDetailLoader(this, mImageFetchUrl);
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called after finishing loader {@link NewsLoader} background method.
     * Clear the progressbar(if any) and show fetched news detail image through imageView
     */
    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap imageBitmap) {
        if(imageBitmap != null) {
            mDetailImage.setImageBitmap(imageBitmap);
        } else {
            Toast.makeText(this, "News image fetch FAILED!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called on loader reset anytime.
     */
    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }
}
