package com.example.baskara.customlauncher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomeScreenAdapter extends FragmentStatePagerAdapter {

    public HomeScreenAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0) {
            return new AmazonFeed();
        } else {
            return new HomeScreen();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
