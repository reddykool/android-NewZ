package com.reddyz.newz;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TECH_CHUNCH_NEWS_FETCH_URL = "https://newsapi.org/v1/articles?source=techcrunch&apiKey=a88da1ce6d0e4b51a643f4ba415e6b98";
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

        List<NewsCategoryData> categories = new ArrayList<>();
        categories.add(new NewsCategoryData(THE_HINDU_NEWS_FETCH_URL, getString(R.string.news_category_india)));
        categories.add(new NewsCategoryData(GOOGLE_NEWS_FETCH_URL, getString(R.string.news_category_world)));
        categories.add(new NewsCategoryData(ESPN_NEWS_FETCH_URL, getString(R.string.news_category_sports)));
        categories.add(new NewsCategoryData(TECH_CHUNCH_NEWS_FETCH_URL, getString(R.string.news_category_technology)));

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        NewsViewPagerAdapter pagerAdapter = new NewsViewPagerAdapter(getSupportFragmentManager(), categories);
        viewPager.setAdapter(pagerAdapter);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        */
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
