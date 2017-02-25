package com.reddyz.newz;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class NewsDetailActivity extends AppCompatActivity{

    public static final String NEWS_LIST = "newsList";
    public static final String ITEM_POSITION = "itemPosition";
    public static final int ITEM_POSITION_DEFAULT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_activity);

        // Get the position and newsList array parceled from intent caller(NewsFragment - when list item is clicked)
        List<NewsData> newsList = getIntent().getParcelableArrayListExtra(NEWS_LIST);
        int position = getIntent().getIntExtra(ITEM_POSITION, ITEM_POSITION_DEFAULT);

        //Set the view pager with news list detail adapter and correct position as per click to be shown in detail fragment page
        ViewPager viewPager = (ViewPager) findViewById(R.id.detail_view_pager);
        NewsDetailViewPagerAdapter pagerAdapter = new NewsDetailViewPagerAdapter(getSupportFragmentManager(), newsList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(position);
    }

}
