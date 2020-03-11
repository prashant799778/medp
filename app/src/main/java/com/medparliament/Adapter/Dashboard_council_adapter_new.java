

package com.medparliament.Adapter;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Activity.Up_Skill_Details_Activity;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Font;

import java.util.ArrayList;

public class Dashboard_council_adapter_new extends PagerAdapter {

    private Context context;
    ArrayList<up_skill_model> itemList;
    public Dashboard_council_adapter_new(Context context, ArrayList<up_skill_model> itemList, boolean flag) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Log.d("aaa","aaaaa");
        final up_skill_model pm = itemList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.dashboard_event_layout, collection, false);

        ImageView mainimg;
        Segow_UI_Font date,title,place;
        Open_Sans_Regular_Font msg;

        title=convertView.findViewById(R.id.title);
        date=convertView.findViewById(R.id.date);
        msg=convertView.findViewById(R.id.msg);
        place=convertView.findViewById(R.id.place);
        mainimg=convertView.findViewById(R.id.mainimg);

        Comman.setRectangleImage(context,mainimg,pm.getImagePath());
        msg.setText(Html.fromHtml(pm.getNewsDesc()));
        title.setText(pm.getNewsTitle());
        place.setText(pm.getSummary());
        date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(context)){
                Intent intent = new Intent(context, Up_Skill_Details_Activity.class);
                intent.putExtra("data",pm);
                context.startActivity(intent);} else {

                    Intent intent = new Intent(context, Login_Signup_Activity.class);


                    context.startActivity(intent);
                }
            }
        });

        collection.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final up_skill_model pm = itemList.get(position);
        return "";
    }


    public void updateList(ArrayList<up_skill_model> itemList){

        this.itemList=itemList;
        super.notifyDataSetChanged();
    }

}