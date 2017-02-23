package com.reddyz.newz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by reddy on 22-Feb-17.
 */

public class NewsViewPagerAdapter extends FragmentPagerAdapter {
    List<NewsCategoryData> mCategories;

    public NewsViewPagerAdapter(FragmentManager fm, List<NewsCategoryData> categories) {
        super(fm);
        mCategories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        String url = mCategories.get(position).getUrl();
        Fragment currentFragment = NewsFragment.newInstance(url);
        return currentFragment;
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = mCategories.get(position).getTitle();
        return title;
    }
}
