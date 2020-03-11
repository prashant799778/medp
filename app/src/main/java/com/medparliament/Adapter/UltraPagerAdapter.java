

package com.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.medparliament.Activity.Up_Skill_Details_Activity;
import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.R;
import com.medparliament.Utility.Comman;

import java.util.ArrayList;

/**
 * Created by mikeafc on 15/11/26.
 */
public class UltraPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    ArrayList<up_skill_model>arrayList;
    Context context;

    public UltraPagerAdapter(boolean isMultiScr, ArrayList<up_skill_model>arrayList, Context context) {
        this.isMultiScr = isMultiScr;
        this.arrayList=arrayList;
        this.context=context;
        Comman.log("11111","InsideUltraAdapter");
    }
    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.skill_up_layout, null);
//        up_skill_model obj=arrayList.get(position);
//        container.addView(linearLayout);
//        ImageView imageView=container.findViewById(R.id.main_img);
//        Comman.setRectangleImage(context,imageView,obj.getImagePath());
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, Up_Skill_Details_Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
        return linearLayout;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}