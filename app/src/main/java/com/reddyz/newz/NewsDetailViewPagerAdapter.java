package com.reddyz.newz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by reddy on 25-Feb-17.
 */

public class NewsDetailViewPagerAdapter extends FragmentPagerAdapter {
    List<NewsData> mNewsList;

    public NewsDetailViewPagerAdapter(FragmentManager fm, List<NewsData> newsList) {
        super(fm);
        mNewsList = newsList;
    }

    @Override
    public Fragment getItem(int position) {
        NewsData currentItem = mNewsList.get(position);
        String detailUrl = currentItem.getUrl();
        String imageUrl = currentItem.getUrlToImage();
        String title = currentItem.getTitle();
        String desciption = currentItem.getDescription();
        Fragment currentFragment = NewsDetailFragment.newInstance( detailUrl, imageUrl, title, desciption);
        return currentFragment;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }
}
