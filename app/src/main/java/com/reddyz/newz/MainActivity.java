package com.reddyz.newz;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsData>>{

    private static final int NEWS_LOADER_ID = 1;
    private static final String TECH_CHUNCH_NEWS_FETCH_URL_test = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String ESPN_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=espn-cric-info&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String GOOGLE_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String THE_HINDU_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String NEWS_API_KEY = "a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String TEST_WRONG_API_KEY_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=88da1ce6d0e4b51a643f4ba415e6b98a";
    private static final String TEST_WRONG_URL_NEWS_FETCH_URL = "https://newapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";


    NewsListAdapter mListAdapter;
    ProgressBar mProgressBar;
    TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find a references to xml views in the activity_main layout
        ListView newsListView = (ListView) findViewById(R.id.main_list_view);
        mProgressBar = (ProgressBar) findViewById(R.id.main_list_progress_bar);
        mEmptyTextView = (TextView) findViewById(R.id.main_list_empty_text);
        mEmptyTextView.setText(R.string.loading_state_text);

        // set empty state (text)view to show relevant info(text) when the list is empty.
        newsListView.setEmptyView(mEmptyTextView);

        // Create a new adapter{@link NewsListAdapter} with empty list of news
        mListAdapter = new NewsListAdapter(this, R.layout.news_list_item, new ArrayList<NewsData>());
        // Set the adapter to {@link ListView}, so the list can be populated in the user interface
        newsListView.setAdapter(mListAdapter);

        // handle clicks on list items to open the detailed URL(for now) via default browser.
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsData currentItem = (NewsData) parent.getItemAtPosition(position);
                Uri detailUri = Uri.parse(currentItem.getUrl());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, detailUri);
                if(browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
            }
        });

        boolean isConnected = isInternetAvailable();
        if(isConnected) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mEmptyTextView.setText(R.string.no_internet_state_text);
        }
    }

    /**
     * Helper function to check if Internet connection is up and running.
     */
    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return (network !=null && network.isConnectedOrConnecting());
    }

    /**
     * LoaderManager.LoaderCallbacks function.
     * Is called by initLoader() to start a loader {@link NewsLoader}  background method.
     */
    @Override
    public Loader<List<NewsData>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, THE_HINDU_NEWS_FETCH_URL);
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
            Toast.makeText(this, "News data fetch FAILED!", Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean menuItemHandled = false;
        switch (item.getItemId()) {
            case R.id.menu_refresh :
                mProgressBar.setVisibility(View.VISIBLE);
                Loader<List<NewsData>> loader = getLoaderManager().getLoader(NEWS_LOADER_ID);
                loader.forceLoad();
                menuItemHandled = true;
                break;

            case R.id.menu_settings :
                menuItemHandled = true;
                break;

            case R.id.menu_about :
                Toast.makeText(this, " Reddyz - The Z Company \n Contact : reddykool@gmail.com", Toast.LENGTH_LONG).show();
                menuItemHandled = true;
                break;
        }
        return menuItemHandled;
    }
}
