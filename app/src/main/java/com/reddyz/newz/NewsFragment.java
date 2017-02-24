package com.reddyz.newz;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by reddy on 22-Feb-17.
 */

public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsData>> {
    public static final String ARG_URL_KEY = "NewsUrl";

    private static final int NEWS_LOADER_ID = 1;

    private String mUrl;
    NewsListAdapter mListAdapter;
    ProgressBar mProgressBar;
    TextView mEmptyTextView;
    boolean mIsConnected;

    public static NewsFragment newInstance(String url) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_URL_KEY, url);
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getArguments().getString(ARG_URL_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list,container, false);

        // Indicate this fragment will participate in menu activities.
        setHasOptionsMenu(true);

        //Find a references to xml views in the activity_main layout
        ListView newsListView = (ListView) rootView.findViewById(R.id.list_view);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.list_progress_bar);
        mEmptyTextView = (TextView) rootView.findViewById(R.id.list_empty_text);
        mEmptyTextView.setText(R.string.loading_state_text);

        // set empty state (text)view to show relevant info(text) when the list is empty.
        newsListView.setEmptyView(mEmptyTextView);

        // Create a new adapter{@link NewsListAdapter} with empty list of news
        mListAdapter = new NewsListAdapter(getActivity(), R.layout.news_list_item, new ArrayList<NewsData>());
        // Set the adapter to {@link ListView}, so the list can be populated in the user interface
        newsListView.setAdapter(mListAdapter);

        // handle clicks on list items to open the detailed URL(for now) via default browser.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsData currentItem = (NewsData) parent.getItemAtPosition(position);
                Intent detailIntent = new Intent(getActivity(), NewsDetailActivity.class);
                detailIntent.putExtra(NewsDetailActivity.NEWS_DETAIL_URL, currentItem.getUrl());
                detailIntent.putExtra(NewsDetailActivity.NEWS_IMAGE_URL, currentItem.getUrlToImage());
                detailIntent.putExtra(NewsDetailActivity.NEWS_DETAIL_TITLE, currentItem.getTitle());
                detailIntent.putExtra(NewsDetailActivity.NEWS_DETAIL_TEXT, currentItem.getDescription());
                startActivity(detailIntent);
            }
        });

        mIsConnected = isInternetAvailable();
        if(mIsConnected) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_state_text);
        }

        return rootView;
    }

    /**
     * Helper function to check if Internet connection is up and running.
     */
    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return (network !=null && network.isConnectedOrConnecting());
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called by initLoader() to start a loader {@link NewsLoader}  background method.
     */
    @Override
    public Loader<List<NewsData>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(getActivity(), mUrl);
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called after finishing loader {@link NewsLoader} background method.
     * Clear the progressbar and show fetched news data through listView adapter.
     */
    @Override
    public void onLoadFinished(Loader<List<NewsData>> loader, List<NewsData> data) {
        mProgressBar.setVisibility(View.GONE);

        mListAdapter.clear();
        if(data != null && !data.isEmpty()) {
            mListAdapter.addAll(data);
        } else {
            mEmptyTextView.setText(R.string.empty_state_text);
            Toast.makeText(getActivity(), "News data fetch FAILED!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called on loader reset anytime.
     * Clear the listView adapter. No data to be shown.
     */
    @Override
    public void onLoaderReset(Loader<List<NewsData>> loader) {
        mListAdapter.clear();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean menuItemHandled = false;
        switch (item.getItemId()) {
            case R.id.menu_refresh :
                if(mIsConnected) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Loader<List<NewsData>> loader = getLoaderManager().getLoader(NEWS_LOADER_ID);
                    NewsLoader newsLoader = (NewsLoader) loader;
                    newsLoader.setUrl(mUrl);
                    newsLoader.forceLoad();
                }
                menuItemHandled = true;
                break;

            case R.id.menu_settings :
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                menuItemHandled = true;
                break;

            case R.id.menu_about :
                Toast.makeText(getActivity(), " Reddyz - The Z Company \n Contact : reddykool@gmail.com", Toast.LENGTH_LONG).show();
                menuItemHandled = true;
                break;
        }
        return menuItemHandled;
    }
}
