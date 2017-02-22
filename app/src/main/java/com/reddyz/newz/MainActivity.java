package com.reddyz.newz;

import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NEWS_LOADER_ID = 1;
    private static final String TECH_CHUNCH_NEWS_FETCH_URL_test = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String ESPN_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=espn-cric-info&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String GOOGLE_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String THE_HINDU_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String NEWS_API_KEY = "a88da1ce6d0e4b51a643f4ba415e6b98";
    private static final String TEST_WRONG_API_KEY_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=88da1ce6d0e4b51a643f4ba415e6b98a";
    private static final String TEST_WRONG_URL_NEWS_FETCH_URL = "https://newapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.main_activity_id, new NewsFragment()).commit();
    }
}
