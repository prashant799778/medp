package com.example.medparliament.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.medparliament.Fragments.Accpeted_Fragemnt;
import com.example.medparliament.Fragments.Pending_Fragment;
import com.example.medparliament.Fragments.Rejected_Fragment;


public   class Post_Page_Adapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    private static  String title[]= new String[]{"Pending","Accepted","Rejected"};

    public Post_Page_Adapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
//        title=new String[]{context.getResources().getString(R.string.Voting_Campaigns),context.getResources().getString(R.string.Rewards_Campaigns),context.getResources().getString(R.string.Fan_Launchpad),context.getResources().getString(R.string.Favorites)};

    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new Pending_Fragment();
            case 1: // Fragment # 0 - This will show FirstFragment
                return new Accpeted_Fragemnt();
            case 2: // Fragment # 0 - This will show FirstFragment
                return new Rejected_Fragment();

            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}

