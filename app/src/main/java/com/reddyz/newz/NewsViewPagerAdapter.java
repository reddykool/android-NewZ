package com.reddyz.newz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by reddy on 22-Feb-17.
 */

public class NewsViewPagerAdapter extends FragmentPagerAdapter {
    List<String> mUrls;

    public NewsViewPagerAdapter(FragmentManager fm, List<String> urls) {
        super(fm);
        mUrls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment currentFragment = new NewsFragment();
        return currentFragment;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //extract "source" from url(source=espn-cric-info&)
        String urlString = mUrls.get(position);
        int startIndex = urlString.indexOf("source=");
        int endIndex = urlString.indexOf("&");
        String name = urlString.substring(startIndex, endIndex);
        return name;
    }
}
