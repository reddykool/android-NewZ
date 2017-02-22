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
}
