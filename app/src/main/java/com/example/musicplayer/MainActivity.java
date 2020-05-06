package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout mtab;
    TabItem curr,all,play;
    ViewPager mview;
    PageController pageController;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtab=(TabLayout)findViewById(R.id.tablayout);
        curr=(TabItem)findViewById(R.id.currentmusic);
        all=(TabItem)findViewById(R.id.allmusic);
        play=(TabItem)findViewById(R.id.playlist);
        mview=(ViewPager)findViewById(R.id.viewpager);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Player");
        pageController=new PageController(getSupportFragmentManager(),mtab.getTabCount());
        mview.setAdapter(pageController);
        mtab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mview.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mview.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mtab));


    }


}
