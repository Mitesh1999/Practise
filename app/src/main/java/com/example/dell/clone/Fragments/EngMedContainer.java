package com.example.dell.clone.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.clone.R;
import com.example.dell.clone.drawer;

public class EngMedContainer extends Fragment {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    String[] TABS = {"Eng", "Med"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.eng_med_container,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.view_pager);

//        myViewPagerAdapter mAdapter = new drawer.myViewPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(new myViewPagerAdapter(getChildFragmentManager()));

        mTabLayout.setupWithViewPager(mViewPager);

    }


    class myViewPagerAdapter extends FragmentPagerAdapter {


        public myViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return TABS.length;
        }

        @Override
        public Fragment getItem(int i) {

            Fragment fragment = null;

            switch (i){

                case 0: {

                    fragment = new EngFrg();
                    break;
                }

                case 1: {
                    fragment = new MedFrg();
                    break;
                }

            }

            return fragment;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TABS[position];
        }
    }
}
