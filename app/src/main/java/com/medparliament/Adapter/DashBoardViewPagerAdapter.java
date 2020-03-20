package com.medparliament.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.medparliament.Fragments.Event_Fragment;
import com.medparliament.Fragments.Gallery_Fragment;
import com.medparliament.Fragments.Market_Insights_Fragments;
import com.medparliament.Fragments.MedAchievers_Fragment;
import com.medparliament.Fragments.News_Fragment;
import com.medparliament.Fragments.Our_Partners_Fragments;
import com.medparliament.Fragments.Rejected_Fragment;
import com.medparliament.Fragments.Skill_Up_Fragment;
import com.medparliament.Fragments.Write_to_Us_Fragments;
import com.medparliament.Fragments.highlighted_initiative_Fragment;
import com.medparliament.R;

public   class DashBoardViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 7;
    private static  String title[];
    int id;

    public DashBoardViewPagerAdapter(FragmentManager fragmentManager, Context context,String id) {
        super(fragmentManager);
        this.id=Integer.parseInt(id);
        title=new String[]{context.getResources().getString(R.string.Market_Insights),context.getResources().getString(R.string.News),context.getResources().getString(R.string.MedAchieversTv),context.getResources().getString(R.string.highlighted_initiative),context.getResources().getString(R.string.Event),context.getResources().getString(R.string.Up_Skill_Academy),context.getResources().getString(R.string.Gallery)};

    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int y) {
        switch (y) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new Market_Insights_Fragments();
            case 1: // Fragment # 0 - This will show FirstFragment
                return new News_Fragment();
            case 2: // Fragment # 0 - This will show FirstFragment
                return new MedAchievers_Fragment();
            case 3: // Fragment # 0 - This will show FirstFragment
                return new highlighted_initiative_Fragment();
            case 4: // Fragment # 0 - This will show FirstFragment
                return new Event_Fragment();
            case 5: // Fragment # 0 - This will show FirstFragment
                return new Skill_Up_Fragment();
            case 6: // Fragment # 0 - This will show FirstFragment
                return new Gallery_Fragment();
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

