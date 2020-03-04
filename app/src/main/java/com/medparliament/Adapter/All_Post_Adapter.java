package com.medparliament.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.medparliament.Fragments.Accpeted_Fragemnt;
import com.medparliament.Fragments.All_Post_Pending_Fragment;
import com.medparliament.Fragments.Rejected_Fragment;

public class All_Post_Adapter  extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 1;
    //    private static  String title[]= new String[]{"Pending","Accepted","Rejected"};
    private static  String title[]= new String[]{""};
    public All_Post_Adapter(FragmentManager fragmentManager, Context context) {
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
                return new All_Post_Pending_Fragment();
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
