package com.ermile.salamquran.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NavQuran_adapterTab extends FragmentPagerAdapter {

    private final List<Fragment> ftagment_list = new  ArrayList<>();
    private final List<String> title_list = new  ArrayList<>();

    public NavQuran_adapterTab(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int posetion) {
        return ftagment_list.get(posetion);
    }

    @Override
    public int getCount() {
        return title_list.size();
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title_list.get(position);
    }

    public void addFragment(Fragment fragment , String title){
        ftagment_list.add(fragment);
        title_list.add(title);
    }
}
