package com.ermile.salamquran;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ermile.salamquran.adapter.NavQuran_adapterTab;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nav_Quran extends Fragment {
    TabLayout tabLayout_NavQuran;
    ViewPager viewPager_NavQuran;

    public Nav_Quran() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View nav_quran = inflater.inflate(R.layout.nav_quran, container, false);

        viewPager_NavQuran = nav_quran.findViewById(R.id.viewPager_NavQuran);
        tabLayout_NavQuran = nav_quran.findViewById(R.id.tabLayout_NavQuran);

        NavQuran_adapterTab adapterTab = new NavQuran_adapterTab(getChildFragmentManager());

        adapterTab.addFragment(new NavQuran_Surah() , "Sureh");
        adapterTab.addFragment(new NavQuran_Juz() , "Juz");
        adapterTab.addFragment(new NavQuran_Bookmark() , "Bookmark");

        viewPager_NavQuran.setAdapter(adapterTab);
        tabLayout_NavQuran.setupWithViewPager(viewPager_NavQuran);



        return nav_quran;

    }
}
