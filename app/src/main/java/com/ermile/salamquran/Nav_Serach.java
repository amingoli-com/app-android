package com.ermile.salamquran;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nav_Serach extends Fragment {

    Toolbar toolbars;

    public Nav_Serach() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View nav_search = inflater.inflate(R.layout.nav_serach, container, false);

        toolbars = nav_search.findViewById(R.id.toolbars);



        return nav_search;
    }

}
