package com.reddyz.newz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by reddy on 25-Feb-17.
 */

public class NewsDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Bitmap> {
    private static final int NEWS_DETAIL_LOADER_ID = 2;
    private static final String NEWS_DETAIL_URL = "NewsDetailUrl";
    private static final String NEWS_IMAGE_URL = "NewsImageUrl";
    private static final String NEWS_DETAIL_TITLE = "NewsDetailTitle";
    private static final String NEWS_DETAIL_TEXT = "NewsDetailText";

    ImageView mDetailImageView;

    String mImageFetchUrl;
    String mDetailUrl;
    String mDetailTitle;
    String mDetailText;

    //Static method to allow other activities to initiate this fragment class with arguments.
    public static NewsDetailFragment newInstance(String detailUrl, String imageUrl, String title, String description ) {
        Bundle arguments = new Bundle();
        arguments.putString(NEWS_DETAIL_URL, detailUrl);
        arguments.putString(NEWS_IMAGE_URL, imageUrl);
        arguments.putString(NEWS_DETAIL_TITLE, title);
        arguments.putString(NEWS_DETAIL_TEXT, description);

        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    //default non-argument constructor is a MUST for fragment framework to work properly
    public NewsDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get the relevant data from earlier passed arguments
        mDetailUrl = getArguments().getString(NEWS_DETAIL_URL);
        mImageFetchUrl = getArguments().getString(NEWS_IMAGE_URL);
        mDetailTitle = getArguments().getString(NEWS_DETAIL_TITLE);
        mDetailText = getArguments().getString(NEWS_DETAIL_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_detail_fragment, container, false);

        //Set the data into corresponding views
        mDetailImageView = (ImageView) rootView.findViewById(R.id.news_detail_image);
        TextView detailTextView = (TextView) rootView.findViewById(R.id.news_detail_text);
        TextView detailTitleView = (TextView) rootView.findViewById(R.id.news_detail_title);

        detailTitleView.setText(mDetailTitle);
        detailTextView.setText(mDetailText);

        //Set click listener on "Ream More" view to open browser intent with full url.
        TextView readMore = (TextView) rootView.findViewById(R.id.news_read_more);
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(mDetailUrl);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
                if(browserIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
            }
        });

        // Initiate loader to fetch image form the url(in Bagkground thread) and display the same once
        // it is fetched and notified through loader callback {@ link onLoadFinished}
        getLoaderManager().initLoader(NEWS_DETAIL_LOADER_ID, null, this);

        return rootView;
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called by initLoader() to start a loader {@link NewsLoader}  background method.
     */
    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        return new NewsDetailLoader(getActivity(), mImageFetchUrl);
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called after finishing loader {@link NewsLoader} background method.
     * Clear the progressbar(if any) and show fetched news detail image through imageView
     */
    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap imageBitmap) {
        if(imageBitmap != null) {
            mDetailImageView.setImageBitmap(imageBitmap);
        } else {
            Toast.makeText(getActivity(), "News image fetch FAILED!", Toast.LENGTH_LONG).show();
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
