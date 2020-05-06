package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageController extends FragmentPagerAdapter {
    int tabcounts;

    public PageController(@NonNull FragmentManager fm, int tabcounts) {
        super(fm);
        this.tabcounts = tabcounts;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Allmusic();
            case 1:
                return new Albums();
            case 2:
                return new Playlist();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabcounts;
    }
}
