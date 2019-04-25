package com.ermile.salamquran;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ermile.salamquran.adapter.NavQuran_adapterTab;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nav_Quran extends Fragment {
    TabLayout tbLayout;
    ViewPager vPager;

    public Nav_Quran() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View nav_quran = inflater.inflate(R.layout.nav_quran, container, false);

        vPager = nav_quran.findViewById(R.id.view_pager);
        tbLayout = nav_quran.findViewById(R.id.tab_layout);

        NavQuran_adapterTab adapterTab = new NavQuran_adapterTab(getChildFragmentManager());

        adapterTab.addFragment(new NavQuran_Surah() , "Sureh");
        adapterTab.addFragment(new NavQuran_Juz() , "Juz");
        adapterTab.addFragment(new NavQuran_Bookmark() , "Bookmark");

        vPager.setAdapter(adapterTab);
        tbLayout.setupWithViewPager(vPager);





        return nav_quran;

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {
        Toast.makeText(getContext(), "Pause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }
}
