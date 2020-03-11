package com.medparliament.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.R;
import com.medparliament.Utility.Comman;

import java.util.ArrayList;

public class UltraViewPagerAdapterNew extends PagerAdapter {
    private boolean isMultiScr;
    private   ArrayList<up_skill_model> arrayList;
    private  Context context;
    private int size=5;

    public UltraViewPagerAdapterNew(boolean isMultiScr, ArrayList<up_skill_model> arrayList, Context context) {
        this.isMultiScr = isMultiScr;
        this.arrayList=arrayList;
         this.context=context;
         this.size=arrayList.size()+2;
    }

    @Override
    public int getCount() {
        Comman.log("size data",size+"");
        return  this.size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.skill_up_layout, null);
        //new LinearLayout(container.getContext());
//        TextView textView = (TextView) linearLayout.findViewById(R.id.pager_textview);
//        textView.setText(position + "");
        linearLayout.setId(R.id.item_id);

        container.addView(linearLayout);
//        linearLayout.getLayoutParams().width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, container.getContext().getResources().getDisplayMetrics());
//        linearLayout.getLayoutParams().height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, container.getContext().getResources().getDisplayMetrics());
        return linearLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}